package it.polimi.ingsw.cli;

import com.google.gson.Gson;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.LeaderDeck;

import java.io.*;
import java.util.*;

public class CommandLine {

    static LeaderDeck leaderCards;
    static DevelopmentCard[] developmentCards;


    public static void developmentPopulate(String path){
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
            System.out.println("Leader Type: \u001B[31mDiscount\n\u001B[0mRequirements: 1 " + leaderCards.getCard(id).getColor1DiscountCard() + " card and 1 " + leaderCards.getCard(id).getColor2DiscountCard() + " card\nDiscount: -1 " + leaderCards.getCard(id).getPower() + "\nVictory Points: \u001B[36m+" + leaderCards.getCard(id).getPv() + "\u001B[0m");
        }
        else if (id >= 4 && id <= 7) //LeaderCardStorage
        {
            System.out.println("Leader Type: \u001B[31mStorage\n\u001B[0mCost: 5 " + leaderCards.getCard(id).getStorageCardCost() + "\nResource contained: " + leaderCards.getCard(id).getPower() + "\nVictory Points: \u001B[36m+" + leaderCards.getCard(id).getPv() + "\u001B[0m");
        }
        else if (id >= 8 && id <= 11) //LeaderCardWhiteBall
        {
            System.out.println("Leader Type: \u001B[31mWhite Ball\n\u001B[0mRequirements: 2 " + leaderCards.getCard(id).getColor1WhiteBallCard() + " card and 1 " + leaderCards.getCard(id).getColor2WhiteBallCard() + " card\nTurns white balls into: " + leaderCards.getCard(id).getPower() + "\nVictory Points: \u001B[36m+" + leaderCards.getCard(id).getPv() + "\u001B[0m");
        }
        else if (id >= 12 && id <= 15) //LeaderCardFactory
        {
            System.out.println("Leader Type: \u001B[31mProduction\n\u001B[0mRequirements: 1 Level Two " + leaderCards.getCard(id).getColorProductionCard() + " card\nProduction: 1 " + leaderCards.getCard(id).getPower() + " => 1 resource of any type + 1 \u001B[31mfaith point\u001B[0m\nVictory Points: \u001B[36m+" + leaderCards.getCard(id).getPv() + "\u001B[0m");
        }
    }

    /**
     * printDevelopmentCard shows all the characteristics of a Development Card to the user; it's invoked from other methods
     * @param id
     */
    public static void printDevelopmentCard(int id) {
        id = id - 1;
        System.out.println("\n- This is a level " + developmentCards[id].getLevel() + " " + developmentCards[id].getColor() + " card");
        // costi
        System.out.println("\n- Cost:");
        printArray(developmentCards[id].getCost());
        System.out.println("\n- Production Cost:");
        printArray(developmentCards[id].getProductionCost());
        System.out.println("\n- Production Power:");
        printArray(developmentCards[id].getProduct());
        System.out.println("\n- Victory points: \u001B[32m" + developmentCards[id].getPv() + "\u001B[0m\n");
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
            chosenL1 = scanner.nextInt();
            System.out.println("\nChoose your second leader! Number:");
            chosenL2 = scanner.nextInt();
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
    public static void cardDetails(int[] marketCards) throws IOException {
        Scanner scanner = new Scanner (System.in);
        String userInput;
        int cardId = 0;
        System.out.println("Type the id of the card you want to know about, or 'quit' if you're ready to choose the card you want to buy.\n");
        userInput = scanner.nextLine();
        while (!userInput.equalsIgnoreCase("quit")) {
            try { cardId = Integer.parseInt(userInput); }
            catch (NumberFormatException e) {
                System.out.println("Not a number!");
            }
            if (cardId >= 1 && cardId <= 48) {
                for (int i=0; i<12; i++) {
                    if (cardId == marketCards[i]) {
                        printDevelopmentCard(cardId );
                        break;
                    }
                }
            }
            System.out.println("Type the id of the card you want to know about, or 'quit' if you're ready to choose the card you want to buy.\n");
            userInput = scanner.nextLine();
        }
    }

    /**
     * printMarket receives an array containing the 12 IDs of the cards in the market and interacts with the user so he can buy one
     * @param marketCards
     * @throws IOException
     */
    public static void printMarket(int[] marketCards) throws IOException {
        Scanner scanner = new Scanner (System.in);
        String userInput;
        int cardId = 0;
        boolean cardBought = false;
        System.out.println("Welcome to the market!\nThese are the available cards:\n");
        for (int i=0; i<12; i++) {
            // stampa del mercato provvisoria, da mostrare id + costo o id + potere di produzione
            // reminder: i getter in developmentCard vanno usati sulla carta una posizione indietro rispetto agli interi passati
            System.out.println("Id: \u001B[31m" + marketCards[i] + "\u001B[0m; Cost:");
            printArray(developmentCards[marketCards[i]-1].getCost());
        }

        while (!cardBought) {
            System.out.println("Type the id of the card you want to buy, or 'details' if you want to know more about a card.");
            userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("details")) { cardDetails(marketCards); }
            else {
                try { cardId = Integer.parseInt(userInput); }
                catch (NumberFormatException e) {
                    System.out.println("Not a number!");
                }
                if (cardId >= 1 && cardId <= 48) {
                    for (int i=0; i<12; i++) {
                        if (cardId == marketCards[i]) {
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

    public static void main(String[] args) {
        // testing startingLeaders & printMarket:
        developmentCards = new DevelopmentCard[48];
        developmentPopulate("src/main/resources/devCards.json");
        leaderCards = new LeaderDeck();
        startingLeaders(49, 50, 51, 52);



        int[] inputMarket = {1, 5, 9, 13, 17, 21, 25, 29, 33, 37, 41, 45};
        try { printMarket(inputMarket); }
        catch (IOException e) {
            System.out.println("finito malissimo");
        }
    }

}
