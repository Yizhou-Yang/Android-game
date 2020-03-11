package com.example.cs_game.CombatGame.CombatSystem.CombatPresenter;


import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CombatGame.CharacterSystem.CharacterStuff.Monster;
import com.example.cs_game.CombatGame.CharacterSystem.CharacterStuff.Player;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Consumable;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Weapon;
import com.example.cs_game.CombatGame.CombatSystem.CombatModel.CombatManager;
import com.example.cs_game.CombatGame.CombatSystem.CombatModel.ConsumableAction;
import com.example.cs_game.CombatGame.CombatSystem.CombatModel.IAction;
import com.example.cs_game.CombatGame.CombatSystem.CombatModel.Turn;
import com.example.cs_game.CombatGame.CombatSystem.CombatView.CombatView;
import com.example.cs_game.DatabaseSystem.LocalSLSystem.GameManager;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseInterface;
import com.example.cs_game.CombatGame.GenerationSystem.BasicGenerator;
import com.example.cs_game.CombatGame.GenerationSystem.StartupGenerator;
import com.example.cs_game.CombatGame.CombatSystem.CombatModel.AttackAction;
import com.example.cs_game.Utilities.TimeManager;

import java.util.Arrays;

/**
 * Presenter part of MVP for Combat.
 */
public class CombatPresenter {
    private StartupGenerator startupGenerator = BasicGenerator.buildGenerator();
    private Monster monster = startupGenerator.generateMonster();

    private CombatManager combatManager;

    private TimeManager timeManager = new TimeManager();

    // These variables keep track of which consumable, weapon, or body part the player chose
    private Consumable consumable;
    private Weapon weapon;
    private BodyPart bodyPart;

    private CombatView combatView;

    //this one is public FOR NOW, will make this private given time.
    public GameManager gameManager;

    private Boolean playerAttackBool = false;
    private IAction playerAction;

    // Measured by adding all damage done by player to enemy
    private int combatScore = 0;

    // Hidden action using consumable, attacking, attacking again, then consumable. "CAAC"
    private String hiddenActionTracker = "";

    private UserDatabaseInterface dbHelper;
    private User user;

    public CombatPresenter(CombatView combatView, UserDatabaseInterface dbHelper, User user) {
        if (gameManager == null) {
            gameManager = GameManager.getGameManager();
            gameManager.setGameState(user,startupGenerator.generatePlayer());
        }

        combatManager = CombatManager.getCombatManager(monster,GameManager.getPlayer());

        this.combatView = combatView;
        this.dbHelper = dbHelper;
        this.user = user;
    }

    public void onCreate() {
        combatView.setButtons();
        combatView.setHealthBars(GameManager.getPlayer().getMaxHealth(),
                GameManager.getPlayer().getHealth(), monster.getMaxHealth(), monster.getHealth());

        int choice = user.getAudioCustomization();
        combatView.setMusic(choice);

        timeManager.beginCount();
    }

    public void onDestroy() {
        dbHelper.close();
        user = null;
    }

    /**
     * Construct next player action depending on what playerAttackBool is (what user chose)
     */
    private void constructPlayerAction() {
        if (playerAttackBool) {
            playerAction = new AttackAction(GameManager.getPlayer(), monster, weapon, bodyPart);
            hiddenActionTracker += "A";
        } else {
            playerAction = new ConsumableAction(GameManager.getPlayer(), bodyPart, consumable);
            hiddenActionTracker += "C";
        }
    }

    /**
     * Play the turn with the given action; then check if combat has ended and proceed accordingly
     *
     * @param action the action the player is taking
     */
    private void takeTurn(IAction action) {
        //playing turns
        Turn.playTurn(action);
        combatView.updateHealths(GameManager.getPlayer().getHealth(), monster.getHealth());

        //checking if finished
        combatManager.checkIfDead();
        if (hasCombatEnded()) {
            onCombatEnd();
        }
    }

