package it.polimi.ingsw.cli;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.controller.PlayerUpdate;
import it.polimi.ingsw.model.*;

import java.io.*;
import java.util.*;

import static it.polimi.ingsw.model.MarbleColor.*;

public class CommandLine {

    static LeaderDeck leaderCards;
    static DevelopmentCard[] developmentCards;


    public static void developmentPopulate(String path) {
        Gson gson= new Gson();
        BufferedReader buffer=null;
        try{
            buffer=new BufferedReader(new FileReader(path));
        }catch (FileNotFoundException e){
            System.out.println("File non trovato");
        }
        developmentCards=gson.fromJson(buffer, DevelopmentCard[].class);
    }


    /**
     * printLeader shows all the characteristics of a Leader Card to the user; it's invoked from other methods
     * @param id
     */
    public static void printLeader(int id) {
        id = id - 49;
        if (id >= 0 && id <= 3) //LeaderCardDiscount
        {
            System.out.println("Leader Type: \u001B[31mDiscount\n\u001B[0mRequirements: 1 " + leaderCards.getCard(id).getColor1DiscountCard() + " card and 1 " + leaderCards.getCard(id).getColor2DiscountCard() + " card\nDiscount: -1 " + leaderCards.getCard(id).getPower() + "\nVictory Points: \u001B[32m+" + leaderCards.getCard(id).getPv() + "\u001B[0m");
        }
        else if (id >= 4 && id <= 7) //LeaderCardStorage
        {
            System.out.println("Leader Type: \u001B[31mStorage\n\u001B[0mCost: 5 " + leaderCards.getCard(id).getStorageCardCost() + "\nResource contained: " + leaderCards.getCard(id).getPower() + "\nVictory Points: \u001B[32m+" + leaderCards.getCard(id).getPv() + "\u001B[0m");
        }
        else if (id >= 8 && id <= 11) //LeaderCardWhiteBall
        {
            System.out.println("Leader Type: \u001B[31mWhite Ball\n\u001B[0mRequirements: 2 " + leaderCards.getCard(id).getColor1WhiteBallCard() + " card and 1 " + leaderCards.getCard(id).getColor2WhiteBallCard() + " card\nTurns white balls into: " + leaderCards.getCard(id).getPower() + "\nVictory Points: \u001B[32m+" + leaderCards.getCard(id).getPv() + "\u001B[0m");
        }
        else if (id >= 12 && id <= 15) //LeaderCardFactory
        {
            System.out.println("Leader Type: \u001B[31mProduction\n\u001B[0mRequirements: 1 Level Two " + leaderCards.getCard(id).getColorProductionCard() + " card\nProduction: 1 " + leaderCards.getCard(id).getPower() + " => 1 resource of any type + 1 \u001B[31mfaith point\u001B[0m\nVictory Points: \u001B[32m+" + leaderCards.getCard(id).getPv() + "\u001B[0m");
        }
    }

    /**
     * printDevelopmentCard shows all the characteristics of a Development Card to the user; it's invoked from other methods
     * @param id
     */
    public static void printDevelopmentCard(int id) {
        id = id - 1;
        System.out.println("- Level " + developmentCards[id].getLevel() + " " + developmentCards[id].getColor() + " card");
        // costi
        System.out.println("- Cost:");
        printArray(developmentCards[id].getCost());
        System.out.println("- Production Cost:");
        printArray(developmentCards[id].getProductionCost());
        System.out.println("- Production Power:");
        printArray(developmentCards[id].getProduct());
        System.out.println("- Victory points: \u001B[32m" + developmentCards[id].getPv() + "\u001B[0m\n");
    }

    /**
     * printArray is useful to show costs and productions to the user; it's invoked from printDevelopmentCard
     * @param array
     */
    public static void printArray(int[] array) {
        if (array[0] != 0) { System.out.println(array[0] + "x \u001B[33mcoins\u001B[0m"); }
        if (array[1] != 0) { System.out.println(array[1] + "x \u001B[35mservants\u001B[0m"); }
        if (array[2] != 0) { System.out.println(array[2] + "x \u001B[36mshields\u001B[0m"); }
        if (array[3] != 0) { System.out.println(array[3] + "x \u001B[37mstones\u001B[0m"); }
        if (array[4] != 0) { System.out.println(array[4] + "x \u001B[31mfaith\u001B[0m"); }
    }

