package com.example.cs_game.DungeonGame;


import android.content.Context;

import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;

import java.util.List;

class DungeonActivityPresenter {

    private UserDatabaseHelper userDatabaseHelper;
    private User user;



    DungeonActivityPresenter(String username, Context context) {
        this.userDatabaseHelper = new UserDatabaseHelper(context);
        this.user = userDatabaseHelper.getUser(username);

        user.setResources(0);
        userDatabaseHelper.updateUser(user);
    }

    int getDungeonSize() {
        return user.getDifficultyCustomization() + 1;
    }

    int getEnemyInTurns() {
        return (3 - this.user.getDifficultyCustomization()) * 75;
    }

    int getCharacterType() {
        return this.user.getSpriteCustomization();
    }

    int getMusic() {
        return user.getAudioCustomization();
    }

    void updateUser(int seconds, int resources, int experience, int level) {
        this.user.addResources(resources);
        this.user.addExperience(experience);
        this.user.addSecondsSpent(seconds);
        this.user.setLevel(level);
        userDatabaseHelper.updateUser(this.user);

    }

    public interface DungeonView {
        void setVerticalWalls();
        void setEnemies();
        void setResources();
        void setHero();
        void setMusic();
        void launchExitActivity(int resources);
        void update();
        void updateHero();
        void updateEnemies();
        boolean heroNearWallx();
        boolean heroNearWally();
        boolean heroAtDoor();
        boolean heroDead();
        void isAtResource();
        List<DungeonItem> getVerticalWalls();
        List<DungeonItem> getResourceList();
        List<DungeonEnemy> getEnemyList();
        DungeonHero getHero();
        DungeonItem getExit();

    }



}