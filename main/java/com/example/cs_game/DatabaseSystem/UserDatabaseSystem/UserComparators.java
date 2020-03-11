/**
 * This class will hold different Comparator objects, that will be useful in sorting the User objects
 * by each of the following variables - Resources, SecondsSpent, Experience
 *
 * Design Features
 * -----------------
 * Interface implementations
 */
package com.example.cs_game.DatabaseSystem.UserDatabaseSystem;


import java.util.Comparator;

public abstract class UserComparators {

    public static class ResourceSorter implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            return o1.getResources() - o2.getResources();
        }
    }

    public static class SecondsSpentSorter implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            return o1.getSecondsSpent() - o2.getSecondsSpent();
        }
    }

    public static class ExperienceSorter implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            return o1.getExperience() - o2.getExperience();
        }
    }

}

