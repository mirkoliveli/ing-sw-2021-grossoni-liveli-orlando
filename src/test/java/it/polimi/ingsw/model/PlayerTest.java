package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void nameTest() {
        Player test = new Player("gigi", 1);
        assertEquals("gigi", test.getName());
        assertEquals(1, test.getId());
    }

    @Test
    public void victoryPointTest() {
        Player test = new Player("gigi", 1);
        test.setVictoryPoints(100);
        assertEquals(100, test.getVictoryPoints());
    }

//    @Test
//    public void finishedTest(){
//        Player test = new Player("gigi", 1);
//        PersonalBoard boardTest = new PersonalBoard();
//        boardTest.countTotalCards();
//    }
}
