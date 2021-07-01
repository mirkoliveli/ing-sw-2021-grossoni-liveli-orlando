package it.polimi.ingsw.singlePlayer.cli;


import com.google.gson.Gson;
import it.polimi.ingsw.cli.CommandLine;
import it.polimi.ingsw.cli.ViewState;
import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.messages.chooseDepotMessage;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.LeaderDeck;
import it.polimi.ingsw.model.SinglePlayerMatch;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.networking.Client;
import it.polimi.ingsw.singlePlayer.controller.SinglePlayerHandler;
import it.polimi.ingsw.utils.StaticMethods;

import java.io.*;
import java.util.Scanner;

public class CliForSP {

    public static final String pathForDevCards="/devCards.json";
    public static LeaderDeck leaderCards=new LeaderDeck("aa");
    public static DevelopmentCard[] developmentCards;

    public static void developmentPopulate(String path) {
        Gson gson = new Gson();
        Reader reader =new InputStreamReader(DevelopmentCard[].class.getResourceAsStream(pathForDevCards));
        developmentCards = gson.fromJson(reader, DevelopmentCard[].class);


        /*Gson gson = new Gson();
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
        developmentCards = gson.fromJson(buffer, DevelopmentCard[].class);*/
    }


    public CliForSP(){
        developmentPopulate("src/main/resources/devCards.json");
    }
    /**
     * method that takes the 4 leaders (form which the player chooses 2), prints them and asks for a selection, then returns the 2 selected values.
     * @param choices vector that contains the 4 possible leaders
     * @return vector containing the 2 selected leaders
     */
    public static int[] leaderChoice(int[] choices){
        int[] selection=new int[2];
        int[] realSelection=new int[2];
        System.out.println("These are the choices for the 2 leader, please select 2 of them by typing the number 1-4");
        for(int i=0; i<4; i++){
            printLeader(choices[i]);
            System.out.println();
        }
        System.out.println("Please select the first leader (type a number between 1 and 4)");
        selection[0]=selectANumber(1, 4, -1);
        realSelection[0]=choices[selection[0]-1];
        System.out.println("Please select the second leader (type a number between 1 and 4)");
        selection[1]=selectANumber(1, 4, selection[0]);
        realSelection[1]=choices[selection[1]-1];
        System.out.println("Thanks! The game will start soon");
        return realSelection;
    }

    /**
     * method that requests a int from the client and returns it after it passes some checkes
     * @param min minimum value accepted
     * @param max maximum value accepted
     * @param mustBeDiverseFromThis optional value (set at -1 if not necessary), it's used if the number selected must be diverse from a specific number
     * @return the slected and accepted value
     */
    public static int selectANumber(int min, int max, int mustBeDiverseFromThis){
        Scanner scanner=new Scanner(System.in);
        String answer;
        int value=0;

        while(true){
            answer=scanner.nextLine();
            try{
                value=Integer.parseInt(answer);
            if(value<min || value>max){
                System.out.println("Your number must be between " + min + " "+ max +"!");
            }
            else if(value>=min && value<=max){
                if( mustBeDiverseFromThis!=value){
                return value;
                }
                else{
                    System.out.println("you must insert a different value from "+ mustBeDiverseFromThis + "!");
                }
            }
            }catch(NumberFormatException e){
                System.out.println("You must insert a number!");
            }
        }
    }

