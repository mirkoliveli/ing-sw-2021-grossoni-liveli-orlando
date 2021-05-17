package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SoloFaithTrackTest {

    //tester per moveEnemy. moveEnemy muove la pedina del "computer"


    @Test
    public void moveEnemy() {
        SoloFaithTrack tester = new SoloFaithTrack();
        tester.moveEnemy(2);
        tester.Movement(4);
        assertEquals(4, tester.getFaithMarker());
        assertEquals(2, tester.getEnemy());
        tester.moveEnemy(2);
        tester.moveEnemy(2);
        tester.moveEnemy(2);
        assertTrue(tester.getFirst().isDiscarded());
        assertFalse(tester.getFirst().isObtained());
        assertEquals(0, tester.getPopepoints());
        tester.Movement(8);
        tester.moveEnemy(2);
        tester.moveEnemy(2);
        tester.moveEnemy(2);
        tester.moveEnemy(2);
        assertTrue(tester.getSecond().isObtained());
        assertFalse(tester.getSecond().isDiscarded());
        assertTrue(tester.getFirst().isDiscarded());
        assertFalse(tester.getFirst().isObtained());
        assertEquals(3, tester.getPopepoints());
        assertEquals(16, tester.getEnemy());
        tester.moveEnemy(1);
        tester.moveEnemy(2);
        tester.moveEnemy(2);
        tester.moveEnemy(2);
        tester.moveEnemy(2);
        assertEquals(24, tester.getEnemy());
        assertFalse(tester.getThird().isObtained());
        assertTrue(tester.getThird().isDiscarded());
        assertEquals(3, tester.getPopepoints());
    }
}