    /**
     * Get all data (time spent, exp earned, whether battle was won, and corresponding message)
     * and proceed to next level with given data
     */
    private void onCombatEnd() {
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
        toNextLevel(message, timeSpent, exp, won);
    }

    private void runTurns() {
        String combatLog = "";

        // To calculate damage done to the monster after player's action
        // If not an attack action, should be 0 damage.
        int previousHealth = monster.getHealth();
        takeTurn(playerAction);
        int damageDone = previousHealth - monster.getHealth();
        combatScore += damageDone;

        combatLog += playerAction.toLog();
        combatLog += "\n";

        IAction aiAction = combatManager.getAiAction();
        takeTurn(aiAction);
        combatLog += aiAction.toLog();

        updateCombatLog(combatLog);
        checkHiddenActionCompletion();
    }

    /**
     * Check whether user has performed the hidden action, and act appropriately.
     */
    private void checkHiddenActionCompletion() {
        if (hiddenActionTracker.contains("CAAC")) {
            combatScore = monster.getMaxHealth() * 10;
            int timeSpent = timeManager.getDifference();
            int exp = monster.getTotalExp() * 5;
            updateUser(timeSpent, exp);
            toNextLevel("Hidden Action Detected!", timeSpent, exp, true);
        }
        if (hiddenActionTracker.length() > "CAAC".length() * 2) {
            hiddenActionTracker = "";
        }
    }

    /**
     * Update the combat log with given combatLog
     * @param combatLog the new combat log to be updated with
     */
    private void updateCombatLog(String combatLog) {
        combatView.updateCombatLog(combatLog);
    }


    private boolean hasCombatEnded() {
        return combatManager.getCombatStatus() == CombatManager.MONSTER_DEAD ||
                combatManager.getCombatStatus() == CombatManager.PLAYER_DEAD;
    }

    private void toNextLevel(String message, int timeSpent, int exp, boolean won) {
        updateUser(timeSpent, exp);
        combatView.toNextLevelDialog("To Next Level", message, timeSpent,
                exp, won, combatScore);
    }

    /**
     * Update user information in database
     *
     * @param timeSpent the amount of time spent in combat
     * @param exp       the amount of experience earned from combat
     */
    private void updateUser(int timeSpent, int exp) {
        user.addSecondsSpent(timeSpent);
        user.addExperience(exp);
        user.setLevel(3);
        dbHelper.updateUser(user);
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
     *
     * @param weapon the weapon the player chose to attack with
     */
    public void onItemClicked(Weapon weapon) {
        if (weapon != null) {
            this.weapon = weapon;
            playerAttackBool = true;

            combatView.showBodyPartsList(monster.getAliveBodyParts());
        }
    }

    /**
     * Upon consumable clicked, set playerAttackBool to false and show player's body parts
     *
     * @param consumable the consumable that player chose to use
     */
    public void onItemClicked(Consumable consumable) {
        this.consumable = consumable;
        playerAttackBool = false;

        combatView.showBodyPartsList(GameManager.getPlayer().getAliveBodyParts());
    }

    /**
     * If body part is clicked, an action will be constructed and a turn will be executed
     * Precondition: this.consumable != null or this.weapon != null
     *
     * @param bodyPart BodyPart object that was clicked to act upon (either weapon or consumable)
     */
    public void onItemClicked(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
        constructPlayerAction();

        //this will actually run the player and monster turn (in that order), every other
        // call is just for constructing stuff.
        runTurns();
    }


    /**
     * Show list of weapons when attack button is clicked
     */
    public void onAttackButtonClicked() {
        combatView.showWeaponList(Arrays.asList(GameManager.getPlayer().getEquippedWeapons()));
    }

    /**
     * Show list of consumables when consumable button is clicked
     */
    public void onConsumableButtonClicked() {
        combatView.showConsumableList(GameManager.getPlayer().getConsumables());
    }


}
