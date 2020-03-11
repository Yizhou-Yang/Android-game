package com.example.cs_game.ShopGame;

import com.example.cs_game.CharacterSystem.Item.Armor;
import com.example.cs_game.CharacterSystem.Item.Consumable;
import com.example.cs_game.CharacterSystem.Item.Item;
import com.example.cs_game.CharacterSystem.Item.Weapon;
import com.example.cs_game.GenerationSystem.BasicGenerator;
import com.example.cs_game.GenerationSystem.StartupGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Shop Item Manager is meant to create items to buy at the current instance using a generator
 */
class ShopItemsManager {
    private List<Consumable> consumables = new ArrayList<>();
    private List<Weapon> weapons = new ArrayList<>();
    private List<Armor> armors = new ArrayList<>();

    /**
     * constructor creates a set of weapons, consumables and armor to be bought in the shop
     */
    ShopItemsManager() {
        StartupGenerator gen = BasicGenerator.buildGenerator();
        for (int i = 0; i < 20; i++) {


            consumables.add(gen.generateHealthPotionClass("Random"));
            weapons.add(gen.generateWeaponClass("Random"));
            armors.add(gen.generateArmorClass("Random"));
        }
    }

    /**
     * return consumables in the store
     */
    List<Consumable> getConsumables() {
        return consumables;
    }

    /**
     * return armor in the store
     */
    List<Armor> getArmors() {
        return armors;
    }

    /**
     * return weapons in the store
     */
    List<Weapon> getWeapons() {
        return weapons;
    }
}
