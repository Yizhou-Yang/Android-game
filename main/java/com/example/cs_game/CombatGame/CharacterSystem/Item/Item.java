package com.example.cs_game.CombatGame.CharacterSystem.Item;

import com.example.cs_game.Utilities.Displayable;

/**
 * Abstract class for all items
 */
public abstract class Item implements Displayable {
    // Name of the Item
    private String name;

    // Price of the Item;
    private int price;

    /**
     * constructor (takes in name and price)
     */
    Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * return the name of the item
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * return the price of the item
     */
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Remove this item from the itemManager
     *
     * @param itemManager the ItemManager object that this Item is removing itself from
     */
    public abstract void removeFrom(ItemManager itemManager);

    /**
     * Add this item to itemManager
     *
     * @param itemManager the ItemManager object that this Item is adding itself to
     */
    public abstract void addTo(ItemManager itemManager);

    /**
     * Return a deep copy of this Item.
     *
     * @return deep copy of this Item.
     */
    public abstract Item copy();
}
