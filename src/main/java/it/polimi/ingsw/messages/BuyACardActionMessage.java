package it.polimi.ingsw.messages;

public class BuyACardActionMessage {
    InBetweenActionExchanges action;
    String objectToSend;

    public BuyACardActionMessage(InBetweenActionExchanges action, String Object){
        this.action=action;
        this.objectToSend=Object;
    }

    public InBetweenActionExchanges getAction() {
        return action;
    }

    public String getObjectToSend() {
        return objectToSend;
    }

    public void setAction(InBetweenActionExchanges action) {
        this.action = action;
    }

    public void setObjectToSend(String objectToSend) {
        this.objectToSend = objectToSend;
    }
}
