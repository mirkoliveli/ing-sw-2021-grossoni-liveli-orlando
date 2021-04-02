package model;

public class PopesFavorCard extends Card {
    private boolean isObtained;
    private boolean isDiscarded;

    public boolean isDiscarded() {
        return isDiscarded;
    }

    public boolean isObtained() {
        return isObtained;
    }


    /**
     * mettere eccezione per vedere se non è già stata ottenuta
     */
    public void discard(){
        this.isDiscarded=true;
    }

    /**
     * mettere eccezione per vedere se non è già stata scartata
     */
    public void flip(){
        this.isObtained=true;
    }

}
