package org.academiadecodigo.monopoly.game;

import org.academiadecodigo.monopoly.player.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {


    private static final int PORT_NUMBER = 9000;
    private ServerSocket serverSocket;


    public GameServer() {

        try {

            this.serverSocket = new ServerSocket(PORT_NUMBER);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void start() {


        try {
            Socket playerSocket = serverSocket.accept();

            //TODO socket no construtor de player

            //Thread client = new Thread(new Player(playerSocket));
            //client.start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
