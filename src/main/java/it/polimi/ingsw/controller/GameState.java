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
    private static int joinedPlayers;
    private static boolean[] DisconnectedPlayers;
    private static boolean[] hasRightToLastTurn;


    public GameState() {
        loginPhase = true;
        gettingStartedPhase = false;
        turnPhase = false;
        lastTurnPhase = false;
        gameEndedPhase = false;

        startingPlayer = 0;
        totalPlayersNumber = 0;
        idOfPlayerInTurn = 1;
        winnerId = 0;
        joinedPlayers = 0;
    }


    //------------------------------------------------------------------------------------------------------------------


    public synchronized static void increaseJoinedPlayers() {
        joinedPlayers++;
    }


    /**
     * method that changes the player in turn, if a player is set as disconnected then the player is skipped and the method recalls itself
     */
    public synchronized static void changeTurn() {
        if (idOfPlayerInTurn == totalPlayersNumber) idOfPlayerInTurn = 1;
        else {
            int i = idOfPlayerInTurn + 1;
            idOfPlayerInTurn = i;
        }
        if (DisconnectedPlayers[idOfPlayerInTurn - 1]) changeTurn();
    }


    public synchronized static int getIdOfPlayerInTurn() {
        return idOfPlayerInTurn;
    }

    /**
     * deprecated method :)
     *
     * @param idOfPlayerInTurn
     */
    private static void setIdOfPlayerInTurn(int idOfPlayerInTurn) {
        GameState.idOfPlayerInTurn = idOfPlayerInTurn;
    }

    public static int getStartingPlayer() {
        return startingPlayer;
    }

    public static void setStartingPlayer(int startingPlayer) {
        GameState.startingPlayer = startingPlayer;
    }

    public static int getTotalPlayersNumber() {
        return totalPlayersNumber;
    }

    public static void setTotalPlayersNumber(int totalPlayersNumber) {
        GameState.totalPlayersNumber = totalPlayersNumber;
        DisconnectedPlayers = new boolean[totalPlayersNumber];
    }

    public static int getWinnerId() {
        return winnerId;
    }

    public static void setWinnerId(int winnerId) {
        GameState.winnerId = winnerId;
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

    public static void setPhase(int idPhase) {
        switch (idPhase) {
            case 0:
                setAllFalse();
                loginPhase = true;
                break;
            case 1:
                setAllFalse();
                gettingStartedPhase = true;
                break;
            case 2:
                setAllFalse();
                turnPhase = true;
                break;
            case 3:
                setAllFalse();
                lastTurnPhase = true;
                break;
            case 4:
                setAllFalse();
                gameEndedPhase = true;
                break;
        }
    }

    //overload
    public static void setPhase(GamePhase phase) {
        switch (phase) {
            case LOGIN:
                setAllFalse();
                loginPhase = true;
                break;
            case GETTING_STARTED:
                setAllFalse();
                gettingStartedPhase = true;
                break;
            case TURN:
                setAllFalse();
                turnPhase = true;
                break;
            case LAST_TURN:
                setAllFalse();
                lastTurnPhase = true;
                break;
            case GAME_ENDED:
                setAllFalse();
                gameEndedPhase = true;
                break;
        }
    }

    private static void setAllFalse() {
        loginPhase = false;
        gettingStartedPhase = false;
        turnPhase = false;
        lastTurnPhase = false;
        gameEndedPhase = false;
    }

    public static int getJoinedPlayers() {
        return joinedPlayers;
    }

    public static void setJoinedPlayers(int joinedPlayers) {
        GameState.joinedPlayers = joinedPlayers;
    }

    /**
     * creates a boolean array stating which player has the right to a last turn.
     *
     * @param numOfPlayers       number of players
     * @param IDofWhoHasFinished ID of whom has reached first the end of a faithTrack or has acquired 7 development cards
     */
    public static void setHasRightToLastTurn(int numOfPlayers, int IDofWhoHasFinished) {
        hasRightToLastTurn = new boolean[numOfPlayers];
        for (int i = 0; i < hasRightToLastTurn.length; i++) {
            if (i >= IDofWhoHasFinished && !DisconnectedPlayers[i]) hasRightToLastTurn[i] = true;
        }
    }

    /**
     * returns the array stating which players can take another turn in the end game
     *
     * @return boolean array (size= numOfPlayers
     */
    public static boolean[] getHasRightToLastTurn() {
        return hasRightToLastTurn;
    }

    public static void setSpecificLastTurnPlayer(int idPlayer, boolean status) {
        if (hasRightToLastTurn != null) {
            hasRightToLastTurn[idPlayer - 1] = status;
        }
    }

    /**
     * set a player status to disconnected
     *
     * @param idPlayer player who disconnected
     */
    public static void playerDisconnected(int idPlayer) {
        if (DisconnectedPlayers != null) {
            try {
                DisconnectedPlayers[idPlayer - 1] = true;
            } catch (NullPointerException e) {
                System.out.println("weird bug when disconnecting player");
            }
        }
    }
}
