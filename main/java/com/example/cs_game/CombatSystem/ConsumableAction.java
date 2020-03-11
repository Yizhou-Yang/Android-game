package com.example.cs_game.CombatSystem;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CharacterSystem.CharacterStuff.GameCharacter;
import com.example.cs_game.CharacterSystem.Item.Consumable;
import com.example.cs_game.CharacterSystem.Item.Weapon;
import com.example.cs_game.CombatSystemManager.GameManager;
import com.example.cs_game.Transporter;

import java.util.Random;

public class ConsumableAction implements IAction, Presentable {
    private GameCharacter receiver;
    private BodyPart bodyPart;
    private Consumable consumable;

    //random variable instance
    private Random rv = new Random();

    //constructor
    ConsumableAction(GameCharacter receiver, BodyPart bodyPart, Consumable consumable) {
        this.receiver = receiver;
        this.bodyPart = bodyPart;
        this.consumable = consumable;

    }

    //health reduction on the select body part and generate combat log
    @Override
    public void effect() {
        Transporter transporter = new Transporter();
        transporter.setBodyPart(bodyPart);

        consumable.use(transporter);
        GameManager.getPlayer().removeItem(consumable);
    }

    // presentable method that generates the text for the textView
    @Override
    public String toLog(){


        return "Used " + consumable.getName();
        }
}

