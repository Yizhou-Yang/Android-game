package com.example.cs_game.CombatGame.CombatSystem.AI;

import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Weapon;
import com.example.cs_game.CombatGame.CharacterSystem.CharacterStuff.GameCharacter;

//AI, random or advanced
public interface AI {
    BodyPart getBodyPart(GameCharacter character);
    Weapon getWeapon(GameCharacter character);

}
