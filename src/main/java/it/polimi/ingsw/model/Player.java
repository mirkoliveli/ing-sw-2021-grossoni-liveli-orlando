package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.AlreadyPlayedOrDiscardedLeader;
import it.polimi.ingsw.model.exceptions.NotEnoughResources;
import it.polimi.ingsw.utils.StaticMethods;


public class Player {

    private final boolean inkwell;
    private final PersonalBoard board;
    private String name;
    private int id;
    private LeaderCard leaderCard1;
    private LeaderCard leaderCard2;
    private int victoryPoints;

    public Player(String name, int id) {
        this.name = name;
        this.setId(id);
        this.inkwell = false;
        this.leaderCard1 = null;
        this.leaderCard2 = null;
        this.victoryPoints = 0;
        this.board = new PersonalBoard(id);

    }

    public PersonalBoard getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int value) {
        this.victoryPoints = value;
    }

    public LeaderCard getLeaderCard1() {
        return leaderCard1;
    }

    public LeaderCard getLeaderCard2() {
        return leaderCard2;
    }

    /**
     * setter of total amount of victory point
     */
    public void setPvTotal() {
        int pvtemp = 0;
        if (leaderCard1 != null) {
            if (leaderCard1.checkIfPlayed()) pvtemp += leaderCard1.getPv();
        }
        if (leaderCard2 != null) {
            if (leaderCard2.checkIfPlayed()) pvtemp += leaderCard2.getPv();
        }
        pvtemp += getBoard().getBoardVictoryPoints();

        this.setVictoryPoints(pvtemp);
    }

    public void setLeaderCard1(LeaderDeck deck, int id) {
        leaderCard1 = new LeaderCard(deck.getCardById(id));
    }

    public void setLeaderCard2(LeaderDeck deck, int id) {
        leaderCard2 = new LeaderCard(deck.getCardById(id));
    }

    /**
     * method that is used to play a leader, it does all the checks and play the leader if possible.
     * @param switcher indicates first or second leader card
     * @return true if played, false otherwise
     */
    public boolean playLeader(int switcher) throws AlreadyPlayedOrDiscardedLeader {
        if(switcher>2 || switcher<1) return false;
        LeaderCard leaderPointer=switchLeader(switcher);
        if(leaderPointer.checkIfPlayed() || leaderPointer.isDiscarded()) throw new AlreadyPlayedOrDiscardedLeader();
            //discount
            if(leaderPointer.getId()>=49 && leaderPointer.getId()<53){
                if(checkForDiscountLeader(switcher)){
                    leaderPointer.playCard();
                    return true;
                }
                else{
                    return false;
                }
            }
            //storage
            else if(leaderPointer.getId()>=53 && leaderPointer.getId()<57 ){
                if(checkForStorageLeader(switcher)){
                    leaderPointer.playCard();
                    board.getStorage().addLeader(leaderPointer.getPower());
                    return true;
                }
                else{
                    return false;
                }
            }
            //white ball
            else if(leaderPointer.getId()>=57 && leaderPointer.getId()<61 ){
                //carte white ball
                if(checkForWhiteBallLeader(switcher)){
                    leaderPointer.playCard();
                    return true;
                }
                else{
                    return false;
                }
            }
            //carte production
            else if(leaderPointer.getId()>=61 && leaderPointer.getId()<65 ){
                if(checkForProductionLeader(switcher)){
                    leaderPointer.playCard();
                    return true;
                }
                else{
                    return false;
                }
            }

        return false;
    }



    public LeaderCard switchLeader(int leader){
        if(leader==1) return leaderCard1;
        else if(leader==2) return leaderCard2;
        return null;
    }


    /**
     * given the leader (only if it's the first or second) this method checks if the card is playable via the discountLeader check. If the card is not a discount leader the result will always be false so
     * there is no problem of false leader checks
     * @param leader 1 or 2 depending on which leader the client wants to play
     * @return true if playable, false otherwise
     */
    public boolean checkForDiscountLeader(int leader){
        LeaderCard leaderPointer=switchLeader(leader);
        Color[][] allCardsColor= new Color[3][3];
        for(int i=0;i < 3 ; i++){
        allCardsColor[i]=board.getSlot(i+1).get_colors();
        }
        boolean[] conditions=new boolean[2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(allCardsColor[i][j]==leaderPointer.getColor2DiscountCard() && allCardsColor[i][j]!=null) conditions[0]=true;
                if(allCardsColor[i][j]==leaderPointer.getColor1DiscountCard() && allCardsColor[i][j]!=null) conditions[1]=true;
            }
        }
        return (conditions[0] && conditions[1]);
    }

    /**
     * checks if a leader is playable under the white ball conditions. if a leader which is not a white ball leader is checked the result will always be false
     * @param leader 1 or 2 stating which leader the client chose
     * @return true if playable, false otherwise
     */
    public boolean checkForWhiteBallLeader(int leader){
        LeaderCard leaderPointer=switchLeader(leader);
        Color[][] allCardsColor= new Color[3][3];
        for(int i=0;i < 3 ; i++){
            allCardsColor[i]=board.getSlot(i+1).get_colors();
        }
        boolean[] conditions=new boolean[3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(conditions[0] && allCardsColor[i][j]==leaderPointer.getColor1WhiteBallCard() && allCardsColor[i][j]!=null) conditions[1]= true;
                if(allCardsColor[i][j]==leaderPointer.getColor1WhiteBallCard() && allCardsColor[i][j]!=null) conditions[0]=true;
                if(allCardsColor[i][j]==leaderPointer.getColor2WhiteBallCard() && allCardsColor[i][j]!=null) conditions[2]=true;
            }
        }
        return (conditions[0] && conditions[1] && conditions[2]);
    }

    /**
     * checks if a leader is playable under the production conditions. if a leader which is not a production leader is checked the result will always be false
     * @param leader 1 or 2 stating which leader the client chose
     * @return true if playable, false otherwise
     */
    public boolean checkForProductionLeader(int leader){
        LeaderCard leaderPointer=switchLeader(leader);
        Color[][] allCardsColor= new Color[3][3];
        boolean condition=false;
        for(int i=0;i < 3 ; i++){
            allCardsColor[i]=board.getSlot(i+1).get_colors();
            if(allCardsColor[i][2]==leaderPointer.getColorProductionCard() && allCardsColor[i][2]!=null) condition=true;
        }
        return condition;
    }

    /**
     * checks if the Leader selected is playable under the storage conditions. If a leader is not a storage leader the result will always be false
     * @param leader 1 or 2 stating which leader the client chose
     * @return true if playable, false otherwise
     */
    public boolean checkForStorageLeader(int leader){
        LeaderCard leaderPointer=switchLeader(leader);
        if(leaderPointer.getStorageCardCost()!=null){
            if(board.getTotalResources()[StaticMethods.TypeOfResourceToInt(leaderPointer.getStorageCardCost())]>4) return true;
        }
        return false;
    }

    /**
     * returns an double boolean stating if each leader is a whiteball leader (and is played) or not
     * @return double boolean, has true if the leader is a PLAYED white ball leader, false otherwise
     */
    public boolean[] doIHaveAWhiteBallLeader(){
        boolean[] leaders=new boolean[2];
        for(int i=1; i<3; i++){
            if(switchLeader(i).checkIfPlayed() && switchLeader(i).getColor1WhiteBallCard()!=null) leaders[i-1]=true;
        }
        return leaders;
    }

    /**
     * returns a double boolean stating if each leader is a discount leader (if that leader is played) or not
     * @return boolean[i]=true if the leader i is played AND is a discount leader, false in any other case
     */
    public boolean[] doIHaveADiscountLeader(){
        boolean[] leaders=new boolean[2];
        for(int i=1; i<3; i++){
            if(switchLeader(i)!=null && switchLeader(i).checkIfPlayed() && switchLeader(i).getColor1DiscountCard()!=null) leaders[i-1]=true;
        }
        return leaders;
    }

    /**
     * returns a double boolean stating if each leader is a production leader (if that leader is played) or not
     * @return boolean[i]=true if the leader i is played AND is a production leader, false in any other case
     */
    public boolean[] doIHaveAProductionLeader(){
        boolean[] leaders=new boolean[2];
        for(int i=1; i<3; i++){
            if(switchLeader(i)!=null && switchLeader(i).checkIfPlayed() && switchLeader(i).getColorProductionCard()!=null) leaders[i-1]=true;
        }
        return leaders;
    }



    /**
     * automatic payment (don't need to be checked first but should be checked for discount leaders). The payment is frist made in the storage, while the remaining is made in the strongbox
     * @param costo cost of the card
     * @throws NotEnoughResources the check is made "just to be sure", but the method should be called only when it's has already checked that the player can pay for the card.
     */
    public void payForACard(int[] costo) throws NotEnoughResources {
        int[] cost=costo.clone();
        applyDiscount(cost);
        if(!StaticMethods.isItAffordable(cost, this.board.getTotalResources())) throw new NotEnoughResources();
        int[] resourcesFromStorage=this.getBoard().getStorage().conversionToArray();
        int[] storageCost=new int[4];
        for(int i=0; i<4; i++){
            if(cost[i]>=resourcesFromStorage[i]){
                storageCost[i]=resourcesFromStorage[i];
                cost[i]-=storageCost[i];
            }
            else{
                storageCost[i]=cost[i];
                cost[i]=0;
            }
        }
        this.getBoard().getStorage().ResourceDecreaser(storageCost);
        this.getBoard().getStrongbox().remove(cost);
    }

    /**
     * method that pays for a production action. The method behaves in the same way the payForACard method, but avoids checking for a discount
     * @param costo cost calcuated by the controller
     * @throws NotEnoughResources if the cost is not payable by the player
     */
    public void payForProductions(int[] costo) throws NotEnoughResources{
        int[] cost=costo.clone();
        if(!StaticMethods.isItAffordable(cost, this.board.getTotalResources())) throw new NotEnoughResources();
        int[] resourcesFromStorage=this.getBoard().getStorage().conversionToArray();
        int[] storageCost=new int[4];
        for(int i=0; i<4; i++){
            if(cost[i]>=resourcesFromStorage[i]){
                storageCost[i]=resourcesFromStorage[i];
                cost[i]-=storageCost[i];
            }
            else{
                storageCost[i]=cost[i];
                cost[i]=0;
            }
        }
        this.getBoard().getStorage().ResourceDecreaser(storageCost);
        this.getBoard().getStrongbox().remove(cost);
    }

    /**
     * given a Card cost a discount is applied if the player has a leader card discount (and if the resource affected is greater than zero)
     * @param cost cost of the card
     */
    public void applyDiscount(int[] cost){
        if(doIHaveADiscountLeader()[0]){
            int i=StaticMethods.TypeOfResourceToInt(leaderCard1.getPower());
            if(cost[i]>0) cost[i]--;
        }
        if(doIHaveADiscountLeader()[1]){
            int i=StaticMethods.TypeOfResourceToInt(leaderCard2.getPower());
            if(cost[i]>0) cost[i]--;
        }
    }

    /**
     * checks if a cost can be payed with the resources that a player has in this moment.
     * @param cost total cost checked
     * @return true if the player has enough resources to pay the cost, false otherwise
     */
    public boolean canIPayForProductions(int[] cost){
        int[] totResources= board.getTotalResources().clone();
        return StaticMethods.isItAffordable(cost.clone(), totResources);
    }

    /**
     * returns a message containing all the productions that can (ideally) be activated. The control is just:
     * <br> if the basic production can be activated (the player has at least 2 resources),
     * <br> if the resources are greater than 0 and for each slot at least one card is present,
     * <br> if each leader is played an is a production leader.
     * @return boolean array stating which production is ideally activatable (to ease the choice client-side)
     */
    public boolean[] whichProductionIsUsable(){
        boolean[] actionableProductions=new boolean[6];
        if(board.countOfResources()>1) actionableProductions[0]= true;
        if(board.countOfResources()>0) {
            for (int i = 1; i < 4; i++) {
                if (board.getSlot(i).get_top() != null) actionableProductions[i] = true;
            }
            actionableProductions[4] = doIHaveAProductionLeader()[0];
            actionableProductions[5] = doIHaveAProductionLeader()[1];
        }
            return actionableProductions;
    }

    /**
     * method that updates the player victory points. It checks if the player gets any points from resources in some cases
     * @param alsoCheckResources if given true the method checks for victory points given by resources
     */
    public void updateVictoryPoints(boolean alsoCheckResources){
        int totalPoints=0;
        if(alsoCheckResources){
            board.setBoardVictoryPoints();
        }
        else{
            board.setBoardVictoryPointsIgnoreResources();
        }
        totalPoints+= board.getBoardVictoryPoints();
        if(leaderCard1.checkIfPlayed()) totalPoints+=leaderCard1.getPv();
        if(leaderCard2.checkIfPlayed()) totalPoints+=leaderCard2.getPv();
        this.victoryPoints=totalPoints;
    }

    /**
     * method that checks if a player has reached an end game status
     * @return true if a player has reached an end game status, false otherwise (faithTrack completed OR 7 development cards obtained)
     */
    public boolean hasPlayerFinished(){
        if(board.countTotalCards()==7) return true;
        if(board.getFaithTrack().getFaithMarker()==24) return true;
        return false;
    }


}
