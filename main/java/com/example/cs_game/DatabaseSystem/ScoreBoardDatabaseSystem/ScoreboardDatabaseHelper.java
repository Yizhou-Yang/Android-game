/**
 * DatabaseHelper object for Score Database
 *
 * Design Features
 * ----------------
 * Separation of Front End and Back End
 */
package com.example.cs_game.DatabaseSystem.ScoreBoardDatabaseSystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;

import java.util.ArrayList;
import java.util.List;


public class ScoreboardDatabaseHelper extends SQLiteOpenHelper {
    /**
     * Different tables for each game
     * All tables have the first 3 columns in common
     * Timestamp string is the unique primary key for all entries
     * All tables have 4 columns where column 4 is the score of that game
     * They have been made as different tables because it makes expansion easier and lets us identify levels
     */

    public static final String DATABASENAME = "ScoreboardDatabase";

    public static final String COL_0 = "Timestamp";
    public static final String COL_1 = "Username";
    public static final String COL_2 = "Alias";


    public static final String TABLE_1_NAME = "DungeonGameTable";
    public static final String T1_COL_3 = "Resources";

    public static final String TABLE_2_NAME = "ShopGameTable";
    public static final String T2_COL_3 = "GambleScore";


    public static final String TABLE_3_NAME = "CombatGameTable";
    public static final String T3_COL_3 = "CombatScore";


    public ScoreboardDatabaseHelper(@Nullable Context context) {
        super(context, DATABASENAME, null, 1);
    }

    /**
     * Insert a new score object into the database
     *
     * @param score new score object to be created
     * @return true iff operation successful
     */
    public boolean createScore(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvs = new ContentValues();

        String table = getTableFromLevel(score.getLevel());
        String col;
        cvs.put(COL_0, score.getDate());
        cvs.put(COL_1, score.getUsername());
        cvs.put(COL_2, score.getAlias());
        if (score.getLevel() == 1) {
            col = T1_COL_3;
        } else if (score.getLevel() == 2) {
            col = T2_COL_3;
        } else {
            col = T3_COL_3;
        }
        cvs.put(col, score.getScore());
        long result = db.insert(table, null, cvs);
        return (result != -1);
    }

    /**
     * Get a list of all the scores of a user from a particular level
     *
     * @param user
     * @param level
     * @return list of all scores for that user in that level
     */
    public List<Score> getScoresOfUser(User user, int level) {
        return getScoresOfUser(user.getUsername(), level);

    }

    /**
     * String argument username instead of User object itself is also allowed
     */
    public List<Score> getScoresOfUser(String username, int level) {

        SQLiteDatabase db = this.getWritableDatabase();
        String table = getTableFromLevel(level);


        String query = "SELECT * FROM " + table + " WHERE " + COL_1 + "= '" + username + "'";
        Cursor res = db.rawQuery(query, null);
        return decodeScoreArrayCursor(level, res);
    }

    /**
     * Fetches the list of all scores on a particular level
     *
     * @param level
     * @return list of all scores on the level
     */
    public List<Score> getAllScores(int level) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + getTableFromLevel(level);
        Cursor cursor = db.rawQuery(query, null);
        return decodeScoreArrayCursor(level, cursor);
    }

    /**
     * Utility function to read SQLite query cursor into a list
     *
     * @param level
     * @param cursor
     * @return list of scores that the cursor describes
     */
    private List<Score> decodeScoreArrayCursor(int level, Cursor cursor) {
        ArrayList<Score> scores = new ArrayList<Score>();
        if (cursor.getCount() == 0) {
            // No data
        } else {
            while (cursor.moveToNext()) {
                String date = cursor.getString(0);
                String username = cursor.getString(1);
                String alias = cursor.getString(2);
                int score = cursor.getInt(3);
                Score.Builder sb = new Score.Builder(username, score, level);
                sb.setDate(date);
                sb.setAlias(alias);
                Score s = sb.build();
                scores.add(s);
            }
        }
        return scores;
    }

    /*
    private Score decodeScoreCursor(Cursor result, int level){
        String date;
        String username;
        String alias;
        int score;
        if (result.moveToNext()) {
            date = result.getString(0);
            username = result.getString(1);
            alias = result.getString(2);
            score = result.getInt(3);

        } else {
            date = "NULL";
            username = "";
            alias = "";
            score = 0;
            level = -1;
        }
        Score.Builder sb = new Score.Builder(username, score, level);
        sb.setDate(date);
        sb.setAlias(alias);
        return sb.build();
    }

     */

    /**
     * Utility function that returns the table name for a given level
     *
     * @param level
     * @return name of the level
     */
    private String getTableFromLevel(int level) {
        if (level == 1) {
            return TABLE_1_NAME;
        } else if (level == 2) {
            return TABLE_2_NAME;
        } else {
            return TABLE_3_NAME;
        }

    }

    public void onCreate(SQLiteDatabase db) {
        String t1_query = "create table " + TABLE_1_NAME + " (" + COL_0 + " TEXT PRIMARY KEY, " +
                COL_1 + " text, " + COL_2 + " text, " + T1_COL_3 + " INTEGER)";
        String t2_query = "create table " + TABLE_2_NAME + " (" + COL_0 + " TEXT PRIMARY KEY, " +
                COL_1 + " text, " + COL_2 + " text, " + T2_COL_3 + " INTEGER)";
        String t3_query = "create table " + TABLE_3_NAME + " (" + COL_0 + " TEXT PRIMARY KEY, " +
                COL_1 + " text, " + COL_2 + " text, " + T3_COL_3 + " INTEGER)";
        db.execSQL(t1_query);
        db.execSQL(t2_query);
        db.execSQL(t3_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String t1_query = "DROP TABLE IF EXISTS " + TABLE_1_NAME;
        String t2_query = "DROP TABLE IF EXISTS " + TABLE_2_NAME;
        String t3_query = "DROP TABLE IF EXISTS " + TABLE_3_NAME;
        db.execSQL(t1_query);
        db.execSQL(t2_query);
        db.execSQL(t3_query);
        onCreate(db);
    }
}
