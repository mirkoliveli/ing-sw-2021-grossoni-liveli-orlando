package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.ActionMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.TypeOfAction;
import it.polimi.ingsw.messages.chooseDepotMessage;
import it.polimi.ingsw.model.MatchMultiPlayer;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.exceptions.GameIsEnding;
import it.polimi.ingsw.networking.ThreadedServer;
import it.polimi.ingsw.utils.Generic_intANDboolean;
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

    /**
     * method that should handle all the turnPhase section of the game.
     */
    public void TurnPhase(){
        while(idPlayer!= GameState.getIdOfPlayerInTurn() && !GameState.isGameEndedPhase()){
            waitingMyTurn();
        }
        System.out.println("sei in turnphase prima di turn manager");
            turnManager();


    }

    private void resetTurnStatus(){
        turnEnding=false;
        mainAction=false;
    }


    /**
     * general method that handles the turn of a player, by sending an update to the client as soon as his turn starts, then by managing the messages that are sent to the server by the client
     */
    public void turnManager(){
        Gson gson=new Gson();
        //send first message with game update, then expects an answer for client with the action chosen
        ActionMessage action=new ActionMessage(TypeOfAction.BEGIN_TURN);
        GameStatusUpdate status=gson.fromJson(StaticMethods.GameStatusString(match, idPlayer), GameStatusUpdate.class);
        action.BeginTurn(StaticMethods.objToJson(status));
        clientConnection.messageToClient(StaticMethods.objToJson(action));


        while(!turnEnding){
            System.out.println("sei in turn manager prima di handleanswer");
        handleAnswer();
        }

    }


    /**
     * method that handles the waiting time between the client turn and other clients turn
     */
    //for now it just waits, but soon it will send some gameUpdate messages to the client.
    public void waitingMyTurn(){
        try {
            //manderà uno status update al client con l'ultima azione effettuata
            Thread.sleep(1000);
        }catch (InterruptedException e){
            System.out.println("interrupted while sleeping");
        }
    }


    /**
     * this is the method that handles the action the client requests to do. it receives a message from the client containing the action that they want to perform, then manages that action
     * by either performing it by calling the right methods or by sending a denial. After the action is managed a gameUpdate is sent to the client.
     */
    public void handleAnswer(){
        Gson gson =new Gson();
        String messageFromC="";
        try {
            messageFromC = clientConnection.messageFromClient();
            System.out.println(messageFromC);
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
                if(!mainAction){
                    goToMarketAction(action.getActionAsMessage());
                    mainAction=true;
                }
                else{
                    clientConnection.messageToClient("Operation denied");
                }
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
        clientConnection.messageToClient(StaticMethods.GameStatusString(match, idPlayer));
    }

    public ThreadedServer getClientConnection() {
        return clientConnection;
    }


    /**
     * method that performs the swap levels action after it's communicated to the server by the client, either way if the selection can be done the operation is completed and an "operation succesful" message is sent
     * if the operation cannot be completed (because the action violates some game rules) an "operation denied" message is sent instead
     * @param action contains the action sent by the client as a JSON string
     */
    public void swapLevelsAction(String action){
        Gson gson= new Gson();
        int[] swapToDO= gson.fromJson(action, int[].class);
        boolean result=match.getPlayers().get(idPlayer-1).getBoard().getStorage().swapLevels(swapToDO[0], swapToDO[1]);

        if(result) clientConnection.messageToClient("Operation successful");
        else{ clientConnection.messageToClient("Operation denied");}
    }


    /**
     * method that handles the end of turn of a player, if the main action has already been performed a ""operation succesful" message is sent
     * if the main action has not been performed yet a "operation denied" message is sent instead
     */
    public void handleEndTurnRequest(){
        if(mainAction){
            turnEnding=true;
            clientConnection.messageToClient("Operation successful");
        }
        else{
            clientConnection.messageToClient("operation denied");
        }
    }


    /**
     * handles the action that interacts with the marble market. It receives  a string that contains the JSON file of the action sent by the client.
     * the action is then performed (given a valid check CLIENT-side for the basic inputs of selecting the section of the market the action is always allowed by the rules, so no check is needed)
     * the action can cause 3 interactions:
     * <br>
     * 1) if the client has a leader card that modifies the white ball given resource a new interaction with the client is needed. <br>
     * 2) if some resources do not have an already placed depotLevel in the storage the client is required to decide what to do with the given resource (either store in an available level or discard them)<br>
     * 3) discarding resources causes the other players to gain faith points, that can cause a vatican report.<br>
     * <br>
     * after the action is completed an operation completed message is sent to the client
     * @param string
     */
    public void goToMarketAction(String string){
        Gson gson= new Gson();
        Generic_intANDboolean action;
        action=gson.fromJson(string, Generic_intANDboolean.class);
        System.out.println(action.getNumber());
        //ancora non gestito il caso in cui abbia il leader delle palline
        int[] resourcesGained=match.getMarket().ConversionToArray(!action.isChoice(), action.getNumber()-1);

        // stampa resources
        for(int i=0; i<5; i++){
            System.out.println(i + " resource " + resourcesGained[i]);
        }
        match.getMarket().ChangeBoard(!action.isChoice(), action.getNumber());
        if(resourcesGained[4]>0){
            try {
                match.MoveInFaithTrack(1, idPlayer);
            }catch(GameIsEnding e){
                System.out.println("gestisci dopo");
            }
        }
        int[] actualResources={resourcesGained[0], resourcesGained[1], resourcesGained[2], resourcesGained[3]};
        for(int i=0; i<4; i++){
            System.out.println(i + " resource " + actualResources[i]);
        }
        match.getPlayers().get(idPlayer-1).getBoard().getStorage().IncreaseResources(actualResources, this);
        clientConnection.messageToClient("action completed");

    }

    /**
     * method that handles the case 2) of the goToMarketAction, that requires a player to select a new level for a type of resource that is not stored in any storage level at that given moment. the interaction is
     * repeated for every resource that needs to be stored or discarded, until either the storage is full of other types of resources or the all the types are handled. after the action is finished a "end" message is sent to the client
     * @param storage storage of the client
     * @param resourcesStatus a type of vector generated in the IncreaseResources method inside storage (where this method is called). It's referred as NullLevel inside that section.
     * @param resources the array has the quantity of resources to be stored or discarded
     * @param clientConnect connection to the client is needed to handle all the exchange of messages
     */
    public static void  handleNotStoredResources(Storage storage, boolean[] resourcesStatus, int[] resources, ClientHandler clientConnect){
        Gson gson=new Gson();
        int discarded=0;
        for(int i=0; i<4; i++){
            if(!resourcesStatus[i] && storage.EmptyDepot() && resources[i]>0) {
                chooseDepotMessage mex=new chooseDepotMessage(storage, i, resources[i]);

                //mando mex con depot liberi e un tipo di risorsa + quantità di quella risorsa

                clientConnect.getClientConnection().messageToClient(gson.toJson(mex));

                try {
                    String answerFromClient = clientConnect.getClientConnection().messageFromClient();

                    //ricevuto mex, analizzarlo e procedere con le operazioni

                    if(answerFromClient.equals("0")){
                        //non faccio nulla, a fine del metodo padre le risorse verranno contate tra quelle scartate
                    }
                    else{
                        storage.setNewResourceToDepot(Integer.parseInt(answerFromClient), StaticMethods.IntToTypeOfResource(i+1), i, resources);
                    }
                }catch(IOException e){
                    System.out.println("error in not stored resources (during marble action) needed to change later");
                }
            }
        }
        clientConnect.getClientConnection().messageToClient("end");
    }

}
