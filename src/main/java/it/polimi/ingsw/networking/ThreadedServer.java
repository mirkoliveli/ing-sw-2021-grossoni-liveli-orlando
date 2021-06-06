package it.polimi.ingsw.networking;

import com.google.gson.Gson;
import it.polimi.ingsw.ServerMain;
import it.polimi.ingsw.controller.ClientHandler;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.controller.StagesQueue;
import it.polimi.ingsw.controller.WaitingQueue;
import it.polimi.ingsw.messages.FirstLoginMessage;
import it.polimi.ingsw.messages.GettingStartedMessage;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.MatchMultiPlayer;
import it.polimi.ingsw.model.exceptions.GameIsEnding;
import it.polimi.ingsw.utils.StaticMethods;

import java.io.*;
import java.net.Socket;

public class ThreadedServer extends Thread {

    protected Socket clientSocket;
    protected int idPlayer;
    private final MatchMultiPlayer match;
    private final OutputStreamWriter streamToClient;
    private final BufferedWriter bufferToClient;
    private final InputStreamReader inputFromClient;
    private BufferedReader inFromClient;

    public ThreadedServer(Socket clientSocket, MatchMultiPlayer match) throws IOException {
        this.clientSocket = clientSocket;
        this.idPlayer = 0;
        this.match = match;
        try {
            streamToClient = new OutputStreamWriter(this.clientSocket.getOutputStream());
            bufferToClient = new BufferedWriter(streamToClient);
            inputFromClient = new InputStreamReader(this.clientSocket.getInputStream());
            inFromClient = new BufferedReader(inputFromClient);

        } catch (IOException e) {
            throw e;
        }
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void run() {

        //login section

        if (match.getPlayers().size() == 0) {
            //lobbysetup
            try {
                firstLogin(match);
            } catch (IOException e) {
                System.out.println("error while setting up the lobby, please restart server");
                System.exit(1);
            }
            GameState.setStartingPlayer(this.idPlayer);
            ServerMain.setIsLobbyCreated(true);
        } else {
            try {
                standardLogin(match);
            } catch (IOException e) {
                System.out.println("error while logging in player " + idPlayer);
                return;


                //fare qualcosa per problema disconnesione
            }
        }

        //end of login

        waitingPlayersPhase();

        //start of gettingStartedPhase

        this.GettingStartedPhaseManager();

        //waiting to start turns

        settingUpGame();

        ClientHandler clientHandler=new ClientHandler(this);

        while(!GameState.isTurnPhase()) sleeping(1000);


        clientHandler.TurnPhase();

        //messo per non killare subito il thread, così posso testare riconnessioni
        try {
            System.out.println("game finished");
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            System.out.println("error while waiting");
        }
    }


    /**
     * method that handles the first login of the game, that player needs to choose how large the lobby will be, and then decides what nickname they will have.
     *
     * @param match given to handle the match data
     * @throws IOException error with first login, process is terminated. restart server in case
     */
    public void firstLogin(MatchMultiPlayer match) throws IOException {
        Gson gson = new Gson();
        LoginMessage login = new LoginMessage(GameState.getJoinedPlayers(), true);
        String message = login.GenerateMessage().getMessage();

        //sends first message
        messageToClient(message);
        //ricevo messaggio da user che contiene numero di giocatori totali e nome del giocatore.
        //se nome sbagliato setta in automatico a giocatore1 il nome
        try {

            FirstLoginMessage answerFromClient = gson.fromJson(messageFromClient(), FirstLoginMessage.class);

            if (answerFromClient.getName() != null) {
                match.AddPlayer(answerFromClient.getName());
                messageToClient("Connesione riuscita!");
            } else {
                match.AddPlayer("giocatore1");
                messageToClient("Connesione riuscita!");
            }

            idPlayer = match.getPlayers().size(); //give the id to the player
            GameState.setTotalPlayersNumber(answerFromClient.getNumberOfPlayersToWait()); //creates lobby

            System.out.println("è entrato il giocatore " + idPlayer);
            System.out.println("il suo nickname è: " + match.getPlayers().get(match.getPlayers().size() - 1).getName());

        } catch (IOException e) {
            throw e;
        }

    }

    /**
     * handles the connection of player 2,3 and 4
     *
     * @param match match currently being populated
     * @throws IOException error while trying to log in a player, thread is killed
     */
    public void standardLogin(MatchMultiPlayer match) throws IOException {
        Gson gson = new Gson();
        LoginMessage login = new LoginMessage(GameState.getJoinedPlayers(), true);
        String message = login.GenerateMessage().getMessage();
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
                match.AddPlayer("giocatore" + (match.getPlayers().size() + 1)); //assegna in automatico il nome giocatoreN al giocatore in questione
            }
            idPlayer = match.getPlayers().size(); //give the id to the player
            GameState.increaseJoinedPlayers(); //increase the number of players in the lobby
        } catch (IOException e) {
            StagesQueue.setSomeoneCrashed(true);
            StagesQueue.setSomeoneLoggingIn(false);
            throw e;
        }

        System.out.println("è entrato il giocatore " + idPlayer);
        System.out.println("il suo nickname è: " + match.getPlayers().get(match.getPlayers().size() - 1).getName());


