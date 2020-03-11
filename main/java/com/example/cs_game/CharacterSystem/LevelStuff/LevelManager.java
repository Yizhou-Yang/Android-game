package com.example.cs_game.CharacterSystem.LevelStuff;

import com.example.cs_game.CharacterSystem.Observer.CharacterWatcher;

public class LevelManager {
    private CharacterWatcher characterWatcher;
    // current level
    private Level level;

    /**
     * @param curObserver CharacterWatcher to notify character
     * @param level       the level object for the character
     */
    public LevelManager(CharacterWatcher curObserver, Level level) {
        this.characterWatcher = curObserver;
        this.level = level;
    }

    public int getLevel() {
        return level.getLevel();
    }

    public int gainExp(int amount) {
        return level.gainExp(amount);
    }

    public int getTotalExp() {
        return level.getTotalExp();
    }
}
