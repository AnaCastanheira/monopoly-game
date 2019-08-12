package org.academiadecodigo.monopoly.game;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerSetInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.monopoly.house.House;
import org.academiadecodigo.monopoly.player.Player;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class Game extends Thread {

    private final int INITIAL_MONEY = 16000;
    private final int PORT = 9999;
    private Board board;
    private LinkedList<Player> playersList;
    private Dice dice;
    private int numberOfPlayers;
    private int[] playerTurn;
    private Prompt prompt;
    private int playCounter;
    private ServerSocket serverSocket;


    /**
     * Instantiate:
     * Board;
     * Dice;
     * Initialize:
     * player Order array;
     * Prompt;
     * playCounter control variable;
     */
    public Game(int numberOfPlayers) {

        this.numberOfPlayers = numberOfPlayers;
        board = new Board();
        dice = new Dice();
        playerTurn = new int[numberOfPlayers];
        playCounter = 0;

    }


    public synchronized void init() throws IOException {

        setupGame();

        while (playCounter < 20) {

            playCounter++;
        }

    }


    /**
     * Instantiate new Player's on LinkedList 'players'
     * Set an array with Players index distributed by playing order
     * invoke method Distribute initial money
     */
    private synchronized void setupGame() throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] openingGameChooser = new int[numberOfPlayers];
        playersList = new LinkedList<>();

        for (int i = 0; i < numberOfPlayers; i++) {

            try {
                super.run();
                playersList.add(i, new Player(Player.PlayerType.values()[i], serverSocket.accept()));

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(playersList.get(i).getPlayerType().getName() + " is Connected");




            prompt = new Prompt(playersList.get(i).getPlayerSocket().getInputStream(),
                    new PrintStream(playersList.get(i).getPlayerSocket().getOutputStream()));


            StringInputScanner whoRU = new StringInputScanner();
            whoRU.setMessage("What's your name bitch?");
            playersList.get(i).setName(prompt.getUserInput(whoRU));
            String string = playersList.get(i).getName() + " is the character: " + playersList.get(i).getPlayerType();
            broadcast("Welcome " + playersList.get(i).getName()+ "!");

            System.out.println("You just set the name");

            broadcast(playersList.get(i).getName());

            broadcast(string);


            openingGameChooser[i] = dice.rollTheDice();
            playerTurn[i] = i;

        }

        int temp;
        int temp2;
        for (int i = 0; i < openingGameChooser.length; i++) {
            for (int j = i + 1; j < openingGameChooser.length; j++) {

                if (openingGameChooser[i] > openingGameChooser[j]) {

                    temp = openingGameChooser[i];
                    temp2 = playerTurn[i];

                    openingGameChooser[i] = openingGameChooser[j];
                    playerTurn[i] = playerTurn[j];

                    openingGameChooser[j] = temp;
                    playerTurn[j] = temp2;
                }
            }
        }
        board.buildBoard();
        distributeInitialMoney();
        TextArt.welcomeMessage();
        new Thread();

        for (int i = 0; i < numberOfPlayers; i++) {
            startPlay(playersList.get(i));
        }


    }


    /**
     * Distribute initial money to all players
     */
    private synchronized void distributeInitialMoney() {
        for (Player list : playersList) {
            list.addMoney(INITIAL_MONEY + list.getPlayerType().getBufferedInitialMoney());
        }
    }


    /**
     * menu Prompted before player rolls dice:
     * gives options to:
     * - Roll the dice ( and ask Player to move himself )
     * - Remove himeself from game
     * - ForceShutdown the whole game
     */
    private synchronized void menuUserRollDice(Player player) {



        String s = "It's " + player.getName() + "'s turn!" + "  \n" + "You are currently in: " + board.getHouse(player.getCurrentPosition()).getHouseName() + "\n";
        System.out.println(s);
        broadcast(s);

        Set<Integer> validOptions = new HashSet<>();
        validOptions.add(1);
        validOptions.add(2);
        validOptions.add(3);

        IntegerInputScanner menuOption = new IntegerSetInputScanner(validOptions);
        menuOption.setMessage(" What do you wanna do " + player.getName() + "\n" +
                "(1) Roll the dice and move.\n" +
                "(2) Leave Game  \n" +
                "(3) Force  game shutdown.\n");
        int option = player.prompt.getUserInput(menuOption);

        if (option == 2) {

            playersList.remove(player);

            String s1 = player.getName() + " is a little bitch and quit the game.";
            System.out.println(s1);
            broadcast(s1);
            return;
        }

        if (option == 3) {

            String s2 = "The Game is now over.\n" + player.getName() + " has decided to finish the game.\n";
            System.out.println(s2);
            broadcast(s2);

            System.exit(1);

        }
        int diceValue = dice.rollTheDice();
        player.move(diceValue);

    }


    /**
     * menu Prompted after the player Rolls the dice,
     * IF the house where he isn't owned
     * gives options to:
     * - Buy the house
     * - Sell a house
     * - End his turn
     */
    private synchronized void menuOptionBuy(Player player) throws IOException {

        String s3 = player.getName() + " threw : " + dice.getSteps() + "and you are in: " + board.getHouse(player.getCurrentPosition()).getHouseName() + ".";
        System.out.println(s3);
        broadcast(s3);

        Set<Integer> validOptions = new HashSet<>();
        validOptions.add(1);
        validOptions.add(2);
        validOptions.add(3);

        IntegerInputScanner menuOption = new IntegerSetInputScanner(validOptions);
        menuOption.setMessage(" What do you wanna do :" + player.getName() + "\n" +
                "(1) Buy\n" +
                "(2) Sell\n" +
                "(3) End Turn\n");

        int option = player.prompt.getUserInput(menuOption);

        if (option == 1) {
            if (player.getBalance() < board.getHouse(player.getCurrentPosition()).getValue()) {
                System.out.println("AHAH You don't have any cash");
                //TODO WANNA SELL ONE ??????????????????????
                menuOptionBuy(player);
            }
            player.addHouse(board.getHouse(player.getCurrentPosition()));

            String s4 = player.getName() + "Congrats you just bought :" + board.getHouse(player.getCurrentPosition()).getHouseName();
            System.out.println(s4);
            broadcast(s4);

        }
        if (option == 2) {
            menuForcedSell(player);
        }

        String s5 = player.getName() + " has ended his turn.\n";
        System.out.println(s5);
        broadcast(s5);
    }


    /**
     * menu Prompted IF player has to pay rent && has insifucient funds:
     * lists player owned houses giving options to:
     * - Select the house he wants to sell
     */
    private synchronized void menuForcedSell(Player player) throws IOException {

        Set<Integer> validOptions = new HashSet<>();

        int i;

        for (i = 0; i < player.nrOfHouses(); i++) {
            validOptions.add(i + 1);
        }

        validOptions.add(player.nrOfHouses() + 1);

        IntegerInputScanner menuOption = new IntegerSetInputScanner(validOptions);
        menuOption.setMessage("Choose one house to sell " + player.getName()
                + ", please select one of your houses : \n " + player.getHouses() + "(" + (player.nrOfHouses() + 1) + ") Go Back\n");
        int option = player.prompt.getUserInput(menuOption);

        if (option == (player.nrOfHouses() + 1)) {
            playerMove(player);
        }

        //TODO método getter LinkedList position of house in Player player.sellHouse(board.getHouse();

        player.sellHouse(option - 1);
        System.out.println("### house removed ###\n");
        //}
        //}
    }

    public synchronized void playRound(Player player) throws IOException {
        //TODO
        for (int i = 0; i < numberOfPlayers; i++) {
            menuUserRollDice(playersList.get(i));
            playerMove(playersList.get(i));
        }

    }

    private synchronized void playerMove(Player player) throws IOException {


        int currentPos = player.getCurrentPosition();

        String s6 = player.getName() + "You are currently at : " + board.getHouse(currentPos).getHouseName() +
                "\n Your current balance is " + player.getBalance();
        System.out.println(s6);
        whisper(s6, player);

        if (player.getPlayerType().getName().equals(Player.PlayerType.DRUG_DEALER_DOUG.getName()) && board.getHouse(currentPos).getHouseType().equals(House.HouseType.WHITE)) {
            // TODO: 30/06/2019 drug dealer perk implemented
            player.addMoney(player.getPlayerType().getWhiteHouseMoney());
            System.out.println(player.getPlayerType().getName() + " won " + player.getPlayerType().getWhiteHouseMoney());
        }

        if ((board.getHouse(currentPos).isOwned()) && (!player.haveThisHouse(board.getHouse(currentPos)))) {
            String s7 = "This house is taken";
            System.out.println(s7);
            whisper(s7, player);

            if (player.getBalance() < board.getHouse(currentPos).getRent()) {
                menuForcedSell(player);
            }

            if (player.getPlayerType().getName().contains(Player.PlayerType.ROXANNE.getName())) {
                // TODO: 30/06/2019 desconto na renda aplicado
                player.removeMoney(board.getHouse(currentPos).getRent() - player.getPlayerType().getDiscountRent());
                System.out.println(player.getPlayerType().getName() + " payed " + board.getHouse(currentPos).getRent() * player.getPlayerType().getDiscountRent());
                return;
            }
            player.removeMoney(board.getHouse(currentPos).getRent());

            for (Player list : playersList) {
                if (list.haveThisHouse(board.getHouse(currentPos))) {
                    list.removeMoney(board.getHouse(currentPos).getRent());
                }
            }


            String s8 = player.getName() + " payed rent: " + board.getHouse(currentPos).getRent();
            System.out.println(s8);
            broadcast(s8);
            return;
        }

        menuOptionBuy(player);
    }


    private synchronized void startPlay(Player player) throws IOException {

        for (int i = 0; i < playersList.size(); i++) {
            if (playersList.contains(playersList.get(playerTurn[i]))) { /** Jump player position if player has left */
                playRound(playersList.get(playerTurn[i]));
                new Thread();
            }
        }
    }

    @Override
    public void run() {
        try {
            super.run();
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void broadcast(String string) {

        PrintWriter printWriter;

        for (int i = 0; i < playersList.size(); i++) {

            try {
                printWriter = new PrintWriter(playersList.get(i).getPlayerSocket().getOutputStream());
                printWriter.println(string);
                printWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void whisper(String string, Player player) {

        PrintWriter printWriter;

        try {
            printWriter = new PrintWriter(player.getPlayerSocket().getOutputStream());
            printWriter.println(string);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}