package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.controller.PlayerUpdate;
import it.polimi.ingsw.model.MatchMultiPlayer;
import it.polimi.ingsw.model.TypeOfResource;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticMethods {

    public static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = original[i];
        }
        return copy;
    }

    public static void GameStatus(MatchMultiPlayer match, String filePath) throws IOException {
        Gson gson = new Gson();
        Writer writer = Files.newBufferedWriter(Paths.get(filePath));
        GameStatusUpdate gameState = new GameStatusUpdate(match.getPlayers().size());

        //setup for the game markets
        //marbleMarket
        gameState.getMarketsStatus().setMarketBoard(match.getMarket().status());
        gameState.getMarketsStatus().setSlideMarble(match.getMarket().getSlideMarble().getColore());
        //cardMarket
        gameState.getMarketsStatus().setCardMarket(match.getCardMarket().cardMarketStatus());

        //setup for the Players
        PlayerUpdate[] temp = new PlayerUpdate[match.getPlayers().size()];

        for (int i = 0; i < match.getPlayers().size(); i++) {
            temp[i] = match.UpdatePlayerStatus(i + 1);
        }
        gameState.setPlayersStatus(temp);

        gson.toJson(gameState, writer);
        writer.close();

    }

    public static String objToJson(Object convertible) {
        Gson gson = new Gson();
        return gson.toJson(convertible);
    }

    /**
     * method that convert a type of resource to an int (that is used as an index for vectors). it uses the usual
     * alphabetical distribution for coins through stones, starting from 0 to 3. it returns -1 if the resource is faith
     *
     * @param type
     * @return
     */
    public static int TypeOfResourceToInt(TypeOfResource type) {
        switch (type) {
            case coins:
                return 0;
            case servants:
                return 1;
            case shields:
                return 2;
            case stones:
                return 3;
            case faith:
                return -1;
        }

        return 42;

    }

    /**
     * method that convert the resourceID to the resource enum
     *
     * @param idResource id of resource, standard order from 0 to 4
     * @return resource enum corresponding to the resource id
     */
    public static TypeOfResource IntToTypeOfResource(int idResource) {
        switch (idResource) {
            case 1:
                return TypeOfResource.coins;
            case 2:
                return TypeOfResource.servants;
            case 3:
                return TypeOfResource.shields;
            case 4:
                return TypeOfResource.stones;
            case 5:
                return TypeOfResource.faith;
        }

        return null;
    }

}
