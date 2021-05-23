package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.model.exceptions.NotEnoughResources;
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


    @Test
    public void AddResourcesIfLevelIsPresent() {
       /* Storage Tester = new Storage();
        Tester.getLevel(3).setQuantity(1);
        Tester.getLevel(3).setResourceType(TypeOfResource.coins);
        int[] prova = {1, 0, 0, 0};
        assertEquals(0, Tester.IncreaseResources(prova));
        prova[0] = 2;
        assertEquals(1, Tester.IncreaseResources(prova)); //c'è una risorsa scartata
        assertEquals(3, Tester.getLevel(3).getQuantity()); //lo slot è pieno
        Tester.addLeader(coins);
        assertEquals(0, Tester.IncreaseResources(prova));
        System.out.println(Tester.getFirstLeader().getQuantity());
        */
    }


    //breve controllo sul decreaser
//    @Test
//    public void DecreaserTest(){
//        Storage tester=new Storage();
//        tester.getLevel(1).setResourceType(coins);
//        tester.getLevel(1).setQuantity(1);
//        try {
//            tester.ResourceDecreaser(new int[]{2, 0, 0, 0});
//        }catch (NotEnoughResources e){
//            System.out.println("errore");
//            assertEquals(1, 0);//dato che NON 00 devo entrare qua faccio fallire il test automaticamente nel caso succeda
//        }
//        System.out.print(tester.getLevel(1).getResourceType() + "" + tester.getLevel(1).getQuantity());
//    }


    //controllo se la conversione funziona
    @Test
    public void ConversionToArrayTest() {
        Storage tester = new Storage();
        tester.getLevel(1).setResourceType(coins);
        tester.getLevel(1).setQuantity(1);
        tester.getLevel(2).setResourceType(shields);
        tester.getLevel(2).setQuantity(1);
        int[] resources = tester.conversionToArray();
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) assertEquals(1, resources[i]);
            else {
                assertEquals(0, resources[i]);
            }
        }
    }


    @Test
    public void LeaderTests(){
        Storage storage=new Storage();
        storage.getLevel(1).setResourceType(coins);
        storage.getLevel(1).setQuantity(1);
        storage.getLevel(2).setResourceType(stones);
        storage.getLevel(2).setQuantity(2);
        storage.getLevel(3).setResourceType(shields);
        storage.getLevel(3).setQuantity(3);

        storage.addLeader(coins);
        storage.getFirstLeader().setQuantity(1);
        storage.addLeader(shields);

        storage.transferToLeaderDepots();

        assertEquals(2, storage.getFirstLeader().getQuantity());
        assertEquals(2, storage.getSecondLeader().getQuantity());
        assertEquals(1, storage.getLevel(3).getQuantity());
        assertEquals(2, storage.getLevel(2).getQuantity());
        assertEquals(0, storage.getLevel(1).getQuantity());
        Gson gson=new Gson();
        int[][] test=storage.storageStatus();
        System.out.println(gson.toJson(test));
        storage.getLevel(1).setResourceType(servants);
        storage.getLevel(1).setQuantity(1);
        storage.transferToLeaderDepots();
        test=storage.storageStatus();
        System.out.println(gson.toJson(test));

    }

    @Test
    public void decreaserTest(){
        Storage storage=new Storage();
        storage.getLevel(1).setResourceType(coins);
        storage.getLevel(1).setQuantity(1);
        storage.getLevel(2).setResourceType(stones);
        storage.getLevel(2).setQuantity(2);
        storage.getLevel(3).setResourceType(shields);
        storage.getLevel(3).setQuantity(3);
        storage.addLeader(coins);
        storage.getFirstLeader().setQuantity(1);
        storage.addLeader(shields);
        storage.transferToLeaderDepots();
        Gson gson=new Gson();
        int[][] test=storage.storageStatus();
        System.out.println(gson.toJson(test));
        // a questo punto possiedo
        // 2 coins      0 servants
        // 3 shields    2 stones
        int[] costo={1, 0, 2, 1}; //è pagabile
        try {
            storage.ResourceDecreaser(costo);
        }catch (NotEnoughResources e){
            System.out.println("error");
        }
        test=storage.storageStatus();
        System.out.println(gson.toJson(test));

    }


}