package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.MatchMultiPlayer;
import it.polimi.ingsw.utils.StaticMethods;

public class GettingStartedMessage {
    private int[] cardID;
    private int playerPosition;
    private int[] resources;

    /**
     * from server to client this message is sent
     * @param match
     * @param idPlayer id of the player, to recognize the starting position
     */
    public GettingStartedMessage(MatchMultiPlayer match, int idPlayer){
        cardID=new int[4];
        for(int i =0; i<4; i++) {
            cardID[i]=match.getLeaderDeck().getChoices(idPlayer)[i].getId();
        }
        playerPosition=idPlayer;
        resources=null;

    }

    /**
     * from client to server this message is sent
     * @param id1 selected leader number 1
     * @param id2 selected leader number 2
     */
    public GettingStartedMessage(int id1, int id2,int resourcesNum){
        cardID=new int[2];
        cardID[0]=id1;
        cardID[1]=id2;
        playerPosition=0;
        resources=new int[resourcesNum];
    }

    public int[] getCardID() {
        return cardID;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void setResources(int resource, int resource2) {
        this.resources[0]=resource;
        if(resource2!=-1) this.resources[1]=resource2;
    }

    public int[] getResources() {
        return resources;
    }

    /**
     * creates a string in JSON format of the object
     * @return string JSON
     */
    public String getMessageAsString(){
        return StaticMethods.objToJson(this);
    }

}
