package model;

public abstract class LeaderCard extends Card{

private boolean played;
private boolean discarded;
private boolean isPlayable;

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



}
