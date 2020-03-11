package com.example.cs_game.CombatGame.CharacterSystem.TraitStuff;

import java.util.List;

/**
 * This class is meant to make creating characters easier, as it holds the different arrays of traits
 * that the character needs to instantiate.
 */
public class TraitHolder {

    private List<TraitBody> bodyTraits;
    private List<TraitDeath> deathTraits;
    private List<TraitItem> itemTraits;

    /**
     * constructor to hold different arrays
     */
    public TraitHolder(List<TraitBody> bodyTraits, List<TraitDeath> deathTraits,
                       List<TraitItem> itemTraits) {
        this.bodyTraits = bodyTraits;
        this.deathTraits = deathTraits;
        this.itemTraits = itemTraits;
    }

    List<TraitBody> getBodyTraits() {
        return bodyTraits;
    }

    List<TraitDeath> getDeathTraits() {
        return deathTraits;
    }

    List<TraitItem> getItemTraits() {
        return itemTraits;
    }
}

