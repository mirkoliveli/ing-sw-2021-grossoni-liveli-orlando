package it.polimi.ingsw.controller;


/**
 * class that determines the state of the game, in particular which phase is the match currently in, also it carries some util info about the players
 */
public class GameState {

    //phases (ID 0 TO 4)
    private static boolean loginPhase; //before the game starts all players connect to the game and select a (unique) username
    private static boolean gettingStartedPhase; //player1 waits the other players to get the starting bonuses
    private static boolean turnPhase; //starting the game, players exchange turns taking actions until one reaches an EndGame condition
    private static boolean lastTurnPhase; // after an EndGame condition all the remaining players take one last turn each (until the first player)
    private static boolean gameEndedPhase; //the game has ended, a winning or losing screen is displayed on each player's screen


    private static int startingPlayer; //determines who is the first player
    private static int totalPlayersNumber; //determines how many players will be in the room
    private static int idOfPlayerInTurn; //tracker for the player taking the action
    private static int winnerId; //tracker for the winner of the game

    public GameState(){
        loginPhase=true;
        gettingStartedPhase=false;
        turnPhase=false;
        lastTurnPhase=false;
        gameEndedPhase=false;

        startingPlayer=0;
        totalPlayersNumber=0;
        idOfPlayerInTurn=0;
        winnerId=0;
    }


    //------------------------------------------------------------------------------------------------------------------


    public static int getIdOfPlayerInTurn() {
        return idOfPlayerInTurn;
    }

    public static int getStartingPlayer() {
        return startingPlayer;
    }

    public static int getTotalPlayersNumber() {
        return totalPlayersNumber;
    }

    public static int getWinnerId() {
        return winnerId;
    }

    public static boolean isLoginPhase() {
        return loginPhase;
    }

    public static boolean isGettingStartedPhase() {
        return gettingStartedPhase;
    }

    public static boolean isTurnPhase() {
        return turnPhase;
    }

    public static boolean isLastTurnPhase() {
        return lastTurnPhase;
    }

    public static boolean isGameEndedPhase() {
        return gameEndedPhase;
    }

    public static void setStartingPlayer(int startingPlayer) {
        GameState.startingPlayer = startingPlayer;
    }

    public static void setTotalPlayersNumber(int totalPlayersNumber) {
        GameState.totalPlayersNumber = totalPlayersNumber;
    }

    public static void setWinnerId(int winnerId) {
        GameState.winnerId = winnerId;
    }

    public static void setIdOfPlayerInTurn(int idOfPlayerInTurn) {
        GameState.idOfPlayerInTurn = idOfPlayerInTurn;
    }

    public static void setPhase(int idPhase){
        switch (idPhase){
            case 0:
                setAllFalse();
                loginPhase=true;
                break;
            case 1:
                setAllFalse();
                gettingStartedPhase=true;
                break;
            case 2:
                setAllFalse();
                turnPhase=true;
                break;
            case 3:
                setAllFalse();
                lastTurnPhase=true;
                break;
            case 4:
                setAllFalse();
                gameEndedPhase=true;
                break;
        }
    }

    //overload
    public static void setPhase(GamePhase phase){
        switch (phase){
            case LOGIN:
                setAllFalse();
                loginPhase=true;
                break;
            case GETTING_STARTED:
                setAllFalse();
                gettingStartedPhase=true;
                break;
            case TURN:
                setAllFalse();
                turnPhase=true;
                break;
            case LAST_TURN:
                setAllFalse();
                lastTurnPhase=true;
                break;
            case GAME_ENDED:
                setAllFalse();
                gameEndedPhase=true;
                break;
        }
    }

    private static void setAllFalse(){
        loginPhase=false;
        gettingStartedPhase=false;
        turnPhase=false;
        lastTurnPhase=false;
        gameEndedPhase=false;
    }




}
