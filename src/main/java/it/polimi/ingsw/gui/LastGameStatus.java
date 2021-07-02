package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.model.MarbleColor;

public class LastGameStatus {
    public static int leader1;
    public static int leader2;
    public static boolean leader1Played;
    public static boolean leader2Played;
    public static boolean leader1Discarded= false;
    public static boolean leader2Discarded= false;
    public static int[] strongboxStatus;
    public static int[][] marketBoardStatus;
    public static int sideMarbleStatus;
    public static int faithMarkerStatus;
    public static boolean[] popeCards;
    public static int victoryPoints;
    public static int[][] cardMarketStatus;
    public static int[][] storageState;
    public static int[][] slotsStatus;
    public static int[] activatableCards;
    public static int idPlayer;
    public static boolean lastTurn=false;
    public static boolean gameEnd=false;
    public static String lastMessage;


    public static void setIdPlayer(GameStatusUpdate status){
        LastGameStatus.idPlayer=status.getNextPlayer()-1;
    }

    public static void setLeader1Discarded(boolean leader1Discarded) {
        LastGameStatus.leader1Discarded = leader1Discarded;
    }

    public static void setLastMessage(String lastMessage) {
        LastGameStatus.lastMessage = lastMessage;
    }

    public static String getLastMessage() {
        return lastMessage;
    }

    public static void setGameEnd(boolean gameEnd) {
        LastGameStatus.gameEnd = gameEnd;
    }

    public static void setLastTurn(boolean lastTurn) {
        LastGameStatus.lastTurn = lastTurn;
    }

    public static boolean isLastTurn() {
        return lastTurn;
    }

    public static boolean isGameEnd() {
        return gameEnd;
    }

    public static void setLeader2Discarded(boolean leader2Discarded) {
        LastGameStatus.leader2Discarded = leader2Discarded;
    }

    public static void setALeaderToDiscarded( int leader){
        if(leader==1) setLeader1Discarded(true);
        if(leader==2) setLeader2Discarded(true);
    }

    public static void update(GameStatusUpdate status){
        setIdPlayer(status);
        LastGameStatus.leader2Played= status.getPlayersStatus()[idPlayer].isSecondLeaderPlayed();
        LastGameStatus.leader1Played= status.getPlayersStatus()[idPlayer].isFirstLeaderPlayed();
        LastGameStatus.leader1=status.getPlayersStatus()[idPlayer].getFirstLeader();
        LastGameStatus.leader2=status.getPlayersStatus()[idPlayer].getSecondLeader();
        LastGameStatus.strongboxStatus=status.getPlayersStatus()[idPlayer].getStrongBox().clone();
        LastGameStatus.marketBoardStatus=convertMarket(status.getMarketsStatus().getMarketBoard());
        LastGameStatus.sideMarbleStatus=convertMarble(status.getMarketsStatus().getSlideMarble());
        LastGameStatus.faithMarkerStatus=status.getPlayersStatus()[idPlayer].getFaithTrackProgress();
        LastGameStatus.popeCards=status.getPlayersStatus()[idPlayer].getPopesFavorCards().clone();
        LastGameStatus.victoryPoints=status.getPlayersStatus()[idPlayer].getPv();
        LastGameStatus.storageState=status.getPlayersStatus()[idPlayer].getStorage().clone();
        LastGameStatus.slotsStatus=status.getPlayersStatus()[idPlayer].getDevelopMentSlots().clone();
        LastGameStatus.cardMarketStatus=status.getMarketsStatus().getCardMarket();
        LastGameStatus.activatableCards=status.getSpecificPlayerStatus(idPlayer+1).getActivableCards().clone();
    }

    /**
     * creates an int copy following convertMarble method assignment
     * @param market marketboard generated from GameStatusUpdate
     * @return int matrix copy
     */
    public static int[][] convertMarket(MarbleColor[][] market){
        int[][] marketBoard=new int[3][4];
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
               marketBoard[i][j]=convertMarble(market[i][j]);
            }
        }
        return marketBoard;
    }

    public static void printEverything(){
        System.out.println("in print everything");


        System.out.println("leader1: " +leader1 + " è giocato? " + leader1Played);
        System.out.println("leader2: " +leader2 + " è giocato? " + leader2Played);
        System.out.println("status strongbox: " + ConnectionHandlerForGui.getGson().toJson(strongboxStatus));
        System.out.println("marketboard status: " + ConnectionHandlerForGui.getGson().toJson(marketBoardStatus));
        System.out.println("sideMarble: " + sideMarbleStatus);
        System.out.println("faithtrack progress: " + faithMarkerStatus);
        System.out.println("popeCards: " + ConnectionHandlerForGui.getGson().toJson(popeCards));
        System.out.println("victoryPoints: " + victoryPoints);
        System.out.println("cardMarketStatus: " + ConnectionHandlerForGui.getGson().toJson(cardMarketStatus));
        System.out.println("storageStatus: " + ConnectionHandlerForGui.getGson().toJson(storageState));
        System.out.println("slotStatus: " + ConnectionHandlerForGui.getGson().toJson(slotsStatus));
        System.out.println("Activatable cards: " + ConnectionHandlerForGui.getGson().toJson(activatableCards));
        System.out.println("idPlayer: " + idPlayer);
    }

    /**
     * This method assigns to each color in MarbleColor.class an int value, used for showing the right ball in the GUI
     * <br>
     *     0=yellow<br>
     *     1=purple<br>
     *     2=blue<br>
     *     3=grey<br>
     *     4=red<br>
     *     5=white<br>
     * @param ball MarbleBall to be converted
     */
    public static int convertMarble(MarbleColor ball){
        switch(ball){
            case red:
                return 4;
            case blue:
                return 2;
            case grey:
                return 3;
            case yellow:
                return 0;
            case white:
                return 5;
            case purple:
                return 1;
            default:
                return -1;
        }
    }
}
