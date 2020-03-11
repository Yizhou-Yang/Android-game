package com.example.cs_game.CharacterSystem.Item;

import java.util.Locale;

/**
 * Class to represent weapons
 */
public class Weapon extends Equipment {
    //damage shop_item does per attack
    private int damage;

    //accuracy of weapon, subtract this from dodge to get hit chance
    private double accuracy;

    /**
     * constructor
     */
    public Weapon(String name, int price, int dmg, double accuracy) {
        super(name, price);
        damage = dmg;
        this.accuracy = accuracy;

    }

    @Override
    public String getDescription() {
        return String.format(Locale.CANADA, "Name: %s \nDamage: %d \nAccuracy: %.2f",
                getName(), damage, accuracy);
    }

    /**
     * return the damage of this weapon
     */
    public int getDamage() {
        return damage;
    }

    /**
     * negate this from enemy dodge (how well this performs against agile targets)
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * set damage to value
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * set accuracy to value
     */
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    /**
     * Add this weapon to the corresponding item manager
     */
    @Override
    public void addTo(ItemManager itemManager) {
        itemManager.addItem(this);
    }

    /**
     * Remove this weapon from the corresponding item manager
     */
    @Override
    public void removeFrom(ItemManager itemManager) {
        itemManager.removeItem(this);
    }
}
