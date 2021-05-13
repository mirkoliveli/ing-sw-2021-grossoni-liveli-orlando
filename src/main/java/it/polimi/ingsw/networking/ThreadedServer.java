package it.polimi.ingsw.networking;

import com.google.gson.Gson;
import it.polimi.ingsw.ServerMain;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.StagesQueue;
import it.polimi.ingsw.messages.FirstLoginMessage;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.MatchMultiPlayer;
import it.polimi.ingsw.model.Player;

import java.io.*;
import java.net.Socket;

public class ThreadedServer extends Thread {


    protected Socket clientSocket;
    protected int idPlayer;

    public ThreadedServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.idPlayer = 0;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void run(MatchMultiPlayer match) {

        if(match.getPlayers().size()==0){
            //lobbysetup
            firstLogin(match);
            GameState.setStartingPlayer(this.idPlayer);
            ServerMain.setIsLobbyCreated(true);
        }
        else{
            standardLogin(match);
        }


        BufferedReader inFromClient = null;
        PrintWriter outToTheClient = null;
        while (true) {

            //METORO CHE CONTROLLA SE È IL TURNO DEL GIOCATORE
//            CheckTurn();

            try {
                // Per ricevere una stringa aprire un input stream reader
                InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                inFromClient = new BufferedReader(isr);

                OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
                BufferedWriter bw = new BufferedWriter(osw);
                outToTheClient = new PrintWriter(bw, true);
                String str = inFromClient.readLine();

                if ((str == null) || str.equalsIgnoreCase("QUIT")) {
                    System.out.println("Closing " + clientSocket);
                    break;
                }

                //-----------------TEST DELLA CONNESIONE -> REPLICA DEL TESTO RICEVUTO------------------------//
                System.out.println("ANSWERING TO" + clientSocket.getInetAddress() + ":\n" + str);
                outToTheClient.println(str);
                StarterManager(str);
                str = inFromClient.readLine();


//                TurnToTheNext();

            } catch (IOException e) {
                return;
            }
        }
        try {
            clientSocket.close();
            outToTheClient.close();
            inFromClient.close();
            System.out.println("CLOSED!");
        } catch (IOException e) {
            System.out.println("ESPLOSO! KABOOM\n");
        }
    }

    public void StarterManager(String in) {
        String[] splitted = in.split("%;%");
        if (splitted.length > 0) {
            switch (splitted[0]) {
                case "0":
                    //0 crea una nuova partita
                    break;
                case "1":

                    //1 si unisce ad una partita preesistente
                    break;
                default:
                    //caso default
                    break;
            }
        }
    }

    /**
     * method that handles the first login of the game, that player needs to choose how large the lobby will be, and then decides what nickname they will have.
     * @param match given to handle the match data
     */
    public void firstLogin(MatchMultiPlayer match){
        Gson gson=new Gson();
        LoginMessage login=new LoginMessage(GameState.getJoinedPlayers(), true);
        String message= login.GenerateMessage().getMessage();
        BufferedReader inFromClient = null;
        PrintWriter messageToClient = null;
        try{

            OutputStreamWriter streamToClient = new OutputStreamWriter(clientSocket.getOutputStream());
            BufferedWriter bufferToClient = new BufferedWriter(streamToClient);
            messageToClient = new PrintWriter(bufferToClient, true);
            messageToClient.println(message);
        }catch(IOException e){
            System.out.println("connection error with user " + clientSocket.getInetAddress());
            System.exit(0);
        }
        //ricevo messaggio da user che contiene numero di giocatori totali e nome del giocatore.
        //se nome sbagliato setta in automatico a giocatore1 il nome
        try{
        InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
        inFromClient = new BufferedReader(isr);
        String clientAnswer = inFromClient.readLine();
        FirstLoginMessage answerFromClient=gson.fromJson(clientAnswer, FirstLoginMessage.class);
        if(answerFromClient.getName()!=null){
            match.AddPlayer(answerFromClient.getName());
        }
        else{match.AddPlayer("giocatore1");}
        idPlayer=match.getPlayers().size(); //give the id to the player
        GameState.setTotalPlayersNumber(answerFromClient.getNumberOfPlayersToWait()); //creates lobby

            //System.out.println(match.getPlayers().size());
            //System.out.println(match.getPlayers().get(0).getName());

        }catch(IOException e){
            System.out.println("error");}

    }

    /**
     * handles the connection of player 2,3 and 4
     * @param match
     */
    public void standardLogin(MatchMultiPlayer match){
        Gson gson=new Gson();
        LoginMessage login=new LoginMessage(GameState.getJoinedPlayers(), true);
        String message= login.GenerateMessage().getMessage();
        BufferedReader inFromClient = null;
        PrintWriter messageToClient = null;
        //sends an update to the client
        try{

            OutputStreamWriter streamToClient = new OutputStreamWriter(clientSocket.getOutputStream());
            BufferedWriter bufferToClient = new BufferedWriter(streamToClient);
            messageToClient = new PrintWriter(bufferToClient, true);
            messageToClient.println(message);
        }catch(IOException e){
            System.out.println("connection error with user " + clientSocket.getInetAddress());
            System.exit(0);
        }

        //receives the nickname and creates the player
        try {
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            inFromClient = new BufferedReader(isr);
            String clientAnswer = inFromClient.readLine();
            Message answerFromClient = gson.fromJson(clientAnswer, Message.class); //messaggio contiene nome del giocatore
            if (answerFromClient.getMessage() != null) {
                match.AddPlayer(answerFromClient.getMessage());
            } else {
                match.AddPlayer("giocatore" + match.getPlayers().size()+1); //assegna in automatico il nome giocatoreN al giocatore in questione
            }
            idPlayer = match.getPlayers().size(); //give the id to the player
            GameState.increaseJoinedPlayers(); //increase the number of players in the lobby
        }catch(IOException e){
                StagesQueue.setSomeoneLoggingIn(false);
                System.out.println("error");
        }

        System.out.println(match.getPlayers().get(match.getPlayers().size()-1).getName());
        System.out.println(match.getPlayers().size());

        StagesQueue.setSomeoneLoggingIn(false);
            //potrebbe mancare qualcosa
    }



//
//    public void CheckTurn() {
//        while (Server.getTurno() != idPlayer) {
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                System.out.println("Eh vabbè!1!");
//            }
//        }
//    }
//
//    public void TurnToTheNext () {
//        Server.setTurno(idPlayer+1);
//    }
}




