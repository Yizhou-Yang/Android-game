package com.example.cs_game.CharacterSystem.Item;


import java.util.Locale;

/**
 * Abstract class to represent all potions which heal (may need a 'healing' potion for mana, if
 * it is ever added
 */
abstract class HealingPotion extends Potion {

    // The amount of health this HealingPotion will heal
    private int potency;

    /**
     * constructor
     */
    HealingPotion(String name, int price, int potency) {
        super(name, price);
        this.potency = potency;
    }

    /**
     * return the potency of the potion
     */
    int getPotency() {
        return potency;
    }

    /**
     * log string for potion after being used
     */
    @Override
    public String toLog() {
        return String.format(Locale.CANADA, "Healed %d", potency);
    }
}
