package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.CardNotFoundException;
import it.polimi.ingsw.model.exceptions.IllegalCardException;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardMarketTest {

    @Test
    public void constructortest(){
        CardMarket prova=new CardMarket();
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getId());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getPv());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getColor());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getCost());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getLevel());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getProductionCost());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getProduct());
    }
    @Test
    public void shuffleTest(){
        CardMarket prova=new CardMarket();
        prova.PrintId();
        prova.shuffle();
        prova.PrintId();
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getId());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getPv());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getColor());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getCost());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getLevel());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getProductionCost());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getProduct());

    }





    @Test
    public void CardNotFoundExceptionTest(){
        CardMarket prova=new CardMarket();
        int[] tester=prova.getCost(1);
        assertArrayEquals(tester,prova.getCost(1) );
        assertNotSame(tester, prova.getCost(1) );
        /*try {
            prova.getCardById(1).stampCost();
        }catch(CardNotFoundException e){
            System.out.println("not found");
        }
        for(int i: tester){
            System.out.println(tester[i]);
        }

         */
        /*
        SE VUOI VEDERE CHE GLI INDIRIZZI DEI DUE ARRAY SONO EFFETTIVAMENTE DIVERSI
        System.out.print(prova.getCost(1).toString());
        System.out.print("\n"+tester.toString());

         */
        tester=prova.getCost(-1);
        assertNull(tester);

    }

    /**
     * test for the method buyCard
     * it makes 4 checks:
     *<br><br>
     * first check is if the card selected (with a valid id) gets deleted and no Exception is thrown
     *<br><br>
     * second check is if now buying a new card from the same deck runs normally (if i bought the first card of a deck now the second card is the top card
     * so it should be legal to call the method)
     *<br><br>
     * third check triggers the IllegalCardException (when a card is not a top of the deck card) and checks if it gets triggered
     *<br><br>
     * fourth check triggers CardNotFoundException (when the card is not present in the market
     */
    @Test
    public void buyCardTest(){
        CardMarket prova=new CardMarket();
        DevelopmentCard Comprata=new DevelopmentCard();
        int checker=0;
        try {
             Comprata = prova.BuyCard(1);
        }catch(CardNotFoundException e){
            checker=1;
        }catch(IllegalCardException e){
            checker=2;
        }
        assertEquals(0, checker);
        assertNull(prova.getMatrixDevelopment()[0][0][0]);

        try {
            Comprata = prova.BuyCard(5);
        }catch(CardNotFoundException e){
            checker=1;
        }catch(IllegalCardException e){
            checker=2;
        }
        assertEquals(0, checker);
        assertNull(prova.getMatrixDevelopment()[0][0][1]);
        try {
            Comprata = prova.BuyCard(48);
        }catch(CardNotFoundException e){
            checker=1;
        }catch(IllegalCardException e){
            checker=2;
        }
        assertEquals(2, checker);

        try {
            Comprata = prova.BuyCard(-20);
        }catch(CardNotFoundException e){
            checker=1;
        }catch(IllegalCardException e){
            checker=2;
        }
        assertEquals(1, checker);
    }

}