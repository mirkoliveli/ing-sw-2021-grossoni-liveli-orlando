package it.polimi.ingsw.controller;

import it.polimi.ingsw.networking.Client;

import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket client;

    public ClientHandler(Socket client){
         this.client=client;
    }





}
