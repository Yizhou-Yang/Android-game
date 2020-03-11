/**
 * The User object is an API style, placeholder object that can be used to easily get and set variables
 * Changes to the User Object do not change the actual database values, the changes need to be committed
 * by passing the user into the updateDisplay function of a UserDatabaseHelper instance
 *
 * Design Features
 * -----------------
 * Abstraction/ encapsulation of database specifics
 */

package com.example.cs_game.DatabaseSystem.UserDatabaseSystem;

public class User {
    /**
     * Username: Unique name for each user
     * Resources: Net resources in the User has at the moment
     * Experience: A value reflecting how much the player has progressed
     * Level: The exact game level the user is on (-1) if no game saved
     * Customization: Various customization variables.
     */
    private final String username;
    private int resources;
    private int secondsSpent;
    private int experience;
    private int level;
    private int audioCustomization;
    private int spriteCustomization;
    private int difficultyCustomization;

    public User(String username, int resources, int secondsSpent,
                int experience, int level, int audioCustomization,
                int spriteCustomization, int difficultyCustomization) {
        this.username = username;
        this.resources = resources;
        this.secondsSpent = secondsSpent;
        this.experience = experience;
        this.level = level;
        this.audioCustomization = audioCustomization;
        this.spriteCustomization = spriteCustomization;
        this.difficultyCustomization = difficultyCustomization;
    }

    public String getUsername() {
        return username;
    }

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public int getSecondsSpent() {
        return secondsSpent;
    }

    public void setSecondsSpent(int secondsSpent) {
        this.secondsSpent = secondsSpent;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAudioCustomization() {
        return audioCustomization;
    }

    public void setAudioCustomization(int audioCustomization) {
        this.audioCustomization = audioCustomization;
    }

    public int getSpriteCustomization() {
        return spriteCustomization;
    }

    public void setSpriteCustomization(int spriteCustomization) {
        this.spriteCustomization = spriteCustomization;
    }

    public int getDifficultyCustomization() {
        return difficultyCustomization;
    }

    public void setDifficultyCustomization(int difficultyCustomization) {
        this.difficultyCustomization = difficultyCustomization;
    }

    public void addExperience(int add) {
        this.experience += add;
    }

    public void addSecondsSpent(int add) {
        this.secondsSpent += add;
    }

    public void addResources(int add) {
        this.resources += add;
    }

    public void resetAll() {
        /**
         * Method to reset all variables to start a new game.
         */
        this.resources = 0;
        this.experience = 0;
        this.secondsSpent = 0;
        this.level = 0;
    }

    public String report() {
        /**
         * @returns f: a string representation of the Users performance
         */
        String f = "Player Details for : " + username + "\nResources: "
                + resources + "||Seconds Spent: " + secondsSpent + "||Experience: " + experience +
                "||Level: " + level;
        return f;
    }


}
