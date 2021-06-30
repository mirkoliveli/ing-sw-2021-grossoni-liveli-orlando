package it.polimi.ingsw.model;

public class PopesFavorCard extends Card {
    private boolean isObtained;
    private boolean isDiscarded;

    /**
     * tester constructor
     */
    public PopesFavorCard() {
        super();
        isDiscarded = false;
        isObtained = false;
    }

    /**
     * constructor that handles the pv value of the cards
     *
     * @param value sets the pv of the card
     */
    public PopesFavorCard(int value) {
        super(value);
        isDiscarded = false;
        isObtained = false;
    }

    public boolean isDiscarded() {
        return isDiscarded;
    }

    public boolean isObtained() {
        return isObtained;
    }


    /**
     * discard the card, method to be called only if not flipped already
     */
    public void discard() {
        this.isDiscarded = true;
    }

    /**
     * flip the card, as in the card is obtained
     */
    public void flip() {
        this.isObtained = true;
    }


}
