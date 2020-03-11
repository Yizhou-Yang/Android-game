package com.example.cs_game.CombatGame.CharacterSystem.CharacterStuff;

import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Weapon;
import com.example.cs_game.CombatGame.CharacterSystem.LevelStuff.Level;
import com.example.cs_game.CombatGame.CharacterSystem.TraitStuff.TraitHolder;

import java.util.List;

/**
 * Monster subclass of the character class. This is meant to represent the enemies a player will
 * fight against
 */
public class Monster extends GameCharacter {

    /**
     * creates a boiler plate character
     */
    public Monster(List<BodyPart> bodyParts, TraitHolder tholder, String charname) {
        super(bodyParts, tholder, charname, new Level(1, 0));
    }

    /**
     * gives the monster two weapons to use
     */
    public void getWeapons(List<Weapon> weapons) {
        // Equip first two weapons of given list of weapons, and stores rest to inventory
        for (int i = 0; i < weapons.size(); i++) {
            equipWeapon(i, weapons.get(i));
        }
    }
}