    public static void printLeader(int id) {
        id = id - 49;
        if (id >= 0 && id <= 3) //LeaderCardDiscount
        {
            System.out.println("Leader Type: \u001B[31mDiscount\n\u001B[0mRequirements: 1 " + leaderCards.getCard(id).getColor1DiscountCard() + " card and 1 " + leaderCards.getCard(id).getColor2DiscountCard() + " card\nDiscount: -1 " + leaderCards.getCard(id).getPower() + "\nVictory Points: \u001B[32m+" + leaderCards.getCard(id).getPv() + "\u001B[0m");
        } else if (id >= 4 && id <= 7) //LeaderCardStorage
        {
            System.out.println("Leader Type: \u001B[31mStorage\n\u001B[0mCost: 5 " + leaderCards.getCard(id).getStorageCardCost() + "\nResource contained: " + leaderCards.getCard(id).getPower() + "\nVictory Points: \u001B[32m+" + leaderCards.getCard(id).getPv() + "\u001B[0m");
        } else if (id >= 8 && id <= 11) //LeaderCardWhiteBall
        {
            System.out.println("Leader Type: \u001B[31mWhite Ball\n\u001B[0mRequirements: 2 " + leaderCards.getCard(id).getColor1WhiteBallCard() + " card and 1 " + leaderCards.getCard(id).getColor2WhiteBallCard() + " card\nTurns white balls into: " + leaderCards.getCard(id).getPower() + "\nVictory Points: \u001B[32m+" + leaderCards.getCard(id).getPv() + "\u001B[0m");
        } else if (id >= 12 && id <= 15) //LeaderCardFactory
        {
            System.out.println("Leader Type: \u001B[31mProduction\n\u001B[0mRequirements: 1 Level Two " + leaderCards.getCard(id).getColorProductionCard() + " card\nProduction: 1 " + leaderCards.getCard(id).getPower() + " => 1 resource of any type + 1 \u001B[31mfaith point\u001B[0m\nVictory Points: \u001B[32m+" + leaderCards.getCard(id).getPv() + "\u001B[0m");
        }
    }

    /**
     * method that prints the storage for single player games, it uses the printStorageStatus form CommandLine.class
     * @param status gamestatus used to find informations quickly
     */
    public static void printStorage(GameStatusUpdate status){
        CommandLine.printStorageStatus(status.getPlayersStatus()[0]);
    }

    /**
     * method that prints the storage status and asks what depots should be swapped
     * @param status game status used to print the storage
     * @return vector containing the 2 selected levels+
     */
    public static int[] storageSwapAction(GameStatusUpdate status){
        System.out.println("You chose to swap depots!");
        printStorage(status);
        System.out.println("please type 2 numbers between 1 and 3 (referring to the depot with that amount of resources), or type 0 if you want to exit");
        int[] selection =new int[2];
        System.out.println("type the first number:");
        selection[0]=selectANumber(0, 3, -1);
        if(selection[0]==0){
            System.out.println("you selected 0, the action will be aborted");
            selection[1]=0;
            return selection;
        }
        System.out.println("Now type the second number:");
        selection[1]=selectANumber(1, 3, selection[0]);
        return selection;
    }

    public static void printStorageEmptyStatus(boolean[] emptyDepots, int[] resourceToManage){
        CommandLine.printDepotChoice(emptyDepots, resourceToManage);
    }

    /**
     * manages the interaction with the storage when deciding where to place resources
     * @param storage storage given to ease the interaction
     * @param resourcesStatus status created in the storage stating what resources are yet to be handled
     * @param resources resources left to place or discard
     */
    public static void resourceAdderInteraction(Storage storage, boolean[] resourcesStatus, int[] resources){
        int discarded=0;
        int[] resource_and_quantity= new int[2];
        for(int i=0; i<4; i++){
            if(!resourcesStatus[i] && storage.EmptyDepot() && resources[i]>0) {
                resource_and_quantity[0]=i;
                resource_and_quantity[1]=resources[i];
                printStorageEmptyStatus(storage.emptyStatus(), resource_and_quantity);
                System.out.println("Select 0 if you wish to discard the resources, otherwise select the level where you wish them to be stored: ");

                SinglePlayerHandler.addToStorage(storage, storage.emptyStatus(), i, resources);

            }
        }
    }

