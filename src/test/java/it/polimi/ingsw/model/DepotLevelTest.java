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
        it.polimi.ingsw.model.TypeOfResource ToR = coins;
        int q = 2;
        assertTrue(testing.increaseQuantity(ToR, q));
        q = 4;
        assertFalse(testing.increaseQuantity(ToR, q));
        ToR = shields;
        q = 2;
        assertFalse(testing.increaseQuantity(ToR, q));
        System.out.println("Fine test increaseQuantity");
    }

    @org.junit.Test
    public void decreaseQuantity() {
        it.polimi.ingsw.model.DepotLevel testing = new it.polimi.ingsw.model.DepotLevel(coins, 1, 3);
        it.polimi.ingsw.model.TypeOfResource ToR = coins;
        int q = 1;
        assertTrue(testing.decreaseQuantity(ToR, q));
        q = 2;
        assertFalse(testing.decreaseQuantity(ToR, q));
        ToR = shields;
        assertFalse(testing.decreaseQuantity(ToR, q));
        System.out.println("Fine test decreaseQuantity");
    }
}