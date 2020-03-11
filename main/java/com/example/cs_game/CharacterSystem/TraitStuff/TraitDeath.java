package com.example.cs_game.CharacterSystem.TraitStuff;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPartManager;

/**
 * A trait affecting the death conditions of a character
 */
public class TraitDeath extends Trait {

    private TraitDeathFunctionInterface traitFunction;

    /**
     * constructor
     */
    public TraitDeath(int power, TraitDeathFunctionInterface func, String name) {
        super(power, name);
        traitFunction = func;
    }

    /**
     * the result of this trait (determining whether the character should die because of this trait
     * or not
     */
    boolean isDead(BodyPartManager partManager) {
        return traitFunction.apply(partManager, getPower());
    }

    /**private boolean updateDeath(BodyPartManager body) {
     return traitFunction.apply(body, getPower());
     }*/


}
