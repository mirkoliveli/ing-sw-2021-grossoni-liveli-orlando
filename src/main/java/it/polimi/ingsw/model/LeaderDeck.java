package it.polimi.ingsw.model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class LeaderDeck {
    private LeaderCard[] deck;

    public LeaderDeck(){
        deck= new LeaderCard[2];
        Gson gson= new Gson();
        BufferedReader buffer=null;
        try{
            buffer=new BufferedReader(new FileReader("src/main/resources/LeaderCards.json"));
        }catch (FileNotFoundException e){
            System.out.println("File non trovato");
        }
        this.deck=gson.fromJson(buffer, LeaderCard[].class);
    }


    public LeaderCard getCard(int index){
        return deck[index];
    }
    /**
     * serve ad inizio partita per mescolare le carte
     */
    public void shuffle (){
    //implementare come mescolare carte

    }

    public LeaderCard[] getDeck() {
        return deck;
    }

    /**
     * decidere SIA implementazione SIA cosa ritorna, dato che comunica sia con game che con player
     *
     */

    /* non mi builda sennò
    public LeaderCard pickFour(){}*/
}
