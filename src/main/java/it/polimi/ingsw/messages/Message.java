package it.polimi.ingsw.messages;

/**
 * standard message that can contain a basic String or a Json file (sent as a String)
 */
public class Message {
    private String message;

    public Message(String messaggio){
        message=messaggio;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
