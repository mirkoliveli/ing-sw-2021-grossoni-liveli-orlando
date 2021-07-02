package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonalBoardTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getTotalResources() {
    }

    @Test
    public void countTotalCards() {
        PersonalBoard personalBoardTest = new PersonalBoard(1);
        assertEquals(0, personalBoardTest.countTotalCards());
        assertEquals(0, personalBoardTest.getBoardVictoryPoints());
    }

    @Test
    public void getCosts() {
    }

    @Test
    public void testGetCosts() {
    }
}