package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class StrongboxTest {


    //test per il costruttore
    //usato principalmente per provare funzionalità dei tests
    @Test
    public void costruttoretest(){
        Strongbox inizializzazione= new Strongbox();
        assertEquals(0, inizializzazione.getSpecific(1));
        assertEquals(0, inizializzazione.getSpecific(2));
        assertEquals(0, inizializzazione.getSpecific(3));
        assertEquals(0, inizializzazione.getSpecific(4));
        System.out.println("fine test costruttore");
    }

    //metodi getters non necessari da testare, getSpecific viene comunque usato ampliamente negli altri tests

    @Test
    public void getContents() {
    }

    @Test
    public void getSpecific() {
    }

    //verifica se funziona il metodo canBuy
    //questo metodo restituisce true se è possibile pagare un costo (int[4]) passato come parametro
    //viene testato un caso in cui sia possibile comprare
    //e un caso in cui non è possibile comprare
    @Test
    public void canBuy() {
        Strongbox testing= new Strongbox(3, 3, 3, 3);
        assertEquals(3, testing.getSpecific(1));
        assertEquals(3, testing.getSpecific(2));
        assertEquals(3, testing.getSpecific(3));
        assertEquals(3, testing.getSpecific(4));

        int[] costo=new int[4];
        costo[0]=2;
        costo[1]=2;
        costo[2]=2;
        costo[3]=2;
        //possibile comprare con questo costo
        assertTrue(testing.canBuy(costo));

        costo[0]=4;
        costo[1]=2;
        costo[2]=2;
        costo[3]=2;
        //non possibile comprare con questo costo (costo[0]>contents[0])
        assertFalse(testing.canBuy(costo));
        System.out.println("fine test canBuy");
    }

    //testa se funziona la rimozione di un costo
    //la rimozione viene eseguita solo se è possibile pagare il costo
    //vengono portati due casi test, uno in cui è possibile pagare
    //uno in cui non è possibile pagare
    @Test
    public void remove() {
        Strongbox testing= new Strongbox(3, 3, 3, 3);
        assertEquals(3, testing.getSpecific(1));
        assertEquals(3, testing.getSpecific(2));
        assertEquals(3, testing.getSpecific(3));
        assertEquals(3, testing.getSpecific(4));

        int[] costo=new int[4];
        costo[0]=2;
        costo[1]=2;
        costo[2]=2;
        costo[3]=2;
        //qua è possibile pagare, deve rimanere una risorsa per tipo
        assertTrue(testing.remove(costo));
        assertEquals(1, testing.getSpecific(1));
        assertEquals(1, testing.getSpecific(2));
        assertEquals(1, testing.getSpecific(3));
        assertEquals(1, testing.getSpecific(4));
        testing.print();
        costo[0]=0;
        costo[1]=1;
        costo[2]=1;
        costo[3]=1;
        assertTrue(testing.remove(costo));
        testing.print();
        System.out.println("end of remove test");
    }


    //testa l'aggiunta di risorse nella strongbox dato un parametro costo (int[4]) da aggiungere
    //viene testato una sola volta in quanto è un metodo semplice che fa solo addizioni, senza condizioni particolari

    @Test
    public void store() {
        Strongbox testing= new Strongbox(3, 3, 3, 3);
        assertEquals(3, testing.getSpecific(1));
        assertEquals(3, testing.getSpecific(2));
        assertEquals(3, testing.getSpecific(3));
        assertEquals(3, testing.getSpecific(4));
        int[] costo=new int[4];
        costo[0]=2;
        costo[1]=2;
        costo[2]=2;
        costo[3]=2;
        testing.store(costo);
        assertEquals(5, testing.getSpecific(1));
        assertEquals(5, testing.getSpecific(2));
        assertEquals(5, testing.getSpecific(3));
        assertEquals(5, testing.getSpecific(4));
        testing.print();
        System.out.println("end of test store");
    }

    //questi due metodi stampano a video e non servono agli scopi del programma, inoltre vengono testati indirettamente
    //dentro i test di store e remove.

    @Test
    public void print() {
    }

    @Test
    public void testPrint() {
    }
}