package com.example.cs_game.CombatGame.CharacterSystem.Death;

import com.example.cs_game.CombatGame.CharacterSystem.Observer.CharacterWatcher;


/**
 * people die when they are killed
 * this class is meant to handle all interactions relating to a character dying in combat
 */
public class DeathManager {

    private CharacterWatcher curObserver;

    /**
     * adds observer to this class
     */
    public DeathManager(CharacterWatcher observer) {
        curObserver = observer;
    }

    /**
     * function to check if the character is dead
     */
    public boolean checkIfDead() {
        System.out.println(curObserver.updateDead());
        return curObserver.updateDead();
    }
}
