package com.example.cs_game;

import com.example.cs_game.CharacterSystem.BodyPartStuff.BodyPart;

/**
 * Because Consumables can act differently, they might require completely different arguments in
 * their Consumable.use method. Hence, they will take in Transporter object, which will contain
 * the objects they need.
 */
public class Transporter {
    private BodyPart bodyPart;

    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
    }
}
