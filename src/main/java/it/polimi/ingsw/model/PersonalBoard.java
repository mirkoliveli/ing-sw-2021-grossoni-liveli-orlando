package it.polimi.ingsw.model;

import static it.polimi.ingsw.model.TypeOfResource.*;

public class PersonalBoard {

    private final int idPlayer;
    private final FaithTrack faithTrack;
    private final Strongbox strongbox;
    private final Storage storage;
    private final DevelopmentCardSlot developmentSlot1;
    private final DevelopmentCardSlot developmentSlot2;
    private final DevelopmentCardSlot developmentSlot3;
    private final LeaderCard leader1;
    private final LeaderCard leader2;
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
        this.boardVictoryPoints = 0;
    }


    /**
     * This methods returns an array representing all the resources owned by a player in both his storage and his strongbox
     */
    public int[] getTotalResources() {
        int[] totResources = new int[4];
        totResources = strongbox.CreateCopy();
        DepotLevel temp = new DepotLevel();
        temp = this.storage.seekerOfResource(coins);
        if (temp != null) totResources[0] += temp.getQuantity();
        temp = this.storage.seekerOfResource(servants);
        if (temp != null) totResources[1] += temp.getQuantity();
        temp = this.storage.seekerOfResource(shields);
        if (temp != null) totResources[2] += temp.getQuantity();
        temp = this.storage.seekerOfResource(stones);
        if (temp != null) totResources[3] += temp.getQuantity();
        // VANNO AGGIUNTE QUELLE NELLE CARTE LEADER
        storage.updateTotalResourcesWithLeaders(totResources);
        return totResources;
    }

    /**
     * Method that count the number of development card on personal board
     *
     * @return totCards, represent the the number of development card on personal board
     */
    public int countTotalCards() {
        int totCards = 0;
        if (developmentSlot1.get_top() != null)
            totCards = developmentSlot1.get_top().getLevel() + totCards;
        if (developmentSlot2.get_top() != null)
            totCards = developmentSlot2.get_top().getLevel() + totCards;
        if (developmentSlot3.get_top() != null)
            totCards = developmentSlot3.get_top().getLevel() + totCards;
        return totCards;
    }


    public DevelopmentCardSlot getSlot(int slot) {
        if (slot == 1) return developmentSlot1;
        if (slot == 2) return developmentSlot2;
        if (slot == 3) return developmentSlot3;
        return null;
    }

    public int getId() {
        return this.idPlayer;
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
            for (i = 0; i < 4; i++) {
                if (developmentSlot1.get_top() != null)
                    costs[i] = costs[i] + developmentSlot1.get_top().getCost()[i];
            }
        }
        if (productions[1]) {
            for (i = 0; i < 4; i++) {
                if (developmentSlot2.get_top() != null)
                    costs[i] = costs[i] + developmentSlot2.get_top().getCost()[i];
            }
        }
        if (productions[2]) {
            for (i = 0; i < 4; i++) {
                if (developmentSlot3.get_top() != null)
                    costs[i] = costs[i] + developmentSlot3.get_top().getCost()[i];
            }
        }
        if (productions[3]) {
            switch (leader1.getPower()) {
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
            switch (leader2.getPower()) {
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
        switch (type1) {
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
        switch (type2) {
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


    /**
     * changes the board victory points (equal to the total victory points the player has gained)
     */
    public void setBoardVictoryPoints() {
        int boardVictoryPoints;
        int temp = countOfResources();
        int pvFromResources = 0;
        while (temp >= 5) {
            pvFromResources++;
            temp = temp - 5;
        }
        boardVictoryPoints = pvFromResources + developmentSlot1.getSlotPV() + developmentSlot2.getSlotPV() + developmentSlot3.getSlotPV() + faithTrack.TotalVictoryPointsFaithTrack();
        this.boardVictoryPoints = boardVictoryPoints;
    }

    /**
     * Updates the board victory points but ignores the resources, this method is used during the game, where having more than 5 resources can change very fast during a turn, so it's useless to keep track of that value
     * at the end of the game the setBoardVictoryPoints should be used instead
     */
    public void setBoardVictoryPointsIgnoreResources() {
        int boardVictoryPoints;
        boardVictoryPoints = developmentSlot1.getSlotPV() + developmentSlot2.getSlotPV() + developmentSlot3.getSlotPV() + faithTrack.TotalVictoryPointsFaithTrack();
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


    /**
     * method used by the PlayerUpdate class to generate a status of the DevelopmentCard Slots. Check the PlayerUpdate class to understand how the matrix is used in the view
     *
     * @return
     */
    public int[][] DevelopMentSlotsStatus() {
        int[][] status = new int[3][3];
        DevelopmentCardSlot temp;
        DevelopmentCard cardtemp;
        for (int i = 1; i < 4; i++) {
            temp = getSlot(i);
            for (int j = 0; j < 3; j++) {
                cardtemp = temp.getCard(j);
                if (cardtemp != null) {
                    status[i - 1][j] = cardtemp.getId();
                } else {
                    status[i - 1][j] = 0;
                }
            }
        }
        return status;
    }

    /**
     * returns an array stating which level of a development card can be placed on the slots
     *
     * @return return an int[3], if the slot is full the int[i] will be 0, otherwise it's the next level placeable
     */
    public int[] getNextPlaceableLevelOnSlots() {
        int[] level = new int[3];
        for (int i = 1; i < 4; i++) {
            level[i - 1] = 1;
            if (getSlot(i).get_top() != null) level[i - 1] = getSlot(i).get_top().getLevel() + 1;
            if (level[i - 1] == 4) level[i - 1] = 0;
        }
        return level;
    }

    /**
     * get a total number of resources that the player has, used to get the victory points
     *
     * @return total number of resources the player has (only the number)
     */
    public int countOfResources() {
        int[] temp = this.getTotalResources();
        int tot = temp[0] + temp[1] + temp[3] + temp[2];
        return tot;
    }

    /**
     * returns a boolean vector containing the slots where a card of a certain level can be placed
     *
     * @param level level of the card willing to be placed
     * @return boolean vector containing as "true" the slots where the card is placeable
     */
    public boolean[] whereCanIPlaceTheCard(int level) {
        boolean[] placeable = new boolean[3];
        int[] availableLevels = getNextPlaceableLevelOnSlots();
        for (int i = 0; i < 3; i++) {
            if (level == availableLevels[i]) placeable[i] = true;
        }

        return placeable;
    }

    /**
     * Counts the total number of cards in the 3 slots
     *
     * @return total number of cards in the 3 slots
     */
    public int numberOfDevelopmentCards() {
        int tot = 0;
        for (int i = 1; i < 4; i++) {
            tot += getSlot(i).numberOfCardsInSlot();
        }
        return tot;
    }

}
