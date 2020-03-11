package com.example.cs_game.CharacterSystem.Item;

import com.example.cs_game.CombatSystem.Presentable;
import com.example.cs_game.Transporter;


/**
 * Consumable class to represent all abstract consumables
 */
public abstract class Consumable extends Item implements Presentable {

    Consumable(String name, int price) {
        super(name, price);
    }

    /**
     * Use this Consumable with the data given by transporter
     *
     * @param transporter Transporter object which contains many types of data needed
     */
    public abstract void use(Transporter transporter);

    /**
     * Add this item to itemManager; visitor pattern to avoid type checking
     *
     * @param itemManager ItemManager object that this consumables adds itself to
     */
    @Override
    public void addTo(ItemManager itemManager) {
        itemManager.addItem(this);
    }

    /**
     * Remove this item from itemManager; visitor pattern to avoid type checking
     *
     * @param itemManager ItemManager object that this consumables removes itself from
     */
    @Override
    public void removeFrom(ItemManager itemManager) {
        itemManager.removeItem(this);
    }

    // for now, all items are health potions
    @Override
    public String toLog() {
        return "you used health potion";
    }
}
