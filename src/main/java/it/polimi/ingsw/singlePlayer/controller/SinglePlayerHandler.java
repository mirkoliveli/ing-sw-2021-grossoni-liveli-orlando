package it.polimi.ingsw.singlePlayer.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.cli.ViewState;
import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.model.SinglePlayerMatch;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.TypeOfResource;
import it.polimi.ingsw.singlePlayer.cli.CliForSP;
import it.polimi.ingsw.utils.StaticMethods;

import java.util.Scanner;

public class SinglePlayerHandler {
    private SinglePlayerMatch game;
    private boolean gui;
    private CliForSP cli;


    public SinglePlayerHandler(String name, boolean cliOrGuichoice){
        game=new SinglePlayerMatch(name);
        gui=cliOrGuichoice;
        cli=new CliForSP();
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
        turnMgmt();
        utilForChecks();
    }


    public void utilForChecks(){
        GameStatusUpdate gamer=game.gameUpdate();
        Gson gson =new Gson();
        System.out.println(gson.toJson(gamer));
    }

    /**
     * method that handles the swap depot levels action. It invokes the cli method that prints the storage and get the numbers, then
     * it invokes the storage method that swap the levels and notify the user with the outcome.
     */
    public void storageSwapHandler(){
        int[] depots=CliForSP.storageSwapAction(game.gameUpdate());
        if(depots[0]==0){
            //abort action
        }
        else if((depots[0]<4 && depots[0]>0) && (depots[1]<4 && depots[1]>0)){
            if(game.getPlayer().getBoard().getStorage().swapLevels(depots[0], depots[1])){
                System.out.println("Operation successful, will now show the status of the storage");
                CliForSP.printStorage(game.gameUpdate());
            }
        }
        else{
            //abort action
        }
    }

    /**
     * method that asks for the depot to place resources until a valid option is given
     * @param emptyStatus boolean vector cotaining the info to which level are free
     * @return an int with a valid option
     */
    public static int checkForDepotChoice( boolean[] emptyStatus){
        int selection=CliForSP.selectANumber(0, 3, -1);
        boolean exit=false;
        do{
        switch(selection){
            case 0:
                exit=true;
                System.out.println("selected to discard the resources");
                break;

            case 1:
                System.out.println("selected depot 1");
                if(emptyStatus[selection-1]) {
                    System.out.println("the resource will be stored here!");
                    exit=true;
                    break;
                }
                else{
                    selection=CliForSP.selectANumber(0, 3, -1);
                    System.out.println("you can't select this level because another resource is already stored! please retry");
                    break;
                }
            case 2:
                System.out.println("selected depot 2");
                if(emptyStatus[selection-1]) {
                    System.out.println("the resource will be stored here!");
                    exit=true;
                    break;
                }
                else{
                    System.out.println("you can't select this level because another resource is already stored! please retry");
                    selection=CliForSP.selectANumber(0, 3, -1);
                    break;
                }

            case 3:
                System.out.println("selected depot 3");
                if(emptyStatus[selection-1]) {
                    System.out.println("the resource will be stored here!");
                    exit=true;
                    break;
                }
                else{
                    System.out.println("you can't select this level because another resource is already stored! please retry");
                    selection=CliForSP.selectANumber(0, 3, -1);
                    break;
                }

            default:
                System.out.println("not a valid input! please retry");
                break;
        }

        }while (!exit);
        return selection;
    }

    /**
     * method that handles the sorting of resources to a valid place
     * @param storage
     * @param emptyStatus
     * @param index
     * @param resources
     */
    public static void addToStorage(Storage storage, boolean[] emptyStatus, int index, int[] resources){
        Gson gson=new Gson();
        System.out.println("empty status: "+ gson.toJson(emptyStatus));
        int selection=checkForDepotChoice(emptyStatus);
        if(selection!=0)
        storage.setNewResourceToDepot(selection, StaticMethods.IntToTypeOfResource(index+1), index, resources);
        System.out.println(gson.toJson(storage.storageStatus()));
    }

