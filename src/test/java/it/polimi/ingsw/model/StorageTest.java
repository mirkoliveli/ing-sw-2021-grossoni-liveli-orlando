package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static it.polimi.ingsw.model.TypeOfResource.*;
import static org.junit.Assert.*;

public class StorageTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void swapLevels() {

        /* ancora da correggere l'errore: causa NullPointerException */

        Storage testing = new Storage();
        assertEquals(1, testing.getLevel(0));
        assertEquals(2, testing.getLevel(1));
        assertEquals(3, testing.getLevel(2));
        /* testing add */
        testing.add(coins, 1, 0);
        testing.add(shields, 1, 1);
        testing.add(stones, 3, 2);
        assertFalse(testing.add(coins, 1, 1));
        assertFalse(testing.add(coins, 1, 0));
        assertEquals(coins, testing.getType(0));
        assertEquals(shields, testing.getType(1));
        assertEquals(stones, testing.getType(2));
        assertEquals(1, testing.getStorage(0));
        assertEquals(1, testing.getStorage(1));
        assertEquals(3, testing.getStorage(2));
        /* testing checkDifferentTypes */
        assertTrue(testing.checkDifferentTypes());
        /* testing remove */
        testing.remove(stones, 1, 2);
        assertFalse(testing.remove(coins, 2, 0));
        assertFalse(testing.remove(coins, 1, 1));
        assertEquals(2, testing.getStorage(2));
        /* testing swapLevels */
        assertTrue(testing.swapLevels(1, 2));
        assertEquals(3, testing.getLevel(1));
        assertEquals(2, testing.getLevel(2));
        assertFalse(testing.swapLevels(0, 2));
    }

    @Test
    public void add() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void getType() {
    }

    @Test
    public void getLevel() {
    }

    @Test
    public void getStorage() {
    }
}