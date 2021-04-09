package test.java.it.polimi.ingsw.model;

import static it.polimi.ingsw.model.TypeOfResource.coins;
import org.junit.Test;

import static it.polimi.ingsw.model.TypeOfResource.shields;
import static org.junit.Assert.*;

public class DepotLevel {

    private it.polimi.ingsw.model.TypeOfResource resourceType;
    private int quantity;
    private int maxQuantity;

    public DepotLevel() {
        resourceType=null;
        quantity=0;
        maxQuantity=0;
    }

    public DepotLevel(it.polimi.ingsw.model.TypeOfResource resourceType, int quantity, int maxQuantity) {
        this.resourceType=resourceType;
        this.quantity=quantity;
        this.maxQuantity=maxQuantity;
    }

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