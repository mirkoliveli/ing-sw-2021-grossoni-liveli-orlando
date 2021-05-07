package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.EndSoloGame;
import org.junit.Test;

import static org.junit.Assert.*;

public class SinglePlayerMatchTest {

    @Test
    public void lorenzoAction() {
        SinglePlayerMatch match=new SinglePlayerMatch("Player");
        try{
            match.LorenzoAction();
            match.getTokensStack().printStack();
            match.getCardMarket().PrintId();
            match.getPlayer().getBoard().getFaithTrack().CoolPrint();
            System.out.println(match.getLorenzoFaithTrack().getEnemy());
        }catch (EndSoloGame e){
            System.out.println("error");
        }



    }
}