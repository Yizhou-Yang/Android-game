package com.example.cs_game.CombatGame.GenerationSystem;

import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CombatGame.CharacterSystem.TraitStuff.TraitBodyFunctionInterface;

import java.util.List;


/**
 * This class is essentially a proof of concept, that we can extend the Basic generator and add in
 * some new traits
 */
public class AdvancedGenerator extends BasicGenerator implements StartupGenerator {


    public static StartupGenerator buildGenerator() {

        return new AdvancedGenerator();
    }

    /**
     * basic constructor
     */
    private AdvancedGenerator() {
        super();

    }

    /**
     * Adding a second body related function for a trait, from the original body related function
     * in basic generator
     * @return list of implementations of TraitBodyFunctionInterface's
     */
    public List<TraitBodyFunctionInterface> generateBodyTraits() {

        List<TraitBodyFunctionInterface> traits = super.generateBodyTraits();

        TraitBodyFunctionInterface dodgeTrait =
                new TraitBodyFunctionInterface() {
                    @Override
                    public void apply(BodyPart bodyPart, int power) {
                        double dodge = bodyPart.getDodge();
                        bodyPart.setDodge(dodge + setPower(power));

                    }

                    @Override
                    public double setPower(int num) {
                        return 0.01 * num;
                    }
                };

        traits.add(dodgeTrait);

        return traits;
    }

}

