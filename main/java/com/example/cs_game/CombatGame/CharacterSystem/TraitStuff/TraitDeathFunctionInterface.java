package com.example.cs_game.CombatGame.CharacterSystem.TraitStuff;


import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPartManager;

/**
 * the trait function for creating/affecting the death conditions of a character
 */
public interface TraitDeathFunctionInterface extends TraitFunctionInterface {

    /**
     * the expectation of a function using the body part manager to determine
     * a death condition
     */
    boolean apply(BodyPartManager partManager, int power);
}
