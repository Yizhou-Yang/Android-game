/**
 * API, placeholder object for the Score data.
 * Users can choose to have an alias to save their scores under.
 *
 * Design Features
 * ------------------
 * Builder for Score object
 * Abstraction and encapsulation from database
 */
package com.example.cs_game.DatabaseSystem.ScoreBoardDatabaseSystem;

import java.util.Date;

public class Score implements Comparable<Score> {
    private final String date;
    private final String username;
    private final String alias;
    private final int score;
    private final int level;

    private Score(String date, String username, String alias, int score, int level) {
        this.date = date;
        this.username = username;
        this.alias = alias;
        this.score = score;
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public String getAlias() {
        return alias;
    }

    public String toString() {
        String levelDeclaration;
        if (level == 1) {
            levelDeclaration = "Dungeon Game";
        } else if (level == 2) {
            levelDeclaration = "Shop Game";
        } else {
            levelDeclaration = "Combat Game";
        }
        String f = levelDeclaration + " || Name: " + alias + " || Score: " + score + " || UID: " + date;
        return f;
    }

    /**
     * Comparison is based only on the score integer variable.
     */
    @Override
    public int compareTo(Score o) {
        if (this.score == o.score) {
            return 0;
        } else if (this.score > o.score) {
            return 1;
        } else {
            return -1;
        }
    }

    public static class Builder {
        private String date;
        private String username;
        private String alias;
        private int score;
        private int level;

        public Builder(String username, int score, int level) {
            this.username = username;
            this.score = score;
            this.level = level;
            this.alias = username;
            this.date = (new Date()).toString();
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public Score build() {
            return new Score(date, username, alias, score, level);
        }
    }

}
