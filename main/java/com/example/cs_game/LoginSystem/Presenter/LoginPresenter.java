package com.example.cs_game.LoginSystem.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.cs_game.LoginSystem.Model.LoginDatabaseHelper;
import com.example.cs_game.MainMenu.View.MainActivity;

public abstract class LoginPresenter {


    public static void loginStatusCheck(Intent intent, Activity login){
        if (!intent.getBooleanExtra("logging_out", false)) {

            String loggedInUser = LoginDatabaseHelper.getLoggedInUser(login);

            if (!loggedInUser.equals("")) {

                login.startActivity(logInUser(loggedInUser, login));
            }
        } else {
            LoginDatabaseHelper.logOutUser(login);
        }
    }

    /**
     * Request the database to log in the user and then move to the main menu
     *
     * @param username
     * @param context
     * @return Intent that can be started at any time.
     */
    public static Intent logInUser(String username, Context context) {
        // Save user as logged_in
        LoginDatabaseHelper.logInUser(context, username);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("username", username);
        return intent;
    }


    /**
     * Utility functions to ensure the username is available
     *
     * @param username
     * @param password
     * @return true iff available
     */
    public static boolean authenticateCredentials(String username, String password, Context context) {
        String realPassword = LoginDatabaseHelper.getPassword(context, username);
        if (realPassword.equals("")) {
            return false;
        }
        return (realPassword.equals(password));
    }

}
