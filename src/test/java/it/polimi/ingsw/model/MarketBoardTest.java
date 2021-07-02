package it.polimi.ingsw.model;

import org.junit.Test;

public class MarketBoardTest {

    //testa sia ConversionToResource sia ConversionToArray
    @Test
    public void convertions() {
        MarketBoard tester = new MarketBoard();
        tester.printBoard();
        System.out.println();
        System.out.println("resources obtained with conversionToResource:");
        tester.printGivenResources(true, 0);
        System.out.println("resources obtained with ConversionToArray");
        int[] temp = tester.ConversionToArray(true, 0);
        for (int i = 0; i < 4; i++) {
            System.out.println(temp[i] + " resource " + (i + 1));
        }
        System.out.println("\n");
        tester.printGivenResources(false, 0);
    }

    @Test
    public void shuffle() {
        MarketBoard tester = new MarketBoard();
        tester.printBoard();
        System.out.println("\nShuffled board:\n");
        tester.shuffle();
        tester.printBoard();

    }

    @Test
    public void ChangeBoard() {
        MarketBoard tester = new MarketBoard();
        tester.printBoard();
        tester.ChangeBoard(true, 1);
        tester.printBoard();

        tester = new MarketBoard();
        tester.printBoard();
        tester.ChangeBoard(false, 1);
        tester.printBoard();

    }


    //testa se funziona la print
    @Test
    public void printBoard() {
        MarketBoard tester = new MarketBoard();
        tester.printBoard();
    }


}