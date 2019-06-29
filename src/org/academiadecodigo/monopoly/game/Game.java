
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
    private int[] playerTurn;
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
            openingGameChooser[i] = dice.randomizer();
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

    private void promptUserRollDice(Player player) {

        Set<Integer> validOptions = new HashSet<>();
        validOptions.add(1);
        validOptions.add(2);
        IntegerInputScanner menuOption = new IntegerSetInputScanner(validOptions);
        menuOption.setMessage(player.getName() + " it's your turn, now you can:\n" +
                "(1) Roll the dice and move.\n" +
                "(2) Quit  \n");

        int option = prompt.getUserInput(menuOption);

        if(option == 2){

        }
    }

    private void promptUserSellMenu(Player player){

        Set<Integer> validOptions = new HashSet<>();
        validOptions.add(1);
        validOptions.add(2);
        IntegerInputScanner menuOption = new IntegerSetInputScanner(validOptions);
        menuOption.setMessage(player.getName() + " these are your properties: \n" +
                "(1) I.\n" +
                "(2) Sell one property \n");

        int option = prompt.getUserInput(menuOption);

    }


    private void playerMove(Player player){

        int currentPos =player.getCurrentPosition();
        player.setCurrentPosition(player.getCurrentPosition()+(dice.randomizer()));
        System.out.println(player.getName()+" has moved from "+board.getHouseName(currentPos-1));

        player.move(dice.rollTheDice());

    }





    private void startPlay() {


        for (int i = 0; i <numberOfPlayers ; i++) {
            if(playersList.contains(playersList.get(i))){
            playRound(playersList.get(i));
            }
        }


    }

    public void playRound(Player player){
        //TODO



//        if (promptUserTurnMenu(playersList.get(playerTurn[0])) == 2) {
//            promptUserSellMenu(playersList.get(playerTurn[0]));
//
//        }
//        playerMove(playersList.get(playerTurn[0]));
    }



}

