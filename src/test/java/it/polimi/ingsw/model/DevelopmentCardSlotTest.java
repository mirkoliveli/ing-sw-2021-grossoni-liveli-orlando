package it.polimi.ingsw.model;

import org.junit.Test;

public class DevelopmentCardSlotTest {

    @Test
    public void get_top() {
        DevelopmentCardSlot slot = new DevelopmentCardSlot();
        slot.get_top();
    }

    @Test
    public void colors() {
        DevelopmentCardSlot slot = new DevelopmentCardSlot();
        Color[] vector=slot.get_colors();
        for (Color i: vector
             ) {
            System.out.println(i);
        }
        if(vector[0]!=Color.blue) System.out.println("lol");

    }

}