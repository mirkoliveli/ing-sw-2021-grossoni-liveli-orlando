package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class LeaderDeckTest {

    @Test
    public void TestConstructor(){

        LeaderDeck tester=new LeaderDeck();
        System.out.println(tester.getCard(15).getPower());
    }

    @Test
    public void TestShuffle(){
        LeaderDeck tester=new LeaderDeck();
        System.out.println("id mazzo prima di mescolare");
        tester.printIds();
        tester.shuffle();
        System.out.println("id mazzo dopo mescolo");
        tester.printIds();
    }




    @Test
    public void NullToValueAndValueToNullTest(){
        LeaderDeck tester=new LeaderDeck();
        LeaderCard swap=new LeaderCard(tester.getCard(0));
        tester.getCard(0).printCard();
        System.out.println("\n\nla carta test per swap:\n");
        swap.printCard();
        System.out.println("\n");
        tester.getCard(4).printCard();
        swap.setAllLeaderCard(tester.getCard(4));
        System.out.println("\n\nla carta test per swap:\n");
        swap.printCard();


    }


}