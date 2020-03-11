package com.example.cs_game.CharacterSystem.Item;

import com.example.cs_game.CharacterSystem.Observer.CharacterWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the item manager class. The purpose of this class is to manage all interactions relating
 * to items of a character. this class also holds the characters inventory.
 */
public class ItemManager {

    private Weapon[] equippedWeapons = new Weapon[2];
    private List<Consumable> consumables;
    private List<Armor> armors;
    private List<Weapon> weapons;
    private CharacterWatcher curObserver;

    /**
     * instantiate the item manager with the give observer
     */
    public ItemManager(CharacterWatcher observer) {
        curObserver = observer;
        weapons = new ArrayList<>();
        armors = new ArrayList<>();
        consumables = new ArrayList<>();
    }


    /**
     * add an item to the current inventory
     */
    public void addItem(Item item) {
        item.addTo(this);
        curObserver.updateItems();
    }

    /**
     * override for consumable
     */
    void addItem(Consumable consumable) {
        if (!consumables.contains(consumable)) consumables.add(consumable);
        curObserver.updateItems();
    }

    /**
     * override for weapon
     */
    void addItem(Weapon weapon) {
        if (!weapons.contains(weapon)) weapons.add(weapon);
        curObserver.updateItems();
    }

    /**
     * override for armor
     */
    void addItem(Armor armor) {
        if (!armors.contains(armor)) armors.add(armor);
        curObserver.updateItems();
    }

    /**
     * remove an item from the character inventory
     */
    public void removeItem(Item item) {
        item.removeFrom(this);
    }

    /**
     * override for consumable
     */
    void removeItem(Consumable consumable) {
        consumables.remove(consumable);
    }

    /**
     * override for weapon
     */
    void removeItem(Weapon weapon) {
        weapons.remove(weapon);
    }

    /**
     * override for armor
     */
    void removeItem(Armor armor) {
        armors.remove(armor);
    }

    /**
     * get list of consumables in the inventory
     */
    public List<Consumable> getConsumables() {
        return new ArrayList<>(consumables);
    }

    /**
     * get list of weapons in the inventory
     */
    public List<Weapon> getWeapons() {
        return new ArrayList<>(weapons);
    }

    /**
     * get list of armors in the inventory
     */
    public List<Armor> getArmors() {
        return new ArrayList<>(armors);
    }

    /**
     * Equip weapon at slot
     * @param slot slot == 0 or slot == 1
     * @param weapon the weapon to equip; if it's not in possession, don't equip it
     */
    public void equipWeapon(int slot, Weapon weapon) {

        // Make sure there is no weapon in unequipped weapons inventory
        weapons.remove(weapon);
        if (slot == 0 || slot == 1) {
            if (equippedWeapons[slot] != null) weapons.add(equippedWeapons[slot]);
            // If weapon is already equipped at different slot, make the slot null
            if (slot == 0 && weapon == equippedWeapons[1]) equippedWeapons[1] = null;
            if (slot == 1 && weapon == equippedWeapons[0]) equippedWeapons[0] = null;
            equippedWeapons[slot] = weapon;
        } else weapons.add(weapon); // Wrong slot, add it to unequipped weapons inventory
    }

    public void unEquipWeapon(Weapon weapon) {
        if (equippedWeapons[0] == weapon) {
            weapons.add(equippedWeapons[0]);
            equippedWeapons[0] = null;
        } else if (equippedWeapons[1] == weapon) {
            weapons.add(equippedWeapons[1]);
            equippedWeapons[1] = null;
        }
    }

    /**
     * return the whole inventory
     */
    public List<Item> getInventory() {
        List<Item> inventory = new ArrayList<>();
        inventory.addAll(weapons);
        inventory.addAll(armors);
        inventory.addAll(consumables);

        return inventory;
    }

    /**
     * return the list of equipped weapons
     */
    public Weapon[] getEquippedWeapons() {
        return equippedWeapons.clone();
    }
}

