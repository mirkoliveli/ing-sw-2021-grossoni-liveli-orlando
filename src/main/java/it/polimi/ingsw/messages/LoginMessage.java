package it.polimi.ingsw.messages;

import com.google.gson.Gson;

/**
 * message sent by the server to the client, contains the number of logged players and if the logging in has been successful
 */
public class LoginMessage {
    private final boolean successfulLogin;
    private final int numOfPlayersInRoom;


    public LoginMessage(int players, boolean login) {
        this.successfulLogin = login;
        this.numOfPlayersInRoom = players;
    }

    public Message GenerateMessage() {

        Gson gson = new Gson();
        Message login = new Message(gson.toJson(this));
        return login;
    }

    public int getNumOfPlayersInRoom() {
        return numOfPlayersInRoom;
    }

    public boolean isSuccessfulLogin() {
        return successfulLogin;
    }
}
