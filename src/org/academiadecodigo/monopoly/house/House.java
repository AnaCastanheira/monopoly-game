package org.academiadecodigo.monopoly.house;

public class House {

    private HouseName houseName;
    private HouseType houseType;
    private int position;
    private boolean owned;

    public House(int position, HouseType houseType, HouseName houseName) {
        this.position = position;
        this.houseType = houseType;
        this.houseName = houseName;
    }

    public int getValue() {

        return houseType.value;
    }

    public int getRent() {
        return houseType.rent;
    }

    public HouseName getHouseName() {
        return houseName;
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

    public enum HouseName {
        BELL_LABS("Bell Labs"),
        INSTAGRAM("Instagram"),
        FACEBOOK("Facebook"),
        GOOGLE("Google"),
        ACADEMIA_DE_CODIGO("Academia de Codigo"),
        WHILED_BITS_INC("Whiled Bits Inc."),
        APPLE("Apple"),
        MICROSOFT("Microsoft"),
        XEROX("Xerox"),
        AMAZON("Amazon"),
        FARFETCH("Farfetch"),
        SAMSUNG("Samsung"),
        LG("LG"),
        SUN_MICROSYSTEMS("Sun MicroSystems"),
        HUAWEI("Huawei"),
        MIKE_FUCKING_P_ENTERPRISE("Mike Fucking P Enterprise"),
        ANA_INC("Ana Inc"),
        RICARDO_INC("Ricardo Inc"),
        TASCO_DO_JORGITO("Tasco do Jorgito"),
        ;

        private String name;

        HouseName(String name){
            this.name = name;
        }

    }
}
