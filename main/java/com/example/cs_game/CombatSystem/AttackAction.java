package com.example.cs_game.CombatSystem;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CharacterSystem.CharacterStuff.GameCharacter;
import com.example.cs_game.CharacterSystem.Item.Armor;
import com.example.cs_game.CharacterSystem.Item.Weapon;

import java.util.Random;

public class AttackAction implements IAction, Presentable {
    private GameCharacter attacker,receiver;
    private Weapon weapon;
    private BodyPart bodyPart;
    private int damage;

    //random variable instance
    private Random random = new Random();

    //returns the damage of the attack for health bar purposes
    int getDamage() {
        return damage;
    }

    //constructor
    AttackAction(GameCharacter giver, GameCharacter receiver, Weapon weapon, BodyPart bodyPart) {
        this.attacker = giver;
        this.receiver = receiver;
        this.weapon = weapon;
        this.bodyPart = bodyPart;
    }

    //health reduction on the select body part and generate combat log
    @Override
    public void effect() {
        damage = weapon.getDamage();
        double finalDodge = bodyPart.getDodge() - weapon.getAccuracy();
        Armor armor = bodyPart.getArmor();
        if (random.nextDouble() < finalDodge){
            damage = 0;
        }

        else if (armor != null && armor.takeDamage()){
            damage = 0;
        }
        bodyPart.takeDamage(damage);
    }

    // presentable method that generates the text for the textView
    @Override
    public String toLog() {
        String miss = "";
        if (damage == 0){
            miss = " (dodged) ";
        }
        return attacker.toString()+" uses "+ weapon.getName()+ " to deal "+
               damage + miss + " damage to " +receiver.toString()+"'s "+bodyPart.getName();
    }
}
