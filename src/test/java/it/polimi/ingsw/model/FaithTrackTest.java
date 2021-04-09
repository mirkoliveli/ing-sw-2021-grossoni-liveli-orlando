package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class FaithTrackTest {

    @Test
    public void increasePosition() {
    }

    @Test
    public void checkPopeSpace() {
    }

    @Test
    public void checkZone() {
    }

    @Test
    public void updateScore() {
    }

    @Test
    public void movement() {

        //assicuriamoci che i movimenti si updateano correttamente,
        FaithTrack test=new FaithTrack();
        test.Movement(4);
        assertEquals(1, test.getTotalpoints());
        assertEquals(4, test.getFaithMarker());
        test.Movement(3);
        assertEquals(2, test.getTotalpoints());
        assertEquals(7, test.getFaithMarker());


        //assicuriamoci che una volta superato lo spazio 24 il mio faithMarker non avanza oltre.
        FaithTrack testendgame = new FaithTrack(20);
        testendgame.Movement(6);
        assertEquals(24, testendgame.getFaithMarker());
        assertEquals(20, testendgame.getTotalpoints());
        System.out.println("fine test moviemento");


        //manca test per verificare che si attivi il Vatican report
        //deve essere ancora scritto dato che non ci sono ancora le interazioni tra personaggi


    }

    @Test
    public void flip_discardPopeCardTest(){

        //vericano che le tre zone vengono attivate e venga eseguita la giusta operazione


        FaithTrack testPopeZone = new FaithTrack(8);
        testPopeZone.activatePopeSpace(1);
        assertTrue(testPopeZone.getFirst().isObtained());
        assertFalse(testPopeZone.getFirst().isDiscarded());

        testPopeZone=new FaithTrack(13);
        testPopeZone.activatePopeSpace(2);
        assertTrue(testPopeZone.getSecond().isObtained());
        assertFalse(testPopeZone.getFirst().isDiscarded());

        testPopeZone=new FaithTrack(22);
        testPopeZone.activatePopeSpace(3);
        assertTrue(testPopeZone.getThird().isObtained());

        testPopeZone = new FaithTrack(3);
        testPopeZone.activatePopeSpace(1);
        assertTrue(testPopeZone.getFirst().isDiscarded());

        testPopeZone=new FaithTrack(11);
        testPopeZone.activatePopeSpace(2);
        assertTrue(testPopeZone.getSecond().isDiscarded());

        testPopeZone=new FaithTrack(14);
        testPopeZone.activatePopeSpace(3);
        assertTrue(testPopeZone.getThird().isDiscarded());

        System.out.println("fine test flip//discard");
    }



}