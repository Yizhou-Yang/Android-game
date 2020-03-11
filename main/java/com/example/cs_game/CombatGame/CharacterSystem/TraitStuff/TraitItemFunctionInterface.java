package com.example.cs_game.CombatGame.CharacterSystem.TraitStuff;

import com.example.cs_game.CombatGame.CharacterSystem.Item.Item;

/**
 * the trait function for affecting the items
 */
public interface TraitItemFunctionInterface extends TraitFunctionInterface {

    /**
     * the expectation that we have a function applied to an item
     */
    void apply(Item item, int power);

    /**
     * need to be able to check whether our item is of the correct type
     */
    boolean isType(Item item);
}
