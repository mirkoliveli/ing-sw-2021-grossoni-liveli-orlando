package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.EndSoloGame;

public class SinglePlayerMatch {
    private Player player;
    private CardMarket cardMarket;
    private MarketBoard marketBoard;
    private LeaderDeck leaderDeck;
    private SoloFaithTrack lorenzoFaithTrack;
    private StackActionToken tokensStack;

    public SinglePlayerMatch(String name){
        this.player=new Player(name, 1);
        this.cardMarket=new CardMarket();
        //this.cardMarket.shuffle();    //messo così per ora per testare, RICORDARSI DI TOGLIERE COMMENTO
        this.marketBoard=new MarketBoard();
        //this.marketBoard.shuffle();   //messo così per ora per testare, RICORDARSI DI TOGLIERE COMMENTO
        this.leaderDeck=new LeaderDeck();
        //this.leaderDeck.shuffle();    //messo così per ora per testare, RICORDARSI DI TOGLIERE COMMENTO
        this.lorenzoFaithTrack=new SoloFaithTrack();
        this.tokensStack =new StackActionToken();
    }







//----------------------------------------------------------------------------------------------------------------------
    //azione di Lorenzo il Magnifico

    /**
     * method that handles the action of the enemy (lorenzo) on single player mode.
     * @throws EndSoloGame if the game ends on this turn, the exceptions is thrown.
     */
    public void LorenzoAction() throws EndSoloGame{
        int tempForFaithTrack=0;
        ActionToken action=tokensStack.playFirst();
        switch (action.getType()){
            case greenToken:
                try {
                    cardMarket.SoloAction(Color.green);
                }catch (EndSoloGame e){
                    throw e;
                }
                break;
            case purpleToken:
                try {
                    cardMarket.SoloAction(Color.purple);
                }catch (EndSoloGame e){
                    throw e;
                }
                break;
            case blueToken:
                try {
                    cardMarket.SoloAction(Color.blue);
                }catch (EndSoloGame e){
                    throw e;
                }
                break;
            case yellowToken:
                try {
                    cardMarket.SoloAction(Color.yellow);
                }catch (EndSoloGame e){
                    throw e;
                }
                break;

            case moveCrossAndShuffle:
                tempForFaithTrack=this.lorenzoFaithTrack.Action(1);
                if(tempForFaithTrack!=0){
                    if(this.player.getBoard().getFaithTrack().DoIActivateTheZone(tempForFaithTrack)){
                        this.player.getBoard().getFaithTrack().activatePopeSpace(tempForFaithTrack);
                    }
                }
                this.tokensStack.resetStack();
                if(tempForFaithTrack==3){ throw new EndSoloGame();}
                break;

            case twoSpaceMovement:
                tempForFaithTrack=this.lorenzoFaithTrack.Action(2);
                if(tempForFaithTrack!=0){
                    if(this.player.getBoard().getFaithTrack().DoIActivateTheZone(tempForFaithTrack)){
                        this.player.getBoard().getFaithTrack().activatePopeSpace(tempForFaithTrack);
                    }
                }
                if(tempForFaithTrack==3){ throw new EndSoloGame();}
                break;
        }

    }








//----------------------------------------------------------------------------------------------------------------------
    //getters e setters


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public CardMarket getCardMarket() {
        return cardMarket;
    }

    public void setCardMarket(CardMarket cardMarket) {
        this.cardMarket = cardMarket;
    }

    public MarketBoard getMarketBoard() {
        return marketBoard;
    }

    public void setMarketBoard(MarketBoard marketBoard) {
        this.marketBoard = marketBoard;
    }

    public LeaderDeck getLeaderDeck() {
        return leaderDeck;
    }

    public void setLeaderDeck(LeaderDeck leaderDeck) {
        this.leaderDeck = leaderDeck;
    }

    public StackActionToken getTokensStack() {
        return tokensStack;
    }

    public void setTokensStack(StackActionToken tokensStack) {
        this.tokensStack = tokensStack;
    }

    public SoloFaithTrack getLorenzoFaithTrack() {
        return lorenzoFaithTrack;
    }

    public void setLorenzoFaithTrack(SoloFaithTrack lorenzoFaithTrack) {
        this.lorenzoFaithTrack = lorenzoFaithTrack;
    }
}
