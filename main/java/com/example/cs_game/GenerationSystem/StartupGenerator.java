package com.example.cs_game.GenerationSystem;

import com.example.cs_game.CharacterSystem.CharacterStuff.Monster;
import com.example.cs_game.CharacterSystem.CharacterStuff.Player;
import com.example.cs_game.CharacterSystem.Item.Armor;
import com.example.cs_game.CharacterSystem.Item.HealthPotion;
import com.example.cs_game.CharacterSystem.Item.Weapon;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitBodyFunctionInterface;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitDeathFunctionInterface;
import com.example.cs_game.CharacterSystem.TraitStuff.TraitItemFunctionInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * For the purpose of creating a centralized strategy to define classes of weapons and different traits,
 * there is the StartupGenerator Interface, which is meant to implement to the Strategy Interface. The
 * implementation of this interface will be a class which will create traits, classes of items, and a
 * player/monster creator.
 */
public interface StartupGenerator {
    //a generator is a strategy pattern that can be set. it can generate players and monsters
    // depending on the specific algorithm.
    List<TraitDeathFunctionInterface> generateDeathTraits();

    List<TraitBodyFunctionInterface> generateBodyTraits();

    List<TraitItemFunctionInterface> generateItemTraits();

    Player generatePlayer();

    Monster generateMonster();

    //this function should take input in the form of a weapon 'class' (a string),
    // and output a weapon of that type
    Weapon generateWeaponClass(String str);

    HealthPotion generateHealthPotionClass(String str);

    Armor generateArmorClass(String str);
}
