package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.model.MarbleColor;

public class LastGameStatus {
    public static int leader1;
    public static int leader2;
    public static boolean leader1Played;
    public static boolean leader2Played;
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

    public static void setIdPlayer(GameStatusUpdate status){
        LastGameStatus.idPlayer=status.getNextPlayer()-1;
    }


    public static void update(GameStatusUpdate status){

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
        LastGameStatus.activatableCards=status.getSpecificPlayerStatus(idPlayer).getActivableCards().clone();
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
