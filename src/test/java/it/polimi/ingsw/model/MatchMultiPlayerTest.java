package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.PlayerUpdate;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class MatchMultiPlayerTest {

    @Test
    public void addpgTest(){
        MatchMultiPlayer match=new MatchMultiPlayer();
        assertEquals(0, match.getPlayers().size());
        Player pg= new Player("pg", 0);
        match.AddPlayer(pg);
        System.out.println(match.getPlayers().get(0).toString());
        System.out.println(pg.toString());
        System.out.println(match.getPlayers().size());

    }



    @Test
    public void updatePlayerStatus() {
        MatchMultiPlayer match=new MatchMultiPlayer();
        Player pg=new Player("pg", 0);
        match.AddPlayer(pg);
        PlayerUpdate status= match.UpdatePlayerStatus(1);

        Gson gson=new Gson();

        String stringa=gson.toJson(status);
        System.out.println(stringa);




    }
}