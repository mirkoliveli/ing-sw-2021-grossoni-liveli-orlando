package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PopesFavorCardTest {

    @Test
    public void discard() {
        PopesFavorCard test= new PopesFavorCard();
        test.discard();
        assertTrue(test.isDiscarded());
        System.out.println( "end of discard test");
    }

    @Test
    public void flip() {
        PopesFavorCard test= new PopesFavorCard();
        test.flip();
        assertTrue(test.isObtained());
        System.out.println( "end of flip test");
    }
}