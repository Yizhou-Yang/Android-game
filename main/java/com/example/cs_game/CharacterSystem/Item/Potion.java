package com.example.cs_game.CharacterSystem.Item;

/**
 * abstract class to represent all potions
 */
abstract class Potion extends Consumable {
    /**
     * constructor
     */
    Potion(String name, int price) {
        super(name, price);
    }
}
