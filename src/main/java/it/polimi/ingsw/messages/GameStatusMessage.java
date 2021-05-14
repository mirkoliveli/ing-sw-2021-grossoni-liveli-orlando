package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.model.DevelopmentCard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * message containing an object GameStatusUpdate
 */
public class GameStatusMessage extends Message {


    public GameStatusMessage(String messaggio) {
        super(messaggio);
    }

    //loads the message from a JSON file
    public void createMessage(String Source){
        Gson gson = new Gson();
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new FileReader(Source));
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
        GameStatusUpdate status = gson.fromJson(buffer, GameStatusUpdate.class);
        setMessage(gson.toJson(status));
        try {
            buffer.close();
        } catch (IOException e) {
            System.out.println("error");
        }





    }




}
