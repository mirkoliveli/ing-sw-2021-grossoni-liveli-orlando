package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StackActionTokenTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void TestConstructorAndShuffling() {
        StackActionToken testing = new StackActionToken();
        testing.printStack();
    }

    @Test
    public void playFirst() {
        StackActionToken testing = new StackActionToken();
        assertEquals(0, testing.getIndex());
        testing.playFirst();
        testing.playFirst();
        assertEquals(2, testing.getIndex());
        assertFalse(testing.zeroDiscarded());
        testing.playFirst();
        assertEquals(3, testing.getIndex());
    }


    @Test
    public void resetStack() {
        StackActionToken testing = new StackActionToken();
        testing.playFirst();
        testing.playFirst();
        assertEquals(2, testing.getIndex());
        assertFalse(testing.zeroDiscarded());
        testing.resetStack();
        assertEquals(0, testing.getIndex());
        assertTrue(testing.zeroDiscarded());
    }

    @Test
    public void TestAbunch() {
        StackActionToken prova = new StackActionToken();
        prova.printStack();
        System.out.println("\n");
        ActionToken test = prova.playFirst();
        System.out.println(test.getType());
        test = prova.playFirst();
        System.out.println(test.getType());
    }


}