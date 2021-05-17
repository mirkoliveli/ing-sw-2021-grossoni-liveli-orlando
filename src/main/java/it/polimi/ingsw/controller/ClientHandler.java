package it.polimi.ingsw.controller;

import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }


}
