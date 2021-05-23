package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.ActionMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.TypeOfAction;
import it.polimi.ingsw.messages.chooseDepotMessage;
import it.polimi.ingsw.model.MatchMultiPlayer;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.networking.ThreadedServer;
import it.polimi.ingsw.utils.StaticMethods;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private ThreadedServer clientConnection;
    private MatchMultiPlayer match;
    private int idPlayer;
    private boolean turnEnding;
    private boolean mainAction;

    public ClientHandler(ThreadedServer clientIn) {
        clientConnection=clientIn;
        match=clientConnection.getMatch();
        idPlayer=clientConnection.getIdPlayer();
    }

    public void TurnPhase(){
        while(idPlayer!= GameState.getIdOfPlayerInTurn() && !GameState.isGameEndedPhase()){
            waitingMyTurn();
        }
            turnManager();


    }

    private void resetTurnStatus(){
        turnEnding=false;
        mainAction=false;
    }

    public void turnManager(){
        //send first message with game update, then expects an answer for client with the action chosen
        ActionMessage action=new ActionMessage(TypeOfAction.BEGIN_TURN);
        action.BeginTurn(match, idPlayer);
        clientConnection.messageToClient(StaticMethods.objToJson(action));

        while(!turnEnding){
        handleAnswer();
        }

    }

    public void waitingMyTurn(){
        try {
            //manderà uno status update al client con l'ultima azione effettuata
            Thread.sleep(1000);
        }catch (InterruptedException e){
            System.out.println("interrupted while sleeping");
        }
    }

    public void handleAnswer(){
        Gson gson =new Gson();
        String messageFromC="";
        try {
            messageFromC = clientConnection.messageFromClient();
        }catch (IOException e){
            System.out.println("client disconnected before sending TurnAction");
        }
        if(!messageFromC.contains("action")) {
            clientConnection.messageToClient("invalid message");
            return;
        }
        ActionMessage action= gson.fromJson(messageFromC, ActionMessage.class);
        switch (action.getAction()){
            case END_TURN:
                handleEndTurnRequest();
                break;
            case SWAP_DEPOTS:
                swapLevelsAction(action.getActionAsMessage());
                break;
            case BUY_A_CARD:
                //scelta ancora da gestire
                break;
            case GO_TO_MARKET:
                //scelta ancora da gestire
                break;
            case ACTIVATE_PRDUCTION:
                //scelta ancora da gestire
                break;
            case PLAY_OR_DISCARD_LEADER:
                //scelta ancora da gestire
                break;
            default:
                break;
        }
    }

    public ThreadedServer getClientConnection() {
        return clientConnection;
    }

    public void swapLevelsAction(String action){
        Gson gson= new Gson();
        int[] swapToDO= gson.fromJson(action, int[].class);
        boolean result=match.getPlayers().get(idPlayer).getBoard().getStorage().swapLevels(swapToDO[0], swapToDO[1]);

        if(result) clientConnection.messageToClient("Operation successful");
        else{ clientConnection.messageToClient("Operation denied");}
    }

    public void handleEndTurnRequest(){
        if(mainAction){
            turnEnding=true;
            clientConnection.messageToClient("Operation successful");
        }
        else{
            clientConnection.messageToClient("operation denied");
        }
    }


    public static int  handleNotStoredResources(Storage storage, boolean[] resourcesStatus, int[] resources, ClientHandler clientConnect){
        Gson gson=new Gson();
        for(int i=0; i<4; i++){
            if(resourcesStatus[i] && resources[i]>0) {
            chooseDepotMessage mex=new chooseDepotMessage(storage, i, resources[i]);
            //mando mex con depot liberi e un tipo di risorsa + quantità di quella risorsa
            clientConnect.getClientConnection().messageToClient(gson.toJson(mex));
            try {
                String answerFromClient = clientConnect.getClientConnection().messageFromClient();

                //ricevuto mex, analizzarlo e procedere con le operazioni

            }catch(IOException e){
                System.out.println("error in not stored resources (during marble action) needed to change later");
            }


            }
        }




        return 0;

    }

}