    /**
     * method used for the action play or discard a leader, prints a status of the leaders (prints the played leaders then the "playable or discardable" leaders)
     * then asks for a selection
     * @param status game status used to get faster the ids of the leaders
     * @param isDiscarded1 missing info in the game status is given directly
     * @param isDiscarded2 missing info in the game status is given directly
     * @return selection
     */
    public static int[] playOrDiscardLeader(GameStatusUpdate status, boolean isDiscarded1, boolean isDiscarded2){
        boolean firstPlayed=false, secondPlayed=false; //just for ease of reading
        int[] selection=new int[2];
       if(status.getPlayersStatus()[0].isFirstLeaderPlayed() || status.getPlayersStatus()[0].isSecondLeaderPlayed()) System.out.println("You have already played these leaders:");
        if(status.getPlayersStatus()[0].isFirstLeaderPlayed()){
            printLeader(status.getPlayersStatus()[0].getFirstLeader());
            firstPlayed=true;
        }
        System.out.println();
        if(status.getPlayersStatus()[0].isSecondLeaderPlayed()){
            printLeader(status.getPlayersStatus()[0].getSecondLeader());
            secondPlayed=true;
        }
        System.out.println();
        if((!firstPlayed && !isDiscarded1 ) || (!secondPlayed && !isDiscarded2))
        System.out.println("You currently can play or discard these leaders");

        if(!firstPlayed && !isDiscarded1) printLeader(status.getPlayersStatus()[0].getFirstLeader());
        if(!secondPlayed && !isDiscarded2) printLeader(status.getPlayersStatus()[0].getSecondLeader());

        System.out.println("select 1 if you want to do an action with the first leader, 2 if you want to do an action with the second, or 0 to abort: ");
        selection[0]=selectANumber(0, 2, -1);
        if(selection[0]!=0){
        System.out.println("nice! Now select 1 of you want to play it or 2 if you want to discard it:");
        selection[1]=selectANumber(1, 2, -1);}
        return selection;
    }

    /**
     * prints a result for the play or discard a leader action. has 5 possible results (one is the default, which is presented if an error occurs)
     * @param result result is genereated as an int from the SinglePlayerHandler.playOrDiscardLeadersLogic method
     */
    public static void playOrDiscardActionResult(int result){
        switch(result){
            case 0:
                System.out.println("Leader played correctly");
                break;
            case 1:
                System.out.println("Leader discarded correctly");
                break;
            case 2:
                System.out.println("Sorry, you cannot play this leader right now!");
                break;
            case 3:
                System.out.println("you have already played or discarded this leader!");
                break;
            default:
                System.out.println("Some error occurred, the action was aborted, please retry");
                break;
        }
    }

    /**
     * prints all the cards visible in the market at the moment
     * @param status gameStatus, contains the marketCards status
     */
    public static void printCardMarket(GameStatusUpdate status){
        int id;
        for(int i=0; i<3; i++){
            System.out.println("CARDS OF LEVEL " + (i+1));
            System.out.println();
            for(int j=0; j<4; j++){
                id=status.getMarketsStatus().getCardMarket()[i][j];
                printCard(id);
            }
        }
    }

    /**
     * prints a specific card or EMPTY if the given id is zero
     * @param id id of the card
     */
    public static void printCard(int id){
        id = id - 1;
        if(id>-1 && id<49){
            System.out.println("- Id: " + developmentCards[id].getId());
            System.out.println("- Level " + developmentCards[id].getLevel() + " " + developmentCards[id].getColor() + " card");
            // costi
            System.out.println("- Cost:");
            CommandLine.printArray(developmentCards[id].getCost());
            System.out.println("- Production Cost:");
            CommandLine.printArray(developmentCards[id].getProductionCost());
            System.out.println("- Production Power:");
            CommandLine.printArray(developmentCards[id].getProduct());
            System.out.println("- Victory points: \u001B[32m" + developmentCards[id].getPv() + "\u001B[0m\n");
            System.out.println();
        }
        else{
            System.out.println("SLOT EMPTY\n");
        }
    }

