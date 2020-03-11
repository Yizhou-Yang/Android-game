package com.example.cs_game.CombatGame.CharacterSystem.TraitStuff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * generic class for traits. Traits are objects which can affect any part of a character in a
 * beneficial or detrimental way, based on the power of the trait
 */
public abstract class Trait {

    //between 0 - 100
    private int power;
    private String name;

    private List<Integer> effected = new ArrayList<>();

    /**
     * initialize with a name and a power level
     */
    Trait(int power, String name) {
        this.power = power;
        this.name = name;
    }

    /**
     * A trait holds a set of objects it has effected, this function is to return the id
     * of an object
     */
    int checkID(Object obj) {
        return System.identityHashCode(obj);
    }


    /**
     * checks whether an object has been effected by a trait or not.
     */
    boolean checkEffected(Object obj) {
        int id = checkID(obj);
        return effected.contains(id);
    }

    /**
     * adds an object's id to the list of effected objects' ids
     */
    void addEffected(Object obj) {
        int id = checkID(obj);
        effected.add(id);
    }

    /**
     * will return the compliment of the intersection of the two sets, where superset is the larger
     * set. This is intended for removing non-used id's or id's of deleted items.
     */
    void removeEffected(List<Integer> subset, List<Integer> superset) {
        Integer[] newSuperSet = new Integer[superset.size()];
        System.arraycopy(superset.toArray(), 0, newSuperSet, 0, superset.size());

        List<Integer> newsuperarray = new ArrayList<Integer>(Arrays.asList(newSuperSet));

        newsuperarray.addAll(Arrays.asList(newSuperSet));

        for (int i : subset) {
            newsuperarray.remove(i);
        }
        for (int i : newsuperarray) {
            effected.remove((Integer) i);
        }
    }

    /**
     * get the effected item id list
     */
    public List<Integer> getEffected() {
        return effected;
    }

    /**
     * get the power of the trait
     */
    int getPower() {
        return power;
    }


}
