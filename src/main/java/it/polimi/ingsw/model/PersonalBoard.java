package it.polimi.ingsw.model;

import static it.polimi.ingsw.model.TypeOfResource.*;

public class PersonalBoard {

    private int idPlayer;
    private FaithTrack faithTrack;
    private Strongbox strongbox;
    private Storage storage;
    private DevelopmentCardSlot developmentSlot1;
    private DevelopmentCardSlot developmentSlot2;
    private DevelopmentCardSlot developmentSlot3;


    /**
     This methods returns an array representing all the resources owned by a player in both his storage and his strongbox
     */
    public int[] getTotalResources() {
        int[] totResources = new int[4];
        totResources = strongbox.getContents();
        totResources[0] += storage.seekerOfResource(coins).getQuantity();
        totResources[1] += storage.seekerOfResource(servants).getQuantity();
        totResources[2] += storage.seekerOfResource(shields).getQuantity();
        totResources[3] += storage.seekerOfResource(stones).getQuantity();
        return totResources;
    }

    public int countTotalCards() {
        int totCards = 0;
        totCards = developmentSlot1.get_top().getLevel() + totCards;
        totCards = developmentSlot2.get_top().getLevel() + totCards;
        totCards = developmentSlot3.get_top().getLevel() + totCards;
        return totCards;
    }
}
