package com.example.cs_game.CharacterSystem.BodyPartStuff;

import com.example.cs_game.CharacterSystem.Item.Armor;
import com.example.cs_game.CharacterSystem.Observer.CharacterWatcher;

import java.util.ArrayList;
import java.util.List;


public class BodyPartManager {
    // keeps a list of body parts of the given character
    private List<BodyPart> bodyParts;

    //The observer class reference for the body part manager
    private CharacterWatcher curObserver;

    /**
     * Creates a Manager which holds all the body parts of a given player
     */

    public BodyPartManager(List<BodyPart> parts, CharacterWatcher observer) {
        curObserver = observer;
        bodyParts = parts;
    }


    /**
     * returns the sum of the total health of each of the body parts in this manager
     */

    public int getTotalHealth() {
        int total = 0;
        for (BodyPart i : bodyParts) {
            total += i.getTotalHealth();
        }
        return total;
    }


    /**
     * returns the same of the current health of each of the body parts in this manager
     */
    public int getCurrHealth() {
        int total = 0;
        for (BodyPart i : bodyParts) {
            total += i.getHealth();
        }
        return total;
    }

    /**
     * Gets the amount of body parts in this manager
     */
    public int getBodySize() {
        return bodyParts.size();
    }

    /**
     * returns the body part list associated with this manager.
     */
    public List<BodyPart> getBodyPartsList() {
        return bodyParts;
    }

    /**
     * Return names of all the body parts
     *
     * @return String array containing all the names of each body parts
     */
    public String[] getBodyPartsNames() {
        String[] names = new String[bodyParts.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = bodyParts.get(i).getName();
        }

        return names;
    }

    /**
     * Equips given armor and returns the armor equipped previously. If there was no armor equipped,
     * return null
     *
     * @param location indicates which body part to equip the armor on
     * @param armor    the Armor object to be equipped
     * @return Previously equipped Armor; if there was none, return null
     */
    public Armor equipArmor(int location, Armor armor) {
        for (BodyPart bodyPart : bodyParts) {
            if (bodyPart.getLocation() == location) {
                return bodyPart.equipArmor(armor);
            }
        }

        return null;
    }

    /**
     * Unequip the given armor
     *
     * @param armor the Armor object that is to be unequipped from any body part equipping it
     */
    public void unequipArmor(Armor armor) {
        for (BodyPart bodyPart : bodyParts) {
            if (bodyPart.getArmor() == armor) bodyPart.equipArmor(null);
        }
    }

    /**
     * The associated updateDisplay method for curObserver
     * Broadly indicates that something in the body manager has changed.
     */
    void update() {
        curObserver.updateBody();
    }

    /**
     * Returns a list of equipped armors on each body part in this manager
     */
    public List<Armor> getArmors() {
        List<Armor> armors = new ArrayList<>();
        for (BodyPart bodyPart : bodyParts) {
            armors.add(bodyPart.getArmor());
        }

        return armors;
    }

    public List<BodyPart> getAliveBodyParts() {
        List<BodyPart> aliveBodyParts = new ArrayList<>();
        for (BodyPart bodyPart : bodyParts) {
            if (bodyPart.getHealth() > 0) {
                aliveBodyParts.add(bodyPart);
            }
        }

        return aliveBodyParts;
    }


    /**
     * returns a body part based on the index of the part
     */
    public BodyPart getBodyPart(int index) {
        return bodyParts.get(index);
    }
}
