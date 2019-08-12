package org.academiadecodigo.monopoly.player;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.monopoly.house.House;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class Player implements Iterable<House> {


    private String name;
    private int balance;
    private LinkedList<House> houses;
    private int currentPosition;
    private Socket playerSocket;
    private PlayerType playerType;
    public Prompt prompt;


    public Player(PlayerType playerType, Socket playerSocket) throws IOException {

        this.prompt = new Prompt(playerSocket.getInputStream(),new PrintStream(playerSocket.getOutputStream()));
        this.playerType = playerType;
        this.houses = new LinkedList<House>();
        this.playerSocket = playerSocket;

    }

    public Prompt getPrompt() {
        return prompt;
    }

    // TODO build the string with the all the properties of the houses.
    // Returns a String with the properties the player has.
    public String getHouses() {

        String myProperties = "";

        int i = 1;

        for (House house : houses) {
            myProperties += "(" + i + ") Name: " + house.getHouseName() + "; Value: " + house.getValue() + "; Rent: " +
                    house.getRent() + "\n";
            i++;
        }

        return myProperties;
    }

    public int nrOfHouses() {

        return houses.size();
    }

    public boolean haveThisHouse(House house) {
        if (houses.contains(house)) {
            return true;
        }
        return false;
    }


    //Returns a string with the name of the client
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getPlayerSocket() {
        return playerSocket;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    //Returns the total amount of money the player currently has
    public int getBalance() {
        return balance;
    }


    //returns the current position of the player in the board
    public int getCurrentPosition() {
        return currentPosition;
    }


    //adds the value price in the balance of the player
    public void addMoney(int price) {
        balance += price;

    }


    //decreases the value price to the balance of the player
    public void removeMoney(int price) {
        balance -= price;

    }


    //adds a new house to the set of properties the player has, and decreases its value to the balance
    public void addHouse(House house) {
        int i = houses.size();
        houses.add(i, house);
        removeMoney(house.getValue());
        house.buy();
    }


    //removes a house form the set of properties the player has, and adds its value to the balance
    public void sellHouse(int index) {
        houses.remove(index);
        addMoney(houses.get(index).getValue());
        houses.get(index).sell();
    }


    //TODO check the variable "diceResult" with Ricardo and his randomizer
    //moves the position the player has in the board accordingly to the result of the dice play
    public void move(int diceResult) {


        if (currentPosition + diceResult > 40) {
            currentPosition = (currentPosition + diceResult - 40);
            balance+=200;
            System.out.println("Balance restored");
            return;
        }
        currentPosition += diceResult;

    }


//    public void recyclePosition() {
//        currentPosition = currentPosition - 40;
//    }


    @Override
    public Iterator<House> iterator() {

        return houses.iterator();

    }

    public enum PlayerType {

        BAD_ASS_NANCY("Bad Ass Nancy", 50, 0, 0, 0),
        DRUG_DEALER_DOUG("Drug Dealer Doug", 0, 100, 0, 0),
        ROXANNE("Roxanne", 0, 0, 3000, 0),
        LUCKY_STRIKE_LANCE("Lucky Strike Lance", 0, 0, 0, 50);
        private String name;
        private int discountRent;
        private int whiteHouseMoney;
        private int bufferedInitialMoney;
        private int extraRent;

        PlayerType(String name, int discountRent, int whiteHouseMoney, int bufferedInitialMoney, int extraRent) {
            this.name = name;
            this.discountRent = discountRent;
            this.whiteHouseMoney = whiteHouseMoney;
            this.bufferedInitialMoney = bufferedInitialMoney;
            this.extraRent = extraRent;
        }

        public String getName() {
            return name;
        }

        public int getDiscountRent() {
            return discountRent;
        }

        public int getWhiteHouseMoney() {
            return whiteHouseMoney;
        }

        public int getBufferedInitialMoney() {
            return bufferedInitialMoney;
        }

        public int getExtraRent() {
            return extraRent;
        }

    }
}


// TODO: Check following changes with group:
//  Changed method names: getProperties, addProperties, sellProperties to getHouses, addHouse, sellHouse;
//  Add and remove money methods receive int price(from selling, buying houses, rent or fees);
//  Method move receives parameter int diceResult;