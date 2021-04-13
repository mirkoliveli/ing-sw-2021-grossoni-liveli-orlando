package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MarketBoardTest {


    @Test
    public void conversioni() {
        MarketBoard tester= new MarketBoard();
        tester.printBoard();
        tester.printGivenResources(true, 0);
        System.out.println();
        tester.printGivenResources(false, 0);

    }

    @Test
    public void shuffle() {
        MarketBoard tester= new MarketBoard();
        tester.printBoard();
        System.out.println("\necco ora la board mescolata\n");
        tester.shuffle();
        tester.printBoard();

    }

    @Test
    public void ChangeBoard(){
        MarketBoard tester= new MarketBoard();
        tester.printBoard();
        tester.ChangeBoard(true, 1);
        tester.printBoard();

        tester= new MarketBoard();
        tester.printBoard();
        tester.ChangeBoard(false, 1);
        tester.printBoard();

    }


    //testa se funziona la print
    @Test
    public void printBoard() {
        MarketBoard tester= new MarketBoard();
        tester.printBoard();
    }
}