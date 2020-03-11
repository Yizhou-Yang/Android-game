/**
 * Utility class that should be instantiated. It generates every type of list that might be displayed
 * as a scoreboard.
 *
 * Design Features
 * ----------------------------------------------------------------
 * Separation of front end and back end for scoreboard
 */
package com.example.cs_game.Scoreboard;

import android.content.Context;

import com.example.cs_game.DatabaseSystem.ScoreBoardDatabaseSystem.Score;
import com.example.cs_game.DatabaseSystem.ScoreBoardDatabaseSystem.ScoreboardDatabaseHelper;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.User;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserComparators;
import com.example.cs_game.DatabaseSystem.UserDatabaseSystem.UserDatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreBoardListProvider {
    UserDatabaseHelper userDb;
    ScoreboardDatabaseHelper scoreDb;

    public ScoreBoardListProvider(Context context) {
        userDb = new UserDatabaseHelper(context);
        scoreDb = new ScoreboardDatabaseHelper(context);
    }

    /**
     * Returns the sorted list of scores for a given level
     *
     * @param level
     * @param ascending
     * @return
     */
    public List<Score> getLevelLeaderboard(int level, boolean ascending) {
        List<Score> scores = scoreDb.getAllScores(level);
        return sorted(ascending, scores);
    }

    /**
     * Returns the sorted list of scores for a given level for a given user
     *
     * @param username
     * @param level
     * @param ascending
     * @return
     */
    public List<Score> getPersonalboard(String username, int level, boolean ascending) {
        List<Score> scores = scoreDb.getScoresOfUser(username, level);
        return sorted(ascending, scores);


    }

    /**
     * Utility function to sort lists as per parameter
     *
     * @param ascending
     * @param scores
     * @return Sorted list
     */
    private List<Score> sorted(boolean ascending, List<Score> scores) {
        if (!ascending) {
            Collections.sort(scores, Collections.reverseOrder());
        } else {
            Collections.sort(scores);
        }
        return scores;
    }

    /**
     * The initial display of all user statistics. Sorted by experience, resources and then seconds spent
     * in descending order.
     *
     * @return
     */
    public List<User> getGlobalLeaderboard() {
        List<User> users = userDb.getAllUsers();
        Collections.sort(users, new UserComparators.SecondsSpentSorter()
                .thenComparing(new UserComparators.ResourceSorter())
                .thenComparing(new UserComparators.ExperienceSorter()));
        Collections.reverse(users);
        return users;
    }

    /**
     * Takes the given list and sorts in by one of 3 possible options and returns it
     *
     * @param users
     * @param variableName - "resources", "experience" or "secondsSpent" default to secondsSpent
     * @param ascending
     * @return
     */
    public List<User> sortUsersBy(List<User> users, String variableName, boolean ascending) {
        if (variableName.equals("resources")) {
            Collections.sort(users, new UserComparators.ResourceSorter());

        } else if (variableName.equals("experience")) {
            Collections.sort(users, new UserComparators.ExperienceSorter());

        } else {
            Collections.sort(users, new UserComparators.SecondsSpentSorter());
        }
        if (!ascending) {
            Collections.reverse(users);
        }
        return users;

    }

}
