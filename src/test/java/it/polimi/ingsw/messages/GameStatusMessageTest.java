package it.polimi.ingsw.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameStatusMessageTest {


    @Test
    public void CreateMessageTest(){
        GameStatusMessage message=new GameStatusMessage("baba");
        message.createMessage("src/main/resources/matchFromTestGameStatus.json");
        System.out.println(message.getMessage());
    }



}