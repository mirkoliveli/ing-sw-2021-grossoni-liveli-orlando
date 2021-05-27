package it.polimi.ingsw.cli;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.controller.PlayerUpdate;
import it.polimi.ingsw.messages.ActionMessage;
import it.polimi.ingsw.messages.TypeOfAction;
import it.polimi.ingsw.messages.chooseDepotMessage;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.LeaderDeck;
import it.polimi.ingsw.model.MarbleColor;
import it.polimi.ingsw.model.TypeOfResource;
import it.polimi.ingsw.networking.Client;
import it.polimi.ingsw.utils.StaticMethods;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static it.polimi.ingsw.model.MarbleColor.*;
import static it.polimi.ingsw.model.TypeOfResource.*;

public class CommandLine {

    static LeaderDeck leaderCards=new LeaderDeck();
    static DevelopmentCard[] developmentCards;

    /**
     * developmentPopulate populates the developmentCards array of the class, so that the cli can keep a local copy of the whole deck of development cards
     *
     * @param path
     */
    public static void developmentPopulate(String path) {
        Gson gson = new Gson();
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
        developmentCards = gson.fromJson(buffer, DevelopmentCard[].class);
    }

    /**
     * printLeader shows all the characteristics of a Leader Card to the user; it's invoked from other methods
     *
     * @param id
     */
    public static synchronized void printLeader(int id) {
        System.out.println("prova");
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
     * printDevelopmentCard shows all the characteristics of a Development Card to the user; it's invoked from other methods
     *
     * @param id
     */
    public static synchronized void printDevelopmentCard(int id) {
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
     *
     * @param array
     */
    public static synchronized void printArray(int[] array) {
        if (array[0] != 0) {
            System.out.println(array[0] + "x \u001B[33mcoins\u001B[0m");
        }
        if (array[1] != 0) {
            System.out.println(array[1] + "x \u001B[35mservants\u001B[0m");
        }
        if (array[2] != 0) {
            System.out.println(array[2] + "x \u001B[36mshields\u001B[0m");
        }
        if (array[3] != 0) {
            System.out.println(array[3] + "x \u001B[37mstones\u001B[0m");
        }
        if (array[4] != 0) {
            System.out.println(array[4] + "x \u001B[31mfaith\u001B[0m");
        }
    }

    /**
     * printResourcesArray is useful to show costs and production to the user; the same as printArray but it doesn't include faith points
     *
     * @param array
     */
    public static synchronized void printResourcesArray(int[] array) {
        if (array[0] != 0) {
            System.out.println(array[0] + "x \u001B[33mcoins\u001B[0m");
        }
        if (array[1] != 0) {
            System.out.println(array[1] + "x \u001B[35mservants\u001B[0m");
        }
        if (array[2] != 0) {
            System.out.println(array[2] + "x \u001B[36mshields\u001B[0m");
        }
        if (array[3] != 0) {
            System.out.println(array[3] + "x \u001B[37mstones\u001B[0m");
        }
    }

    /**
     * printPersonalBoard shows every relevant characteristic of the personal board of a single user
     *
     * @param board
     */
    public static synchronized void printPersonalBoard(PlayerUpdate board) {
        int[] developmentBoard = board.getActivatableCards();
        int j = 0;
        System.out.println(board.getName() + "'s board\n\nDevelopment cards:");
        for (int i = 0; i < 3; i++) {
            if (developmentBoard[i] != 0) {
                printDevelopmentCard(developmentBoard[i]);
            }
        }
        System.out.println("Available resources in storage:");
        for (int i = 0; i < 5; i++) {
            if (i < 3) {
                j = i + 1;
                System.out.println("Depot with capacity " + j + ": ");
            } else {
                if (board.getStorage()[i][0] != -1) {
                    System.out.println("\nResources on leader: ");
                } else {
                }
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
        if (board.isFirstLeaderPlayed()) {
            printLeader(board.getFirstLeader());
        }
        if (board.isSecondLeaderPlayed()) {
            printLeader(board.getSecondLeader());
        }
        System.out.println("\nVictory points: \u001B[32m" + board.getPv() + "\u001B[0m");
    }

    /**
     * startingLeaders is invoked at the beginning of a game to help a user choose his initial leaders
     *
     * @param l1
     * @param l2
     * @param l3
     * @param l4
     */
    public static synchronized void startingLeaders(int l1, int l2, int l3, int l4) {
        Scanner scanner = new Scanner(System.in);
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
            try {
                chosenL1 = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
            }
            System.out.println("\nChoose your second leader! Number:");
            userInput = scanner.nextLine();
            try {
                chosenL2 = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
            }
            if (chosenL1 < 1 || chosenL1 > 4 || chosenL2 < 1 || chosenL2 > 4) {
                System.out.println("\n\u001B[31mWarning: you can only choose numbers between 1 and 4!\u001B[0m");
            } else if (chosenL1 == chosenL2) {
                System.out.println("\n\u001B[31mWarning: you have to choose different leaders!\u001B[0m");
            } else {
                System.out.println("\nOkay!");
                //comunica al controller i leader scelti
                leadersChosen = true;
            }
        }
    }

    /**
     * cardDetails can be invoked from printMarket when the user types "details"; it helps the user giving him more information about the cards in the market
     *
     * @param marketCards
     * @throws IOException
     */
    public static synchronized void cardDetails(int[][] marketCards) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        int cardId = 0;
        System.out.println("\nType the id of the card you want to know about, or 'quit' if you're ready to choose the card you want to buy.");
        userInput = scanner.nextLine();
        while (!userInput.equalsIgnoreCase("quit")) {
            try {
                cardId = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Not a number!");
            }
            if (cardId >= 1 && cardId <= 48) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 4; j++) {
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
     *
     * @param marketCards
     * @return true if the user confirms he wants to buy a card, false if he wants to choose another action
     * @throws IOException
     */
    public static synchronized boolean printCardMarket(int[][] marketCards) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        int cardId = 0;
        boolean cardBought = false;
        System.out.println("Welcome to the card market!\nThese are the available cards:\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                // stampa del mercato provvisoria, da mostrare id + costo o id + potere di produzione
                // reminder: i getter in developmentCard vanno usati sulla carta una posizione indietro rispetto agli interi passati
                cardId = marketCards[i][j];
                System.out.println("Id: \u001B[31m" + cardId + "\u001B[0m; Cost:");
                printArray(developmentCards[cardId - 1].getCost());
            }
        }
        cardId = 0;
        while (!cardBought) {
            System.out.println("\nType the id of the card you want to buy,'details' if you want to know more about a card or 'quit' if you don't want to buy anything.");
            userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("details")) {
                cardDetails(marketCards);
            } else if (userInput.equals("quit")) {
                return false;
            } else {
                try {
                    cardId = Integer.parseInt(userInput);
                } catch (NumberFormatException e) {
                    System.out.println("Not a number!");
                }
                if (cardId >= 1 && cardId <= 48) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 4; j++) {
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
        return true;
    }

    /**
     * printMarbleMarket interacts with the user to make him take resource from the market tray;
     *
     * @param tray
     */
    public static synchronized void actionMarbleMarket(MarbleColor[][] tray, MarbleColor slide) {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        int numberChosen = 2;
        boolean rowOrColumn = false; // vale 0 se viene scelta una riga, 1 se viene scelta una colonna
        boolean chosen = false;
        marbleMarketStatus(tray, slide);
       //System.out.println("Welcome to the marble market!\nThis is the current market tray:\n");
       //System.out.println(tray[0][0] + " | " + tray[0][1] + " | " + tray[0][2] + " | " + tray[0][3]);
       //System.out.println("---------------------------------");
       //System.out.println(tray[1][0] + " | " + tray[1][1] + " | " + tray[1][2] + " | " + tray[1][3]);
       //System.out.println("---------------------------------");
       //System.out.println(tray[2][0] + " | " + tray[2][1] + " | " + tray[2][2] + " | " + tray[2][3]);
       //System.out.println("\nSlide marble: " + slide);
        while (!chosen) {
            System.out.println("\nType '0' if you want to choose a row, '1' if you want to choose a column");
            userInput = scanner.nextLine();
            try {
                numberChosen = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
            }
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
                try {
                    numberChosen = Integer.parseInt(userInput);
                } catch (NumberFormatException e) {
                    System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
                }
                if (numberChosen >= 1 && numberChosen <= 3) {
                    //comunicare riga scelta al controller
                    chosen = true;
                } else {
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                }
            } else {
                System.out.println("\nType the number of the column you want to choose (1-4)");
                userInput = scanner.nextLine();
                try {
                    numberChosen = Integer.parseInt(userInput);
                } catch (NumberFormatException e) {
                    System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
                }
                if (numberChosen >= 1 && numberChosen <= 4) {
                    //comunicare colonna scelta al controller
                    chosen = true;
                } else {
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                }
            }
        }
    }

    /**
     * playLeader interacts with the user when he wants to play or discard a leader card from his hand
     *
     * @param player
     */
    public static synchronized void playLeader(PlayerUpdate player) {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        boolean chosen = false;
        boolean playOrDiscard; // 1 se si vuole giocare, 0 se si vuole scartare
        int numberChosen = 5;
        System.out.println("You have chosen to play or discard a leader!");
        if (player.isFirstLeaderPlayed() && player.isSecondLeaderPlayed()) {
            System.out.println("\u001B[31mSorry, you don't have any leader to play!\u001B[0m");
        } else {
            System.out.println("You can choose among these leaders:");
            if (!player.isFirstLeaderPlayed()) {
                System.out.println("First leader: ");
                printLeader(player.getFirstLeader());
            }
            if (!player.isSecondLeaderPlayed()) {
                System.out.println("Second leader: ");
                printLeader(player.getSecondLeader());
            }
            while (!chosen) {
                System.out.println("Type:\n[0] to quit\n[1] to play the first leader\n[2] to play the second leader\n[3] to discard the first leader\n[4] to discard the second leader");
                userInput = scanner.nextLine();
                try {
                    numberChosen = Integer.parseInt(userInput);
                } catch (NumberFormatException e) {
                    System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
                }
                switch (numberChosen) {
                    case 0:
                        chosen = true;
                        break;
                    case 1:
                        playOrDiscard = true;
                        chosen = true;
                        break;
                    // comunicare scelta a controller
                    case 2:
                        playOrDiscard = true;
                        chosen = true;
                        // comunicare scelta a controller
                        break;
                    case 3:
                        playOrDiscard = false;
                        chosen = true;
                        // comunicare scelta a controller
                        break;
                    case 4:
                        playOrDiscard = false;
                        chosen = true;
                        // comunicare scelta a controller
                        break;
                    default:
                        System.out.println("\u001B[31mInvalid input!\u001B[0m");
                        break;
                }
            }
        }
    }

    /**
     * production interacts with the user when he wants to activate the production of his development and/or leader cards
     *
     * @param player
     * @return true if the user confirms the production action, false if he wants to choose another one
     */
    public static synchronized boolean production(PlayerUpdate player) {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        int numberChosen = 0;
        int j = 0;
        TypeOfResource[] ToR = new TypeOfResource[5]; //0-2 for the basic production action, 3-4 for the leader production action
        boolean[] power = new boolean[6];
        boolean chosen = false;
        int[] developmentBoard = player.getActivatableCards();
        for (int i = 0; i < 3; i++) {
            if (developmentBoard[i] != 0) {
                j = i + 1;
                System.out.println("Development card " + j + ":\n- Cost:");
                printArray(developmentCards[developmentBoard[i]].getProductionCost());
                System.out.println("- Production:");
                printArray(developmentCards[developmentBoard[i]].getProduct());
            }
        }
        if (player.isFirstLeaderPlayed() && player.getFirstLeader() > 60) {
            System.out.println("First leader:\n1x " + leaderCards.getCard(player.getFirstLeader() - 49).getPower() + " => 1x resource of a type of your choice & 1 \u001B[31mfaith\u001B[0m");
        }
        if (player.isSecondLeaderPlayed() && player.getSecondLeader() > 60) {
            System.out.println("Second leader:\n1x " + leaderCards.getCard(player.getSecondLeader() - 49).getPower() + " => 1x resource of a type of your choice & 1 \u001B[31mfaith\u001B[0m");
        }
        while (true) {
            System.out.println("Choose the power you want to activate or deactivate\n(0 for base production power, 1-3 for development cards, 4-5 for leader cards, 'view' to review your selection, 'finish' to confirm it or 'quit' to quit without producing anything");
            userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("view")) {
            } else if (userInput.equalsIgnoreCase("finish")) {
                // comunicare produzione a controller
                return true;
            } else if (userInput.equalsIgnoreCase("quit")) {
                return false;
            } else {
                try {
                    numberChosen = Integer.parseInt(userInput);
                    if (numberChosen >= 0 && numberChosen < 6) {
                        power[numberChosen] = !power[numberChosen];
                    }
                    if (numberChosen == 0 && power[numberChosen]) {
                        int indexBaseProd = 0;
                        System.out.println("You selected the base production action! Choose, in order, the two resources you want to pay and the one you want to obtain([0]: coins; [1]: servants; [2]: shields; [3]: stones)");
                        while (indexBaseProd < 3) {
                            userInput = scanner.nextLine();
                            try {
                                numberChosen = Integer.parseInt(userInput);
                            } catch (NumberFormatException e) {
                                System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
                            }
                            switch (numberChosen) {
                                case 0:
                                    ToR[indexBaseProd] = coins;
                                    indexBaseProd++;
                                    break;
                                case 1:
                                    ToR[indexBaseProd] = servants;
                                    indexBaseProd++;
                                    break;
                                case 2:
                                    ToR[indexBaseProd] = shields;
                                    indexBaseProd++;
                                    break;
                                case 3:
                                    ToR[indexBaseProd] = stones;
                                    indexBaseProd++;
                                    break;
                                default:
                                    System.out.println("\u001B[31mYou have to type a number between 0 and 3!\u001B[0m");
                                    break;
                            }
                        }
                    }
                    if (numberChosen == 4 && power[numberChosen]) {
                        while (!chosen) {
                            System.out.println("Choose the resource you want to produce with your leader 1 ([0]: coins; [1]: servants; [2]: shields; [3]: stones)");
                            userInput = scanner.nextLine();
                            try {
                                numberChosen = Integer.parseInt(userInput);
                            } catch (NumberFormatException e) {
                                System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
                            }
                            switch (numberChosen) {
                                case 0:
                                    ToR[3] = coins;
                                    chosen = true;
                                    break;
                                case 1:
                                    ToR[3] = servants;
                                    chosen = true;
                                    break;
                                case 2:
                                    ToR[3] = shields;
                                    chosen = true;
                                    break;
                                case 3:
                                    ToR[3] = stones;
                                    chosen = true;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    if (numberChosen == 5 && power[numberChosen]) {
                        while (!chosen) {
                            System.out.println("Choose the resource you want to produce with your leader 2 ([0]: coins; [1]: servants; [2]: shields; [3]: stones)");
                            userInput = scanner.nextLine();
                            try {
                                numberChosen = Integer.parseInt(userInput);
                            } catch (NumberFormatException e) {
                                System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
                            }
                            switch (numberChosen) {
                                case 0:
                                    ToR[4] = coins;
                                    chosen = true;
                                    break;
                                case 1:
                                    ToR[4] = servants;
                                    chosen = true;
                                    break;
                                case 2:
                                    ToR[4] = shields;
                                    chosen = true;
                                    break;
                                case 3:
                                    ToR[4] = stones;
                                    chosen = true;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
                }
            }

        }
    }

    /**
     * swapDepots helps the user swap resources between his depots
     *
     * @param storage
     */
    public static synchronized void swapDepots(int[][] storage) {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        boolean depotsSwapped = false;
        int j = 0;
        int depot1 = 0;
        int depot2 = 0;
        System.out.println("You have chosen to swap resources! You have these resources available:");
        for (int i = 0; i < 3; i++) {
            j = i + 1;
            System.out.println("Depot with capacity " + j + ": ");
            switch (storage[i][0]) {
                case 0:
                    System.out.println(storage[i][1] + "x \u001B[33mcoins\u001B[0m");
                    break;
                case 1:
                    System.out.println(storage[i][1] + "x \u001B[35mservants\u001B[0m");
                    break;
                case 2:
                    System.out.println(storage[i][1] + "x \u001B[36mshields\u001B[0m");
                    break;
                case 3:
                    System.out.println(storage[i][1] + "x \u001B[37mstones\u001B[0m");
                    break;
                default:
                    break;
            }
        }
        while (!depotsSwapped) {
            System.out.println("\nIf you want to quit, type [0] two times\nChoose the first depot to swap! Number:");
            userInput = scanner.nextLine();
            try {
                depot1 = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
            }
            System.out.println("\nChoose the second depot to swap! Number:");
            userInput = scanner.nextLine();
            try {
                depot2 = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
            }
            if (depot1 == 0 && depot2 == 0) {
                depotsSwapped = true;
            } else if (depot1 < 1 || depot1 > 3 || depot2 < 1 || depot2 > 3) {
                System.out.println("\n\u001B[31mWarning: you can only choose depots between 1 and 3!\u001B[0m");
            } else if (depot1 == depot2) {
                System.out.println("\n\u001B[31mWarning: you have to choose different depots!\u001B[0m");
            } else {
                System.out.println("\nOkay!");
                //comunica al controller i depots da invertire
                depotsSwapped = true;
            }
        }
    }

    /**
     * turnMgmt manages every action that can be done during the user's turn; it receives a Json containing the game state, deserializes it and interacts with the user to ask him what action he wants to perform, invoking methods accordingly
     *
     * @param inputJson
     */
    public static synchronized void turnMgmt(String inputJson) {
        Gson gson = new Gson();
        GameStatusUpdate status = gson.fromJson(inputJson, GameStatusUpdate.class);
        Scanner scanner = new Scanner(System.in);
        String userInput;
        int numberChosen = 3;
        boolean actionChosen = false;
        while (!actionChosen) {
            System.out.println("\nIt's your turn! Choose an action:\n[0]: Take resources from the market\n[1]: Buy one development card\n[2]: Activate the production\nElse, you can (before or after your action):\n[3]: Swap resources between your depots\n[4]: Play or discard a leader card");
            userInput = scanner.nextLine();
            try {
                numberChosen = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
            }
            switch (numberChosen) {
                case 0:
                    actionChosen = true;
                    actionMarbleMarket(status.getMarketsStatus().getMarketBoard(), status.getMarketsStatus().getSlideMarble());
                    break;
                case 1:
                    try {
                        actionChosen = printCardMarket(status.getMarketsStatus().getCardMarket());
                    } catch (IOException e) {
                        System.out.println("IOException");
                    }
                    break;
                case 2:
                    actionChosen = production(status.getPlayersStatus()[0]);
                    break;
                case 3:
                    swapDepots(status.getPlayersStatus()[0].getStorage());
                    break;
                case 4:
                    playLeader(status.getPlayersStatus()[0]);
                    break;
                default:
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                    break;
            }
        }
        actionChosen = false;
        while (!actionChosen) {
            System.out.println("You already performed your action! Now you can:\n[0]: Swap resources between your depots\n[1]: Play or discard a leader card\n[2]: End your turn");
            userInput = scanner.nextLine();
            try {
                numberChosen = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
            }
            switch (numberChosen) {
                case 0:
                    swapDepots(status.getPlayersStatus()[0].getStorage());
                    break;
                case 1:
                    playLeader(status.getPlayersStatus()[0]);
                    break;
                case 2:
                    actionChosen = true;
                    break;
                default:
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                    break;
            }
        }

    }

    /**
     * method that prints the state of the marble market.
     * @param tray view of the market grid
     * @param slide view of the slide marble
     *
     */
    public static void marbleMarketStatus(MarbleColor[][] tray, MarbleColor slide){
        System.out.println("Welcome to the marble market!\nThis is the current market tray:\n");
        System.out.println(tray[0][0] + " | " + tray[0][1] + " | " + tray[0][2] + " | " + tray[0][3]);
        System.out.println("---------------------------------");
        System.out.println(tray[1][0] + " | " + tray[1][1] + " | " + tray[1][2] + " | " + tray[1][3]);
        System.out.println("---------------------------------");
        System.out.println(tray[2][0] + " | " + tray[2][1] + " | " + tray[2][2] + " | " + tray[2][3]);
        System.out.println("\nSlide marble: " + slide);
    }

    public static void main(String[] args) {

        developmentCards = new DevelopmentCard[48];
        leaderCards = new LeaderDeck();
        developmentPopulate("src/main/resources/devCards.json");

        Gson gson = new Gson();
        String jsonInput;

        // testing printPersonalBoard
        PlayerUpdate playerTest = new PlayerUpdate("Andrea", 1);
        int[][] storageTest = {{0, 1}, {3, 2}, {1, 3}, {-1, 0}, {1, 1}};
        playerTest.setStorage(storageTest);
        int[] strongboxTest = {1, 1, 1, 1};
        playerTest.setStrongBox(strongboxTest);
        int[][] developmentTest = {{2, 0, 0}, {5, 29, 0}, {0, 0, 0}};
        playerTest.setDevelopMentSlots(developmentTest);
        playerTest.setFirstLeaderIsPlayed(true);
        playerTest.setFirstLeader(54);
        playerTest.setPv(420);
        printPersonalBoard(playerTest);

        //testing turnMgmt
        //can be used to test printCardMarket and printMarketBoard
        GameStatusUpdate update = new GameStatusUpdate(3);
        MarbleColor[][] board = {{purple, grey, yellow, white}, {blue, purple, white, yellow}, {grey, white, red, blue}};
        update.getMarketsStatus().setMarketBoard(board);
        update.getMarketsStatus().setSlideMarble(white);

        int[][] inputMarket = {{1, 5, 9, 13}, {17, 21, 25, 29}, {33, 37, 41, 45}};
        update.getMarketsStatus().setCardMarket(inputMarket);

        update.setPlayer(playerTest);
        jsonInput = gson.toJson(update);
        turnMgmt(jsonInput);


        // testing startingLeaders
        startingLeaders(49, 50, 51, 52);

    }

    /**
     * overload for actual usage (implements connections)
     * <br>
     *     this method handles all the turn actions a player can make, and is in constant communication with the server by sending actions and receiving gameUpdates.
     * @param inputJson when the method is called a first ActionMessage containing the Status of the game is sent, the GameStatusUpdate object is contained as a JSON string inside this parameter
     * @param serverConnection connection with the server
     */
    public static synchronized void turnMgmt(String inputJson, Client serverConnection) {
        ViewState.resetTurn();
        Gson gson = new Gson();
        GameStatusUpdate status = gson.fromJson(inputJson, GameStatusUpdate.class);
        Scanner scanner = new Scanner(System.in);
        String userInput;
        int numberChosen = 3;
        boolean actionChosen = false;
        while (!actionChosen) {
            ViewState.setAction_aborted(false);
            System.out.println("\nIt's your turn! Choose an action:\n[0]: Take resources from the market\n[1]: Buy one development card\n[2]: Activate the production\nElse, you can (before or after your action):\n[3]: Swap resources between your depots\n[4]: Play or discard a leader card");
            userInput = scanner.nextLine();
            try {
                numberChosen = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
            }
            switch (numberChosen) {
                case 0:
                    actionChosen = true;
                    actionMarbleMarket(status.getMarketsStatus().getMarketBoard(), status.getMarketsStatus().getSlideMarble(), serverConnection, status);
                    try {
                        System.out.println(serverConnection.messageFromServer());
                    }catch(IOException e){
                        System.out.println("disconnected");
                    }
                    break;
                case 1:
                    try {
                        actionChosen = printCardMarket(status.getMarketsStatus().getCardMarket());
                    } catch (IOException e) {
                        System.out.println("IOException");
                    }
                    break;
                case 2:
                    //actionChosen = production(status.getPlayersStatus()[status.getNextPlayer()-1]);
                    break;
                case 3:
                    swapDepots(status.getPlayersStatus()[status.getNextPlayer()-1].getStorage(), serverConnection);
                    break;
                case 4:
                    PlayOrDiscardLeaders(status.getNextPlayer(), status, serverConnection);
                    break;
                default:
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                    break;
            }
            if(!ViewState.isAction_aborted()){
                try {
                    inputJson = serverConnection.messageFromServer();
                    status=gson.fromJson(inputJson, GameStatusUpdate.class);
                }catch(IOException e){
                    System.out.println("disconnected gestisci poi");
                }
            }
        }
        actionChosen = false;
        while (!actionChosen) {
            ViewState.setAction_aborted(false);
            System.out.println("You already performed your action! Now you can:\n[0]: Swap resources between your depots\n[1]: Play or discard a leader card\n[2]: End your turn");
            userInput = scanner.nextLine();
            try {
                numberChosen = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
            }
            switch (numberChosen) {
                case 0:
                    swapDepots(status.getPlayersStatus()[status.getNextPlayer()-1].getStorage(), serverConnection);
                    break;
                case 1:
                    PlayOrDiscardLeaders(status.getNextPlayer(), status, serverConnection);
                    break;
                case 2:
                    EndTurn(serverConnection);
                    if(ViewState.isTurn_ended()){
                    actionChosen = true;}

                    break;
                default:
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                    break;
            }
            //nota che se l'azione viene abortita con questo passaggio si va in deadlock, modificare una volta terminato il testing preliminare
            if(!ViewState.isAction_aborted()){
                try {
                    System.out.println("ricevendo update game");
                    inputJson = serverConnection.messageFromServer();
                    status=gson.fromJson(inputJson, GameStatusUpdate.class);
                }catch(IOException e){
                    System.out.println("disconnected gestisci poi");
                }
            }
        }

    }

    /**
     * handles the request to swap levels made from the client. the action has no effect on the turn status so nothing is affected in
     * either client and server
     * @param storage status of your storage
     * @param client handles all the connection with server stuff
     */
    public static synchronized void swapDepots(int[][] storage, Client client) {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        boolean depotsSwapped = false;
        boolean changedMyMind = false;
        int j = 0;
        int depot1 = 0;
        int depot2 = 0;
        System.out.println("You have chosen to swap resources! You have these resources available:");
        for (int i = 0; i < 3; i++) {
            j = i + 1;
            System.out.println("Depot with capacity " + j + ": ");
            switch (storage[i][0]-1) {
                case 0:
                    System.out.println(storage[i][1] + "x \u001B[33mcoins\u001B[0m");
                    break;
                case 1:
                    System.out.println(storage[i][1] + "x \u001B[35mservants\u001B[0m");
                    break;
                case 2:
                    System.out.println(storage[i][1] + "x \u001B[36mshields\u001B[0m");
                    break;
                case 3:
                    System.out.println(storage[i][1] + "x \u001B[37mstones\u001B[0m");
                    break;
                default:
                    System.out.println("empty");
                    break;
            }
        }
        while (!depotsSwapped) {
            System.out.println("\nIf you want to quit, type [0] two times\nChoose the first depot to swap! Number:");
            userInput = scanner.nextLine();
            try {
                depot1 = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
            }
            System.out.println("\nChoose the second depot to swap! Number:");
            userInput = scanner.nextLine();
            try {
                depot2 = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
            }
            if (depot1 == 0 && depot2 == 0) {
                depotsSwapped = true;
                changedMyMind =true;
            } else if (depot1 < 1 || depot1 > 3 || depot2 < 1 || depot2 > 3) {
                System.out.println("\n\u001B[31mWarning: you can only choose depots between 1 and 3!\u001B[0m");
            } else if (depot1 == depot2) {
                System.out.println("\n\u001B[31mWarning: you have to choose different depots!\u001B[0m");
            } else {
                System.out.println("\nOkay!");
                depotsSwapped = true;
            }
        }

        if(!changedMyMind){
            Gson gson= new Gson();
            ActionMessage action= new ActionMessage(TypeOfAction.SWAP_DEPOTS);
            action.SwapDepotsMessage(depot1, depot2);
            client.messageToServer(gson.toJson(action));
            try{
            if(client.messageFromServer().equals("Operation successful")) System.out.println("Levels swapped successfully!");
            else  {
                System.out.println("Request cannot be completed, you cannot swap this levels!");
            }
            }catch(IOException e){
                System.out.println("disconnected during turn phase while swapping resources");
            }
        }
        else{
            ViewState.setAction_aborted(true);
        }
    }


    /**
     * method that is used if the client selects the action of acquiring resources from the market
     * @param tray view of the state of the marble market
     * @param slide view of the state of the slide_marble in the marble market
     * @param client connection to the server is handled via this object
     */
    public static synchronized void actionMarbleMarket(MarbleColor[][] tray, MarbleColor slide, Client client, GameStatusUpdate status) {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        int numberChosen = 2;
        boolean rowOrColumn = false; // vale 0 se viene scelta una riga, 1 se viene scelta una colonna
        boolean chosen = false;

        marbleMarketStatus(tray, slide);

        while (!chosen) {
            System.out.println("\nType '0' if you want to choose a row, '1' if you want to choose a column");
            userInput = scanner.nextLine();
            try {
                numberChosen = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
            }
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
                try {
                    numberChosen = Integer.parseInt(userInput);
                } catch (NumberFormatException e) {
                    System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
                }
                if (numberChosen >= 1 && numberChosen <= 3) {
                    //message sent
                    handleMarket(client, numberChosen, false, status);
                    chosen = true;
                } else {
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                }
            } else {
                System.out.println("\nType the number of the column you want to choose (1-4)");
                userInput = scanner.nextLine();
                try {
                    numberChosen = Integer.parseInt(userInput);
                } catch (NumberFormatException e) {
                    System.out.println("\u001B[31mYou have to type a number!\u001B[0m");
                }
                if (numberChosen >= 1 && numberChosen <= 4) {
                    //message sent
                    handleMarket(client, numberChosen, true, status);
                    chosen = true;
                } else {
                    System.out.println("\u001B[31mInvalid input!\u001B[0m");
                }
            }
        }
    }


    /**
     * method used inside printMarbleMarket to send the message to the server and to handle additional communication necessities that may surge
     * @param client contains the connection with the server
     * @param line int between 1 and 3 (or 4 depending on the boolean rowColumn) that indicates which part of the marble market was selected
     * @param rowColumn switcher for selecting a row or a column of the marble market
     */
    public static void handleMarket(Client client, int line, boolean rowColumn, GameStatusUpdate status){
        String inputFromC;
        int ans;
        Scanner scanner = new Scanner(System.in);
        ActionMessage message=new ActionMessage(TypeOfAction.GO_TO_MARKET);
        message.MarbleMarketAction(line, rowColumn);

        Gson gson=new Gson();

        client.messageToServer(gson.toJson(message));
        String answerFromServer;
        try{
            answerFromServer= client.messageFromServer();
            //manca parte in cui viene richiesto il leader (se si è in possesso di un leader white ball
            if(!answerFromServer.contains("resourceStillToBeStored") && answerFromServer.contains("true")){
                System.out.println("You can receive additional resources from the market thanks to your leaders!");
                client.messageToServer(gson.toJson(printLeadersAndChoose(gson.fromJson(answerFromServer, boolean[].class), status.getNextPlayer(), status)));
                answerFromServer= client.messageFromServer();
            }
            //handles the case where the client needs to choose the level to store the resources
            while(answerFromServer.contains("resourceStillToBeStored")){
                chooseDepotMessage subMessage=gson.fromJson(answerFromServer, chooseDepotMessage.class);
                printDepotChoice(subMessage.getDepotStateOfEmptyness(), subMessage.getResourceStillToBeStored());
                System.out.println("Select 0 if you wish to discard the resources, otherwise select the level where you wish them to be stored: ");
                do {
                    inputFromC = scanner.nextLine();
                    ans=checkForDepotChoice(inputFromC, subMessage.getDepotStateOfEmptyness());
                }while(ans<0);
                client.messageToServer(String.valueOf(ans));
                answerFromServer= client.messageFromServer();
            }
        }catch(IOException e){
            System.out.println("error during marbleMarketAction");
        }
    }

    /**
     * prints the leaders usable in the action and asks the client which one they want to activate
     * @param whichToPrint boolean vector stating which leaders are usable
     * @param Player id of player
     * @param status GameStatus, used to get the Leaders IDs
     * @return choice of the client (0-1-2) or -1 if some errors occurred
     */
    public static int printLeadersAndChoose (boolean[] whichToPrint, int Player, GameStatusUpdate status){
        String inputFromC;
        int ans=-1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("These leaders are the ones you can use:");
        int usable=0;
        for(int i=0; i<2; i++){
            if(whichToPrint[i] && i==0){
                printLeader(status.getPlayersStatus()[Player-1].getFirstLeader());
                usable++;
            }
            if(whichToPrint[i] && i==1){
                printLeader(status.getPlayersStatus()[Player-1].getSecondLeader());
                usable++;
            }
        }
        if(usable==1){
            System.out.println("Choose if you want to use this leader effect by selecting 1 or ignore it by selecting 0");
            do {
                inputFromC = scanner.nextLine();
                ans = Integer.parseInt(inputFromC);
                if(ans!=0 && ans!=1) System.out.println("not a valid selection, please retry!");
            }while(ans!=0 && ans!=1);
        }
        else if(usable==2){
            System.out.println("Choose if you want to use this leader effect by selecting 1 or 2 (depending on which you want to activate or ignore it by selecting 0");
            do {
                inputFromC = scanner.nextLine();
                ans = Integer.parseInt(inputFromC);
                if(ans!=0 && ans!=1 && ans!=2) System.out.println("not a valid selection, please retry!");
            }while(ans!=0 && ans!=1 && ans!=2);
        }
        return ans;
    }

    /**
     * method that prints the state of the storage before the selection of a level to store a new type of resource
     * @param emptyDepots boolean vector sent by the server generated in Storage.emptyStatus()
     * @param resourceToManage vector containing the type of resource that needs to be handled and the quantity of it
     */
    public static void printDepotChoice(boolean[] emptyDepots, int[] resourceToManage){
        System.out.println("Since the resource " + StaticMethods.IntToTypeOfResource(resourceToManage[0]+1) + "was not stored in any storage level and you still have some empty levels you can choose where to store the resource, or discard it");
        System.out.println(resourceToManage[1] + " " + StaticMethods.IntToTypeOfResource(resourceToManage[0]+1) + " still need to be stored");
        System.out.println("your depots are: ");
        for (int i=0; i<3; i++) {
         if(emptyDepots[i]) System.out.println("level " + (i+1)+ " is empty");
         else{System.out.println("level " + (i+1)+ " is occupied");}
        }
    }

    /**
     * method that handles the request to select a level or discard the resources after a marble market action. this is a twin method to the ClientHandler.handleNotStoredResources() method
     * @param string selection made by the client
     * @param emptyStatus status of the storage, this boolean is created in the Storage.emptyStatus() method
     * @return 0 if the client wants to discard the resources, 1-3 for the level selected (if valid) -1 if the input is invalid, whichever the case would be (not a valid level or not a valid input)
     */
    public static int checkForDepotChoice(String string, boolean[] emptyStatus){
        int inNum=Integer.parseInt(string);
        switch(inNum){
            case 0:
                return 0;

            case 1:
                if(emptyStatus[inNum-1]) {
                    System.out.println("the resource will be stored here!");
                    return 1;
                }
                else{
                    System.out.println("you can't select this level because another resource is already stored! please retry");
                    return -1;
                }
            case 2:
                if(emptyStatus[inNum-1]) {
                    System.out.println("the resource will be stored here!");
                    return 2;
                }
                else{
                    System.out.println("you can't select this level because another resource is already stored! please retry");
                    return -1;
                }

            case 3:
                if(emptyStatus[inNum-1]) {
                    System.out.println("the resource will be stored here!");
                    return 3;
                }
                else{
                    System.out.println("you can't select this level because another resource is already stored! please retry");
                    return -1;
                }

            default:
                System.out.println("not a valid input! please retry");
                return -1;
        }
    }

    /**
     * method that sends a request to end the turn and wait for an answer from the server
     * @param connection handles connection with the server
     */
    public static void EndTurn(Client connection){
        ActionMessage action=new ActionMessage(TypeOfAction.END_TURN);
        action.EndTurn();
        Gson gson=new Gson();
        connection.messageToServer(gson.toJson(action));
        try{
            if(connection.messageFromServer().contains("successful")) {
                System.out.println("Your turn is now completed");
                ViewState.setTurn_ended(true);
            }
            else{
                System.out.println("you can't end your turn now!");
            }
        }catch(IOException e){
            System.out.println("errore di disconnessione da gestire");
        }
    }

    public static void PlayOrDiscardLeaders(int player, GameStatusUpdate status, Client client){
        Gson gson=new Gson();
        printPlayerLeaders(player, status);
        String inputFromClient;
        int value;
        Scanner scanner=new Scanner(System.in);
        System.out.println("\nSelect which leader you want to either play or discard (digit 1 or 2): ");
        do{
            inputFromClient=scanner.nextLine();
            value=Integer.parseInt(inputFromClient);
            if(value!=1 && value!=2) System.out.println("not a valid Leader, please retry!");
        }while(value!=1 && value!=2);
        System.out.println("nice! Now select 1 of you want to play it or 2 if you want to discard it:");
        int temp=value;
        do{
            inputFromClient=scanner.nextLine();
            value=Integer.parseInt(inputFromClient);
            if(value!=1 && value!=2) System.out.println("not a valid selection, please retry!");
        }while(value!=1 && value!=2);
        ActionMessage message=new ActionMessage(TypeOfAction.PLAY_OR_DISCARD_LEADER);
        message.PlayOrDiscardLeaders(temp, value);
        client.messageToServer(gson.toJson(message));

        try{
            inputFromClient= client.messageFromServer();
            int answerFromServer=Integer.parseInt(inputFromClient);
            printAnswerForPlayLeaderAction(answerFromServer);
        }catch(IOException e){
            System.out.println("disconnected from server");
        }

    }

    /**
     * method that prints the leaders of a player and states if they have already obtained them or not (doesn't check if discarded or not obtained)
     * @param player player being checked
     * @param status GameState of the match
     */
    public static void printPlayerLeaders(int player, GameStatusUpdate status){
        printLeader(status.getPlayersStatus()[player-1].getFirstLeader());
        if(status.getPlayersStatus()[player-1].isFirstLeaderPlayed()) System.out.println("This leader have been played");
        else System.out.println("this leader has not been played");
        printLeader(status.getPlayersStatus()[player-1].getSecondLeader());
        if(status.getPlayersStatus()[player-1].isSecondLeaderPlayed()) System.out.println("This leader have been played");
        else System.out.println("this leader has not been played");
    }

    /**
     * method that prints the result of the playOrDiscardLeader action
     * @param answerFromServer answer recived from the server
     */
    public static void printAnswerForPlayLeaderAction(int answerFromServer){
        switch(answerFromServer){
            case 0:
                System.out.println("leader played correctly!");
                break;
            case 1:
                System.out.println("leader discarded correctly!");
                break;
            case 2:
                System.out.println("You didn't meet the conditions to play this leader!");
                break;
            case 3:
                System.out.println("the leader was already played or discarded! You can't do this action!");
                break;
            default:
                System.out.println("Some error occurred with the last message received from the server!");
                break;
        }
    }

}
