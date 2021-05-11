package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.MarbleColor;

public class MarketsUpdate {
    private int[][] cardMarket; //invia l'id solo le 12 carte che il giocatore può acquistare
    private MarbleColor[][] marketBoard; //invia la disposizione delle dodici palline
    private MarbleColor slideMarble;

    //se un mazzo di carte è vuoto l'id presente sarà zero

    public MarketsUpdate() {
        cardMarket = new int[3][4];
        marketBoard = new MarbleColor[3][4];
        slideMarble = null;
    }

    //getters e setters

    public int[][] getCardMarket() {
        return cardMarket;
    }

    public void setCardMarket(int[][] cardMarket) {
        this.cardMarket = cardMarket;
    }

    public MarbleColor[][] getMarketBoard() {
        return marketBoard;
    }

    public void setMarketBoard(MarbleColor[][] marketBoard) {
        this.marketBoard = marketBoard;
    }

    public MarbleColor getSlideMarble() {
        return slideMarble;
    }

    public void setSlideMarble(MarbleColor slideMarble) {
        this.slideMarble = slideMarble;
    }
}
