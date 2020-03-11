package com.example.cs_game.CombatSystem;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CharacterSystem.CharacterStuff.Monster;
import com.example.cs_game.CharacterSystem.Item.Consumable;
import com.example.cs_game.CharacterSystem.Item.Weapon;
import com.example.cs_game.CombatSystemManager.GameManager;

class Turn {
    private Monster monster;

    private Consumable consumable;
    private Weapon weapon;
    private BodyPart bodyPart;




    static void playTurn(IAction action){
        action.effect();
    }





}
