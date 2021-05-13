package it.polimi.ingsw.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginMessageTest {


    @Test
    public void LoginMessageTest(){
        LoginMessage message=new LoginMessage(3, true);
        System.out.println(message.GenerateMessage().getMessage());
    }

}