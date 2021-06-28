package it.polimi.ingsw.singlePlayer.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.cli.CommandLine;
import it.polimi.ingsw.cli.ViewState;
import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.SinglePlayerMatch;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.TypeOfResource;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.singlePlayer.cli.CliForSP;
import it.polimi.ingsw.utils.StaticMethods;

import java.util.Scanner;

public class SinglePlayerHandler {
    private SinglePlayerMatch game;
    private boolean gui;
    private CliForSP cli;
    private boolean gameFinished=false;


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
        while(!gameFinished){
            turnMgmt();
            try {
                game.LorenzoAction();
            }catch(EndSoloGame e){
                youLose();
            }
        }
    }

    /**
     * prints a state of the game
     */
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
     * @param storage pointer to storage to ease the interaction
     * @param emptyStatus status of the depots
     * @param index used as pointer to the right resource
     * @param resources vector containing the remaining resources
     */
    public static void addToStorage(Storage storage, boolean[] emptyStatus, int index, int[] resources){
        int selection=checkForDepotChoice(emptyStatus);
        if(selection!=0)
        storage.setNewResourceToDepot(selection, StaticMethods.IntToTypeOfResource(index+1), index, resources);
    }

    /**
     * method that starts the marbleMarket Action. asks the player for a choice between row and column then asks for the specific row or column
     * then handles the leader interactions, the resource increase and the possible discarded resources by calling the specific methods.
     */
    public void marbleMarketAction(){
        boolean rowColumn=false;
        int rowOrColumn;
        int line=1;
        int[] result;
        int discarded=0;

        if(!gui) {
            System.out.println("you selected the marble market action!");
            CliForSP.printMarbleMarket(game.gameUpdate());
            System.out.println("Select 0 if you choose a row, 1 if you choose a column:");
            rowOrColumn=CliForSP.selectANumber(0,1,-1);
            rowColumn= rowOrColumn == 0;
            System.out.println("now please select the line you desire (1 - "+ (3+rowOrColumn) +"):");
            line=CliForSP.selectANumber(1, (3+rowOrColumn), -1);
        }


        result=game.getMarketBoard().ConversionToArray(rowColumn, line-1);
        game.getMarketBoard().ChangeBoard(rowColumn, line);
        //interazione leader da aggiungere

        //update faithtrack
        if(result[4]>0) game.getPlayer().getBoard().getFaithTrack().Movement(result[4]);

        //update storage
        int[] resoruces={result[0], result[1], result[2], result[3]};
        discarded=game.getPlayer().getBoard().getStorage().IncreaseResources(resoruces);


        if(discarded>0){
            if(!gui) System.out.println("\nyou have discarded: " + discarded + " resources!\n");
            try {
                game.lorenzoFaithTrackMove(discarded);
            }catch (EndSoloGame e){
                youLose();
            }
        }

        if(!gui) CliForSP.printStorage(game.gameUpdate());
    }

    /**
     * logic for the play or discard a leader action. Given the choice as an int[],
     * <br>[0]: which leader; <br>[1] discard or play
     * <br>the method returns an int stating which state has been reached
     * <br>
     * "1" means that the leader has been discarded correctly
     * <br>
     * "0" means that the leader has been played correcly
     * <br>
     * "2" means that the player does not meet the conditions to play the leader (meaning that they can play them in the future)
     * <br>
     * "3" means that the player has already played or discarded the leader, meaning that they can't play them again
     * <br>
     * "-1" input error, abort action
     * @param choice represents the decision made in the cli or gui methods: <br>[0]: which leader; <br>[1] discard or play
     * @return int stating which state has been reached
     */
    public int playOrDiscardLeadersLogic(int[] choice){
        if(choice[1]==2){
                if(     game.getPlayer().switchLeader(choice[0])!=null && //condizione
                        !game.getPlayer().switchLeader(choice[0]).checkIfPlayed() && //condizione
                        !game.getPlayer().switchLeader(choice[0]).isDiscarded() //condizione
                ) {
                    game.getPlayer().getBoard().getFaithTrack().Movement(1);
                    game.getPlayer().switchLeader(choice[0]).discardCard();
                    return 1;
                }
                else{
                    return 3;
                }
        }
        if(choice[1]==1){
            try{
                if(game.getPlayer().playLeader(choice[0])) {
                    return 0;
                }
                else{
                    return 2;
                }
            }catch(AlreadyPlayedOrDiscardedLeader e){
                return 3;
            }
        }
        return -1;
    }

    /**
     * method that starts the play or discard a leader action.
     */
    public void playOrDiscardLeaders(){
        int[] selection;
        if(!gui){
            selection=CliForSP.playOrDiscardLeader(game.gameUpdate(), game.getPlayer().getLeaderCard1().isDiscarded(), game.getPlayer().getLeaderCard2().isDiscarded());
        }
        else{
            selection=new int[]{0, 0};
        }
        if(selection[0]>0){
            if(!gui) CliForSP.playOrDiscardActionResult(playOrDiscardLeadersLogic(selection));
        }
    }

    /**
     * method that buys a card (or not)
     * @return true if a card has been bought, false otherwise
     */
    public boolean buyACardAction(){
    int card;
    int slot;
        if(!gui){
            card=CliForSP.selectACardFromMarket(game.gameUpdate());
            if(card==0){
                System.out.println("you selected exit");
                return false;
            }
            try{
                if(game.CanIBuyThisCard(card)){
                    DevelopmentCard temp=game.getCardMarket().BuyCard(card);
                    game.getPlayer().payForACard(temp.getCost());
                    slot=CliForSP.selectASlot(game.gameUpdate(), game.getPlayer().getBoard().whereCanIPlaceTheCard(temp.getLevel()));
                    game.getPlayer().getBoard().getSlot(slot).placeCard(temp);
                    return true;
                }
                else{
                    System.out.println("you can't buy this card! please retry with another card or choose another action");
                    return false;
                }
            }catch(CardNotFoundException | IllegalCardException | NotEnoughResources e){
                return false;
            }
        }
        else {
            //gui
            return false;
        }
    }

    /**
     * production action for cli, the method does a loop where it asks the client for a production, checks if he can pay for it and then asks for a new production (or quit)
     * @param status game status
     * @param actionable parameter generated by the player.class adding some controls for the production
     * @return true if the action is completed, false otherwise
     */
    public boolean productionActionCli (GameStatusUpdate status, boolean[] actionable){
        int value;
        int position;
        int[] realcost={0, 0, 0, 0};
        boolean[] selection={false, false, false, false, false, false};
        int[] cost=new int[4];
        CliForSP.productionActionStarter(status, actionable);
        do {

            value = CliForSP.selectANumber(-1, CliForSP.totalNumOfTrues(actionable)-1, -2);
            position=CliForSP.positionOfATrueValue(value, actionable);
            if(position>-1 && selection[position]) System.out.println("you have already selected this production");

            cost=realcost.clone();
            //ottengo il costo in un clone
            if(position>-1 && !selection[position]) {
                //produzione base
                if(position==0){
                    StaticMethods.sumArray(cost, CliForSP.baseProductionCostforCli(game.gameUpdate()));
                }
                //produzione slots
                else if(position<4){
                    StaticMethods.sumArray(cost, game.getPlayer().getBoard().getSlot(position).get_top().getProductionCost());

                }
                //produzione leaders
                else{
                    addLeaderCostToProduction(cost, position-4);
                }
            }
            //controllo se può pagare
            if(value!=-1 && game.getPlayer().canIPayForProductions(cost)){
                selection[position]=true;
                realcost=cost.clone();
            }
            //se non può pagare lo avviso
            else{
                if(value!=-1)
                System.out.println("You can't select this production because you can't pay for it, please select another production or quit by typing -1");
            }
            if(value!=-1) System.out.println("please insert a new value:");
        } while (value != -1);

        //pago
        if((realcost[0]+realcost[1]+realcost[2]+realcost[3])>0){
            //faccio effettivamente una produzione
            try {
                paymentAndGain(selection, realcost);
                return true;
            }catch(NotEnoughResources e){
                System.out.println("error while paying, exception thrown");
                return false;
            }
        }
        else{
            return false;
        }

    }

    /**
     * method that pays a production then adds the "gains"
     * @param selection selected productions
     * @param realcost total cost
     * @throws NotEnoughResources cannot complete the payment
     */
    public void paymentAndGain(boolean[] selection, int[] realcost) throws NotEnoughResources{
        game.getPlayer().payForProductions(realcost);
        int[] gain=getPaidForProductions(selection);
        int[] resources={gain[0], gain[1], gain[2], gain[3]};
        game.getPlayer().getBoard().getStrongbox().store(resources);
        game.getPlayer().getBoard().getFaithTrack().Movement(gain[4]);
    }

    /**
     * method for cli that give resources to the player after the productions
     * @param selection all the activated productions
     * @return vector with the gains in resources and faith points
     */
    public int[] getPaidForProductions(boolean[] selection){
        int[] gains=new int[5];
        for(int i=0; i<6; i++){
            if(i==0 && selection[i]){
                CliForSP.baseProductionReward(gains);
            }
            else if(i<3 && selection[i]){
                StaticMethods.sumArray(gains, game.getPlayer().getBoard().getSlot(i).get_top().getProduct());
            }
            else if(selection[i]){
                CliForSP.leaderProductionReward(gains);
            }
        }
        return gains;
    }

    /**
     * starter for the production action
     * @return true if action is done, false if is aborted
     */
    public boolean activateProduction(){
        boolean resultOfAction=false;
        boolean[] ideallyActivable=game.getPlayer().whichProductionIsUsable();
        if(!gui){
            resultOfAction=productionActionCli(game.gameUpdate(), ideallyActivable);
        }
        else{
            //gui?
            resultOfAction=false;
        }
        return resultOfAction;
    }

    /**
     * method that adds the cost of a leader production to the costs
     * @param cost cost (4 resources) at the moment
     * @param leader leader to add resources (1 or 2 expected)
     */
    public void addLeaderCostToProduction(int[] cost, int leader){
        if(leader<3 && leader>0){
            cost[StaticMethods.TypeOfResourceToInt(game.getPlayer().switchLeader(leader).getPower())]++;
        }
    }

    /**
     * message if you lose against lorenzo
     */
    public void youLose(){
        game.getPlayer().updateVictoryPoints(true);
        System.out.println("oh no! Lorenzo has finished before you! but you still scored a lot of points!" +
                "\nThis match you scored a total of: " + game.getPlayer().getVictoryPoints());
        try {
            gameFinished=true;
            Thread.sleep(5000);
        }catch(InterruptedException e){
            System.exit(0);
        }
        System.exit(0);
    }

    /**
     * message statement if the player wins
     */
    public void youWin(){
        game.getPlayer().updateVictoryPoints(true);
        System.out.println("WOW! You have defeated Lorenzo! Congratualtions!!" +
                "\nIn this match you scored a total of: " + game.getPlayer().getVictoryPoints() + " points!");
        try {
            gameFinished=true;
            Thread.sleep(5000);
        }catch(InterruptedException e){
            System.exit(0);
        }
        System.exit(0);
    }



    /**
     * handles all the cli game
     */
    public void turnMgmt() {
        System.out.println("\033[H\033[2J"); //should clear the console when using the jar? does not work on intellij tho
        int numberChosen=-1;

        boolean actionChosen = false;
        while (!actionChosen) {
            System.out.println("\nIt's your turn! Choose an action:\n[0]: Take resources from the market\n[1]: Buy one development card\n[2]: Activate the production\nElse, you can (before or after your action):\n[3]: Swap resources between your depots\n[4]: Play or discard a leader card" +
                    "\n[5]: visualize your board\n[6]: visualize card market and marble market");
            numberChosen=CliForSP.selectANumber(0, 6, -1);
            switch (numberChosen) {
                case 0:
                    actionChosen = true;
                    marbleMarketAction();
                    break;
                case 1:
                    actionChosen = buyACardAction();
                    break;
                case 2:
                    actionChosen=activateProduction();
                    CommandLine.printResourcesArray(game.gameUpdate().getPlayersStatus()[0].getStrongBox());
                    break;
                case 3:
                    storageSwapHandler();
                    break;
                case 4:
                    playOrDiscardLeaders();
                    break;
                case 5:
                    CommandLine.printActivePlayerPersonalBoard(game.gameUpdate().getPlayersStatus()[0]);
                    break;
                case 6:
                    CliForSP.printMarbleMarketAndCardMarket(game.gameUpdate());
                    break;
                default:
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                    break;
            }
            game.getPlayer().updateVictoryPoints(false);
        }

        actionChosen = false;
        while (!actionChosen) {
            System.out.println("You already performed your action! Now you can:\n[0]: Swap resources between your depots\n[1]: Play or discard a leader card\n[2]: End your turn\n[3]: visualize your board\n[4]: visualize Lorenzo's progress on faithTrack\n[5]: visualize card market and marble market");
            numberChosen=CliForSP.selectANumber(0, 5, -1);
            switch (numberChosen) {
                case 0:
                    storageSwapHandler();
                    break;
                case 1:
                    playOrDiscardLeaders();
                    break;
                case 2:
                    actionChosen=true;
                    break;
                case 3:
                    CommandLine.printActivePlayerPersonalBoard(game.gameUpdate().getPlayersStatus()[0]);
                    break;
                case 4:
                    game.getLorenzoFaithTrack().CoolPrint();
                    break;
                case 5:
                    CliForSP.printMarbleMarketAndCardMarket(game.gameUpdate());
                    break;
                default:
                    System.out.println("\u001B[1;31mInvalid input!\u001B[0m");
                    break;
            }
            game.getPlayer().updateVictoryPoints(false);
        }
        if(game.getPlayer().hasPlayerFinished()){
        youWin();
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
