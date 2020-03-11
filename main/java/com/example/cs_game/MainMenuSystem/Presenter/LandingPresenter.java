package com.example.cs_game.MainMenu.Presenter;

import android.content.Context;

import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;

public abstract class LandingPresenter {

    public static void updateUserCustomization(String username, int audio, int difficulty,
                                               int resourceVariety, Context context){
        UserDatabaseHelper userDb = new UserDatabaseHelper(context);
        User u = userDb.getUser(username);
        u.setAudioCustomization(audio);
        u.setDifficultyCustomization(difficulty);
        u.setSpriteCustomization(resourceVariety);
        userDb.updateUser(u);
    }
}
