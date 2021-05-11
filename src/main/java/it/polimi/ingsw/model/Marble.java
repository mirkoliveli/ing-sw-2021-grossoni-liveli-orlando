package it.polimi.ingsw.model;

public class Marble {
    private MarbleColor colore;

    public Marble() {
        this.colore = null;
    }

    public Marble(MarbleColor colore) {
        this.colore = colore;
    }

    public MarbleColor getColore() {
        return colore;
    }

    public void setColore(MarbleColor colore) {
        this.colore = colore;
    }
}
