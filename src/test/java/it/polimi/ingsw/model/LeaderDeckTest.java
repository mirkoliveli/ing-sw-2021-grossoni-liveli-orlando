package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class LeaderDeckTest {

    @Test
    public void TestConstructor(){

        LeaderDeck tester=new LeaderDeck();
        System.out.println(tester.getCard(0).getPower());
    }


}