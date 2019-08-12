package org.academiadecodigo.monopoly.player;

public enum PlayerType {

    BAD_ASS_NANCY(0.5, 1, 1, false),
    DRUG_DEALE_DOUG(1, 2 , 1, false),
    ROXANNE(1, 1, 1.1, false),
    LUCKY_STRIKE_LANCE(1, 1, 1, true);

    //BAD ASS DISCOUNT 20 RENT
    //
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
