package com.example.cs_game.CombatGame.CharacterSystem.JobStuff;

import com.example.cs_game.CombatGame.CharacterSystem.CharacterStuff.GameCharacter;
import com.example.cs_game.CombatGame.CharacterSystem.TraitStuff.TraitDeath;
import com.example.cs_game.CombatGame.CharacterSystem.TraitStuff.TraitDeathFunctionInterface;


public class Immortality extends Ability {

    private int count;
    private double healRatio;

    private TraitDeathFunctionInterface listener;

    public Immortality(int count, double healRatio, TraitDeathFunctionInterface listener) {
        this.count = count;
        this.healRatio = healRatio;
        this.listener = listener;
    }

    @Override
    public void effect(GameCharacter character) {
        TraitDeath traitDeath = new TraitDeath(count, listener, "Immortality");
        character.getTraitManager().addDeathTraits(traitDeath);

    }
}
