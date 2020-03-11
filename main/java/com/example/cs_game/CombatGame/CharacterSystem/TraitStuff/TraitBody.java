package com.example.cs_game.CombatGame.CharacterSystem.TraitStuff;

import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPart;

import java.util.ArrayList;
import java.util.List;

/**
 * A trait specifically needing input of the body of the player
 */
public class TraitBody extends Trait {


    private TraitBodyFunctionInterface traitFunction;

    /**
     * intializer
     */
    public TraitBody(int power, TraitBodyFunctionInterface function, String name) {
        super(power, name);
        traitFunction = function;
    }

    /**
     * effects a body part
     */
    private void effect(BodyPart part) {
        traitFunction.apply(part, getPower());
    }


    /**
     * updates a set of body parts, and adds them to the list of effected items
     */
    void updateBodyParts(List<BodyPart> parts) {

        List<Integer> curPartIDs = new ArrayList<Integer>();
        for (BodyPart x : parts) {
            curPartIDs.add(checkID(x));
            if (!checkEffected(x)) {
                addEffected(x);
                effect(x);
            }
        }
        //implement later (lel)
        //removeEffected(curPartIDs, getEffected());

    }
}
