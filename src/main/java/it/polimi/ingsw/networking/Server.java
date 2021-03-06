package it.polimi.ingsw.networking;
//import it.polimi.ingsw.message.*;

import it.polimi.ingsw.ServerMain;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.StagesQueue;
import it.polimi.ingsw.controller.WaitingQueue;
import it.polimi.ingsw.model.MatchMultiPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private final ServerSocket serverSocket;


    /**
     * Methot that open the server socket on the port number PORT
     *
     * @param PORT,
     * @throws IOException
     */
    public Server(int PORT) throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    /**
     * The method that manage all the server logic
     *
     * @throws IOException
     */
    public void execute() throws IOException {
        ArrayList<ThreadedServer> clients = new ArrayList<>(4);
        MatchMultiPlayer match = new MatchMultiPlayer();
        System.out.println("Server: started ");
        System.out.println("Server Socket: " + serverSocket);

        Socket clientSocket = null;


        //si connette il primo giocatore
        clientSocket = serverSocket.accept();
        System.out.println("Connection accepted: " + clientSocket);
        clients.add(new ThreadedServer(clientSocket, match));
        clients.get(0).start();

        while (!ServerMain.isLobbyCreated) {
            try {

                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("error with waiting time after first connection");
            }
        }
        GameState.setJoinedPlayers(1);
        System.out.println("STARTING LOBBY");

        //lobby
        try {
            do {
                //Accetta la prima connessione in coda e le dedica un thread
                clientSocket = serverSocket.accept();
                System.out.println("Connection accepted: " + clientSocket);
                StagesQueue.setSomeoneLoggingIn(true);
                try {
                    clients.add(new ThreadedServer(clientSocket, match));
                } catch (IOException e) {
                    System.out.println("problem logging in player " + (GameState.getJoinedPlayers() + 1));
                    clients.remove(GameState.getJoinedPlayers());
                }
                int i = GameState.getJoinedPlayers();
                clients.get(GameState.getJoinedPlayers()).start();
                while (StagesQueue.isSomeoneLoggingIn()) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("error with waiting time between connections!");
                    }
                }

                if (!clients.get(i).isAlive()) {
                    System.out.println("Removing player: " + i);
                    clients.remove(i);
                }
            } while (GameState.getJoinedPlayers() != GameState.getTotalPlayersNumber());


        } catch (IOException e) {
            System.err.println("queue failed");
            System.exit(1);
        }
        new WaitingQueue(GameState.getTotalPlayersNumber());
        GameState.setPhase(1);
        System.out.println("\nuscito da login while\n");

        while (!WaitingQueue.allFinished()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("error with waiting during selection phase!");
            }
        }


        GameState.setPhase(2);


        //finisce la fase di login, inzio del game vero e proprio


    }


}
