package model;

public class LeaderDeck {
    private LeaderCard[] deck;


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
    public LeaderCard pickFour(){}
}
