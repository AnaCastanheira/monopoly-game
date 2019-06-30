package org.academiadecodigo.monopoly.game;

import org.academiadecodigo.monopoly.house.House;
import org.academiadecodigo.monopoly.house.HouseTest;

public class BoardTest {

    private HouseTest[] houses;
    private static final int NUM_OF_SPOTS = 40;
    private boolean boardLive;

    public BoardTest (){

        houses = new HouseTest[NUM_OF_SPOTS];
    }

    public void buildBoard(){

        for(int i = 0; i < NUM_OF_SPOTS; i++){

            int random = (int)(Math.random()*4 + 1);

            switch (random){

                case 1:

                    houses[i] = new HouseTest(i,HouseTest.HouseType.AZUL, HouseTest.HouseName.values()[(int)(Math.random()*10)+1]);
                    break;

                case 2:

                    houses[i] = new HouseTest(i,HouseTest.HouseType.VERMELHO, HouseTest.HouseName.values()[(int)(Math.random()*20)+1]);
                    break;

                case 3:

                    houses[i] = new HouseTest(i,HouseTest.HouseType.VERDE,HouseTest.HouseName.values()[(int)(Math.random()*30)+1]);
                    break;

                default:

                    houses[i] = new HouseTest(i,HouseTest.HouseType.AMARELO,HouseTest.HouseName.values()[(int)(Math.random()*40)+1]);

            }
        }
    }

    public boolean isBoardLive() {
        return boardLive;
    }

    public void setBoardLive(boolean boardLive) {
        this.boardLive = boardLive;
    }

    public HouseTest getHouse(int housePosition){

        return houses[housePosition];
    }
}
