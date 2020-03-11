package com.example.cs_game.CombatGame.CharacterSystem.TraitStuff;

import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPart;

/**
 * the trait function for body traits
 */
public interface TraitBodyFunctionInterface extends TraitFunctionInterface {

    /**
     * the expectation of a function affecting a body part
     */
    void apply(BodyPart bodyPart, int power);
}
