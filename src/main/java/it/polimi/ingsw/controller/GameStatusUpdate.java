package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.StaticMethods;

/**
 * class that creates an object that represents the game.
 * <br>
 * the method "gameStatus" in staticMethods can be used to create a JSON file copy of an object of this class.
 */
public class GameStatusUpdate {
    private PlayerUpdate[] playersStatus;
    private MarketsUpdate marketsStatus;
    private boolean IsgameEnding;
    private int nextPlayer; //id del prossimo giocatore


    public GameStatusUpdate(int numGiocatori) {
        playersStatus = new PlayerUpdate[numGiocatori];
        marketsStatus = new MarketsUpdate();
        IsgameEnding = false;
        nextPlayer = 0;
    }


    //getters e setters

    public int getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(int nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public MarketsUpdate getMarketsStatus() {
        return marketsStatus;
    }

    public void setMarketsStatus(MarketsUpdate marketsStatus) {
        this.marketsStatus = marketsStatus;
    }

    public PlayerUpdate[] getPlayersStatus() {
        return playersStatus;
    }

    public void setPlayersStatus(PlayerUpdate[] playersStatus) {
        this.playersStatus = playersStatus;
    }

    public PlayerUpdate getSpecificPlayerStatus(int id) {
        return playersStatus[id - 1];
    }

    public boolean isIsgameEnding() {
        return IsgameEnding;
    }

    public void setIsgameEnding(boolean isgameEnding) {
        IsgameEnding = isgameEnding;
    }

    public void setPlayer(PlayerUpdate player) {
        this.playersStatus[player.getId() - 1] = player;
    }

    /**
     * creates a copy of the object as a Json String.
     *
     * @return Json string representing the object
     */
    public String getJsonString() {
        return StaticMethods.objToJson(this);
    }


}
