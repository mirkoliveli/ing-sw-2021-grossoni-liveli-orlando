package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.MarketsUpdate;
import it.polimi.ingsw.model.MatchMultiPlayer;
import it.polimi.ingsw.model.Player;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class staticMethodsTest {

    @Test
    public void gameStatus() {
        MatchMultiPlayer match=new MatchMultiPlayer();
        match.AddPlayer(new Player("riccardo", 1));

        try {
            staticMethods.GameStatus(match, "src/main/resources/matchFromTestGameStatus.json");
        }catch(IOException e){
            System.out.println("error");
        }



    }
}