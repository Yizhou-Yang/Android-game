package com.example.cs_game.CombatGame.CharacterSystem.TraitStuff;

/**
 * Since we want to put any type of function in each type of trait, we use an interface to insert
 * any function we want into a trait. Since we have a set of traits which need functions applied to
 * different input, this interface is a generic interface, and we implement it in lower level interfaces
 */
public interface TraitFunctionInterface {

    /**
     * must be able to convert power to fit it's own function
     */
    double setPower(int num);

}
