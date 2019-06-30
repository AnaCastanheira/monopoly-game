
package org.academiadecodigo.monopoly.game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerSetInputScanner;

import org.academiadecodigo.monopoly.player.Player;


import java.io.*;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Game implements Runnable {

    private final int INITIAL_MONEY = 16000;
    private final int PORT = 9999;
    private Board board;
    private LinkedList<Player> playersList;
    private Dice dice;
    private int numberOfPlayers;
    private int [] playerTurn;
    private Prompt prompt;
    private int playCounter;
    private ServerSocket serverSocket;


    /**
     *
     * Instantiate:
     *         		Board;
     *              	Dice;
     * Initialize:
     * 			player Order array;
     *			Prompt;
     *			playCounter control variable;
     *
     *
     * */
    public Game (int numberOfPlayers) {
        this.numberOfPlayers=numberOfPlayers;

        board = new Board();

        dice = new Dice();
        playerTurn = new int[numberOfPlayers];
        prompt = new Prompt(System.in, System.out);
        playCounter = 0;
    }



    public void init() throws IOException {
        setupGame();


        while (playCounter <10) {
            startPlay();
            playCounter++;
        }

    }

    /**
     *
     * Instantiate new Player's on LinkedList 'players'
     * Set an array with Players index distributed by playing order
     * invoke method Distribute initial money
     *
     *
     * */

    private void setupGame () throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] openingGameChooser = new int[numberOfPlayers];
        playersList = new LinkedList<>();

        for (int i = 0; i < numberOfPlayers ; i++) {

            try {
                playersList.add(i,new Player("Player "+i,serverSocket.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Player " + i + " is Connected" );
            String string = "Player " + i + " is Connected";

            broadcast(string);
           /* PrintWriter out = new PrintWriter(playersList.get(i).getPlayerSocket().getOutputStream());
            out.println(string);
            out.flush();
            out.close();*/
            openingGameChooser[i] = dice.rollTheDice();
            playerTurn[i]=i;
        }

        int temp;
        int temp2;
        for (int i = 0; i < openingGameChooser.length; i++) {
            for (int j = i+1; j <openingGameChooser.length ; j++) {

                if (openingGameChooser[i]>openingGameChooser[j]) {

                    temp = openingGameChooser[i];
                    temp2 = playerTurn[i];

                    openingGameChooser[i]=openingGameChooser[j];
                    playerTurn[i]=playerTurn[j];

                    openingGameChooser[j]=temp;
                    playerTurn[j]=temp2;
                }
            }
        }
        board.buildBoard();
        distributeInitialMoney();
        TextArt.welcomeMessage();
    }
    /**
     *
     *  Distribute initial money to all players
     *
     */

    private void distributeInitialMoney(){
        for (Player list: playersList) {
            list.addMoney(INITIAL_MONEY);
        }
    }
    /**
     *
     *  menu Prompted before player rolls dice:
     *  gives options to:
     *	- Roll the dice ( and ask Player to move himself )
     *	- Remove himeself from game
     *	- ForceShutdown the whole game
     */

    private void menuUserRollDice(Player player) {
        System.out.println("It's your turn "+player.getName()+ ", you are currently at position: "+player.getCurrentPosition()+"\n");
        String s = player.getName() + " it's your turn! \n" + board.getHouse(player.getCurrentPosition()).getHouseName()+"\n";
        broadcast(s);
        Set<Integer> validOptions = new HashSet<>();
        validOptions.add(1);
        validOptions.add(2);
        validOptions.add(3);

        IntegerInputScanner menuOption = new IntegerSetInputScanner(validOptions);
        menuOption.setMessage(" now you can:\n" +
                "(1) Roll the dice and move.\n" +
                "(2) Leave Game  \n" +
                "(3) Force  game shutdown.\n");
        int option = prompt.getUserInput(menuOption);

        if(option == 2){
            playersList.remove(player);
            System.out.println(player.getName()+ " has left the game.");
            String s1 = player.getName()+ " has left the game.";
            broadcast(s1);
            return;
        }

        if(option==3){

            System.out.println("The Game is now over.\n"+
                    player.getName()+" has decided to finish the game.");

            String s2 = "The Game is now over.\n"+
                    player.getName()+" has decided to finish the game.";

            broadcast(s2);
            System.exit(1);

        }
        int diceValue =dice.rollTheDice();
        player.move(diceValue);


    }

    /**
     *
     *  menu Prompted after the player Rolls the dice,
     *  	IF the house where he isn't owned
     *  gives options to:
     *	- Buy the house
     *	- Sell a house
     *	- End his turn
     */

    private void menuOptionBuy(Player player) throws IOException {
        System.out.println(player.getName()+ "you have moved to "+player.getCurrentPosition());
        String s2 = player.getName()+ " threw :" + dice.getSteps() + "and you are in: " + board.getHouse(player.getCurrentPosition()).getHouseName() + ".";
        broadcast(s2);
        Set<Integer> validOptions = new HashSet<>();
        validOptions.add(1);
        validOptions.add(2);
        validOptions.add(3);

        IntegerInputScanner menuOption = new IntegerSetInputScanner(validOptions);
        menuOption.setMessage(" now you can:\n" +
                "(1) Buy\n" +
                "(2) Sell\n" +
                "(3) End Turn\n");

        int option = prompt.getUserInput(menuOption);

        if(option == 1) {
            if(player.getBalance()<board.getHouse(player.getCurrentPosition()).getValue()){
                System.out.println("You have no money");
                //TODO WANNA SELL ONE ???????????????????????
                return;
            }
            player.addHouse(board.getHouse(player.getCurrentPosition()));
            System.out.println("Congrats you just bought :" + board.getHouse(player.getCurrentPosition()).getHouseName());
            String s4 = player.getName() + "Congrats you just bought :" + board.getHouse(player.getCurrentPosition()).getHouseName();
            broadcast(s4);

        }
        if(option ==2){
            menuForcedSell(player);
            return;
        }
        System.out.println(player.getName()+" has ended his turn.\n");
        String s5 = player.getName()+" has ended his turn.\n";
        broadcast(s5);
    }


    /**
     *
     *  menu Prompted IF player has to pay rent && has insifucient funds:
     *  lists player owned houses giving options to:
     *	- Select the house he wants to sell
     *
     *
     */


    private void menuForcedSell(Player player) throws IOException {
        Set<Integer> validOptions = new HashSet<>();

        int i;

        for ( i = 0; i <player.nrOfHouses(); i++) {
            validOptions.add(i+1);
        }

        validOptions.add(player.nrOfHouses()+1);

        IntegerInputScanner menuOption = new IntegerSetInputScanner(validOptions);
        menuOption.setMessage("You will have to choose one house to sell " + player.getName()
                +", please select one of your houses : \n "+ player.getHouses()+"("+(player.nrOfHouses()+1)+") Go Back\n");
        int option = prompt.getUserInput(menuOption);

        if(option==(player.nrOfHouses()+1)){
            playerMove(player);
        }

                //TODO método getter LinkedList position of house in Player player.sellHouse(board.getHouse();
                player.sellHouse(option-1);
                System.out.println("### house removed ###\n");
            //}
        //}
    }

    public void playRound(Player player) throws IOException {
        //TODO
        playerMove(player);
        menuUserRollDice(player);
    }

    private void playerMove(Player player) throws IOException {


        int currentPos = player.getCurrentPosition();

        System.out.println(player.getName() + ", you are now at house: " + board.getHouse(currentPos).getHouseName());

        if ((board.getHouse(currentPos).isOwned()) && (!player.haveThisHouse(board.getHouse(currentPos)))) {
            System.out.println("This house is taken");

            if (player.getBalance() < board.getHouse(currentPos).getRent()) {
                menuForcedSell(player);
            }
            player.removeMoney(board.getHouse(currentPos).getRent());
            System.out.println("Player payed: " + board.getHouse(currentPos).getRent());
            return;
        }

        menuOptionBuy(player);
    }


    private void startPlay() throws IOException {

        for (int i = 0; i <playersList.size() ; i++) {
            if(playersList.contains(playersList.get(playerTurn[i]))){ /** Jump player position if player has left */
                playRound(playersList.get(playerTurn[i]));
            }
        }
    }

    @Override
    public void run() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String string){

        PrintWriter printWriter;

        for(int i = 0; i < playersList.size(); i++){

            try {
                printWriter = new PrintWriter(playersList.get(i).getPlayerSocket().getOutputStream());
                printWriter.println(string);
                printWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void whisper(String string){

        PrintWriter printWriter;

            try {
                printWriter = new PrintWriter(playerList.get().getPlayerSocket().getOutputStream());
                printWriter.println(string);
                printWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();

}
