package it.polimi.ingsw.networking;
//import it.polimi.ingsw.message.*;

import it.polimi.ingsw.ServerMain;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.StagesQueue;
import it.polimi.ingsw.model.MatchMultiPlayer;

import java.io.IOException;
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
        MatchMultiPlayer match=new MatchMultiPlayer();
        System.out.println("Server: started ");
        System.out.println("Server Socket: " + serverSocket);

        Socket clientSocket = null;




        //si connette il primo giocatore
        clientSocket = serverSocket.accept();
        System.out.println("Connection accepted: " + clientSocket);
        new ThreadedServer(clientSocket, match).start();


        while(!ServerMain.isLobbyCreated){
            try{

                Thread.sleep(5000);
            }catch (InterruptedException e){
                System.out.println("error with waiting time after first connection");
            }
        }
        GameState.setJoinedPlayers(1);
        System.out.println("STARTING LOBBY");

        try {
            do{
                //Accetta la prima connessione in coda e le dedica un thread
                clientSocket = serverSocket.accept();
                System.out.println("Connection accepted: " + clientSocket);
                StagesQueue.setSomeoneLoggingIn(true);
                new ThreadedServer(clientSocket, match).start();
                while(StagesQueue.isSomeoneLoggingIn()){
                    try{
                        Thread.sleep(5000);
                    }catch (InterruptedException e){
                        System.out.println("error with waiting time between connections!");
                    }
                }

            }while (GameState.getJoinedPlayers()!=GameState.getTotalPlayersNumber());


        } catch (IOException e) {
            System.err.println("queue failed");
            System.exit(1);
        }
        serverSocket.close();
    }


}
