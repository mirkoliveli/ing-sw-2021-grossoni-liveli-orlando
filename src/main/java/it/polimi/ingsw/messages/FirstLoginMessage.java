package it.polimi.ingsw.messages;

import it.polimi.ingsw.utils.staticMethods;

public class FirstLoginMessage {
    private String name;
    private int numberOfPlayersToWait;


    public FirstLoginMessage(String name, int players){
        this.name=name;
        this.numberOfPlayersToWait=players;
    }

    public String CreateMessage(){
        String returner;
        returner= staticMethods.objToJson(this);
        return returner;
    }

    public int getNumberOfPlayersToWait() {
        return numberOfPlayersToWait;
    }

    public String getName() {
        return name;
    }
}
