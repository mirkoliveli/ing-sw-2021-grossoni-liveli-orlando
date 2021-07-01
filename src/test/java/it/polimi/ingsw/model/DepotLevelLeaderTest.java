package it.polimi.ingsw.model;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DepotLevelLeaderTest {


    @Test
    public void increase_decreaseQuantityTest(){
        DepotLevelLeader depotLevelLeaderTest = new DepotLevelLeader(TypeOfResource.coins);
        depotLevelLeaderTest.increaseQuantity(TypeOfResource.coins, 2);
        assertEquals(2 , depotLevelLeaderTest.getQuantity());
        depotLevelLeaderTest.decreaseQuantity(TypeOfResource.coins,1);
        assertEquals(1, depotLevelLeaderTest.getQuantity());
        depotLevelLeaderTest.increaseQuantity(TypeOfResource.coins, 5);
        assertEquals(1, depotLevelLeaderTest.getQuantity());
        depotLevelLeaderTest.increaseQuantity(TypeOfResource.coins, 20);
        assertEquals(1, depotLevelLeaderTest.getQuantity());
    }



}