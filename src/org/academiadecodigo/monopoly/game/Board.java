package org.academiadecodigo.monopoly.game;

import org.academiadecodigo.monopoly.house.House;

public class Board {

 private House[] houses;
    private static final int NUM_OF_SPOTS = 40;

    public Board (){

        houses = new House[NUM_OF_SPOTS];
    }

    /**
    public void buildBoard(){

        for(int i = 0; i < NUM_OF_SPOTS; i++){

            int random = (int)(Math.random()*4 + 1);

            switch (random){

                case 1:

                    houses[i] = new House(House.HouseType.AZUL);
                    break;

                case 2:

                    houses[i] = new House(House.HouseType.VERMELHO);
                    break;

                case 3:

                    houses[i] = new House(House.HouseType.VERDE);
                    break;

                default:

                    houses[i] = new House(House.HouseType.AMARELO);

            }
        }
    }
*/
    public House getHouse(int housePosition){

        return houses[housePosition];
    }
}
