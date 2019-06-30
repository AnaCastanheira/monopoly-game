package org.academiadecodigo.monopoly.house;

public class HouseTest {

    private HouseName houseName;
    private HouseType houseType;
    private int position;
    private boolean owned;

    public HouseTest(int position, HouseType houseType, HouseName houseName) {
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
        TASCA_DO_PEDRO("Tasca do Pe"),
        TASCA_DO_ANDRE("Tasca do André"),
        TASCA_DO_MANEL("Tasca do Manecas"),
        TASCA_DO_JONAS("Tasca do piscinas"),
        TASCA_DO_ALBERTO("Tasca do alberto joão"),
        TASCA_DO_MARIO("Tasca do mario"),
        TASCA_DO_FILIPE("Tasca do filipe"),
        TASCA_DA_MARIA("Tasca do maria"),
        TASCA_DO_MIGUEL("Tasca do migas"),
        TASCA_DO_EDU("Tasca do edu"),
        TASCA_DO_PRO("Tasca do Pro"),
        TASCA_DO_GERAS("Tasca do Geras"),
        TASCA_DO_VARANDAS("Tasca do varandas"),
        TASCA_DO_PIZZI("Tasca do Pizzi"),
        TASCA_DO_BDC("Tasca do BDCs"),
        TASCA_DO_BENFAS("Tasca do benfas"),
        TASCA_DO_SPORTING("Tasca do spor"),
        TASCA_DO_FALIDOS("Tasca do falidos"),
        TASCA_DO_ERMAL("Tasca do ermal"),
        TASCA_DO_STOP("Tasca do stop"),
        TASCA_DO_CENTRAL("Tasca do CENTRAL"),
        TASCA_DO_PPP("Tasca do PPP"),
        TASCA_DO_LSD("Tasca do LSD");



        private String name;

        HouseName(String name){
            this.name = name;
        }

    }
}
