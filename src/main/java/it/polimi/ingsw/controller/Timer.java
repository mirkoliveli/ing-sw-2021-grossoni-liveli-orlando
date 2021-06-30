package it.polimi.ingsw.controller;

import java.io.IOException;
import java.net.Socket;

public class Timer extends Thread {
    private  int timeUntilDisconnection;
    private Socket connection;

    public Timer(Socket socket){
        timeUntilDisconnection=60000;
        connection=socket;
    }

    public Timer(Socket socket, int timer){
        timeUntilDisconnection=timer;
        connection=socket;
    }

    public void run(){
        try{
            Thread.sleep(timeUntilDisconnection);
            System.out.println("Client has been kicked");
            connection.close();
        }catch(InterruptedException e){
            System.out.println("timer stopped");
        }catch(IOException e){
            System.out.println("IO exception while closing socket");
        }

    }

}
