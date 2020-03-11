package com.example.cs_game.CombatSystem;


import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CharacterSystem.CharacterStuff.Monster;
import com.example.cs_game.CharacterSystem.Item.Consumable;
import com.example.cs_game.CharacterSystem.Item.Weapon;
import com.example.cs_game.CombatSystemManager.GameManager;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.GenerationSystem.BasicGenerator;
import com.example.cs_game.GenerationSystem.StartupGenerator;
import com.example.cs_game.TimeManager;

import java.util.Arrays;

public class CombatPresenter {
    private StartupGenerator startupGenerator = BasicGenerator.buildGenerator();
    private Monster monster = startupGenerator.generateMonster();

    private CombatManager combatManager;

    private TimeManager timeManager = new TimeManager();

    private Consumable consumable;
    private Weapon weapon;
    private BodyPart bodyPart;

    private CombatView combatView;

    private Boolean playerAttackBool = false;
    private IAction playerAction;

    private User user;

    CombatPresenter(CombatView combatView, User user) {
        if (GameManager.getPlayer() == null) {
            GameManager.setPlayer(startupGenerator.generatePlayer());
        }

        combatManager = CombatManager.getCombatManager(monster, GameManager.getPlayer());

        this.combatView = combatView;
        this.user = user;
    }

    void onCreate() {
        combatView.setButtons();
        combatView.setHealthBars(GameManager.getPlayer().getMaxHealth(),
                GameManager.getPlayer().getHealth(), monster.getMaxHealth(), monster.getHealth());

        int choice = user.getAudioCustomization();
        combatView.setMusic(choice);

        timeManager.beginCount();
    }

    /**
     * Construct next player action depending on what playerAttackBool is (what user chose)
     */
    private void constructPlayerAction() {
        if (playerAttackBool) {
            playerAction = new AttackAction(GameManager.getPlayer(), monster, weapon, bodyPart);
        } else {
            playerAction = new ConsumableAction(GameManager.getPlayer(), bodyPart, consumable);
        }
    }

    /**
     * Play the turn with the given action; then check if combat has ended and proceed accordingly
     * @param action the action the player is taking
     */
    private void takeTurn(IAction action) {
        //playing turns
        Turn.playTurn(action);
        combatView.updateHealths(GameManager.getPlayer().getHealth(), monster.getHealth());

        //checking if finished
        combatManager.checkIfDead();
        if (hasCombatEnded()) onEnd();


    }

    private void runTurns() {
        String combatLog = "";

        takeTurn(playerAction);
        combatLog += playerAction.toLog();
        combatLog += "\n";

        IAction aiAction = combatManager.getAiAction();
        takeTurn(aiAction);
        combatLog += aiAction.toLog();

        updateCombatLog(combatLog);
    }

    private void updateCombatLog(String combatLog) {
        combatView.updateCombatLog(combatLog);
    }


    private boolean hasCombatEnded() {
        return combatManager.getCombatStatus() == CombatManager.MONSTER_DEAD ||
                combatManager.getCombatStatus() == CombatManager.PLAYER_DEAD;
    }

    private void onEnd() {
        int timeSpent = timeManager.getDifference();
        int exp;
        boolean won;
        String message;
        if (combatManager.getCombatStatus() == CombatManager.MONSTER_DEAD) {
            CombatManager.setCombatManager(null);
            exp = monster.getTotalExp();
            won = true;
            message = "You won the battle!";
        } else {
            exp = 0;
            won = false;
            message = "You lost the battle";
        }
        combatView.toNextLevel(message, timeSpent, exp, won);
    }

    public void setBodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
        constructPlayerAction();

        //this will actually run the player and monster turn (in that order), every other
        // call is just for constructing stuff.
        runTurns();
    }

    /**
     * Upon weapon clicked, set playerAttackBool to true and show monster's body parts
     * @param weapon the weapon the player chose to attack with
     */
    void onItemClicked(Weapon weapon) {
        if (weapon != null) {
            this.weapon = weapon;
            playerAttackBool = true;

            combatView.showBodyPartsList(monster.getAliveBodyParts());
        }
    }

    /**
     * Upon consumable clicked, set playerAttackBool to false and show player's body parts
     * @param consumable the consumable that player chose to use
     */
    void onItemClicked(Consumable consumable) {
        this.consumable = consumable;
        playerAttackBool = false;

        combatView.showBodyPartsList(GameManager.getPlayer().getAliveBodyParts());
    }

    /**
     * If body part is clicked, an action will be constructed and a turn will be executed
     * Precondition: this.consumable != null or this.weapon != null
     * @param bodyPart BodyPart object that was clicked to act upon (either weapon or consumable)
     */
    void onItemClicked(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
        constructPlayerAction();

        //this will actually run the player and monster turn (in that order), every other
        // call is just for constructing stuff.
        runTurns();
    }

    /**
     * Show list of weapons when attack button is clicked
     */
    void onAttackButtonClicked() {
        combatView.showWeaponList(Arrays.asList(GameManager.getPlayer().getEquippedWeapons()));
    }

    /**
     * Show list of consumables when consumable button is clicked
     */
    void onConsumableButtonClicked() {
        combatView.showConsumableList(GameManager.getPlayer().getConsumables());
    }


}
