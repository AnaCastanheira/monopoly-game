package org.academiadecodigo.monopoly.player;

import org.academiadecodigo.monopoly.house.House;

import java.util.Set;

public class Player implements Runnable{


    private String name;
    private int balance;
    private Set<House> houses;
    private int currentPosition;


    //TODO: add parameter name?
    public Player(){
        this.name = name;
        this.balance = balance;
        this.houses = houses;

    }


    // TODO build the string with the all the properties of the houses.
    // Returns a String with the properties the player has.
    public String getHouses(){
        String myProperties = houses.toArray().toString();
        return myProperties;
    }

    //Returns the total amount of money the player currently has
    public int getBalance(){
        return balance;
    }

    //returns the current position of the player in the board
    public int getCurrentPosition(){
        return currentPosition;
    }

    //adds the value price in the balance of the player
    public void addMoney(int price){
        balance += price;

    }

    //decreases the value price to the balance of the player
    public void removeMoney(int price){
        balance -= price;

    }

    //adds a new house to the set of properties the player has, and decreases its value to the balance
    public void addHouse(House house){
        houses.add(house);
        removeMoney(house.getValue());
    }

    //removes a house form the set of properties the player has, and adds its value to the balance
    public void sellHouse(House house){
        houses.remove(house);
        addMoney(house.getValue());
    }

    //TODO check the variable "diceResult" with Ricardo and his randomizer
    //moves the position the player has in the board accordingly to the result of the dice play
    public void move(int diceResult){
        currentPosition += diceResult;
    }

    //TODO to continue tomorrow
    @Override
    public void run() {

    }
}

    // TODO: Check following changes with group:
    //  Changed method names: getProperties, addProperties, sellProperties to getHouses, addHouse, sellHouse;
    //  Add and remove money methods receive int price(from selling, buying houses, rent or fees);
    //  Method move receives parameter int diceResult;