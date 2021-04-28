package it.polimi.ingsw.model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class LeaderDeck {
    private LeaderCard[] deck;

    public LeaderDeck(){
        deck= new LeaderCard[16];
       this.populate("src/main/resources/LeaderCards.json");
    }

    public void populate(String path){
        Gson gson= new Gson();
        BufferedReader buffer=null;
        try{
            buffer=new BufferedReader(new FileReader(path));
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
    int index;
    LeaderCard temp;
    for(int i=15; i>0; i--){
        index=(int)Math.floor(Math.random()* (i+1));
        temp=new LeaderCard(this.deck[i]);
        this.deck[i].setAllLeaderCard(this.deck[index]);
        this.deck[index].setAllLeaderCard(temp);
    }

    }

    public LeaderCard[] getDeck() {
        return deck;
    }

    /**
     * decidere SIA implementazione SIA cosa ritorna, dato che comunica sia con game che con player
     *
     */

    public void printId(int index){
        System.out.println(this.getCard(index).getId());
    }

    public void printIds(){
        for(int i=0; i<16; i++){
            this.printId(i);
        }
    }

}
