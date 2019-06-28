package org.academiadecodigo.monopoly.house;

public class House {

    private String name;
    private HouseType houseType;
    private int position;
    private boolean owned;

    public House(){}

    public int getValue(){

        return;
    }

    public int getRent(){
        return;
    }

    public boolean isOwned(){
        return;
    }

    public void buy(){}

    public void sell(){}

    public enum HouseType {
        AMARELO(30,30),
        AZUL(30,30),
        VERDE(30,30),
        VERMELHO(30, 30);


        private int value;
        private int rent;

        HouseType(int value, int rent){
            this.value=value;
            this.rent=rent;

        }

    }
}
