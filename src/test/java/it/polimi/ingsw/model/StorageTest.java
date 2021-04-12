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
    public void testStorageMethods() {
        Storage testing = new Storage();
        assertEquals(1, testing.getLevel(1).getMaxQuantity());
        assertEquals(2, testing.getLevel(2).getMaxQuantity());
        assertEquals(3, testing.getLevel(3).getMaxQuantity());
        testing.getLevel(1).increaseQuantity(coins, 1);
        testing.getLevel(2).increaseQuantity(shields, 1);
        testing.getLevel(3).increaseQuantity(stones, 3);
        assertFalse(testing.getLevel(1).increaseQuantity(coins, 1));
        assertFalse(testing.getLevel(2).increaseQuantity(coins, 1));
        assertEquals(coins, testing.getLevel(1).getResourceType());
        assertEquals(shields, testing.getLevel(2).getResourceType());
        assertEquals(stones, testing.getLevel(3).getResourceType());
        assertEquals(1, testing.getLevel(1).getQuantity());
        assertEquals(1, testing.getLevel(2).getQuantity());
        assertEquals(3, testing.getLevel(3).getQuantity());
        assertTrue(testing.checkDifferentTypes());
        assertFalse(testing.swapLevels(1, 3));
        testing.swapLevels(1, 2);
        assertEquals(shields, testing.getLevel(1).getResourceType());
        assertEquals(coins, testing.getLevel(2).getResourceType());
    }

}