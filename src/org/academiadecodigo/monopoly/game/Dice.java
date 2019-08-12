package org.academiadecodigo.monopoly.game;

public class Dice {

    private static final int DICE_MAX_VALUE = 12;
    private static final int DICE_MIN_VALUE = 1;
    private int steps;

    public int rollTheDice(){

        steps = (int) (Math.random()*DICE_MAX_VALUE) + DICE_MIN_VALUE;
        return steps;
    }

    public int getSteps() {
        return steps;
    }
}
