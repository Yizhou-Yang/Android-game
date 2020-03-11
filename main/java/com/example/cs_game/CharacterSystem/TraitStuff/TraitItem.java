package com.example.cs_game.CharacterSystem.TraitStuff;

import com.example.cs_game.CharacterSystem.Item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * A trait affecting the use or performance of an item
 */
public class TraitItem extends Trait {

    private TraitItemFunctionInterface traitFunction;

    /**
     * constructor
     */
    public TraitItem(int power, TraitItemFunctionInterface func, String name) {
        super(power, name);
        traitFunction = func;
    }

    /**
     * effects the item inputted
     */
    private void effect(Item item) {
        if (traitFunction.isType(item)) {
            traitFunction.apply(item, getPower());
        }
        int x = 1;
    }

    /**
     * updates a set of items using this trait's function, and keeps track of the effected item's
     * ids
     */
    void updateItems(List<Item> items) {
        List<Integer> curPartIDs = new ArrayList<Integer>();
        for (Item x : items) {
            curPartIDs.add(checkID(x));
            if (!checkEffected(x)) {
                addEffected(x);
                effect(x);
            }
        }
        //implement later
        //removeEffected(curPartIDs, getEffected());

    }
}
