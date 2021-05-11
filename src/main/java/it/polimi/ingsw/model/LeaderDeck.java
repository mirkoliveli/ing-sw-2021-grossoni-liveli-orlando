package it.polimi.ingsw.model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class LeaderDeck {
    private LeaderCard[] deck;

    public LeaderDeck() {
        deck = new LeaderCard[16];
        this.populate("src/main/resources/LeaderCards.json");
    }

    public void populate(String path) {
        Gson gson = new Gson();
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
        this.deck = gson.fromJson(buffer, LeaderCard[].class);
    }


    public LeaderCard getCard(int index) {
        return deck[index];
    }


    /**
     * method used at the start of the game to shuffle the deck
     */
    public void shuffle() {
        int index;
        LeaderCard temp;
        for (int i = 15; i > 0; i--) {
            index = (int) Math.floor(Math.random() * (i + 1));
            temp = new LeaderCard(this.deck[i]);
            this.deck[i].setAllLeaderCard(this.deck[index]);
            this.deck[index].setAllLeaderCard(temp);
        }

    }

    public LeaderCard[] getDeck() {
        return deck;
    }


    /**
     * method used to get the 4 cards from where the Player needs to choose 2 (action managed in the controller)
     *
     * @param PlayerId id of the player, the method expects a valid value between 1 and 4
     * @return a 4 LeaderCard array, which is a copy of the four cards in the deck.
     * @author Riccardo Grossoni
     */


    public LeaderCard[] getChoices(int PlayerId) {
        int counter = (PlayerId - 1) * 4;
        LeaderCard[] choices = new LeaderCard[4];
        for (int i = 0; i < 4; i++) {
            choices[i] = new LeaderCard(this.getCard(counter + i));
        }

        return choices;
    }

    public void printId(int index) {
        System.out.println(this.getCard(index).getId());
    }

    public void printIds() {
        for (int i = 0; i < 16; i++) {
            this.printId(i);
        }
    }

}
