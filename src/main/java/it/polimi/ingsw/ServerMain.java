package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.networking.Server;

import java.io.IOException;


public class ServerMain {

    public static final int PORT = 1234;
    public static boolean isLobbyCreated = false;

    public static void setIsLobbyCreated(boolean isLobbyCreated) {
        ServerMain.isLobbyCreated = isLobbyCreated;
    }

    public static void main(String[] args) throws IOException {
        GameState gamestate = new GameState();

        try {
            Server server = new Server(PORT);
            server.execute();
        } catch (IOException e) {
            // STAMPA MESSAGGIO D'ERRORE
            System.err.println("Can't start server: ");
            System.err.println(e.getMessage());
        }

    }

}
