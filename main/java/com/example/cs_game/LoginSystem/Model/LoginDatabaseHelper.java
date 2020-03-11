/**
 * Shared Preferences Implemented to separate the Front end from the Back end of the login system
 * Design Features
 * ---------------------
 * Back end implementation
 * public API for easy abstraction of database specifics
 */
package com.example.cs_game.LoginSystem.Model;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class LoginDatabaseHelper {


    public static final String userDatabaseName = "user_database";


    /**
     * Utility function to quickly have access to the database
     *
     * @param context
     * @return the sharedpreference database being used
     */
    private static SharedPreferences getUserDatabase(Context context) {
        return context.getSharedPreferences(userDatabaseName, Context.MODE_PRIVATE);
    }

    /**
     * Creates a new user in the database
     *
     * @param context
     * @param username
     * @param password
     */
    public static void registerUser(Context context, String username, String password) {
        SharedPreferences userDatabase = getUserDatabase(context);
        SharedPreferences.Editor se = userDatabase.edit();
        se.putString(username, password);
        se.commit();

    }

    /**
     * Returns a users password
     *
     * @param context
     * @param username
     * @return
     */
    public static String getPassword(Context context, String username) {
        SharedPreferences userDatabase = getUserDatabase(context);
        return userDatabase.getString(username, "");
    }

    /**
     * Notifies the database that the user is logged in
     *
     * @param context
     * @param username
     */
    public static void logInUser(Context context, String username) {
        SharedPreferences userDatabase = getUserDatabase(context);
        SharedPreferences.Editor se = userDatabase.edit();
        se.putString("logged_in_user", username);
        se.commit();

    }

    /**
     * Returns the username of the user currently logged in
     * "" if none.
     *
     * @param context
     * @return
     */
    public static String getLoggedInUser(Context context) {
        SharedPreferences userDatabase = getUserDatabase(context);
        return userDatabase.getString("logged_in_user", "");
    }

    /**
     * Returns true iff the username is already taken
     * @param username
     * @param context
     * @return
     */
    public static boolean UsernameTaken(String username, Context context){
        SharedPreferences userDatabase = getUserDatabase(context);
        return (!userDatabase.getString(username, "").equals(""));
    }
    public static void logOutUser(Context context) {
        SharedPreferences userDatabase = getUserDatabase(context);
        SharedPreferences.Editor se = userDatabase.edit();
        se.putString("logged_in_user", "");
        se.commit();

    }

}