    public void marbleMarketAction(){
        boolean rowColumn=false;
        int rowOrColumn;
        int line=1;
        int[] result;

        if(!gui) {
            System.out.println("you selected the marble market action!");
            CliForSP.printMarbleMarket(game.gameUpdate());
            System.out.println("Select 0 if you choose a row, 1 if you chose a column:");
            rowOrColumn=CliForSP.selectANumber(0,1,-1);
            rowColumn= rowOrColumn == 0;
            System.out.println("now please select the line you desire (1 - "+ (3+rowOrColumn) +"):");
            line=CliForSP.selectANumber(1, (3+rowOrColumn), -1);
        }
        result=game.getMarketBoard().ConversionToArray(rowColumn, line-1);
        //interazione leader da aggiungere

        int[] resoruces={result[0], result[1], result[2], result[3]};

        System.out.println("risorse scartate: " + game.getPlayer().getBoard().getStorage().IncreaseResources(resoruces) + "");
        CliForSP.printStorage(game.gameUpdate());
    }

    public void turnMgmt() {
        //should clear the console when using the jar? does not work on intellij tho
        System.out.println("\033[H\033[2J");

        ViewState.resetTurn();
        GameStatusUpdate status = game.gameUpdate();
        int numberChosen=-1;
        boolean actionChosen = false;

        while (!actionChosen) {
            ViewState.setAction_aborted(false);
            System.out.println("\nIt's your turn! Choose an action:\n[0]: Take resources from the market\n[1]: Buy one development card\n[2]: Activate the production\nElse, you can (before or after your action):\n[3]: Swap resources between your depots\n[4]: Play or discard a leader card");
            numberChosen=CliForSP.selectANumber(0, 6, -1);
            switch (numberChosen) {
                case 0:
                    actionChosen = true;
                    //actionMarbleMarket(status.getMarketsStatus().getMarketBoard(), status.getMarketsStatus().getSlideMarble(), serverConnection, status);
                    marbleMarketAction();
                    break;
                case 1:
                    actionChosen = true;
                    /*try {
                        //printCardMarket(status.getMarketsStatus().getCardMarket(), serverConnection, status);
                    }catch(IOException e){
                        System.out.println("disconnected from server!");
                    }*/
                    if (ViewState.isMainActionCompleted()) actionChosen = true;
                    break;
                case 2:
                    actionChosen = true;
                    //ProductionActionStarter(status, serverConnection);
                    System.out.println("da fastidio l'errore");
                    if (ViewState.isMainActionCompleted()) actionChosen = true;
                    break;
                case 3:
                    storageSwapHandler();
                    //swapDepots(status.getPlayersStatus()[status.getNextPlayer()-1].getStorage(), serverConnection);
                    break;
                case 4:
                    //PlayOrDiscardLeaders(status.getNextPlayer(), status, serverConnection);
                    break;
                case 5:
                    int i = 0;
                    while (i < status.getPlayersStatus().length) {
                        //printPersonalBoard(status.getSpecificPlayerStatus(i+1));
                        i++;
                    }
                    ViewState.setAction_aborted(true);
                    break;
                //used as debug :), the numbers contain a little easter egg
                case 6:
                    //totallyNormalAndNotSuspiciousAtAllMethod(serverConnection);
                    break;
                default:
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                    ViewState.setAction_aborted(true);
                    break;
            }
            if (!ViewState.isAction_aborted()) {

            }

        }
        actionChosen = false;
        while (!actionChosen) {
            ViewState.setAction_aborted(false);
            System.out.println("You already performed your action! Now you can:\n[0]: Swap resources between your depots\n[1]: Play or discard a leader card\n[2]: End your turn");
            numberChosen=CliForSP.selectANumber(0, 6, -1);
            switch (numberChosen) {
                case 0:
                    storageSwapHandler();
                    break;
                case 1:
                    //PlayOrDiscardLeaders(status.getNextPlayer(), status, serverConnection);
                    break;
                case 2:
                    //EndTurn(serverConnection);
                    if (ViewState.isTurn_ended()) {
                        actionChosen = true;
                    }
                    break;
                case 5:
                    int i = 0;
                    while (i < status.getPlayersStatus().length) {
                        //printPersonalBoard(status.getSpecificPlayerStatus(i+1));
                        i++;
                    }
                    ViewState.setAction_aborted(true);
                    break;
                //little easter egg
                case 97532044:
                    //instantFinish(serverConnection);
                    break;

                default:
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                    ViewState.setAction_aborted(true);
                    break;
            }
            //nota che se l'azione viene abortita con questo passaggio si va in deadlock, modificare una volta terminato il testing preliminare
            if (!ViewState.isAction_aborted()) {

            }
        }
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
