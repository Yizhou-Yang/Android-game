package com.example.cs_game.CombatGame.CharacterSystem.TraitStuff;

import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPartManager;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Item;
import com.example.cs_game.CombatGame.CharacterSystem.Observer.CharacterWatcher;

import java.util.List;

/**
 * This class is meant to handle all internal trait functions
 */
public class TraitManager {

    private CharacterWatcher curObserver;

    private List<TraitBody> bodyTraits;
    private List<TraitDeath> deathTraits;
    private List<TraitItem> itemTraits;

    /**
     * create class with three lists of body, death, and item traits, as well as an observer
     */
    public TraitManager(TraitHolder tholder, CharacterWatcher observer) {
        curObserver = observer;
        bodyTraits = tholder.getBodyTraits();
        deathTraits = tholder.getDeathTraits();
        itemTraits = tholder.getItemTraits();
    }

    public void addBodyTraits(TraitBody bodyTraits) {
        this.bodyTraits.add(bodyTraits);
        curObserver.updateBody();
    }

    public void addDeathTraits(TraitDeath deathTraits) {
        this.deathTraits.add(deathTraits);
    }

    public void addItemTraits(TraitItem itemTraits) {
        this.itemTraits.add(itemTraits);
        curObserver.updateItems();
    }

    /**
     * updateDisplay the body based on the body part specific traits
     */

    public void updateBody(List<BodyPart> parts) {
        for (TraitBody x : bodyTraits) {
            x.updateBodyParts(parts);
        }
    }

    /**
     * check if the character is isDead, by checking if and only if every trait fails it's check
     */
    public boolean updateDead(BodyPartManager partManager) {
        for (TraitDeath x : deathTraits) {
            System.out.println(x.isDead(partManager));
            if (!x.isDead(partManager)) {
                return false;
            }
        }
        return true;
    }

    // to implement

    /**
     * updateDisplay items based on the item list specified
     */
    public void updateItems(List<Item> parts) {
        for (TraitItem x : itemTraits) {
            x.updateItems(parts);
        }
    }


}
