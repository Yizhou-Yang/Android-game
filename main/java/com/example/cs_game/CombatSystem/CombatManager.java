package com.example.cs_game.CombatSystem;

import com.example.cs_game.CharacterSystem.CharacterStuff.Monster;
import com.example.cs_game.CharacterSystem.CharacterStuff.Player;

class CombatManager {
    private static CombatManager combatManager;

    static final int PLAYER_DEAD = -1;
    static final int MONSTER_DEAD = 1;
    private int status = 0;
    private Monster monster;
    private Player player;

    //constructor
    private CombatManager(Monster monster, Player player) {
        this.player = player;
        this.monster = monster;
    }

    int getCombatStatus() {
        return status;
    }

    // get the singleton instance of combatManager
    static CombatManager getCombatManager(Monster monster, Player player) {
        if (combatManager == null)
            combatManager = new CombatManager(monster, player);
        return combatManager;
    }




    // two methods to see if they are dead, used for Death implementation
    private void playerDead() {
        status = PLAYER_DEAD;
    }

    private void monsterDead() {
        status = MONSTER_DEAD;
    }
    void checkIfDead(){
        if (player.isDead()){
            playerDead();
        }
        if(monster.isDead()){
            monsterDead();
        }
    }

    IAction getAiAction() {
        AI ai = new RandomAI();
        return new AttackAction(monster, player,
                ai.getWeapon(monster), ai.getBodyPart(player));

    }




    //resets the manager for Death
    static void setCombatManager(CombatManager newCombatManager) {
        combatManager = newCombatManager;
    }


}