    /**
     * method that handles the selection of a card during the buy a card from market.
     * @param status game status used to print the market
     * @return id of the bought card, 0 if action aborted
     */
    public static int selectACardFromMarket(GameStatusUpdate status){
        int selection;
        boolean nextphase=true;
        System.out.println("You selected the buy a card from market action! here's the cards available:\n");
        printCardMarket(status);
        System.out.println("now please type the id of the card you want to buy, write 0 if you want to exit: ");
        do {
            if(!nextphase) System.out.println("now please type the id of the card you want to buy, or 0 if you want to exit: ");

            selection = selectANumber(0, 48, -1);
            nextphase= validate(selection, status.getMarketsStatus().getCardMarket());
            if(selection==0) nextphase=true;

            if(!nextphase) System.out.println("Your selected card is not present in the market!");
        }while(!nextphase);

        return selection;
    }

    /**
     * prints leaders, can handle a boolean vector stating which leader should not be printed
     * @param status game status to get the leaders id
     * @param onlyThese vector stating which leader should be printed (give null if both wanted)
     */
    public static void printLeaders(GameStatusUpdate status, boolean[] onlyThese){
        if(onlyThese==null || onlyThese[0]){
            printLeader(status.getPlayersStatus()[0].getFirstLeader());
        }
        if(onlyThese==null || onlyThese[1]){
            printLeader(status.getPlayersStatus()[0].getSecondLeader());
        }
    }

    /**
     * prints all the slots with the card or "empty"
     * @param status game status to get the info for the print ( game.gameUpdate() )
     */
    public static void printSlots(GameStatusUpdate status){
        for(int i=1; i<4; i++){
            printDevelopmentSlot(status, i);
        }
    }

    /**
     * Cli method for selecting a slot
     * @param status status of the game
     * @param slots available slots
     * @return slot selected
     */
    public static int selectASlot(GameStatusUpdate status ,boolean[] slots){
        int selection;
        boolean goNext=false;
        System.out.println("these are the available slots you can select: ");
        for(int i=0; i<3; i++){
            if(slots[i]) printDevelopmentSlot(status, i+1);
        }
        do{
            System.out.println("please choose a slot");
            selection=selectANumber(1, 3, -1);
            if(!slots[selection-1]) {
                System.out.println("Not a valid slot, please retry");
                goNext=false;
            }
            else{
                goNext=true;
            }
        }while(!goNext);
        return selection;
    }

    /**
     * prints the slot and the relative card inside, prints empty if no card is present
     * @param status game status
     * @param slot slot
     */
    public static void printDevelopmentSlot(GameStatusUpdate status, int slot){
        if(slot<4 && slot>0){
            System.out.println("slot " + slot);
            int cardId=status.getPlayersStatus()[0].getActivableCards()[slot-1];
            if(cardId==0) System.out.println("empty");
            else printCard(cardId);
            System.out.println();
        }
    }

    public static void printProductions(GameStatusUpdate status, boolean[] actionable){
        boolean[] leaders={actionable[4], actionable[5]};

        //print of slots
        for(int i=0; i<3; i++){
            if(actionable[i+1]) {
                printDevelopmentSlot(status, i+1);
            }
        }
        //print of production leaders
        printLeaders(status, leaders);
    }

    /**
     * prints a menù for the production action
     * @param actionable what production can be theoretically activated
     */
    public static void printSelectionForProduction(boolean[] actionable){
        int counter=0;
        if(actionable[0]){
            System.out.println(counter + ") Base Production");
            counter++;
        }
        for(int i=0; i<3; i++){
            if(actionable[i+1]){
                System.out.println(counter + ") slot " + i+1);
                counter++;
            }
        }

        if(actionable[4]) {
            System.out.println(counter + ") First Leader");
            counter++;
        }
        if(actionable[5]) {
            System.out.println(counter + ") Second leader Leader");
            counter++;
        }
    }

