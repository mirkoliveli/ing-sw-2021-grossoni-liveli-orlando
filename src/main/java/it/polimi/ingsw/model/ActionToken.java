package it.polimi.ingsw.model;

public class ActionToken {

    private boolean isDiscarded;
    private final TypeOfActionToken type;


    public ActionToken(TypeOfActionToken t) {
        this.isDiscarded = false;
        this.type = t;
    }

    public void discard() {
        this.isDiscarded = true;
    }

    public void reshuffle() {
        this.isDiscarded = false;
    }

    public boolean isDiscarded() {
        return isDiscarded;
    }

    public TypeOfActionToken getType() {
        return type;
    }

}
