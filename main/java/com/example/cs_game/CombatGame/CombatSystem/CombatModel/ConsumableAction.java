package com.example.cs_game.CombatGame.CombatSystem.CombatModel;

import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CombatGame.CharacterSystem.CharacterStuff.GameCharacter;
import com.example.cs_game.CombatGame.CharacterSystem.CharacterStuff.Player;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Consumable;
import com.example.cs_game.CombatGame.CombatSystem.CombatPresenter.Presentable;
import com.example.cs_game.DatabaseSystem.LocalSLSystem.*;

import java.util.Random;

public class ConsumableAction implements IAction, Presentable {
    private GameCharacter receiver;
    private BodyPart bodyPart;
    private Consumable consumable;
    private GameManager gameManager = GameManager.getGameManager();
    //random variable instance
    private Random rv = new Random();

    //constructor
    public ConsumableAction(GameCharacter receiver, BodyPart bodyPart, Consumable consumable) {
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
        gameManager.getPlayer().removeItem(consumable);
    }

    // presentable method that generates the text for the textView
    @Override
    public String toLog(){


        return "Used " + consumable.getName();
        }
}

