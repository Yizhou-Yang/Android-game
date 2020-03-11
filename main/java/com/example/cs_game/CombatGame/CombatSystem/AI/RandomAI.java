package com.example.cs_game.CombatGame.CombatSystem.AI;

import com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff.BodyPart;
import com.example.cs_game.CombatGame.CharacterSystem.CharacterStuff.GameCharacter;
import com.example.cs_game.CombatGame.CharacterSystem.Item.Weapon;

import java.util.List;

// the brain dead AI that should be replaced by more advanced AI eventually
public class RandomAI implements AI {

    //get a random weapon
    public Weapon getWeapon(GameCharacter character) {
        double rand = Math.random();

        Weapon weapon;
        if (rand < 0.5) {
            //the first equipped weapon
            weapon = character.getEquippedWeapons()[0];
        } else {
            weapon = character.getEquippedWeapons()[1];

        }
        return weapon;
    }

    //get a random bodypart
    public BodyPart getBodyPart(GameCharacter character) {
        /* code reserved for later
        double rand = Math.random();
        BodyPart bodyPart;
        double length = character.getBodyPartManager().getBodySize();
        double num = rand * length;
        bodyPart = character.getBodyPartManager().getBodyPart((int) num);
         */
        //now it gets the body part with highest health
        List<BodyPart> bodyPartList = character.getBodyParts();
        int health = 0;
        BodyPart bodyPart = bodyPartList.get(0);
        for(BodyPart b: bodyPartList){
            if (b.getHealth()> health){
                bodyPart = b;
                health = b.getHealth();
            }
        }
        return bodyPart;
    }

}
