package it.polimi.ingsw.singlePlayer.cli;


import com.google.gson.Gson;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.LeaderDeck;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class CliForSP {

    static LeaderDeck leaderCards=new LeaderDeck();
    static DevelopmentCard[] developmentCards;

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
     * method that takes the 4 leaders (form which the player chooses 2), prints them and asks for a selection, then returns the 2 selected values.
     * @param choices vector that contains the 4 possible leaders
     * @return vector containing the 2 selected leaders
     */
    public static int[] leaderChoice(int[] choices){
        int[] selection=new int[2];
        System.out.println("These are the choices for the 2 leader, please select 2 of them by typing the number 1-4");
        for(int i=0; i<4; i++){
            printLeader(choices[i]);
            System.out.println();
        }
        System.out.println("Please select the first leader (type a number between 1 and 4)");
        selection[0]=selectANumber(1, 4, -1)+48;
        System.out.println("Please select the second leader (type a number between 1 and 4)");
        selection[1]=selectANumber(1, 4, selection[0]-48)+48;
        System.out.println("Thanks! The game will start soon");
        return selection;
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
            }catch(NumberFormatException e){
                System.out.println("You must insert a number!");
            }
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




}
