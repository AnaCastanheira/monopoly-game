package org.academiadecodigo.monopoly.game;

public class Dice {

    private static final int DICE_MAX_VALUE = 6;
    private static final int DICE_MIN_VALUE = 1;

    public int rollTheDice(){

        int steps = (int)Math.round(Math.random()*(DICE_MAX_VALUE - DICE_MIN_VALUE) + DICE_MAX_VALUE);

        return steps;
    }
}
