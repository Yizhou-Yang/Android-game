package com.example.cs_game.CombatGame.CharacterSystem.BodyPartStuff;

import com.example.cs_game.CombatGame.CharacterSystem.Item.Armor;
import com.example.cs_game.Utilities.Displayable;

import java.util.Locale;

public class BodyPart implements Displayable {

    public static final int HEAD = 0;
    public static final int BODY = 1;
    public static final int LEFT_ARM = 2;
    public static final int RIGHT_ARM = 3;
    public static final int LEFT_LEG = 4;
    public static final int RIGHT_LEG = 5;

    //name of the body part
    private String name;

    private int location;

    // Health is a value above 0, represents current health of the body part
    private int health;

    // initial health value of the body part, should always stay constant
    private int totalHealth;

    //Dodge is a value between 0 and 1 (counted as a percentage
    private double dodge;

    // The currently equipped armor for this body part, will reduce damage intake of this part
    private Armor armor;

    //
    public BodyPart(int health, double dodge, String name) {

        this.name = name;
        this.health = health;
        this.totalHealth = health;
        this.dodge = dodge;

        switch (name) {
            case "Head":
                location = BodyPart.HEAD;
                break;
            case "Body":
                location = BodyPart.BODY;
                break;
            case "Right Arm":
                location = BodyPart.LEFT_ARM;
                break;
            case "Left Arm":
                location = BodyPart.RIGHT_ARM;
                break;
            case "Left Leg":
                location = BodyPart.LEFT_LEG;
                break;
            case "Right Leg":
                location = BodyPart.RIGHT_LEG;
                break;
            default:
                location = -1;
        }
    }

    /**
     * getters and setters for items in the body part
     */
    public String getName() {
        return name;
    }

    public int getLocation() {
        return location;
    }

    @Override
    public String getDescription() {
        return String.format(Locale.CANADA, "%d / %d",
                health, totalHealth);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getTotalHealth() {
        return totalHealth;
    }

    public void setTotalHealth(int totalHealth) {
        this.totalHealth = totalHealth;
    }

    public double getDodge() {
        return dodge;
    }

    public void setDodge(double dodge) {
        this.dodge = dodge;
    }

    public Armor getArmor() {
        return armor;
    }

    Armor equipArmor(Armor armor) {
        Armor replacedArmor = this.armor;
        this.armor = armor;

        return replacedArmor;
    }

    public void takeDamage(int damage) {
        if (health - damage < 0) {
            health = 0;
        } else health -= damage;
    }


    /**
     * Heal this BodyPart's hp by amount. If the healed hp > totalHealth, health = totalHealth;
     *
     * @param amount Integer value health increases by
     */
    public void healHealth(int amount) {
        health += amount;
        if (health > totalHealth) health = totalHealth;
    }

    public void healHealthPercentage(double percentage) {
        int amount = (int) ((double) totalHealth * percentage);
        healHealth(amount);
    }
}

