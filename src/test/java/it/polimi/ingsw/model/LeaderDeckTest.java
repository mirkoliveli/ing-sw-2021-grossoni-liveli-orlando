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


    //test molto rudimentale, a causa della complessità dell'oggetto LeaderCard ho preferito controllare ad "occhio"
    //se il metodo ritorna davvero le carte giuste, piuttosto che fare 200 assert diversi.
    @Test
    public void getChoicesTest(){
        LeaderDeck tester=new LeaderDeck();
        LeaderCard[] choice=tester.getChoices(2);
        for(int i=0; i<4; i++) {
            choice[i].printCard();
            System.out.println();
            System.out.println();
        }
        /*
        //CODICE CHE CONTROLLA CHE GLI OGGETTI SONO DIVERSI (E NON DUE PUNTATORI ALLA STESSA CARTA)
        //ho già controllato, "rimuovere" il commento se cambia il metodo e bisogna ricontrollare
        System.out.println(choice[0].toString());
        tester.getCard(4).printCard();
        System.out.println();
        System.out.println(tester.getCard(4).toString());
        */
    }

}