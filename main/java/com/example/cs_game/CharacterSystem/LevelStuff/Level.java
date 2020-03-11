package com.example.cs_game.CharacterSystem.LevelStuff;

public class Level {
    // current level
    private int level;

    // current experience; condition: 0 <= exp <= level**2 = max exp for the current level
    private int exp;


    /**
     * Constructor for LevelManager
     * @param level the initial level the LevelManager starts at
     * @param exp the initial exp the LevelManager starts at
     */
    public Level(int level, int exp) {
        this.level = level;
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    int gainExp(int amount) {
        int gainedLevel = 0;
        while (amount > 0) {
            if (exp + amount <= Math.pow(level, 2)) {
                exp += amount;
                break;
            } else {
                amount = amount - ((int) Math.pow(level, 2) - exp);
                exp = 0;
                level += 1;
                gainedLevel += 1;
            }
        }

        return gainedLevel;
    }

    int getTotalExp() {
        int total = 0;
        for (int i = 1; i <= level; i++) {
            total += (int) Math.pow(i, 2);
        }
        return total + exp;
    }

}