    public static void printResourcesArray(int[] array) {
        if (array[0] != 0) { System.out.println(array[0] + "x \u001B[33mcoins\u001B[0m"); }
        if (array[1] != 0) { System.out.println(array[1] + "x \u001B[35mservants\u001B[0m"); }
        if (array[2] != 0) { System.out.println(array[2] + "x \u001B[36mshields\u001B[0m"); }
        if (array[3] != 0) { System.out.println(array[3] + "x \u001B[37mstones\u001B[0m"); }
    }

    public static void printPersonalBoard(PlayerUpdate board) {
        int[] developmentBoard = board.getActivatableCards();
        int j=0;
        System.out.println(board.getName() + "'s board\n\nDevelopment cards:");
        for (int i=0; i<3; i++) {
            if (developmentBoard[i]!=0) { printDevelopmentCard(developmentBoard[i]); }
        }
        System.out.println("Available resources in storage:");
        for (int i=0; i<5; i++) {
            if (i<3) {
                j = i+1;
                System.out.println("Depot with capacity " + j + ": ");
            }
            else {
                if (board.getStorage()[i][0] != -1) { System.out.println("\nResources on leader: "); }
                else {  }
            }
            switch (board.getStorage()[i][0]) {
                case 0:
                    System.out.println(board.getStorage()[i][1] + "x \u001B[33mcoins\u001B[0m");
                    break;
                case 1:
                    System.out.println(board.getStorage()[i][1] + "x \u001B[35mservants\u001B[0m");
                    break;
                case 2:
                    System.out.println(board.getStorage()[i][1] + "x \u001B[36mshields\u001B[0m");
                    break;
                case 3:
                    System.out.println(board.getStorage()[i][1] + "x \u001B[37mstones\u001B[0m");
                    break;
                default:
                    break;
            }
        }
        System.out.println("\nAvailable resources in strongbox:");
        printResourcesArray(board.getStrongBox());
        System.out.println("\nLeader cards:");
        if (board.isFirstLeaderPlayed()) { printLeader(board.getFirstLeader()); }
        if (board.isSecondLeaderPlayed()) { printLeader(board.getSecondLeader()); }
        System.out.println("\nVictory points: \u001B[32m" + board.getPv() + "\u001B[0m");
    }

    /**
     * startingLeaders is invoked at the beginning of a game to help a user choose his initial leaders
     * @param l1
     * @param l2
     * @param l3
     * @param l4
     */
    public static void startingLeaders (int l1, int l2, int l3, int l4) {
        Scanner scanner = new Scanner (System.in);
        String userInput;
        boolean leadersChosen = false;
        int chosenL1 = 0, chosenL2 = 0;
        System.out.println("Choose your leaders!\n\nLEADER 1:");
        printLeader(l1);
        System.out.println("\nLEADER 2:");
        printLeader(l2);
        System.out.println("\nLEADER 3:");
        printLeader(l3);
        System.out.println("\nLEADER 4:");
        printLeader(l4);
        while (!leadersChosen) {
            System.out.println("\nChoose your first leader! Number:");
            userInput = scanner.nextLine();
            try { chosenL1 = Integer.parseInt(userInput); }
            catch (NumberFormatException e) { }
            System.out.println("\nChoose your second leader! Number:");
            userInput = scanner.nextLine();
            try { chosenL2 = Integer.parseInt(userInput); }
            catch (NumberFormatException e) { }
            if (chosenL1 < 1 || chosenL1 > 4 || chosenL2 < 1 || chosenL2 > 4) {
                System.out.println("\n\u001B[31mWarning: you can only choose numbers between 1 and 4!\u001B[0m");
            }
            else if (chosenL1 == chosenL2) {
                System.out.println("\n\u001B[31mWarning: you have to choose different leaders!\u001B[0m");
            }
            else {
                System.out.println("\nOkay!");
                //comunica al controller i leader scelti
                leadersChosen = true;
            }
        }
    }

