package it.polimi.ingsw.networking;
//import it.polimi.ingsw.message.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;

//--------------CONTERRÀ L'ELENCO SOCKET-NOMEGIOCATORE-----------------//
//    private Map<Socket, String> names = new HashMap<>();
//
//
//    public Map<Socket, String> getNames() {
//        return names;
//    }
//
//    public void setNames(Map<Socket, String> names) {
//        this.names = names;
//    }


//    public static int turno;
//    public static int getTurno() {
//        return turno;
//    }    public static void setTurno(int turno) {
//        this.turno = turno;
//    }


    public Server(int PORT) throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void execute() throws IOException {

        System.out.println("Server: started ");
        System.out.println("Server Socket: " + serverSocket);

        Socket clientSocket = null;
        BufferedReader in_fromClient = null;
        PrintWriter out_toClient = null;
        int idPlayer = 0;


        try {
            while (true) {
                //Accetta la prima connessione in coda e le dedica un thread
                clientSocket = serverSocket.accept();
                System.out.println("Connection accepted: " + clientSocket);
                new ThreadedServer(clientSocket, idPlayer).run();
            }
        } catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
        }
        serverSocket.close();
    }


}
