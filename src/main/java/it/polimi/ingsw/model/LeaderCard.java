package it.polimi.ingsw.model;

public class LeaderCard extends Card {

    private boolean played;
    private boolean discarded;
    private boolean isPlayable;
    private TypeOfResource power;
    private Color color1DiscountCard;
    private Color color2DiscountCard;
    private TypeOfResource storageCardCost;
    private Color color1WhiteBallCard; //questa è quella che ne richiede 2
    private Color color2WhiteBallCard; //questa è quella che ne richiede 1
    private Color colorProductionCard;


    public LeaderCard() {

    }

    /**
     * constructor used to create a cloned card
     *
     * @param cloned Leader card that is going to be cloned
     * @author Riccardo Grossoni
     */

    public LeaderCard(LeaderCard cloned) {
        this.setAllLeaderCard(cloned);
    }

    /**
     * method used to clone cards, can be used to clone an already existing card into another,
     * but mainly used for the constructor
     *
     * @param cloned Leader card that is going to be cloned
     * @author Riccardo Grossoni
     */

    public void setAllLeaderCard(LeaderCard cloned) {
        this.setId(cloned.getId());
        this.setPv(cloned.getPv());
        this.discarded = cloned.isDiscarded();
        this.isPlayable = cloned.checkAvailability();
        this.played = cloned.checkIfPlayed();
        this.power = cloned.getPower();
        this.color1DiscountCard = cloned.getColor1DiscountCard();
        this.color2DiscountCard = cloned.getColor2DiscountCard();
        this.storageCardCost = cloned.getStorageCardCost();
        this.color1WhiteBallCard = cloned.getColor1WhiteBallCard();
        this.color2WhiteBallCard = cloned.getColor2WhiteBallCard();
        this.colorProductionCard = cloned.getColorProductionCard();

    }

    public boolean checkAvailability() {
        return isPlayable;
    }

    public boolean checkIfPlayed() {
        return played;
    }

    public void playCard() {
        this.played = true;
    }

    public void discardCard() {
        this.discarded = true;
    }

    public void setPlayable(boolean playable) {
        isPlayable = playable;
    }

    public TypeOfResource getPower() {
        return power;
    }



    public void setPower(TypeOfResource power) {
        this.power = power;
    }

    public Color getColor1DiscountCard() {
        return color1DiscountCard;
    }

    public Color getColor1WhiteBallCard() {
        return color1WhiteBallCard;
    }

    public Color getColor2DiscountCard() {
        return color2DiscountCard;
    }

    public Color getColor2WhiteBallCard() {
        return color2WhiteBallCard;
    }

    public Color getColorProductionCard() {
        return colorProductionCard;
    }

    public TypeOfResource getStorageCardCost() {
        return storageCardCost;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void printCard() {
        System.out.printf("id: " + this.getId()
                + "\nplayed: " + this.checkIfPlayed()
                + "\ndiscarded: " + this.isDiscarded()
                + "\nisPlayable: " + this.checkAvailability()
                + "\npower: " + this.getPower()
                + "\nColor1DiscountCard: " + this.getColor1DiscountCard()
                + "\nColor2DiscountCard: " + this.getColor2DiscountCard()
                + "\nStorageCardCost: " + this.getStorageCardCost()
                + "\n1Whiteball: " + this.getColor1WhiteBallCard()
                + "\n2whiteBall: " + this.getColor2WhiteBallCard()
                + "\nProductionCard: " + this.getColorProductionCard()
        );
    }
}
