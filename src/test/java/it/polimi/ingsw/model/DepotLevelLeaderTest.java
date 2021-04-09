package it.polimi.ingsw.model;

import org.junit.Test;

import static it.polimi.ingsw.model.TypeOfResource.coins;
import static it.polimi.ingsw.model.TypeOfResource.shields;
import static org.junit.Assert.*;

public class DepotLevelLeaderTest {

    @Test
    public void getMaxQuantity() {
    }

    @Test
    public void setMaxQuantity() {
    }

    @Test
    public void getResourceType() {
    }

    @Test
    public void increaseQuantity() {
        it.polimi.ingsw.model.DepotLevelLeader testing = new it.polimi.ingsw.model.DepotLevelLeader(coins);
        assertEquals(coins,testing.getResourceType());
        testing.increaseQuantity(coins, 2);
        assertEquals(2, testing.getQuantity());
        assertFalse(testing.increaseQuantity(coins, 1));
        assertFalse(testing.increaseQuantity(shields, 0));
    }

    @Test
    public void decreaseQuantity() {
        it.polimi.ingsw.model.DepotLevelLeader testing = new it.polimi.ingsw.model.DepotLevelLeader(coins);
        testing.increaseQuantity(coins, 2);
        testing.decreaseQuantity(coins, 1);
        assertEquals(1, testing.getQuantity());
        assertFalse(testing.increaseQuantity(coins, 2));
        assertFalse(testing.increaseQuantity(shields, 0));
    }
}