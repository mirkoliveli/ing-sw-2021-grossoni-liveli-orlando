package it.polimi.ingsw.messages;

import com.google.gson.Gson;

public class LoginMessage {
    private boolean successfulLogin;
    private int numOfPlayersInRoom;


    public  LoginMessage(int players, boolean login){
        this.successfulLogin=login;
        this.numOfPlayersInRoom=players;
    }

    public Message GenerateMessage(){

        Gson gson=new Gson();
        Message login=new Message(gson.toJson(this));
        return login;
    }

    public int getNumOfPlayersInRoom() {
        return numOfPlayersInRoom;
    }

    public boolean isSuccessfulLogin() {
        return successfulLogin;
    }
}
