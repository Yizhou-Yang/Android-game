package com.example.cs_game.CharacterSystem;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CharacterSystem.CharacterStuff.Monster;
import com.example.cs_game.CharacterSystem.CharacterStuff.Player;
import com.example.cs_game.CharacterSystem.Item.HealthPotion;
import com.example.cs_game.CharacterSystem.Item.Weapon;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitBody;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitBodyFunctionInterface;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitDeath;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitDeathFunctionInterface;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitHolder;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitItem;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitItemFunctionInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is based of the builder design pattern, and creates a set of static functions to help
 * create a character class
 */
public class CharacterStuffBuilder {

    /**
     * creates a list of body parts based on each one's parameteres, inputted as arrays
     */
    static public List<BodyPart> bodyPartCreator(String[] names, int[] healths, double[] dodges) {
        List<BodyPart> BodyPartArray = new ArrayList<>();
        for (int i = 0; i < healths.length; i++) {
            BodyPart x = new BodyPart(healths[i], dodges[i], names[i]);
            BodyPartArray.add(x);
        }
        return BodyPartArray;
    }

    /**
     * creates a weapon based on it's parameters
     */
    static public Weapon weaponCreator(String name, int price, int dmg, double accuracy) {
        return new Weapon(name, price, dmg, accuracy);
    }

    /**
     * creates a potion based on it's parameters
     */
    static public HealthPotion healthPotionCreator(String name, int price, int potency) {
        return new HealthPotion(name, price, potency);
    }

    /**
     * TRAIT CREATORS
     * creates a list of each different type of trait
     */
    static public List<TraitBody> traitBodyCreator(int[] pows,
                                                   List<TraitBodyFunctionInterface> funcs,
                                                   String[] names) {

        List<TraitBody> traitBodyArray = new ArrayList<>();
        for (int i = 0; i < pows.length; i++) {
            TraitBody x = new TraitBody(pows[i], funcs.get(i), names[i]);
            traitBodyArray.add(x);
        }
        return traitBodyArray;

    }

    static public List<TraitDeath> traitDeathCreator(int[] pows,
                                                     List<TraitDeathFunctionInterface> functions,
                                                     String[] names) {

        List<TraitDeath> traitDeathArray = new ArrayList<>();
        for (int i = 0; i < pows.length; i++) {
            TraitDeath x = new TraitDeath(pows[i], functions.get(i), names[i]);
            traitDeathArray.add(x);
        }
        return traitDeathArray;

    }

    static public List<TraitItem> traitItemCreator(int[] pows,
                                                   List<TraitItemFunctionInterface> functions,
                                                   String[] names) {

        List<TraitItem> traitItemArray = new ArrayList<>();
        for (int i = 0; i < pows.length; i++) {
            TraitItem x = new TraitItem(pows[i], functions.get(i), names[i]);
            traitItemArray.add(x);
        }

        return traitItemArray;

    }

    /**
     * creats a TraitHolder object to be able to input the three different lists of traits into
     * a character constructor
     */
    static public TraitHolder traitCreator(List<TraitBody> bodyTraits, List<TraitDeath> deathTraits,
                                           List<TraitItem> itemTraits) {
        return new TraitHolder(bodyTraits, deathTraits, itemTraits);
    }

    /**
     * function for creating a player
     */
    static public Player playerCreator(List<BodyPart> bodyParts, TraitHolder traitHolder) {
        return new Player(bodyParts, traitHolder);
    }


    /**
     * function for creating a monster with a set of weapons.
     */
    static public Monster monsterCreator(List<BodyPart> bodyParts, TraitHolder traitHolder,
                                         String characterName, List<Weapon> weapons) {
        Monster monster = new Monster(bodyParts, traitHolder, characterName);
        monster.getWeapons(weapons);
        return monster;
    }


}
