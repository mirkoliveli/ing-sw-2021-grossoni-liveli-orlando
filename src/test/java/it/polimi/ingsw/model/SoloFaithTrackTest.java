package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SoloFaithTrackTest {

    //tester per moveEnemy. moveEnemy muove la pedina del "computer" e ritorna se attiva o meno una zona favore papale
    //



    @Test
    public void moveEnemy() {
        SoloFaithTrack tester= new SoloFaithTrack();
        int temp;
        temp=tester.moveEnemy(2);
        tester.Movement(4);
        assertEquals(4, tester.getFaithMarker());
        assertEquals(2, tester.getEnemy());
        temp=tester.moveEnemy(2);
        temp=tester.moveEnemy(2);
        if(tester.moveEnemy(2)!=0){
            tester.activatePopeSpace(tester.getEnemy()/8);
        }
        assertTrue(tester.getFirst().isDiscarded());
        assertEquals(8, tester.getEnemy());
    }
}