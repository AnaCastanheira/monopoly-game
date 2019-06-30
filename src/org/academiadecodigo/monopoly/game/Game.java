
package org.academiadecodigo.monopoly.game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerSetInputScanner;

import org.academiadecodigo.monopoly.player.Player;
import org.academiadecodigo.monopoly.player.PlayerTest;


import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLOutput;
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



    public void init() {
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

    private void setupGame (){

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
            return;
        }

        if(option==3){

            System.out.println("The Game is now over.\n"+
                    player.getName()+" has decided to finish the game.");
            System.exit(1);
        }

        player.move(dice.rollTheDice());

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

    private void menuOptionBuy(Player player){
        System.out.println(player.getName()+ "you have moved to "+player.getCurrentPosition());
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
        }
        if(option ==2){
            menuForcedSell(player);
            return;
        }
        System.out.println(player.getName()+" has ended his turn.\n");
    }


    /**
     *
     *  menu Prompted IF player has to pay rent && has insifucient funds:
     *  lists player owned houses giving options to:
     *	- Select the house he wants to sell
     *
     *
     */


    private void menuForcedSell(Player player){
        Set<Integer> validOptions = new HashSet<>();

        int i;

        for ( i = 0; i <player.nrOfHouses(); i++) {
            validOptions.add(i+1);
        }

        validOptions.add(i);

        IntegerInputScanner menuOption = new IntegerSetInputScanner(validOptions);
        menuOption.setMessage("You will have to choose one house to sell " + player.getName()
                +", please select one of your houses : \n "+ player.getHouses());
        int option = prompt.getUserInput(menuOption);

        //for (int i = 0; i <player.nrOfHouses() ; i++) {
          //  if(i == (option - 1)){
                //TODO método getter LinkedList position of house in Player player.sellHouse(board.getHouse();
                player.sellHouse(option-1);
                System.out.println("### house removed ###\n");
            //}
        //}
    }

    public void playRound(Player player){
        //TODO
        playerMove(player);
    }

    private void playerMove(Player player) {

        menuUserRollDice(player);

        int currentPos = player.getCurrentPosition();

        System.out.println(player.getName() + ", you are now at house: " + board.getHouse(currentPos).getHouseName());

        if (board.getHouse(currentPos).isOwned() && !player.haveThisHouse(board.getHouse(currentPos))) {

            if (player.getBalance() < board.getHouse(currentPos).getRent()) {
                menuForcedSell(player);
            }
            player.removeMoney(board.getHouse(currentPos).getRent());

            return;
        }

        menuOptionBuy(player);
    }


    private void startPlay() {

        for (int i = 0; i <playersList.size() ; i++) {
            if(playersList.contains(playersList.get(playerTurn[i]))){ /** Jump player position if player has left */
                playRound(playersList.get(playerTurn[i]));
            }
        }
    }

    @Override
    public void run() {
        init();
    }
}
