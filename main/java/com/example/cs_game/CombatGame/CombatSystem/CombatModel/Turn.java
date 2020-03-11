package com.example.cs_game.CombatGame.CombatSystem.CombatModel;

import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CombatGame.CharacterSystem.CharacterStuff.Monster;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Consumable;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Weapon;

public class Turn {
    private Monster monster;

    private Consumable consumable;
    private Weapon weapon;
    private BodyPart bodyPart;

    public static void playTurn(IAction action){
        action.effect();
    }
}
