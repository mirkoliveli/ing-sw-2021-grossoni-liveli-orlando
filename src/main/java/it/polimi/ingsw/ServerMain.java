package it.polimi.ingsw;

import it.polimi.ingsw.networking.Server;

import java.io.IOException;


public class ServerMain {

    public static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
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