    public static void baseProductionReward(int[] production){
        System.out.println("you have produced with the base production, please select which resource will be given to you");
        System.out.print("1 -> coins\n2 -> servants\n3 -> shields\n4 -> stones\n\n");
        int value=selectANumber(1, 4, -1);
        production[value-1]++;
    }

    public static void leaderProductionReward(int[] production){
        System.out.println("you have produced with a leader, please select which resource will be given to you");
        System.out.print("1 -> coins\n2 -> servants\n3 -> shields\n4 -> stones\n\n");
        int value=selectANumber(1, 4, -1);
        production[value-1]++;
    }



    public static void productionActionStarter(GameStatusUpdate status, boolean[] actionable){
        printProductions(status, actionable);
        System.out.println("You selected the production action! Before this line your slots and leaders have been printed to help with the selection.");
        System.out.println("Type the number you wish to select, or type -1 to exit (note that if you have selected a production it can't be cancelled with -1)");
        System.out.println("note that the resources gained from a selection will be asked at the end of the action (your base and leader productions)");
        printSelectionForProduction(actionable);
    }

    /**
     * prints the storage status of the player and the marble market status
     * @param status game status
     */
    public static void printMarbleMarket(GameStatusUpdate status){
        printStorage(status);
        CommandLine.marbleMarketStatus(status.getMarketsStatus().getMarketBoard(), status.getMarketsStatus().getSlideMarble());
    }

    /**
     * checks if a card is in the market
     * @param num card's id
     * @param matrix id of cardmarket
     * @return true if id is in matrix, false otherwise
     */
    public static boolean validate(int num, int[][] matrix){
        for(int j=0; j<3; j++){
            for(int i=0; i<matrix[j].length; i++){
                if(num==matrix[j][i]) return true;
            }
        }
        return false;
    }

    /**
     * util that returns the position of a true inside vector (if i'm searching for the third true inside a {treu, true, false, false, false, true} the method
     * will return 5
     * @param whatTrueAreYouSearching number of true that must have appeared before the returned position
     * @param array array to scan
     * @return position of the n-th true, -420 if the array contains less than n true
     */
    public static int positionOfATrueValue(int whatTrueAreYouSearching, boolean[] array){
        int counter=-1;
        for(int i=0; i< array.length; i++){
            if(array[i]) counter++;
            if(counter==whatTrueAreYouSearching) return i;
        }
        return -420;
    }

    /**
     * returns a number of trues in an array
     * @param array array scanned
     * @return number of true in the array
     */
    public static int totalNumOfTrues(boolean[] array){
        int counter=0;
        for(int i=0; i< array.length; i++){
            if(array[i]) counter++;
        }
        return counter;
    }

    /**
     * prints to system.in a readable version of the cardMarket and MarbleMarket
     */
    public static void printMarbleMarketAndCardMarket(GameStatusUpdate game){
        System.out.println("Card Market:\n");
        CliForSP.printCardMarket(game);
        System.out.println();
        System.out.println("Marble Market:\n");
        CliForSP.printMarbleMarket(game);
    }

    public static int[] baseProductionCostforCli(GameStatusUpdate status){
        int[] result=new int[4];
        CliForSP.printStorage(status);
        System.out.println("\nStrongbox:\n");
        CommandLine.printResourcesArray(status.getPlayersStatus()[0].getStrongBox());
        System.out.println("you selected the base production, please select what resources you will spend:");
        System.out.println("type the number relative to the selected resource in this list:");
        System.out.print("1 -> coins\n2 -> servants\n3 -> shields\n4 -> stones\n\n");
        int value=CliForSP.selectANumber(1,4,-4);
        result[value-1]++;
        System.out.println("now select the second resource:");
        value=CliForSP.selectANumber(1,4,-4);
        result[value-1]++;
        return result;
    }

}
