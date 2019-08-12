package org.academiadecodigo.monopoly.game;

import org.academiadecodigo.monopoly.house.House;

public class Board {

    private House[] houses;
    private static final int NUM_OF_PURCHASE_SPOTS = 20;
    private static final int NUM_OF_FREE_SPOTS = 20;

    public Board() {

        houses = new House[NUM_OF_FREE_SPOTS + NUM_OF_PURCHASE_SPOTS];
    }


    public void buildBoard() {

        int marcador = 1;

        boolean[] testRepeater = new boolean[40];
        for (int i = 0; i < 40; i++) {
            testRepeater[i] = false;
        }
        for (int i = 0; i < 40; i++) {
            double random = Math.random();
            while (testRepeater[(int) (Math.random() * 40)]) {
                random = Math.random();
            }
            testRepeater[(int) (Math.random() * 40)] = true;

            if (random < 0.5) {
                houses[i] = new House(i, House.HouseType.WHITE, House.HouseName.values()[i]);
            }


            int steps = (int) (Math.random() * (4 - 1) + 1);

            switch (steps) {

                case 1:

                    houses[i] = new House(i, House.HouseType.AZUL, House.HouseName.values()[i]);
                    break;

                case 2:

                    houses[i] = new House(i, House.HouseType.VERMELHO, House.HouseName.values()[i]);
                    break;

                case 3:

                    houses[i] = new House(i, House.HouseType.VERDE, House.HouseName.values()[i]);
                    break;

                default:

                    houses[i] = new House(i, House.HouseType.AMARELO, House.HouseName.values()[i]);

            }
        }

    }

    public House getHouse(int housePosition) {

        return houses[housePosition];
    }
}
