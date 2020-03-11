package com.example.cs_game.CombatGame.GenerationSystem;

import com.example.cs_game.CombatGame.CharacterSystem.CharacterStuff.Monster;
import com.example.cs_game.CombatGame.CharacterSystem.CharacterStuff.Player;
import com.example.cs_game.CombatGame.CharacterSystem.CharacterStuffBuilder;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Armor;
import com.example.cs_game.CombatGame.CharacterSystem.Item.HealthPotion;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Weapon;

import java.util.Random;

/**
 * the GenericGenerator implements the StartupGenerator strategy interface, and is meant to be an
 * abstract class which creates the functions needed by the actual generator of items/traits/characters
 * during a combat and shop
 */
public abstract class GenericGenerator implements StartupGenerator {

    String[] weaponClasses;
    int[][] weaponStats;

    String[] healthPotionClasses;
    int[][] healthPotionStats;

    String[] armorClasses;
    int[][] armorStats;

    /**
     * empty constructor
     */
    GenericGenerator() {
    }

    public abstract Player generatePlayer();

    public abstract Monster generateMonster();


    /**
     * This function samples from a uniform distribution with center at item, and
     * ends at item-x, item+x
     */
    int varia(int item, double x) {
        Random randVar = new Random();
        int variation = (int) (item * x);
        return item + (randVar.nextInt((variation * 2) + 1) - variation);
    }

    /**
     * Same function as above for doubles
     */
    private double variaDouble(double item, double x) {
        Random randVar = new Random();
        double variation = item * x;
        return item + randVar.nextDouble() * variation * 2 - variation;
    }

    /**
     * these functions will generate their corresponding item, i.e. weapon, healthpotion, or armor
     * Note, these functions are special, as they take input in string form. This input should be the
     * name of the 'class' of item defined by the 'weaponClasses' string list above. If the input
     * is the string "Random", a random item from the appropriate classes will be created.
     */
    public Weapon generateWeaponClass(String str) {
        if (str.equals("Random")) {
            return generateWeaponClassRandom();
        } else {
            return generateWeaponClassSet(str);
        }
    }

    public HealthPotion generateHealthPotionClass(String str) {
        if (str.equals("Random")) {
            return generateHealthPotionClassRandom();
        } else {
            return generateHealthPotionClassSet(str);
        }
    }

    public Armor generateArmorClass(String str) {
        if (str.equals("Random")) {
            return generateArmorClassRandom();
        } else {
            return generateArmorClassSet(str);
        }
    }


    /**
     * These implement creating an item from the name of the class of item
     */
    private Weapon generateWeaponClassSet(String str) {
        for (int x = 0; x <= weaponClasses.length; x++) {
            if (str.equals(weaponClasses[x])) {

                return CharacterStuffBuilder.weaponCreator(weaponClasses[x], varia(weaponStats[x][0], 0.1),
                        varia(weaponStats[x][1], 0.1),
                        variaDouble(weaponStats[x][2] * 0.01, 0.1));
            }
        }
        return CharacterStuffBuilder.weaponCreator("Default", 1, 1, 0.1);
    }


    private HealthPotion generateHealthPotionClassSet(String str) {
        for (int x = 0; x <= healthPotionClasses.length; x++) {
            if (str.equals(healthPotionClasses[x])) {
                return CharacterStuffBuilder.healthPotionCreator(healthPotionClasses[x], varia(healthPotionStats[x][0], 0.2),
                        varia(healthPotionStats[x][1], 0.2));
            }
        }
        return CharacterStuffBuilder.healthPotionCreator("Default", 1, 1);

    }

    private Armor generateArmorClassSet(String str) {
        for (int x = 0; x <= armorClasses.length; x++) {
            if (str.equals(armorClasses[x])) {
                return new Armor(armorClasses[x], varia(armorStats[x][0], 0.1),
                        varia(armorStats[x][1], 0.1), 1);
            }
        }
        return new Armor("Default", 1, 1, 1);
    }

    /**
     * These functions represent the "Random" call, and are used to get random items from their
     * corresponding classes
     */
    private Weapon generateWeaponClassRandom() {
        Random random = new Random();
        int x = random.nextInt(weaponClasses.length);

        return CharacterStuffBuilder.weaponCreator(weaponClasses[x], varia(weaponStats[x][0], 0.1),
                varia(weaponStats[x][1], 0.1),
                variaDouble(weaponStats[x][2] * 0.01, 0.1));
    }


    private HealthPotion generateHealthPotionClassRandom() {
        Random random = new Random();
        int x = random.nextInt(healthPotionClasses.length);

        return CharacterStuffBuilder.healthPotionCreator(healthPotionClasses[x], varia(healthPotionStats[x][0], 0.2),
                varia(healthPotionStats[x][1], 0.2));
    }


    private Armor generateArmorClassRandom() {
        Random random = new Random();
        int x = random.nextInt(armorClasses.length);

        return new Armor(armorClasses[x], varia(armorStats[x][0], 0.1),
                varia(armorStats[x][1], 0.1), 1);
    }
}

