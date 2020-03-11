package com.example.cs_game.CombatGame.CharacterSystem.Item;

import com.example.cs_game.CombatGame.CombatSystem.CombatModel.Transporter;

import java.util.Locale;

/**
 * Implementation of a healing potion which increases health
 */
public class HealthPotion extends HealingPotion {

    /**
     * constructor
     */
    public HealthPotion(String name, int price, int potency) {
        super(name, price, potency);
    }

    @Override
    public String getDescription() {
        return String.format(Locale.CANADA, "Name: %s \nHeals %d health",
                getName(), getPotency());
    }

    /**
     * use potion (using the transporter method)
     */
    @Override
    public void use(Transporter transporter) {
        transporter.getBodyPart().healHealth(getPotency());
    }

    /**
     * Return a deep copy of this HealthPotion.
     *
     * @return deep copy of this HealthPotion.
     */
    @Override
    public HealthPotion copy() {
        return new HealthPotion(getName(), getPrice(), getPotency());
    }
}
