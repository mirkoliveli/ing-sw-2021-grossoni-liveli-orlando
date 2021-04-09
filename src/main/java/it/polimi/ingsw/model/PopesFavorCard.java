package it.polimi.ingsw.model;

public class PopesFavorCard extends Card {
    private boolean isObtained;
    private boolean isDiscarded;

    /**
     * costruttore che setta a zero sia id sia pv
     */
    public PopesFavorCard(){
        super();
        isDiscarded=false;
        isObtained=false;
    }

    /**
     * costruttore che setta a zero id ma setta a parametro 'value' i pv
     * @param value
     */
    public PopesFavorCard(int value){
        super(value);
        isDiscarded=false;
        isObtained=false;
    }

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


//----------------------------------------------------------------------------------------------------------------------
//testing metods



}
