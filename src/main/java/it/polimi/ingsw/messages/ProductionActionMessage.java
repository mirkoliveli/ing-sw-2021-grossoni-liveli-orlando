package it.polimi.ingsw.messages;

/**
 * class used when interacting with the client about the production action
 */
public class ProductionActionMessage {
    InBetweenActionExchanges action;
    String objectToSend;

    public ProductionActionMessage(InBetweenActionExchanges action, String Object) {
        this.action = action;
        this.objectToSend = Object;
    }

    public InBetweenActionExchanges getAction() {
        return action;
    }

    public void setAction(InBetweenActionExchanges action) {
        this.action = action;
    }

    public String getObjectToSend() {
        return objectToSend;
    }

    public void setObjectToSend(String objectToSend) {
        this.objectToSend = objectToSend;
    }
}
