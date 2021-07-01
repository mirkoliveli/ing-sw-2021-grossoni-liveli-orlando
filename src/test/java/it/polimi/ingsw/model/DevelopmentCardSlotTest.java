package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

//    @Test
//    public void placeCardTest(){
//        DevelopmentCardSlot slotTest = new DevelopmentCardSlot();
//        DevelopmentCard developmentCardTest1 = new DevelopmentCard();
//        DevelopmentCard developmentCardTest2 = new DevelopmentCard();
//        DevelopmentCard developmentCardTest3 = new DevelopmentCard();
//        developmentCardTest1.setLevel(1);
//        developmentCardTest2.setLevel(2);
//        developmentCardTest3.setLevel(3);
//        slotTest.placeCard(developmentCardTest1);
//        assertEquals (developmentCardTest1, slotTest.get_top());
//        slotTest.placeCard(developmentCardTest3);
//        assertEquals (developmentCardTest1, slotTest.get_top());
//        slotTest.placeCard(developmentCardTest2);
//        assertEquals (developmentCardTest2, slotTest.get_top());
//    }
//
//    @Test
//    public void pvTest(){
//        DevelopmentCardSlot slotTest = new DevelopmentCardSlot();
//        DevelopmentCard developmentCardTest1 = new DevelopmentCard();
//        DevelopmentCard developmentCardTest2 = new DevelopmentCard();
//        DevelopmentCard developmentCardTest3 = new DevelopmentCard();
//        developmentCardTest1.setLevel(1);
//        developmentCardTest2.setLevel(2);
//        developmentCardTest3.setLevel(3);
//        developmentCardTest1.setPv(5);
//        developmentCardTest2.setPv(3);
//        developmentCardTest3.setPv(1);
//        slotTest.placeCard(developmentCardTest1);
//        assertEquals ( 5, slotTest.getSlotPV());
//        slotTest.placeCard(developmentCardTest2);
//        assertEquals (8, slotTest.getSlotPV());
//        slotTest.placeCard(developmentCardTest3);
//        assertEquals (9, slotTest.getSlotPV() );
//    }

}