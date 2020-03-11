package com.example.cs_game.CharacterSystem.Observer;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPartManager;
import com.example.cs_game.CharacterSystem.CharacterStuff.GameCharacter;
import com.example.cs_game.CharacterSystem.Item.ItemManager;
import com.example.cs_game.CharacterSystem.Item.Item;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is meant to be a bridge to connect traits and the rest of the player class.
 * traits are variable, and can affect almost any part of player (or rather, we want to allow a
 * trait to do such), so we have a CharacterWatcher class, which will let trait know what parts, and when
 * to affect which parts of the player
 */
public class CharacterWatcher {

    private BodyPartManager body;
    private TraitManager traits;
    private ItemManager items;

    private GameCharacter currentCharacter;

    /**
     * Constructor saves each relevant part of the player
     */
    public CharacterWatcher(GameCharacter gameCharacter) {
        currentCharacter = gameCharacter;

        body = gameCharacter.getBodyPartManager();
        traits = gameCharacter.getTraitManager();
        items = gameCharacter.getItemManager();
    }

    /**
     * updateDisplay the entire entries to the parts of the observer
     */
    public void updateCharacter() {
        body = currentCharacter.getBodyPartManager();
        traits = currentCharacter.getTraitManager();
        items = currentCharacter.getItemManager();
    }


    /**
     * updateDisplay the body based on traits
     */
    public void updateBody() {
        traits.updateBody(body.getBodyPartsList());
    }

    /**
     * updateDisplay the items based on traits
     */
    public void updateItems() {
        List<Item> items = this.items.getInventory();
        traits.updateItems(items);
    }

    /**
     * updateDisplay the traits concerning the death of the character.
     */
    public boolean updateDead() {
        System.out.println(traits.updateDead(body));
        return traits.updateDead(body);
    }

}


