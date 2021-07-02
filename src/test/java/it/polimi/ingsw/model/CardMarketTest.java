package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.CardNotFoundException;
import it.polimi.ingsw.model.exceptions.EndSoloGame;
import it.polimi.ingsw.model.exceptions.IllegalCardException;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardMarketTest {

    @Test
    public void constructortest() {
        CardMarket prova = new CardMarket();
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getId());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getPv());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getColor());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getCost());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getLevel());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getProductionCost());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getProduct());
    }

    @Test
    public void shuffleTest() {
        CardMarket prova = new CardMarket();
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
    public void CardNotFoundExceptionTest() {
        CardMarket prova = new CardMarket();
        int[] tester = prova.getCost(1);
        assertArrayEquals(tester, prova.getCost(1));
        assertNotSame(tester, prova.getCost(1));
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
        tester = prova.getCost(-1);
        assertNull(tester);

    }

    /**
     * test for the method buyCard
     * it makes 4 checks:
     * <br><br>
     * first check is if the card selected (with a valid id) gets deleted and no Exception is thrown
     * <br><br>
     * second check is if now buying a new card from the same deck runs normally (if i bought the first card of a deck now the second card is the top card
     * so it should be legal to call the method)
     * <br><br>
     * third check triggers the IllegalCardException (when a card is not a top of the deck card) and checks if it gets triggered
     * <br><br>
     * fourth check triggers CardNotFoundException (when the card is not present in the market
     */
    @Test
    public void buyCardTest() {
        CardMarket prova = new CardMarket(" ");
        DevelopmentCard Comprata = new DevelopmentCard();
        int checker = 0;
        try {
            Comprata = prova.BuyCard(1);
        } catch (CardNotFoundException e) {
            checker = 1;
        } catch (IllegalCardException e) {
            checker = 2;
        }
//        assertEquals(0, checker);
//        assertNull(prova.getMatrixDevelopment()[0][0][0]);

        try {
            Comprata = prova.BuyCard(5);
        } catch (CardNotFoundException e) {
            checker = 1;
        } catch (IllegalCardException e) {
            checker = 2;
        }
        assertEquals(0, checker);
        assertNull(prova.getMatrixDevelopment()[0][0][1]);
        try {
            Comprata = prova.BuyCard(48);
        } catch (CardNotFoundException e) {
            checker = 1;
        } catch (IllegalCardException e) {
            checker = 2;
        }
        assertEquals(2, checker);

        try {
            Comprata = prova.BuyCard(-20);
        } catch (CardNotFoundException e) {
            checker = 1;
        } catch (IllegalCardException e) {
            checker = 2;
        }
        assertEquals(1, checker);
    }

    /**
     * test for the main method of the single player action.
     */
    @Test
    public void deleteTwoByColorTest() {
        CardMarket prova = new CardMarket(" ");

        //PRIMO TEST, se ci sono almeno due carte nel mazzo (non livello 3)
        prova.Remove(1); //simula l'acquisto di una carta

        //prova.Remove(5); //aggiungi questo se vuoi provare con esattamente due carte
//        assertNull(prova.getMatrixDevelopment()[0][0][0]);
        //assertNull(prova.getMatrixDevelopment()[0][0][1]); //usare solo se vuoi provare esattamente due carte
        try {
            prova.DeleteTwoCardsByColor(0);
        } catch (EndSoloGame e) {
            System.out.println("errore");
        }
        assertNull(prova.getMatrixDevelopment()[0][0][1]);
        assertNull(prova.getMatrixDevelopment()[0][0][2]);
        //assertNull(prova.getMatrixDevelopment()[0][0][3]); //usare solo se vuoi testare esattametne due carte

//        /*prova.PrintId();*/
//        prova.populate(0, 0, "src/main/resources/green_lvl1.json"); //ripristina stato iniziale
//        /*prova.PrintId();*/

        //SECONDO TEST, una sola carta nel mazzo (deve quindi eliminare la prima del mazzo al livello dopo)
        prova.Remove(1);
        prova.Remove(5);
        prova.Remove(9);
        try {
            prova.DeleteTwoCardsByColor(0);
        } catch (EndSoloGame e) {
            System.out.println("errore");
        }
        //rimane una carta nel mazzo livello 1 verde, in posizione 3, la prossima carta da rimuovere è quindi
        //quella in posizione 0 nel mazzo livello 2 verde!
        assertNull(prova.getMatrixDevelopment()[0][0][3]);
        assertNull(prova.getMatrixDevelopment()[1][0][0]);
        //prova.PrintId(); //nota, deve mancare una carta dal mazzo 5! (e tutto il mazzo uno)

        prova.populate(0, 0, "/green_lvl1.json");
        prova.populate(1, 0, "/green_lvl2.json");

        //TERZO TEST, vediamo se effettivamente va tutto bene quando finisce la partita!
        //

        //questa volta eliminiamone alcune con il metodo che stiamo provando, e non con il remove!
        //per rendere le cose più spicy ne eliminiamo una, così deve fare il jump tra livelli ogni volta
        prova.Remove(1);
        try {
            //questa cosa in realtà è un micro test per altri casi, vedi fondo
            prova.DeleteTwoCardsByColor(0);
            prova.DeleteTwoCardsByColor(0);
            prova.DeleteTwoCardsByColor(0);
            prova.DeleteTwoCardsByColor(0);
        } catch (EndSoloGame e) {
            System.out.println("errore");
        }
        assertNotNull(prova.getMatrixDevelopment()[2][0][1]);
        prova.Remove(37);
        int tester = 42;
        try {
            prova.DeleteTwoCardsByColor(0);
        } catch (EndSoloGame e) {
            tester = 0;

        }
        assertEquals(0, tester);
        assertNull(prova.getMatrixDevelopment()[2][0][2]);
        assertNull(prova.getMatrixDevelopment()[2][0][3]);


        prova.populate(0, 0, "/green_lvl1.json");
        prova.populate(1, 0, "/green_lvl2.json");
        prova.populate(2, 0, "/green_lvl3.json");
        /*prova.PrintId();*/

        //QUARTO TEST, vediamo se finisce comunque il game quando c'è esattamente una carta nei mazzi con livelli diversi
        try {
            //questa cosa in realtà è un micro test per altri casi, vedi fondo
            prova.DeleteTwoCardsByColor(0);
            prova.DeleteTwoCardsByColor(0);
            prova.DeleteTwoCardsByColor(0);
            prova.DeleteTwoCardsByColor(0);
            prova.DeleteTwoCardsByColor(0);
        } catch (EndSoloGame e) {
            System.out.println("errore");
        }
        /*prova.PrintId();*/
        try {
            prova.DeleteTwoCardsByColor(0);
        } catch (EndSoloGame e) {
            tester = 10;
        }
        assertEquals(10, tester);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                assertNull(prova.getMatrixDevelopment()[i][0][j]);
            }
        }
        /*prova.PrintId();*/


        //nota, casi itermedi sono testati automaticamente nella "costruzione" dei
        //quattro tests. ad esempio eliminare due carte quando ho due carte nel livello 2 e basta viene testato
        //automaticamente nella parte di try catch del quarto test!
    }


}