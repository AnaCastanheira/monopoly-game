package org.academiadecodigo.monopoly.player;

public enum PlayerType {

    FOX(0.9, 1, 1, false),
    TIGER(1, 2 , 1, false),
    WALE(1, 1, 1.1, false),
    EAGLE(1, 1, 1, true);

    private double discountRent;
    private int doubleSteps;
    private double getExtraRentMoney;
    private boolean buyOneHouseAnytime;

    PlayerType(double discountRent, int doubleSteps, double getExtraRentMoney, boolean buyOneHouseAnytime){

        this.discountRent = discountRent;
        this.doubleSteps = doubleSteps;
        this.getExtraRentMoney = getExtraRentMoney;
        this.buyOneHouseAnytime = buyOneHouseAnytime;
    }

    public double getDiscountRent() {

        return discountRent;
    }

    public int getDoubleSteps() {

        return doubleSteps;
    }

    public double getGetExtraRentMoney() {

        return getExtraRentMoney;
    }

    public boolean oneTicketHouse() {

        return buyOneHouseAnytime;
    }
}
