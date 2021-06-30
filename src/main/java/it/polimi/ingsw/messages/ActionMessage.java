package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.controller.LastActionMade;
import it.polimi.ingsw.model.MatchMultiPlayer;
import it.polimi.ingsw.model.TypeOfResource;
import it.polimi.ingsw.utils.Generic_intANDboolean;
import it.polimi.ingsw.utils.StaticMethods;

public class ActionMessage {
    private TypeOfAction action;
    private String ActionAsMessage;

    public ActionMessage(TypeOfAction action){
        this.action=action;
        ActionAsMessage="invalidAction";
    }

    /**
     * creates a message by getting the two depot levels and storing them in an array which is then converted to a JSON string.
     * @param lvl1 one of the depots that will be swapped
     * @param lvl2 the other depot that will be swapped
     */
    public void SwapDepotsMessage(int lvl1, int lvl2){
        action=TypeOfAction.SWAP_DEPOTS;
        int[] array={lvl1, lvl2};
        Gson gson=new Gson();
        ActionAsMessage=gson.toJson(array);
    }

    /**
     * creates message containing the info about which leader is going to be played or discarded
     * @param leader 1 equals first leader, 2 equals second leader
     * @param playDiscard 0 equals playLeader, 1 equals discardLeader
     */
    public void PlayOrDiscardLeaders(int leader, int playDiscard){
        action=TypeOfAction.PLAY_OR_DISCARD_LEADER;
        int[] array={leader, playDiscard};
        Gson gson=new Gson();
        ActionAsMessage=gson.toJson(array);
    }

    /**
     * creates message that represents the choice of the client for the Marble_Market Action, boolean represents if the number is a row or column
     * while the number represents the line coshen.
     * <br><br>
     * FALSE IF ROW, TRUE IF COLUMN
     * @param line line of marbles chosen
     * @param rowOrColumn FALSE IF ROW, TRUE IF COLUMN
     */
    public void MarbleMarketAction(int line, boolean rowOrColumn){
        action=TypeOfAction.GO_TO_MARKET;
        Generic_intANDboolean choiceForMarbleMarket= new Generic_intANDboolean(line, rowOrColumn);
        Gson gson=new Gson();
        ActionAsMessage=gson.toJson(choiceForMarbleMarket);
    }


    /**
     * creates a message containing the info for the activation of the production action
     * @param choices activated productions, in order 0 for base production, 1-3 for slot production, 4 and 5 for leaders production (if present)
     * <br>
     * note that for now the leaders are not checked if active, so a check in the controller is needed
     * @param resources
     */
    public void ActivateProduction(boolean[] choices, TypeOfResource[] resources){
        action=TypeOfAction.ACTIVATE_PRDUCTION;
        ProductionMessageSetup ActionProduction= new ProductionMessageSetup(choices, resources);
        Gson gson=new Gson();
        ActionAsMessage=gson.toJson(ActionProduction);
    }


    /**
     * creates a message that contains an int referring to the card id that the player wants to buy
     * @param cardId
     */
    public void BuyCard(int cardId){
        action=TypeOfAction.BUY_A_CARD;
        Gson gson=new Gson();
        ActionAsMessage= gson.toJson(cardId);
    }

    public String getActionAsMessage() {
        return ActionAsMessage;
    }

    public TypeOfAction getAction() {
        return action;
    }

    public void EndTurn(){
        action=TypeOfAction.END_TURN;
        ActionAsMessage= "end_turn";
    }


    /**
     * original BeginTurn method, expected a GameStatusMessage as a parameter
     * @param GameStatusMessage already formatted gameStatusMessage or GameStatusUpdate as Json
     */
    public void BeginTurn(String GameStatusMessage){
        action=TypeOfAction.BEGIN_TURN;
        ActionAsMessage= GameStatusMessage;
    }

    /**
     * overload of BeginTurn method, generates the gameStatusUpdate from an obj of that class
     * @param status
     */
    public void BeginTurn(GameStatusUpdate status){
        Gson gson=new Gson();
        action=TypeOfAction.BEGIN_TURN;
        ActionAsMessage=gson.toJson(status);
    }

    /**
     * overload of BeginTurn method, generates the GameStatusUpdate from the matchMultiPlayer obj
     * @param match generates the GameStatusUpdate
     */
    public void BeginTurn(MatchMultiPlayer match, int player){
        Gson gson=new Gson();
        action=TypeOfAction.BEGIN_TURN;
        ActionAsMessage=gson.toJson(StaticMethods.GameStatusString(match, player));

    }

    public void updatePlayers(LastActionMade action){
        Gson gson=new Gson();
        this.action=TypeOfAction.ACTION;
        ActionAsMessage=gson.toJson(action);
    }

    public void EndGameMessage(String lastMessage){
        ActionAsMessage=lastMessage;
    }

}
