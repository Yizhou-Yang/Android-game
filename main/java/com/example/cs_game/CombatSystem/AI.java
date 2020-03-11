package com.example.cs_game.CombatSystem;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CharacterSystem.Item.Weapon;
import com.example.cs_game.CharacterSystem.CharacterStuff.GameCharacter;

//AI, random or advanced
public interface AI {
    BodyPart getBodyPart(GameCharacter character);
    Weapon getWeapon(GameCharacter character);

}
