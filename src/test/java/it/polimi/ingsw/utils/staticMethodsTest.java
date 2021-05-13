package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.MarketsUpdate;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.MatchMultiPlayer;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Strongbox;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class staticMethodsTest {

    @Test
    public void gameStatus() {
        MatchMultiPlayer match=new MatchMultiPlayer();
        match.AddPlayer(new Player("riccardo", 1));
        match.AddPlayer(new Player("mirko", 2));
        match.AddPlayer(new Player("andrea", 3));
        try {
            staticMethods.GameStatus(match, "src/main/resources/matchFromTestGameStatus.json");
        }catch(IOException e){
            System.out.println("error");
        }

        

    }

    @Test
    public void objToJson() {
        Strongbox testRandom=new Strongbox();
        System.out.println(staticMethods.objToJson(testRandom));
    }


    @Test
    public void utilsTest(){
        String carl="carl";
        Message mess=new Message(carl + 8);
        System.out.println(mess.getMessage());
    }
}