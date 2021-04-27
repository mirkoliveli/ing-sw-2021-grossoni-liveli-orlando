package it.polimi.ingsw.model;

public class LeaderCard extends Card{

private boolean played;
private boolean discarded;
private boolean isPlayable;
private TypeOfResource power;
private Color color1DiscountCard;
private Color color2DiscountCard;
private TypeOfResource StorageCardCost;
private Color color1WhiteBallCard; //questa è quella che ne richiede 2
private Color color2WhiteBallCard; //questa è quella che ne richiede 1
private Color colorProductionCard;


    public boolean checkAvailability() {
        return isPlayable;
    }

    public boolean checkIfPlayed() {
        return played;
    }

    public void playCard(){
        this.played=true;
    }

    public void discardCard(){
        this.discarded=true;
    }

    public TypeOfResource getPower() {
        return power;
    }
}
