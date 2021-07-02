package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.model.MatchMultiPlayer;
import it.polimi.ingsw.utils.StaticMethods;
import org.junit.Test;

public class ActionMessageTest {


    @Test
    public void test() {
        MatchMultiPlayer match = new MatchMultiPlayer();
        ActionMessage message = new ActionMessage(TypeOfAction.BEGIN_TURN);
        message.BeginTurn(match, 1);

        Gson gson = new Gson();
        System.out.println(gson.toJson(message));
        System.out.println(StaticMethods.GameStatusString(match, 1));
        GameStatusUpdate gameStatusUpdate = gson.fromJson(StaticMethods.GameStatusString(match, 1), GameStatusUpdate.class);
        message.BeginTurn(StaticMethods.objToJson(gameStatusUpdate));
        System.out.println(message.getActionAsMessage());


    }

}