package com.example.cs_game.MainMenu.Presenter;

import android.content.Context;

import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;

public abstract class MainMenuPresenter {

    public static void resetUser(Context context, String username){
        UserDatabaseHelper userDb = new UserDatabaseHelper(context);
        User u = userDb.getUser(username);
        u.resetAll();
        userDb.updateUser(u);
    }

    public static int getUserLevel(Context context, String username){
        UserDatabaseHelper userDb = new UserDatabaseHelper(context);
        User u = userDb.getUser(username);
        return u.getLevel();
    }


}
