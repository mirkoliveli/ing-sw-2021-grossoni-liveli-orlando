package it.polimi.ingsw.model;

public abstract class Card {
    private int pv;
    private int id;


    public int getId() {
        return id;
    }

    public int getPv() {
        return pv;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

}