    /**
     * cardDetails can be invoked from printMarket when the user types "details"; it helps the user giving him more information about the cards in the market
     * @param marketCards
     * @throws IOException
     */
    public static void cardDetails(int[][] marketCards) throws IOException {
        Scanner scanner = new Scanner (System.in);
        String userInput;
        int cardId = 0;
        System.out.println("\nType the id of the card you want to know about, or 'quit' if you're ready to choose the card you want to buy.");
        userInput = scanner.nextLine();
        while (!userInput.equalsIgnoreCase("quit")) {
            try { cardId = Integer.parseInt(userInput); }
            catch (NumberFormatException e) {
                System.out.println("Not a number!");
            }
            if (cardId >= 1 && cardId <= 48) {
                for (int i=0; i<3; i++) {
                    for (int j=0; j<4; j++) {
                        if (cardId == marketCards[i][j]) {
                            printDevelopmentCard(cardId);
                            break;
                        }
                    }
                }
            }
            System.out.println("\nType the id of the card you want to know about, or 'quit' if you're ready to choose the card you want to buy.");
            userInput = scanner.nextLine();
        }
    }

    /**
     * printCardMarket receives a two-dimensional array containing the 12 IDs of the cards in the market and interacts with the user so he can buy one
     * @param marketCards
     * @throws IOException
     */
    public static void printCardMarket(int[][] marketCards) throws IOException {
        Scanner scanner = new Scanner (System.in);
        String userInput;
        int cardId = 0;
        boolean cardBought = false;
        System.out.println("Welcome to the card market!\nThese are the available cards:\n");
        for (int i=0; i<3; i++) {
            for (int j=0; j<4; j++) {
                // stampa del mercato provvisoria, da mostrare id + costo o id + potere di produzione
                // reminder: i getter in developmentCard vanno usati sulla carta una posizione indietro rispetto agli interi passati
                cardId = marketCards[i][j];
                System.out.println("Id: \u001B[31m" + cardId + "\u001B[0m; Cost:");
                printArray(developmentCards[cardId-1].getCost());
            }
        }
        cardId = 0;
        while (!cardBought) {
            System.out.println("\nType the id of the card you want to buy, or 'details' if you want to know more about a card.");
            userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("details")) { cardDetails(marketCards); }
            else {
                try { cardId = Integer.parseInt(userInput); }
                catch (NumberFormatException e) {
                    System.out.println("Not a number!");
                }
                if (cardId >= 1 && cardId <= 48) {
                    for (int i=0; i<3; i++) {
                        for (int j=0; j<4; j++) {
                            if (cardId == marketCards[i][j]) {
                                System.out.println("You bought the card no. " + cardId);
                                // comunico al controller la carta da acquistare
                                cardBought = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * printMarbleMarket interacts with the user to make him take resource from the market tray;
     * @param tray
     */
    public static void printMarbleMarket(MarbleColor[][] tray, MarbleColor slide) {
        Scanner scanner = new Scanner (System.in);
        String userInput;
        int numberChosen = 2;
        boolean rowOrColumn = false; // vale 0 se viene scelta una riga, 1 se viene scelta una colonna
        boolean chosen = false;
        System.out.println("Welcome to the marble market!\nThis is the current market tray:\n");
        System.out.println(tray[0][0] + " | " + tray[0][1] + " | " + tray[0][2] + " | " + tray[0][3]);
        System.out.println("---------------------------------");
        System.out.println(tray[1][0] + " | " + tray[1][1] + " | " + tray[1][2] + " | " + tray[1][3]);
        System.out.println("---------------------------------");
        System.out.println(tray[2][0] + " | " + tray[2][1] + " | " + tray[2][2] + " | " + tray[2][3]);
        System.out.println("\nSlide marble: " + slide);
        while (!chosen) {
            System.out.println("\nType '0' if you want to choose a row, '1' if you want to choose a column");
            userInput = scanner.nextLine();
            try { numberChosen = Integer.parseInt(userInput); }
            catch (NumberFormatException e) { System.out.println("\u001B[31mYou have to type a number!\u001B[0m"); }
            switch (numberChosen) {
                case 0:
                    rowOrColumn = false;
                    chosen = true;
                    break;
                case 1:
                    rowOrColumn = true;
                    chosen = true;
                    break;
                default:
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                    break;
            }
        }
        chosen = false;
        numberChosen = 0;
        while (!chosen) {
            if (!rowOrColumn) {
                System.out.println("\nType the number of the row you want to choose (1-3)");
                userInput = scanner.nextLine();
                try { numberChosen = Integer.parseInt(userInput); }
                catch (NumberFormatException e) { System.out.println("\u001B[31mYou have to type a number!\u001B[0m"); }
                if (numberChosen >= 1 && numberChosen <= 3) {
                    //comunicare riga scelta al controller
                    chosen = true;
                }
                else { System.out.println("\u001B[31mInvalid input!\u001B[0m"); }
            }
            else {
                System.out.println("\nType the number of the column you want to choose (1-4)");
                userInput = scanner.nextLine();
                try { numberChosen = Integer.parseInt(userInput); }
                catch (NumberFormatException e) { System.out.println("\u001B[31mYou have to type a number!\u001B[0m"); }
                if (numberChosen >= 1 && numberChosen <= 4) {
                    //comunicare colonna scelta al controller
                    chosen = true;
                }
                else { System.out.println("\u001B[31mInvalid input!\u001B[0m"); }
            }
        }
    }


    public static void turnMgmt(String inputJson) {
        Gson gson = new Gson();
        GameStatusUpdate status = gson.fromJson(inputJson, GameStatusUpdate.class);
        Scanner scanner = new Scanner (System.in);
        String userInput;
        int numberChosen = 3;
        boolean actionChosen = false;
        while (!actionChosen) {
            System.out.println("\nIt's your turn! Choose an action:\n[0]: Take resources from the market\n[1]: Buy one development card\n[2]: Activate the production\n");
            userInput = scanner.nextLine();
            try {
                numberChosen = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
            }
            switch (numberChosen) {
                case 0:
                    actionChosen = true;
                    printMarbleMarket(status.getMarketsStatus().getMarketBoard(), status.getMarketsStatus().getSlideMarble());
                    break;
                case 1:
                    actionChosen = true;
                    try { printCardMarket(status.getMarketsStatus().getCardMarket()); }
                    catch (IOException e) { System.out.println("IOException"); }
                    break;
                case 2:
                    actionChosen = true;
                    //lancia produzione, va ancora fatto il metodo
                    //come parametro (status.getPlayersStatus())
                    //passa un oggetto di tipo PlayerUpdate
                    break;
                default:
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                    break;
            }
        }
    }

    public static void main(String[] args) {

        developmentCards = new DevelopmentCard[48];
        leaderCards = new LeaderDeck();
        developmentPopulate("src/main/resources/devCards.json");

        Gson gson = new Gson();
        String jsonInput;

        // testing printPersonalBoard
        PlayerUpdate playerTest = new PlayerUpdate("Andrea", 1);
        int[][] storageTest = {{0,1},{3,2},{1,3},{-1,0},{1,1}};
        playerTest.setStorage(storageTest);
        int[] strongboxTest = {1,1,1,1};
        playerTest.setStrongBox(strongboxTest);
        int[][] developmentTest = {{2,0,0},{5,29,0},{0,0,0}};
        playerTest.setDevelopMentSlots(developmentTest);
        playerTest.setFirstLeaderIsPlayed(true);
        playerTest.setFirstLeader(54);
        playerTest.setPv(420);
        printPersonalBoard(playerTest);

        //testing turnMgmt
        //can be used to test printCardMarket and printMarketBoard
        GameStatusUpdate update = new GameStatusUpdate(3);
        MarbleColor[][] board = {{purple, grey, yellow, white},{blue, purple, white, yellow},{grey, white, red, blue}};
        update.getMarketsStatus().setMarketBoard(board);
        update.getMarketsStatus().setSlideMarble(white);

        int[][] inputMarket = {{1, 5, 9, 13}, {17, 21, 25, 29}, {33, 37, 41, 45}};
        update.getMarketsStatus().setCardMarket(inputMarket);

        jsonInput = gson.toJson(update);
        turnMgmt(jsonInput);




        // testing startingLeaders
        startingLeaders(49, 50, 51, 52);

    }


}
