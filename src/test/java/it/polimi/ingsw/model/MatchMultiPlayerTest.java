package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatchMultiPlayerTest {

    @Test
    public void addpgTest(){
        MatchMultiPlayer match=new MatchMultiPlayer();
        Player pg= new Player("pg", 0);
        match.AddPlayer(pg);
        System.out.println(match.getPlayers().get(0).toString());
        System.out.println(pg.toString());
        System.out.println(match.getPlayers().size());

    }




}