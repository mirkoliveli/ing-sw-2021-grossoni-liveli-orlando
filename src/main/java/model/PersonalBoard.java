package model;

public class PersonalBoard {

    private int idPlayer;
    private FaithTrack faithTrack;
    private Strongbox strongbox;
    private Storage storage;
    private DevelopmentCardSlot developmentCardSlot[2];

    public Resource[] getTotalResources(){

    }

    public int countTotalCards(){
        int totcard = 0;
        for (int i = 0; i < 3; i++){
            totcard = developmentCardSlot[i].get_top().getLevel() + totcard;
        }
    }
}
