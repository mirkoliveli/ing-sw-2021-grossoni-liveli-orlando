package it.polimi.ingsw.model;

import org.junit.Test;

import static it.polimi.ingsw.model.TypeOfResource.coins;
import static it.polimi.ingsw.model.TypeOfResource.shields;
import static org.junit.Assert.*;

public class DepotLevelTest {



    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.Test
    public void getResourceType() {
    }

    @org.junit.Test
    public void setResourceType() {
    }

    @org.junit.Test
    public void getQuantity() {
    }

    @org.junit.Test
    public void setQuantity() {
    }

    @org.junit.Test
    public void getMaxQuantity() {
    }

    @org.junit.Test
    public void setMaxQuantity() {
    }

    @org.junit.Test
    public void increaseQuantity() {
        it.polimi.ingsw.model.DepotLevel testing = new it.polimi.ingsw.model.DepotLevel(coins, 1, 3);
        testing.increaseQuantity(coins,2);
        assertEquals(3, testing.getQuantity());
        assertFalse(testing.increaseQuantity(coins, 1));
        assertEquals(3, testing.getQuantity());
        assertFalse(testing.increaseQuantity(shields, 1));
        assertEquals(coins, testing.getResourceType());
        System.out.println("Fine test increaseQuantity");
    }

    @org.junit.Test
    public void decreaseQuantity() {
        it.polimi.ingsw.model.DepotLevel testing = new it.polimi.ingsw.model.DepotLevel(coins, 1, 3);
        testing.decreaseQuantity(coins, 1);
        assertEquals(null, testing.getResourceType());
        assertFalse(testing.decreaseQuantity(coins, 1));
        assertFalse(testing.decreaseQuantity(shields, 1));
        System.out.println("Fine test decreaseQuantity");
    }
}