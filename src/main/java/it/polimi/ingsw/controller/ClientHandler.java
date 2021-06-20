package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.MatchMultiPlayer;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.networking.ThreadedServer;
import it.polimi.ingsw.utils.Generic_intANDboolean;
import it.polimi.ingsw.utils.StaticMethods;

import java.io.IOException;
import java.net.Socket;

import static it.polimi.ingsw.messages.InBetweenActionExchanges.*;

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
    public void TurnPhase() throws IOException{
        while(!GameState.isGameEndedPhase()) {
            while (idPlayer != GameState.getIdOfPlayerInTurn() && GameState.isTurnPhase()) {
                waitingMyTurn();
            }
            //System.out.println("sei in turnphase prima di turn manager");
            if(GameState.isTurnPhase()){
                System.out.println("Starting turn of player " + idPlayer);
                turnManager();
                System.out.println("ending turn of player " + idPlayer);
                //check if someone finished
                if(match.hasSomeoneFinished()){
                    handleSomeoneFinishedMoment();
                }
                if(GameState.isTurnPhase())GameState.changeTurn();
            }

            if(GameState.isLastTurnPhase()){
                if(GameState.getHasRightToLastTurn()[idPlayer-1]){
                    handleLastTurn();
                }
                avoidCheckLoop();
            }
        }
        avoidCheckLoop();
        SendGameEndMessage();

    }

    private void resetTurnStatus(){
        turnEnding=false;
        mainAction=false;
    }


    /**
     * general method that handles the turn of a player, by sending an update to the client as soon as his turn starts, then by managing the messages that are sent to the server by the client
     */
    public void turnManager() throws IOException{
        resetTurnStatus();
        Gson gson=new Gson();
        //send first message with game update, then expects an answer for client with the action chosen
        ActionMessage action=new ActionMessage(TypeOfAction.BEGIN_TURN);
        GameStatusUpdate status=gson.fromJson(StaticMethods.GameStatusString(match, idPlayer), GameStatusUpdate.class);
        action.BeginTurn(StaticMethods.objToJson(status));
        clientConnection.messageToClient(StaticMethods.objToJson(action));


        while(!turnEnding){
            //System.out.println("sei in turn manager prima di handleanswer");
        handleAnswer();
        }
        match.UpdatesAllPlayersVictoryPoints(false);
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
    public void handleAnswer() throws IOException{
        Gson gson =new Gson();
        String messageFromC="";
        try {
            messageFromC = clientConnection.messageFromClient();
            System.out.println(messageFromC);
        }catch (IOException e){
            System.out.println("client disconnected before sending TurnAction");
            throw e;
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
                if(!mainAction)
                buyACardAction(action.getActionAsMessage());
                break;
            case GO_TO_MARKET:
                if(!mainAction){
                    goToMarketAction(action.getActionAsMessage());
                    mainAction=true;
                }
                else{
                    //might need a revision
                    clientConnection.messageToClient("Operation denied");
                    clientConnection.messageToClient("Action aborted! You already took an action during this turn!");
                }
                break;
            case ACTIVATE_PRDUCTION:
                if(!mainAction){
                ProductionAction();
                }
                break;
            case PLAY_OR_DISCARD_LEADER:
                PlayOrDiscardLeaders(action.getActionAsMessage());
                break;
            case DEBUG_MODE:
                cheatModeEnabler();
                break;
            case INSTANT_FINISH:
                instantFinish();
                break;
            default:
                break;
        }
        match.UpdatesAllPlayersVictoryPoints(false);
        clientConnection.messageToClient(StaticMethods.GameStatusString(match, idPlayer));
    }

    public ThreadedServer getClientConnection() {
        return clientConnection;
    }

    /**
     * method that handles the interaction when playing or discarding a leader, and call all the logic methods that perform the action requested (or deny it)
     * <br><br>
     * the method also sends back a message stating which state the controller ended with there can be 4 different types of state
     * <br>
     * "1" means that the leader has been discarded correctly
     * <br>
     * "0" means that the leader has been played correcly
     * <br>
     * "2" means that the player does not meet the conditions to play the leader (meaning that they can play them in the future)
     * <br>
     * "3" means that the player has already played or discarded the leader, meaning that they can't play them again
     *
     * @param action action performed by the client and included in the message ActionMessage.
     */
    public void PlayOrDiscardLeaders(String action){
        Gson gson=new Gson();
        int[] choice=gson.fromJson(action, int[].class);
        if(choice[1]==2){
            try {
                if(     match.getPlayers().get(idPlayer-1).switchLeader(choice[0])!=null && //condizione
                        !match.getPlayers().get(idPlayer-1).switchLeader(choice[0]).checkIfPlayed() && //condizione
                        !match.getPlayers().get(idPlayer-1).switchLeader(choice[0]).isDiscarded() //condizione
                ) {
                    int ans=1;
                    clientConnection.messageToClient(gson.toJson(ans));
                    match.MoveInFaithTrack(1, idPlayer);
                    match.getPlayers().get(idPlayer-1).switchLeader(choice[0]).discardCard();

                }
                else{
                    int ans=3;
                    clientConnection.messageToClient(gson.toJson(ans));
                }
            }catch(GameIsEnding e){
                System.out.println("player " + idPlayer + " reached an end game status");
            }
        }
        if(choice[1]==1){
            try{
                if(match.getPlayers().get(idPlayer-1).playLeader(choice[0])) {
                    int ans=0;
                    clientConnection.messageToClient(gson.toJson(ans));
                }
                else{
                    int ans=2;
                    clientConnection.messageToClient(gson.toJson(ans));
                }
            }catch(AlreadyPlayedOrDiscardedLeader e){
                int ans=3;
                clientConnection.messageToClient(gson.toJson(ans));

            }
        }
    }

    /**
     * method called when the client requests to perform a production action. This method sends to the client a ProductionActionMessage which contains whether they can perform the action
     * (via basic checks that DO NOT ensure that the next message from the client contains a performable action). In case they can do some activation the method will send a boolean[] containing which type of production they can activate
     * (0-> basic 1-3-> slots production 4-5-> leader productions). This is done in order to facilitate the client choice, but if an illegal action is sent back (by modifying the checks code) the action is still validated in the server, and any illegal states
     * are either discarded completely (e.g. asking to produce from an empty slot will results in discarding the request, but still performing legal productions) or results in an denial of request which is sent back to the client.
     */
    public void ProductionAction(){
        Gson gson=new Gson();
        if(StaticMethods.AreAnyTrue(match.getPlayers().get(idPlayer-1).whichProductionIsUsable())){
            boolean[] copyOfMethod= match.getPlayers().get(idPlayer-1).whichProductionIsUsable().clone();
            ProductionActionMessage messageClient=new ProductionActionMessage(AVAILABLE_PRODUCTIONS, gson.toJson(copyOfMethod));
            clientConnection.messageToClient(gson.toJson(messageClient));
            try{
                String answer=clientConnection.messageFromClient();
                handleProductionSelection(answer, copyOfMethod.clone());
            }catch(IOException e){
                System.out.println("client " + idPlayer + " disconnected");
            }
        }
        else{
            ProductionActionMessage messageClient=new ProductionActionMessage(UNAVAILABLE_ACTION, "");
            clientConnection.messageToClient(gson.toJson(messageClient));
        }
    }

    /**
     * handles all the logic and the last message to send back to the client. The message is either a ActionSuccess or a denial of request (if the resources exceeds the maximum payment). for further info on how the logic performs refer to the single methods contained
     * in this method or refer to the caller (ClientHandler.ProductionAction) for a brief view on how the different illegal requests are handled
     * @param string choice made by the client on which production to activate
     * @param productionIsUsable boolean[] generated by the controller that indicates which production is theoretically actionable (teh check for all the productions simultaneously is made at the end of the method)
     */
    public void handleProductionSelection(String string, boolean[] productionIsUsable){
        Gson gson=new Gson();
        int[] selections=gson.fromJson(string, int[].class);
        int[] cost=new int[4];
        int[] gains=new int[5];

        if(selections[0]>0 && productionIsUsable[0] && StaticMethods.numBetween1and4(selections[0]) && StaticMethods.numBetween1and4(selections[1]) && StaticMethods.numBetween1and4(selections[7])){
            cost[selections[0]-1]++;
            cost[selections[1]-1]++;
            gains[selections[7]-1]++;
        }
        for(int i=1; i<4; i++){
            if(selections[i+1]>0 && productionIsUsable[i]){
                StaticMethods.sumArray(cost, match.getPlayers().get(idPlayer-1).getBoard().getSlot(i).get_top().getProductionCost());
                StaticMethods.sumArray(gains, match.getPlayers().get(idPlayer-1).getBoard().getSlot(i).get_top().getProduct());
            }
        }
        if(selections[5]>0 && productionIsUsable[4] && StaticMethods.numBetween1and4(selections[5])){
            cost[StaticMethods.TypeOfResourceToInt(match.getPlayers().get(idPlayer-1).getLeaderCard1().getPower())]++;
            gains[selections[5]-1]++;
            gains[4]++;
        }
        if(selections[6]>0 && productionIsUsable[5] && StaticMethods.numBetween1and4(selections[6])){
            cost[StaticMethods.TypeOfResourceToInt(match.getPlayers().get(idPlayer-1).getLeaderCard2().getPower())]++;
            gains[selections[6]-1]++;
            gains[4]++;
        }

        if(match.getPlayers().get(idPlayer-1).canIPayForProductions(cost)){
            try{
                match.getPlayers().get(idPlayer-1).payForProductions(cost);
                int[] resources={gains[0], gains[1], gains[2], gains[3]};
                match.getPlayers().get(idPlayer-1).getBoard().getStrongbox().store(resources);
                match.MoveInFaithTrack(gains[4], idPlayer);
                ProductionActionMessage message=new ProductionActionMessage(ACTION_SUCCESS, "");
                clientConnection.messageToClient(gson.toJson(message));
                mainAction=true;
            }catch(NotEnoughResources e){
                ProductionActionMessage message=new ProductionActionMessage(UNAVAILABLE_ACTION, "");
                clientConnection.messageToClient(gson.toJson(message));
            }catch(GameIsEnding e){
                System.out.println("player " + idPlayer + " reached an end game status");
            }
        }else{
            ProductionActionMessage message=new ProductionActionMessage(UNAVAILABLE_ACTION, "");
            clientConnection.messageToClient(gson.toJson(message));
        }
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
     * method that handles the buy a card action. If the card can be bought the message sent to the client is a request for a choice of depot (even if the only depot available is one), if the
     * card cannot be bought the message sent to the client is a "unavailable action" message.
     * @param idOfCard card selected by the client (is a string because is from an ActionMessage)
     */
    public void buyACardAction(String idOfCard){
        Gson gson=new Gson();
        int cardId=gson.fromJson(idOfCard, int.class);
        String messageFromC;
        try{
            if(match.CanIBuyThisCard(cardId, idPlayer)){
                DevelopmentCard temp=match.getCardMarket().BuyCard(cardId);
                match.getPlayers().get(idPlayer-1).payForACard(temp.getCost());
                BuyACardActionMessage nextChoice=new BuyACardActionMessage(CHOOSE_A_DEPOT, gson.toJson(match.getPlayers().get(idPlayer-1).getBoard().whereCanIPlaceTheCard(temp.getLevel())));
                clientConnection.messageToClient(gson.toJson(nextChoice));
                messageFromC=clientConnection.messageFromClient();
                //add check fro slot=1-3
                int slot=gson.fromJson(messageFromC, int.class);
                match.getPlayers().get(idPlayer-1).getBoard().getSlot(slot).placeCard(temp);
                mainAction=true;
            }
            else{
                BuyACardActionMessage nextChoice=new BuyACardActionMessage(UNAVAILABLE_ACTION, "");
                clientConnection.messageToClient(gson.toJson(nextChoice));
            }
        }catch(CardNotFoundException | IllegalCardException | NotEnoughResources e){
            BuyACardActionMessage nextChoice=new BuyACardActionMessage(UNAVAILABLE_ACTION, "");
            clientConnection.messageToClient(gson.toJson(nextChoice));
        }catch(IOException e){
            System.out.println("client disconnected");
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
     * @param string JSON file of the action
     */
    public void goToMarketAction(String string){
        Gson gson= new Gson();
        Generic_intANDboolean action;
        action=gson.fromJson(string, Generic_intANDboolean.class);
        System.out.println(action.getNumber());

        int[] resourcesGained=match.getMarket().ConversionToArray(!action.isChoice(), action.getNumber()-1);

        if(resourcesGained[5]>0){
            try {
                manageLeaderInteractionWithMarket(resourcesGained);

            }catch(IOException e){
                System.out.println("disconnection to be handled");
            }
        }

        // stampa resources
        //for(int i=0; i<6; i++){
        //    System.out.println(i + " resource " + resourcesGained[i]);
        //}
        match.getMarket().ChangeBoard(!action.isChoice(), action.getNumber());
        if(resourcesGained[4]>0){
            try {
                match.MoveInFaithTrack(1, idPlayer);
            }catch(GameIsEnding e){
                System.out.println("player " + idPlayer + " reached an end game status");
            }
        }
        int[] actualResources={resourcesGained[0], resourcesGained[1], resourcesGained[2], resourcesGained[3]};
        for(int i=0; i<4; i++){
            System.out.println(i + " resource " + actualResources[i]);
        }
        int movement=match.getPlayers().get(idPlayer-1).getBoard().getStorage().IncreaseResources(actualResources, this);
        if(movement!=0) {
            boolean[] someoneFinished;
            someoneFinished=match.parallelMovementInFaithTrack(movement, idPlayer);
            if(someoneFinished!=null){
                //handle endgame
            }
        }
        clientConnection.messageToClient("action completed");
    }


    /**
     * method that sends to the client the leaders available for the power (only when there is one). the method then receives 0 if the client don't want to use the power,
     * 1 if they want to use the first leader power (if it's a correct leader) or 2 if theyt want to use the second leader (if it's a correct leader).
     * <br> the method updates the resources gained from the market and proceeds to the next step (remember that no ack is then resent to the client, as we want to keep a simple exchange of messages that would be redoundant)
     * <br> if any mistake is made by the logic of the client (e.g. sending a wrong number or not even a number at all) the method should keep working and do nothing at all.
     * @param resources resources to be updated
     * @throws IOException client disconnection
     */
    public void manageLeaderInteractionWithMarket(int[] resources) throws IOException{
        Gson gson=new Gson();
        boolean[] leaders=match.getPlayers().get(idPlayer-1).doIHaveAWhiteBallLeader();
        if(leaders[0] || leaders[1]){
            clientConnection.messageToClient(gson.toJson(leaders));
            int choice=gson.fromJson(clientConnection.messageFromClient(), int.class);
            if(choice==1 && match.getPlayers().get(idPlayer-1).getLeaderCard1().getColor1WhiteBallCard()!=null && match.getPlayers().get(idPlayer-1).getLeaderCard1().checkIfPlayed()) {
                resources[StaticMethods.TypeOfResourceToInt(match.getPlayers().get(idPlayer-1).getLeaderCard1().getPower())]+=resources[5];
            }
            else if(choice==2 && match.getPlayers().get(idPlayer-1).getLeaderCard2().getColor1WhiteBallCard()!=null && match.getPlayers().get(idPlayer-1).getLeaderCard2().checkIfPlayed()){
                resources[StaticMethods.TypeOfResourceToInt(match.getPlayers().get(idPlayer-1).getLeaderCard2().getPower())]+=resources[5];
            }
        }
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

    public void handleSomeoneFinishedMoment(){
        GameState.setHasRightToLastTurn(match.getPlayers().size(), match.whoHasFinished());
        if(StaticMethods.AreAnyTrue(GameState.getHasRightToLastTurn())){
            GameState.setPhase(3);
            GameState.changeTurn();
        }
        else{
            GameState.setPhase(4);
        }
    }

    public void handleLastTurn()throws IOException{
        Gson gson=new Gson();
        while(idPlayer!=GameState.getIdOfPlayerInTurn()){
            waitingMyTurn();
        }
        //send message to client stating that your last turn will begin shortly
        ActionMessage SendlastTurn=new ActionMessage(TypeOfAction.BEGIN_LAST_TURN);
        clientConnection.messageToClient(gson.toJson(SendlastTurn));
        //start the actual last turn
        System.out.println("Starting turn of player " + idPlayer);
        turnManager();
        System.out.println("ending turn of player " + idPlayer);
        //change status, set lastTurn to false, change gameState status if everyone finished
        GameState.setSpecificLastTurnPlayer(idPlayer, false);
        if(StaticMethods.AreAnyTrue(GameState.getHasRightToLastTurn())) GameState.changeTurn();
        else GameState.setPhase(4);
    }

    /**
     * method used to not check in a loop
     */
    public void avoidCheckLoop(){
        while(!GameState.isGameEndedPhase()){
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.out.println("interrupted while sleeping");
            }
        }
    }

    /**
     * last message containing the info about who won, and how many victory points the winner and the player had. Also contains a boolean stating if the player is the winner
     */
    public void SendGameEndMessage(){
        Gson gson=new Gson();
        int winnerID= match.whoHasWon();
        LastMessage finalmex= new LastMessage(match.getPlayers().get(winnerID-1).getName(), match.getPlayers().get(winnerID-1).getVictoryPoints(), match.getPlayers().get(idPlayer-1).getVictoryPoints(), winnerID==idPlayer);
        String mex=gson.toJson(finalmex);
        ActionMessage lastMessage= new ActionMessage(TypeOfAction.GAME_ENDED);
        lastMessage.EndGameMessage(mex);

        clientConnection.messageToClient(gson.toJson(lastMessage));
    }

    public void cheatModeEnabler(){
        match.getPlayers().get(idPlayer-1).getBoard().getStrongbox().store(new int[]{999, 999, 999, 999});
    }

    public void instantFinish(){
        match.getPlayers().get(idPlayer-1).getBoard().getFaithTrack().setFaithMarker(24);
    }



}
