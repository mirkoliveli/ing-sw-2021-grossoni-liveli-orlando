package it.polimi.ingsw.model;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DepotLevelLeaderTest {


    @Test
    public void IncreaseDecreaseQuantityTest(){
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

    @Test
    public void ResourceTypeTest(){
        DepotLevelLeader depotLevelLeaderTest1 = new DepotLevelLeader(TypeOfResource.coins);
        DepotLevelLeader depotLevelLeaderTest2 = new DepotLevelLeader(TypeOfResource.shields);
        DepotLevelLeader depotLevelLeaderTest3 = new DepotLevelLeader(TypeOfResource.stones);
        DepotLevelLeader depotLevelLeaderTest4 = new DepotLevelLeader(TypeOfResource.servants);
        assertEquals(TypeOfResource.coins, depotLevelLeaderTest1.getResourceType());
        assertEquals(TypeOfResource.shields, depotLevelLeaderTest2.getResourceType());
        assertEquals(TypeOfResource.stones, depotLevelLeaderTest3.getResourceType());
        assertEquals(TypeOfResource.servants, depotLevelLeaderTest4.getResourceType());
    }



}