        StagesQueue.setSomeoneLoggingIn(false);
        //potrebbe mancare qualcosa
    }


    /**
     * Sends a String message to the client.
     *
     * @param message message sent
     */
    public void messageToClient(String message) {
        PrintWriter messageToClient = null;
        messageToClient = new PrintWriter(bufferToClient, true);
        messageToClient.println(message);

    }

    /**
     * receives a String message from client and return the string
     *
     * @return String message from client
     * @throws IOException if client disconnected or there's an error while receiving the message and IOEexceptions is thrown
     */
    public String messageFromClient() throws IOException {
        return inFromClient.readLine();
    }


    /**
     * small method used to avoid using the try catch for calling the sleep method.
     * @param millseconds
     */
    public void sleeping(int millseconds) {
        try {
            Thread.sleep(millseconds);
        } catch (InterruptedException e) {
            System.out.println("failiure while sleeping! " + idPlayer);
        }

    }

    /**
     * handles the wait while other players are logging in.
     */
    public void waitingPlayersPhase() {
        while (!GameState.isGettingStartedPhase()) {
            sleeping(1000);
            messageToClient("waiting other players...");
        }

        sleeping(500);
        messageToClient("next");

        try {

            System.out.println("giocatore " + idPlayer + " is " + messageFromClient());

        } catch (IOException e) {
            System.out.println("connection lost with client " + idPlayer);
            sleeping(100000);
        }
    }

    /**
     * method that manages the getting started phase, with the first message the client is informed about what he has to chose from, then
     * he answers with the leader Id's selected and with the resources that he chose (if needed).
     * <br>
     * the server then executes the requests, in particular the resources are stored automatically in the largest depots,
     * and for the fourth player the resources are stored also in the second if he chooses 2 different types of resources.
     */
    public void GettingStartedPhaseManager() {
        Gson gson = new Gson();
        messageToClient(new GettingStartedMessage(match, idPlayer).getMessageAsString());
        try {
            //get answer
            GettingStartedMessage messageFromC = gson.fromJson(messageFromClient(), GettingStartedMessage.class);
            //set leaders
            match.getPlayers().get(idPlayer - 1).setLeaderCard1(match.getLeaderDeck(), messageFromC.getCardID()[0]);
            match.getPlayers().get(idPlayer - 1).setLeaderCard2(match.getLeaderDeck(), messageFromC.getCardID()[1]);

            //set additional resources and move faithtrack
            if (idPlayer > 1) {
                //secondo e terzo hanno diritto ad una sola risorsa di partenza
                if (idPlayer != 4) {
                    match.getPlayers().get(idPlayer - 1).getBoard().getStorage().getLevel(3).setResourceType(StaticMethods.IntToTypeOfResource(messageFromC.getResources()[0]));
                    match.getPlayers().get(idPlayer - 1).getBoard().getStorage().getLevel(3).setQuantity(1);
                    //solo il terzo e quarto giocatore avanza di una casella su faithtrack
                    if (idPlayer == 3) {
                        try {
                            match.MoveInFaithTrack(1, idPlayer);
                        } catch (GameIsEnding e) {
                            System.out.println("IMPOSSIBLE!!!!");
                        }
                    }

                }
                //il quarto ha diritto a due risorse
                else {
                    int switcher = 1;
                    if (messageFromC.getResources()[0] == messageFromC.getResources()[1]) switcher = 0;
                    if (switcher == 1) {
                        match.getPlayers().get(idPlayer - 1).getBoard().getStorage().getLevel(2).setResourceType(StaticMethods.IntToTypeOfResource(messageFromC.getResources()[0]));
                        match.getPlayers().get(idPlayer - 1).getBoard().getStorage().getLevel(2).setQuantity(1);
                    }
                    match.getPlayers().get(idPlayer - 1).getBoard().getStorage().getLevel(3).setResourceType(StaticMethods.IntToTypeOfResource(messageFromC.getResources()[0]));
                    match.getPlayers().get(idPlayer - 1).getBoard().getStorage().getLevel(3).setQuantity(1);
                    try {
                        match.MoveInFaithTrack(1, idPlayer);
                    } catch (GameIsEnding e) {
                        System.out.println("IMPOSSIBLE!!!!");
                    }
                }
            }

            //viene mandata la risposta al cliente
            messageToClient("Everything was set up! Now please wait for the other players to finish!");

        } catch (IOException e) {
            System.out.println("giocatore " + idPlayer + " si è disconnesso durante getting started phase");

        }
        WaitingQueue.PlayerFinished(idPlayer);
    }


    /**
     * handles the waiting time between the selection of leaders and additional starting resources, sends a message every second to the client
     * while waiting for the game to start, after the game is started sends a message stating which player is starting. In case the message is sent to the starting player it's specified that
     * he is starting his turn shortly. The messages sent by the server are meant to be used for printing as they are and for the view to identify which message need to be sent back as ack.
     */
    public void settingUpGame(){
        while(!GameState.isTurnPhase()){
            sleeping(1000);
            messageToClient("The game Will start soon...");
        }
        if(idPlayer!=GameState.getIdOfPlayerInTurn())
        messageToClient("Everything was set up correctly, the first player taking is turn is: " +match.getPlayers().get(GameState.getIdOfPlayerInTurn()-1).getName());
        else{
            messageToClient("You are the first player! the game will proceed and start your turn!");
        }
        try {
            System.out.println(messageFromClient());
        }catch(IOException e){
            System.out.println("client " + idPlayer + " disconnected");
        }
    }

    public BufferedReader getInFromClient() {
        return inFromClient;
    }

    public MatchMultiPlayer getMatch() {
        return match;
    }

    public BufferedWriter getBufferToClient() {
        return bufferToClient;
    }

    public InputStreamReader getInputFromClient() {
        return inputFromClient;
    }

    public OutputStreamWriter getStreamToClient() {
        return streamToClient;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }


}




