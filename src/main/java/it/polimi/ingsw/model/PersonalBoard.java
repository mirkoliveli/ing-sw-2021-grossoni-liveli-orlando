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
    private LeaderCard leader1;
    private LeaderCard leader2;
    private int boardVictoryPoints;

    public PersonalBoard(int i) {
        this.idPlayer = i;
        this.faithTrack = new FaithTrack();
        this.strongbox = new Strongbox();
        this.storage = new Storage();
        this.developmentSlot1 = new DevelopmentCardSlot();
        this.developmentSlot2 = new DevelopmentCardSlot();
        this.developmentSlot3 = new DevelopmentCardSlot();
        this.leader1 = new LeaderCard();
        this.leader2 = new LeaderCard();
    }





    /**
     This methods returns an array representing all the resources owned by a player in both his storage and his strongbox
     */
    public int[] getTotalResources() {
        int[] totResources = new int[4];
        totResources = strongbox.CreateCopy();
        DepotLevel temp=new DepotLevel();
         temp=this.storage.seekerOfResource(coins);
         if(temp!=null) totResources[0]+=temp.getQuantity();
        temp=this.storage.seekerOfResource(servants);
        if(temp!=null) totResources[1]+=temp.getQuantity();
        temp=this.storage.seekerOfResource(shields);
        if(temp!=null) totResources[2]+=temp.getQuantity();
        temp=this.storage.seekerOfResource(stones);
        if(temp!=null) totResources[3]+=temp.getQuantity();
        // VANNO AGGIUNTE QUELLE NELLE CARTE LEADER
        return totResources;
    }

    public int countTotalCards() {
        int totCards = 0;
        totCards = developmentSlot1.get_top().getLevel() + totCards;
        totCards = developmentSlot2.get_top().getLevel() + totCards;
        totCards = developmentSlot3.get_top().getLevel() + totCards;
        return totCards;
    }

    public DevelopmentCardSlot getSlot(int slot){
        if(slot==1) return developmentSlot1;
        if(slot==2) return developmentSlot2;
        if(slot==3) return developmentSlot3;
        return null;
    }

    /**
     * This methods returns an array representing the full resource cost of a production action
     * The param is an array of boolean values, each true if the relative production action has been selected by the player
     * index 0 contains DevCardSlot1, index 1 contains DevCardSlot2, index 2 contains DevCardSlot3,
     * index 3 contains LeaderCard1, index 4 contains LeaderCard2
     */
    public int[] getCosts(boolean[] productions) {
        int[] costs = new int[4];
        int i;

        if (productions[0]) {
                for (i=0; i<4; i++) {
                    costs[i] = costs[i] + developmentSlot1.get_top().getCost()[i];
                }
        }
        if (productions[1]) {
            for (i=0; i<4; i++) {
                costs[i] = costs[i] + developmentSlot2.get_top().getCost()[i];
            }
        }
        if (productions[2]) {
            for (i=0; i<4; i++) {
                costs[i] = costs[i] + developmentSlot3.get_top().getCost()[i];
            }
        }
        if (productions[3]) {
            switch(leader1.getPower()) {
                case coins:
                    costs[0]++;
                    break;
                case servants:
                    costs[1]++;
                    break;
                case shields:
                    costs[2]++;
                    break;
                case stones:
                    costs[3]++;
                    break;
            }
        }
        if (productions[4]) {
            switch(leader2.getPower()) {
                case coins:
                    costs[0]++;
                    break;
                case servants:
                    costs[1]++;
                    break;
                case shields:
                    costs[2]++;
                    break;
                case stones:
                    costs[3]++;
                    break;
            }
        }
        return costs;
    }


    /**
     * Overloading of the method with the same name, it has to be used when the base production action is selected
     * The params are the same array of boolean values we saw before, and two types of resources
     * type1 and type2 represent the cost of the base production action (they can be the same resource)
     */
    public int[] getCosts(boolean[] productions, TypeOfResource type1, TypeOfResource type2) {
        int[] costs = this.getCosts(productions);
        switch(type1) {
            case coins:
                costs[0]++;
                break;
            case servants:
                costs[1]++;
                break;
            case shields:
                costs[2]++;
                break;
            case stones:
                costs[3]++;
                break;
        }
        switch(type2) {
            case coins:
                costs[0]++;
                break;
            case servants:
                costs[1]++;
                break;
            case shields:
                costs[2]++;
                break;
            case stones:
                costs[3]++;
                break;
        }
        return costs;
    }

    public int getBoardVictoryPoints() {
        return boardVictoryPoints;
    }

    public void setBoardVictoryPoints() {
        int boardVictoryPoints;
        int temp=countOfResources();
        int pvFromResources=0;
        while(temp>=5){
            pvFromResources++;
            temp=temp-5;
        }
        boardVictoryPoints =pvFromResources + developmentSlot1.getSlotPV() + developmentSlot2.getSlotPV() + developmentSlot3.getSlotPV() +faithTrack.TotalVictoryPointsFaithTrack();
        this.boardVictoryPoints = boardVictoryPoints;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public Storage getStorage() {
        return storage;
    }

    public Strongbox getStrongbox() {
        return strongbox;
    }

    public int[][] DevelopMentSlotsStatus(){
        int[][] status= new int[3][3];
        DevelopmentCardSlot temp;
        DevelopmentCard cardtemp;
        for(int i=1; i<4; i++){
            temp= getSlot(i);
            for(int j=0; j<3; j++){
                cardtemp=temp.getCard(j);
                if(cardtemp!=null){
                    status[i-1][j]=cardtemp.getId();
                }
                else{status[i-1][j]=0;}
            }
        }
        return status;
    }

    public int countOfResources(){
        int[] temp=this.getTotalResources();
        int tot=temp[0] + temp[1] +temp[3] +temp[2];
        return tot;
    }


}
