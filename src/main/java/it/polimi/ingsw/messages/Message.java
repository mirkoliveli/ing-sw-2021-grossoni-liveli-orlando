package it.polimi.ingsw.messages;

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
