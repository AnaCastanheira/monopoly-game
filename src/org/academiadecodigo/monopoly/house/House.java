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

    public String getHouseName() {
        return houseName.name;
    }

    public HouseType getHouseType() {
        return houseType;
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
        VERMELHO(30, 30),
        WHITE(0,0);


        private int value;
        private int rent;

        HouseType(int value, int rent) {
            this.value = value;
            this.rent = rent;
        }

    }

    public enum HouseName {

        MIKE_FUCKING_P_MEDIA_CENTERE("Mike Fucking P Media Center"),
        ANA_PUBLISHING("Ana’s Publishing"),
        RICARDO_MUSCLE_GY2M("Ricardo Muscle Gym"),
        TASCO_DO_JORGITO("Tasco do Jorgito"),
        NINAS_DO_NORONHA("Ninas do Noronha"),
        LIMA_FLY_ESCOBAR("Lima Futebol Clube"),
        DANCE_LIKE_EDMA("Dance Like Edma"),
        JONATHAN_BARBER("Jonathan Barber"),
        PETER_CROSSFIT_BOX("Peter CrossFit Box"),
        ALBUQUERQUE_BARBEQUE("Albuquerque’s Barbeque"),
        BRUNO_HACKING_UNIVERSITY("Bruno Hacking University"),
        FERNAND_RURAL_EXPERIENCE("Fernand Rural Experience"),
        THIAGO_BREWERY("Thiago’s Brewery"),
        RAFA_MUSIC_SCHOOL("Rafa Music School"),
        JAY_CORNER("Jay’s Corner"),
        RUI_ILLEGAL_TECHNOLOGY("Rui Illegal Technology"),
        MARTINI("Martini"),
        DAVID_YOGA_CLASS("David Yoga Class"),
        HUGO_ASS_KICKER("Hugo Ass Kicker"),
        RODRIGO_SCIENCE4KIDS("Rodrigo Science4Kids"),
        BAR_77("Bar 77"),
        BAD_HABITS("Bad Habits"),
        PET_CEMETERY("Pet Cemetery"),
        RED_BEACH("Red Beach"),
        GAS_STATION("Gas Station"),
        AMERICAN_VOMIT_DINNER("American Vomit Dinner"),
        FOREST_FIRE("Forest Fire"),
        CHAMBER_SHAME("Chamber Shame"),
        HOTEL_SUICIDE("Hotel Suicide"),
        VEGAN_CIRCUS("Vegan Circus"),
        LAKE_CHERNOBYL("Lake Chernobyl"),
        DEATH_VALLEY("Death Valley"),
        DESERT_COCAINE("Desert Cocaine"),
        RED_PEPPER_COTTON_FIELD("Red Pepper Cotton Field"),
        MOUNTAIN_CAMEL_WITH_CANCER("Mountain Camel With Cancer"),
        ALLEY_DESPERATION("Alley Desperation"),
        BROKEN_GLASS_TOWER("Broken Glass Tower"),
        MUSEUM_PRETENTIOUS("Museum Pretentious"),
        LIBRARY_BORING("Library Boring"),
        HOME("Home");


        private String name;

        HouseName(String name){
            this.name = name;
        }

    }
}
