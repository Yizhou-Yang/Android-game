package com.example.cs_game.CharacterSystem.CharacterStuff;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CharacterSystem.Item.Item;
import com.example.cs_game.CharacterSystem.LevelStuff.Level;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * This class extends the character class, and is meant to represent the player
 */
public class Player extends GameCharacter {

    // the resources a player can use to buy items with in the shop
    private int resources = 0;

    // how augmented the player is with cybernetics... don't become a machine!
    int humanity = 100;

    /**
     * creates a character with name 'player'
     */
    public Player(List<BodyPart> bodyParts, TraitHolder tholder) {
        super(bodyParts, tholder, "Player", new Level(1, 0));
    }

    /**
     * set the resources of the player
     */
    public void setResources(int resources) {
        this.resources = resources;
    }

    /**
     * get the resources a player has
     */
    public int getResources() {
        return resources;
    }

    /**
     * buy an item for it's price. lose the amount of resources the item asks for, and
     * add the item to the inventory
     */
    public boolean buyItem(Item item) {
        if (resources >= item.getPrice()) {
            resources -= item.getPrice();
            // This needs change; probably should make lootItem have if's instead of this
            lootItem(item);
            return true;
        }
        return false;
    }

    /**
     * increase resources by an amount
     */
    public void gainResource(int amount) {
        resources += amount;
    }

    public boolean pay(int price) {
        if (resources - price < 0) return false;
        resources -= price;
        return true;
    }


    /**
     * remove an item from the inventory
     */
    public void removeItem(Item item) {
        getItemManager().removeItem(item);
    }
}
