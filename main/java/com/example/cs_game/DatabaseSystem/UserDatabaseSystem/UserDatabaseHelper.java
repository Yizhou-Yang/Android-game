/**
 * Holds the DatabaseHelper object for the Users in the database
 * Designed to interact directly with a User object
 * Has every variable stored by Users as a column
 *
 * Design Features
 * ------------------
 * Single Responsibility
 * Separation of back end and front end
 */
package com.example.cs_game.DatabaseSystem.UserDatabaseSystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserDatabase";
    public static final String TABLE_NAME = "UserTable";
    public static final String COL_1 = "Username";
    public static final String COL_2 = "Resources";
    public static final String COL_3 = "SecondsSpent";
    public static final String COL_4 = "Experience";
    public static final String COL_5 = "Level";
    public static final String COL_6 = "AudioCustomization";
    public static final String COL_7 = "SpriteCustomization";
    public static final String COL_8 = "DifficultyCustomization";

    public UserDatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    /**
     * @param username: The unique username of a User
     * @return the user object from the database. Returns a User with username "" if not found.
     */
    public User getUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + "= '" + username + "'";
        Cursor res = db.rawQuery(query, null);
        return decodeSingleUserCursor(res);
    }

    /**
     * @return a list of all users in the database
     */
    public List<User> getAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return decodeUserListCursor(cursor);
    }

    /**
     * @param user: User object to updateDisplay
     * @return true iff operation was successful
     */
    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvs = new ContentValues();
        cvs.put(COL_1, user.getUsername());
        cvs.put(COL_2, user.getResources());
        cvs.put(COL_3, user.getSecondsSpent());
        cvs.put(COL_4, user.getExperience());
        cvs.put(COL_5, user.getLevel());
        cvs.put(COL_6, user.getAudioCustomization());
        cvs.put(COL_7, user.getSpriteCustomization());
        cvs.put(COL_8, user.getDifficultyCustomization());
        long result = db.update(TABLE_NAME, cvs, "" + COL_1 + " = ?", new String[]{user.getUsername()});
        return (result != -1);
    }

    /**
     * @param name unique username of new user
     * @return true iff operation successful
     */
    public boolean createUser(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvs = new ContentValues();
        cvs.put(COL_1, name);
        cvs.put(COL_2, 0); // Resources
        cvs.put(COL_3, 0); // Seconds Spent
        cvs.put(COL_4, 0); // Experience
        cvs.put(COL_5, -1); // Level
        cvs.put(COL_6, 0); // Audio
        cvs.put(COL_7, 0); // Sprite
        cvs.put(COL_8, 0); // Difficulty

        long result = db.insert(TABLE_NAME, null, cvs);
        return (result != -1);

    }

    /**
     * Utility function to decode the SQLite Query output into actual User object
     *
     * @param result Cursor object as a result of querying the database for a single user object
     * @return the User object who's information is contained within the line of the cursor
     */
    public User decodeSingleUserCursor(Cursor result) {
        // Assumes only one row is in the result
        String username = "";
        int resources = 0;
        int secondsSpent = 0;
        int experience = 0;
        int level = -1;
        int audioCustomization = -1;
        int spriteCustomization = -1;
        int difficultyCustomization = -1;
        if (result.moveToNext()) {
            username = result.getString(0);
            resources = result.getInt(1);
            secondsSpent = result.getInt(2);
            experience = result.getInt(3);
            level = result.getInt(4);
            audioCustomization = result.getInt(5);
            spriteCustomization = result.getInt(6);
            difficultyCustomization = result.getInt(7);
        }
        return new User(username, resources, secondsSpent, experience, level, audioCustomization, spriteCustomization, difficultyCustomization);
    }

    /**
     * Utility function to decode SQLite Query output into list of actual Users
     *
     * @param result Cursor from a query that asks for multiple users
     * @return the list of users that the cursor contained information about.
     */
    public List<User> decodeUserListCursor(Cursor result) {
        List<User> users = new ArrayList<User>();
        if (result.getCount() == 0) {
            // No data
        } else {
            boolean flag = false;
            while (!flag) {
                User u = decodeSingleUserCursor(result);
                if (u.getUsername().equals("")) {
                    flag = true;
                } else {
                    users.add(u);
                }
            }
        }
        return users;
    }

    /**
     * Creates the table as per our specifications when database is created
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_NAME + " (" + COL_1 + " TEXT PRIMARY KEY, " + COL_2 +
                " INTEGER, " + COL_3 + " INTEGER, " + COL_4 + " INTEGER, " + COL_5 + " INTEGER, " + COL_6 +
                " INTEGER, " + COL_7 + " INTEGER, " + COL_8 + " INTEGER)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }
}