package it.polimi.ingsw.singlePlayer.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.model.SinglePlayerMatch;
import it.polimi.ingsw.singlePlayer.cli.CliForSP;

public class SinglePlayerHandler {
    private SinglePlayerMatch game;
    private boolean gui;


    public SinglePlayerHandler(String name, boolean cliOrGuichoice){
        game=new SinglePlayerMatch(name);
        gui=cliOrGuichoice;
    }

    public SinglePlayerHandler(String name){
        game=new SinglePlayerMatch(name);
        gui=false;
    }

    /**
     * Method used to choose leaders in singlePlayer
     */
    public void chooseLeaders(){
        int[] selection;
        int[] cardIds=new int[4];
        for(int i=0; i<4; i++){
            cardIds[i]=game.getLeaderDeck().getChoices(1)[i].getId();
        }
        if(!gui){
            selection= CliForSP.leaderChoice(cardIds);
            game.getPlayer().setLeaderCard2(game.getLeaderDeck(), selection[1]);
            game.getPlayer().setLeaderCard1(game.getLeaderDeck(), selection[0]);
        }
    }

    public void startMatch(){
        chooseLeaders();
        utilForChecks();
    }


    public void utilForChecks(){
        GameStatusUpdate gamer=game.gameUpdate();
        Gson gson =new Gson();
        System.out.println(gson.toJson(gamer));
    }

    //------------------------------------------------------------------------------------------------------------------
    public void setGui(boolean gui) {
        this.gui = gui;
    }

    public void setGame(SinglePlayerMatch game) {
        this.game = game;
    }

    public SinglePlayerMatch getGame() {
        return game;
    }

    public boolean isGui() {
        return gui;
    }
}
