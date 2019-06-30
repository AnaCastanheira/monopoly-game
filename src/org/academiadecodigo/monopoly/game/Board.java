package org.academiadecodigo.monopoly.game;

import org.academiadecodigo.monopoly.house.House;

public class Board {

 private House[] houses;
    private static final int NUM_OF_PURCHASE_SPOTS = 20;
    private static final int NUM_OF_FREE_SPOTS = 20;

    public Board (){

        houses = new House[NUM_OF_FREE_SPOTS + NUM_OF_PURCHASE_SPOTS];
    }


    public void buildBoard(){

        for(int i = 0; i < NUM_OF_PURCHASE_SPOTS; i++){

            int random = (int)(Math.random()*4 + 1);

            switch (random){

                case 1:

                    houses[i] = new House(i,House.HouseType.AZUL, House.HouseName.values()[i]);
                    break;

                case 2:

                    houses[i] = new House(i,House.HouseType.VERMELHO, House.HouseName.values()[i]);
                    break;

                case 3:

                    houses[i] = new House(i,House.HouseType.VERDE, House.HouseName.values()[i]);
                    break;

                default:

                    houses[i] = new House(i,House.HouseType.AMARELO, House.HouseName.values()[i]);

            }
        }

        for(int j = 19; j < NUM_OF_FREE_SPOTS +20; j++){
            houses[j] = new House(j,House.HouseType.WHITE, House.HouseName.values()[j]);
        }
    }

    public House getHouse(int housePosition){

        return houses[housePosition];
    }
}
