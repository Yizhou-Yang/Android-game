package com.example.cs_game.CombatGame.CharacterSystem.Item;

import java.util.Locale;
import java.util.Random;

/**
 * Armor item class, for reducing damage to a body part ( not implemented currently ):
 */
public class Armor extends Equipment {

    //from 0-100
    private int toughness;
    private int armorLives;

    /**
     * construction
     */
    public Armor(String name, int price, int toughness, int armorLives) {
        super(name, price);
        this.toughness = toughness;
        this.armorLives = armorLives;
    }

    @Override
    public String getDescription() {
        return String.format(Locale.CANADA, "Name: %s \nToughness: %d \nArmor Lives: %d",
                getName(), toughness, armorLives);
    }

    /**
     * method for adding item to item manager
     */
    @Override
    public void addTo(ItemManager itemManager) {
        itemManager.addItem(this);
    }

    /**
     * method for removing item from item manager
     */
    @Override
    public void removeFrom(ItemManager itemManager) {
        itemManager.removeItem(this);
    }

    public boolean takeDamage() {
        if (armorLives == 0) {
            return false;
        } else {
            Random x = new Random();
            if (this.toughness / 100. < x.nextDouble()) {
                armorLives -= 1;
            }
            return true;
        }
    }

    /**
     * Return a deep copy of this Armor.
     *
     * @return deep copy of this Armor.
     */
    @Override
    public Armor copy() {
        return new Armor(getName(), getPrice(), toughness, armorLives);
    }
}
