package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class StrongboxTest {

    @Test
    public void costruttoretest(){
        Strongbox inizializzazione= new Strongbox();
        assertEquals(0, inizializzazione.getSpecific(1));
        assertEquals(0, inizializzazione.getSpecific(2));
        assertEquals(0, inizializzazione.getSpecific(3));
        assertEquals(0, inizializzazione.getSpecific(4));
        System.out.println("fine test costruttore");
    }

    @Test
    public void getContents() {
    }

    @Test
    public void getSpecific() {
    }

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
        assertTrue(testing.canBuy(costo));
        costo[0]=4;
        costo[1]=2;
        costo[2]=2;
        costo[3]=2;
        assertFalse(testing.canBuy(costo));
        System.out.println("fine test canBuy");
    }

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

    @Test
    public void print() {
    }

    @Test
    public void testPrint() {
    }
}