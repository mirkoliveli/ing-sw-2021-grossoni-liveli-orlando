package it.polimi.ingsw.model;

public abstract class Card {
    private int pv;
    private int id;


    /**
     * costruttore base
     */
    public Card() {
        id = 0;
        pv = 0;
    }
//----------------------------------------------------------------------------------------------------------------------

    //costruttore per testing
    public Card(int victorypt, int code) {
        pv = victorypt;
        id = code;
    }

    public Card(int victorypt) {
        pv = victorypt;
    }

    //------------------------------------------------------------------------------------------------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

}
