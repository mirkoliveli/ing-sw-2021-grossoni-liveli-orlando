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

    /**
     * method that generates the same result as a .clone() method
     * @param original original array
     * @return copy
     */
    public static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = original[i];
        }
        return copy;
    }

    /**
     * method that generates a GameStatusUpdate in a json file.
     * @param match match from witch a GameStatusUpdate is generated
     * @param filePath path of the file that will be generated
     * @throws IOException if the file cannot be created
     */
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

    /**
     * given a match class and the id of the player in turn returns a string containing a JSON object GameStatusUpdate
     * @param match match from witch a GameStatusUpdate is generated
     * @param player player taking is turn
     * @return JSON object GameStatusUpdate as string
     */
    public static String GameStatusString(MatchMultiPlayer match, int player) {
        Gson gson = new Gson();
        GameStatusUpdate gameState = new GameStatusUpdate(match.getPlayers().size());

        gameState.setNextPlayer(player);
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

        String result=gson.toJson(gameState);
        return result;
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


    /**
     * method that values if the second array is bigger than the first(for it to be bigger all his instances must be greater than the other)
     * @param cost first array
     * @param maxPayment second array, the one that is valued
     * @return false if any maxPayment[i] is smaller than cost[i], true otherwise
     */
    public static boolean isItAffordable(int[] cost, int[] maxPayment){
        for(int i=0; i<4; i++){
            if(cost[i]>maxPayment[i]) return false;
        }
        return true;
    }

    /**
     * method that checks if any entry on this boolean vector is true
     * @param toBeChecked boolean vector to be checked
     * @return false if all entries on the vector are false, true otherwise
     */
    public static boolean AreAnyTrue(boolean[] toBeChecked){
        for(int i=0; i<toBeChecked.length; i++){
            if(toBeChecked[i]) return true;
        }
        return false;
    }

    /**
     * method that checks if a number corresponds to a resource
     * @param n number checked
     * @return true if num between 1 and 4, false otherwise
     */
    public static boolean numBetween1and4(int n){
        return (n>0 && n<5);
    }

    /**
     * adds the arrayToAdd_isTemp to array1, ignoring if the arrayToAdd_isTemp is longer. Will throw a NullPointerException if the arrayToAdd_isTemp is shorter than array1
     * @param array1 array where the second one is added to (this DOES INCREASE)
     * @param arrayToAdd_isTemp array which is added to the first one (this DOES NOT INCREASE)
     */
    public static void sumArray(int[] array1, int[] arrayToAdd_isTemp){
        for(int i=0; i<array1.length; i++){
            array1[i]+=arrayToAdd_isTemp[i];
        }
    }


}
