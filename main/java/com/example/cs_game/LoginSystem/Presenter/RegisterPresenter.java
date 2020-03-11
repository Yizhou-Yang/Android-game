package com.example.cs_game.LoginSystem.Presenter;

import android.content.Context;

import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;
import com.example.cs_game.LoginSystem.Model.LoginDatabaseHelper;
import com.example.cs_game.Utilities.FrontEndUtility;

public abstract class RegisterPresenter {

    /**
     * Creates a new user in the login database
     *
     * @param username
     * @param password
     */
    public static void RegisterUser(String username, String password, Context context) {
        UserDatabaseHelper myDb = new UserDatabaseHelper(context);
        // Save User in pref database
        LoginDatabaseHelper.registerUser(context, username, password);

        // Save User in SQLite Database
        myDb.createUser(username);

    }

    public static boolean RegisterChecks(String usernameText, String passwordText, String confirmedPasswordText,
                               Context context){
        if (!FrontEndUtility.passWordMatching(passwordText, confirmedPasswordText)) {
            FrontEndUtility.popUp("Passwords Not Matching", context);
            return false;
        }
        if (!FrontEndUtility.legitimateCredentials(usernameText, passwordText)) {
            FrontEndUtility.popUp("Illegitimate Username and Password Entered", context);
            return false;
        }

        if (LoginDatabaseHelper.UsernameTaken(usernameText, context)) {
            FrontEndUtility.popUp("Sorry that username is taken", context);
            return false;
        }

        return true;
    }



}
