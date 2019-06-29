
package org.academiadecodigo.monopoly.game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerSetInputScanner;

import org.academiadecodigo.monopoly.player.Player;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Game {

    private final int INITIAL_MONEY = 16000;
    private Board board;
    private LinkedList<Player> playersList;
    private Dice dice;
    private int numberOfPlayers;
    private int [] playerTurn;
    private Prompt prompt;
    private int playCounter;


    /**
     *
     * Instanciar:
     *              Board;
     *              Dice;
     *
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


        while (board.isLive || playCounter <10) {
            startPlay();
            playCounter++;
        }
    }

    /**
     *
     * Instantiate new Player's on LinkedList 'players'
     * Set playing order
     * Distribute initial money
     *
     *
     *
     *
     * */

    private void setupGame (){

        int[] openingGameChooser = new int[numberOfPlayers];
        playersList = new LinkedList<>();

        for (int i = 0; i < numberOfPlayers ; i++) {

            playersList.add(i,new Player("Player - "+i));

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
        distributeInitialMoney();
    }

    private void distributeInitialMoney(){
        for (Player list: playersList) {
            list.addMoney(INITIAL_MONEY);
        }
    }

    private void menuUserRollDice(Player player) {

        Set<Integer> validOptions = new HashSet<>();
        validOptions.add(1);
        validOptions.add(2);
        validOptions.add(3);

        IntegerInputScanner menuOption = new IntegerSetInputScanner(validOptions);
        menuOption.setMessage(player.getName() + " it's your turn, now you can:\n" +
                "(1) Roll the dice and move.\n" +
                "(2) Leave Game  \n" +
                "(3) Force  game shutdown.\n");
        int option = prompt.getUserInput(menuOption);

        if(option == 1){
            player.move(dice.rollTheDice());
            return;
        }
        //TODO QUIT GAME
    }

    private void menuOptionBuy(Player player){

        Set<Integer> validOptions = new HashSet<>();
        validOptions.add(1);
        validOptions.add(2);
        validOptions.add(3);

        IntegerInputScanner menuOption = new IntegerSetInputScanner(validOptions);
        menuOption.setMessage(player.getName() + " it's your turn, now you can:\n" +
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
    }
        System.out.println();
    }


    private void menuForcedSell(Player player){

        Set<Integer> validOptions = new HashSet<>();


        for (int i = 0; i <player.nrOfHouses(); i++) {
            validOptions.add(i+1);
        }

        IntegerInputScanner menuOption = new IntegerSetInputScanner(validOptions);
        menuOption.setMessage(player.getName() +" has these houses : \n "+ player.getHouses());
        int option = prompt.getUserInput(menuOption);

        for (int i = 0; i <player.nrOfHouses() ; i++) {
            if(i == (option - 1)){
                //TODO método getter LinkedList position of house in Player player.sellHouse(board.getHouse();
                System.out.println("### house removed ###\n");
            }
        }
    }

    public void playRound(Player player){
        //TODO
        playerMove(playersList.get(playerTurn[0]));
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
}

