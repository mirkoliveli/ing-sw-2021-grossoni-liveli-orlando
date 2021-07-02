package it.polimi.ingsw.controller;

import it.polimi.ingsw.gui.ConnectionHandlerForGui;
import it.polimi.ingsw.gui.LastGameStatus;
import it.polimi.ingsw.gui.TurnController;
import it.polimi.ingsw.messages.ActionMessage;
import it.polimi.ingsw.messages.TypeOfAction;

import java.io.IOException;

public class MessageControllerForGui extends Thread{



    public MessageControllerForGui(){

    }

    public void run(){
        String message="";
        while (true) {
            do {
                try {
                    message = ConnectionHandlerForGui.getMessage();
                } catch (IOException e) {
                    System.out.println("DISCONNECTED");
                    System.exit(1);
                }
            } while (!message.contains("BEGIN_TURN") && !message.contains("BEGIN_LAST_TURN"));
            System.out.println(ConnectionHandlerForGui.getGson().toJson(message));
            sendUpdate(message);
            if(message.contains("BEGIN_LAST_TURN")){
                LastGameStatus.setLastTurn(true);
            }
            else if(message.contains("GAME_ENDED")){
                LastGameStatus.setGameEnd(true);
                LastGameStatus.setLastMessage(message);
            }
            //DEBUG INIZIO
            //ActionMessage action=new ActionMessage(TypeOfAction.DEBUG_MODE);
            //ConnectionHandlerForGui.sendMessage(action);
            //try{
            //    message = ConnectionHandlerForGui.getMessage();
            //    System.out.println(ConnectionHandlerForGui.getGson().toJson(message));
            //    GameStatusUpdate status=ConnectionHandlerForGui.getGson().fromJson(message, GameStatusUpdate.class);
            //    LastGameStatus.update(status);
            //}catch (IOException e){
            //    System.out.println("disconnected");
            //    System.exit(1);
            //}
            //DEBUG FINE
            ConnectionHandlerForGui.setIsItMyTurn(true);
            //notify in some way the gui?
            sleeping(3000);
            ConnectionHandlerForGui.resetWait();
        }
    }

    /**
     * makes the thread sleep while waiting the turn to end
     * @param milliseconds time between checks
     */
    public void sleeping(int milliseconds){
        while(!ConnectionHandlerForGui.IsMyTurnEnded()){
            try{
                Thread.sleep(milliseconds);
            }catch(InterruptedException e){
                System.out.println("wtf");
            }
        }
    }

    /**
     * creates the StatusUpdate and Updates the static class that the gui uses to check for changes
     * @param message message sent by the server containing the update
     */
    public void sendUpdate(String message){
        System.out.println("sto facendo update");
        ActionMessage serverMessage=ConnectionHandlerForGui.getGson().fromJson(message, ActionMessage.class);
        GameStatusUpdate status=ConnectionHandlerForGui.getGson().fromJson(serverMessage.getActionAsMessage(), GameStatusUpdate.class);
        LastGameStatus.update(status);
        LastGameStatus.printEverything();
    }

}
