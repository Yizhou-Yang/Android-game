package com.example.cs_game.CombatSystem;

import com.example.cs_game.GenerationSystem.BasicGenerator;
import com.example.cs_game.GenerationSystem.StartupGenerator;

// a test for combat, will be removed eventually
public class CombatTest {
    public static void main(String[] args) {
        StartupGenerator startupGenerator;
        startupGenerator = BasicGenerator.buildGenerator();
        Player p = startupGenerator.generatePlayer();
        Monster m = startupGenerator.generateMonster();
        Weapon weap = startupGenerator.generateWeaponClass("Spear");

        // adding weapons to monster and player

    }
}
