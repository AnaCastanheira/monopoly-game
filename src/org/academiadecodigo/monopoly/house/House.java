package org.academiadecodigo.monopoly.house;

public class House {

    private String name;
    private HouseType houseType;
    private int position;
    private boolean owned;

    public House(int position, HouseType houseType, String name) {
        this.position = position;
        this.houseType = houseType;
        this.name = name;
    }

    public int getValue() {

        return houseType.value;
    }

    public int getRent() {
        return houseType.rent;
    }

    public String getName() {
        return name;
    }

    public boolean isOwned() {
        return owned;
    }

    public void buy() {
        owned = true;
    }

    public void sell() {
        owned = false;
    }

    public enum HouseType {
        AMARELO(30, 30),
        AZUL(30, 30),
        VERDE(30, 30),
        VERMELHO(30, 30);


        private int value;
        private int rent;

        HouseType(int value, int rent) {
            this.value = value;
            this.rent = rent;

        }

    }
}
