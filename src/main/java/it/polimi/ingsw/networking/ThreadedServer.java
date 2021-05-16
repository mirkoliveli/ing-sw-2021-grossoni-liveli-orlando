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

    private MatchMultiPlayer match;
    protected Socket clientSocket;
    protected int idPlayer;
    private OutputStreamWriter streamToClient;
    private BufferedWriter bufferToClient;
    private InputStreamReader inputFromClient;

    public ThreadedServer(Socket clientSocket, MatchMultiPlayer match) throws IOException {
        this.clientSocket = clientSocket;
        this.idPlayer = 0;
        this.match=match;
        try {
             streamToClient = new OutputStreamWriter(this.clientSocket.getOutputStream());
             bufferToClient = new BufferedWriter(streamToClient);
             inputFromClient = new InputStreamReader(this.clientSocket.getInputStream());
        }catch (IOException e){
            throw e;
        }
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void run() {

        //login section

        if(match.getPlayers().size()==0){
            //lobbysetup
            try {
                firstLogin(match);
            }catch (IOException e){
                System.out.println("error while setting up the lobby, please restart server");
                System.exit(1);
            }
            GameState.setStartingPlayer(this.idPlayer);
            ServerMain.setIsLobbyCreated(true);
        }

        else{
            try {
                standardLogin(match);
            }catch (IOException e){
                System.out.println("error while logging in");
                this.interrupt();

                //fare qualcosa per problema disconnesione
            }
        }

        //end of login

        waitingPlayersPhase();

        //start of gettingStartedPhase


        //messo per non killare subito il thread, così posso testare riconnessioni
            try {
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                System.out.println("error while waiting");
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
     * @throws IOException error with first login, process is terminated. restart server in case
     */
    public void firstLogin(MatchMultiPlayer match) throws IOException {
        Gson gson=new Gson();
        LoginMessage login=new LoginMessage(GameState.getJoinedPlayers(), true);
        String message= login.GenerateMessage().getMessage();

        //sends first message
        messageToClient(message);
        //ricevo messaggio da user che contiene numero di giocatori totali e nome del giocatore.
        //se nome sbagliato setta in automatico a giocatore1 il nome
        try{

        FirstLoginMessage answerFromClient=gson.fromJson(messageFromClient(), FirstLoginMessage.class);

        if(answerFromClient.getName()!=null){
            match.AddPlayer(answerFromClient.getName());
            messageToClient("Connesione riuscita!");
        }
        else{match.AddPlayer("giocatore1");
            messageToClient("Connesione riuscita!");}

        idPlayer=match.getPlayers().size(); //give the id to the player
        GameState.setTotalPlayersNumber(answerFromClient.getNumberOfPlayersToWait()); //creates lobby

            System.out.println("è entrato il giocatore " + idPlayer);
            System.out.println("il suo nickname è: " + match.getPlayers().get(match.getPlayers().size()-1).getName());

        }catch(IOException e){
            throw e;}

    }

    /**
     * handles the connection of player 2,3 and 4
     * @param match match currently being populated
     * @throws IOException error while trying to log in a player, thread is killed
     */
    public void standardLogin(MatchMultiPlayer match) throws IOException {
        Gson gson=new Gson();
        LoginMessage login=new LoginMessage(GameState.getJoinedPlayers(), true);
        String message= login.GenerateMessage().getMessage();
        //sends an update to the client
        messageToClient(message);


        //receives the nickname and creates the player
        try {
            Message answerFromClient = gson.fromJson(messageFromClient(), Message.class); //messaggio contiene nome del giocatore
            if (answerFromClient.getMessage() != null) {
                match.AddPlayer(answerFromClient.getMessage());
                messageToClient("Connesione riuscita!");
            } else {
                messageToClient("Connesione riuscita!");
                match.AddPlayer("giocatore" + (match.getPlayers().size()+1)); //assegna in automatico il nome giocatoreN al giocatore in questione
            }
            idPlayer = match.getPlayers().size(); //give the id to the player
            GameState.increaseJoinedPlayers(); //increase the number of players in the lobby
        }catch(IOException e){
                StagesQueue.setSomeoneLoggingIn(false);
                throw e;
        }

        System.out.println("è entrato il giocatore " + idPlayer);
        System.out.println("il suo nickname è: " + match.getPlayers().get(match.getPlayers().size()-1).getName());


        StagesQueue.setSomeoneLoggingIn(false);
            //potrebbe mancare qualcosa
    }


    /**
     * Sends a String message to the client.
     * @param message message sent
     */
    public void messageToClient(String message){
        PrintWriter messageToClient = null;
        messageToClient = new PrintWriter(bufferToClient, true);
        messageToClient.println(message);

    }

    /**
     * receives a String message from client and return the string
     * @return String message from client
     * @throws IOException if client disconnected or there's an error while receiving the message and IOEexceptions is thrown
     */
    public String messageFromClient() throws IOException {
        BufferedReader inFromClient = null;
        inFromClient = new BufferedReader(inputFromClient);
        return inFromClient.readLine();
    }

    public void sleeping(int millseconds){
        try{
            Thread.sleep(millseconds);
        }catch(InterruptedException e){
            System.out.println("failiure while sleeping!");
        }

    }

    public void waitingPlayersPhase(){
        while(!GameState.isGettingStartedPhase()){
            sleeping(1000);
            messageToClient("waiting other players...");
        }

        sleeping(500);
        messageToClient("next");

        try{

            System.out.println("giocatore "+ idPlayer + " is " + messageFromClient());

        }catch(IOException e){
            System.out.println("connection lost with client " + idPlayer);
            sleeping(100000);
        }
    }


}




