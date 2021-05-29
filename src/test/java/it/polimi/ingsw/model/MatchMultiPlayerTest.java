package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.PlayerUpdate;
import it.polimi.ingsw.model.exceptions.CardNotFoundException;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatchMultiPlayerTest {

    @Test
    public void addpgTest() {
        MatchMultiPlayer match = new MatchMultiPlayer();
        assertEquals(0, match.getPlayers().size());
        Player pg = new Player("pg", 0);
        match.AddPlayer(pg);
        System.out.println(match.getPlayers().get(0).toString());
        System.out.println(pg);
        System.out.println(match.getPlayers().size());

    }


    @Test
    public void updatePlayerStatus() {
        MatchMultiPlayer match = new MatchMultiPlayer();
        Player pg = new Player("pg", 0);
        match.AddPlayer(pg);
        PlayerUpdate status = match.UpdatePlayerStatus(1);

        Gson gson = new Gson();

        String stringa = gson.toJson(status);
        System.out.println(stringa);


    }

    @Test
    public void canIBuyThisCard() {
        MatchMultiPlayer match = new MatchMultiPlayer();
        match.AddPlayer("gross");
        try {
            boolean bool=match.availableSlotForCard(1, 1);
            assertTrue(bool);
            bool=match.CanIBuyThisCard(1, 1);
            assertFalse(bool);
        }catch(CardNotFoundException e){
            assertEquals(1, 0);
        }

    }

    @Test
    public void availableSlotForCard() {
    }
}