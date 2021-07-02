package it.polimi.ingsw.controller;

import it.polimi.ingsw.gui.ConnectionHandlerForGui;
import it.polimi.ingsw.gui.LastGameStatus;
import it.polimi.ingsw.gui.TurnController;
import it.polimi.ingsw.messages.ActionMessage;

import java.io.IOException;

public class MessageControllerForGui extends Thread{

    private TurnController guiView;

    public MessageControllerForGui(TurnController guiView){
        this.guiView=guiView;
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
            } while (!message.contains("BEGIN_TURN"));
            sendUpdate(message);
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
        ActionMessage serverMessage=ConnectionHandlerForGui.getGson().fromJson(message, ActionMessage.class);
        GameStatusUpdate status=ConnectionHandlerForGui.getGson().fromJson(serverMessage.getActionAsMessage(), GameStatusUpdate.class);
        LastGameStatus.update(status);
    }

}
