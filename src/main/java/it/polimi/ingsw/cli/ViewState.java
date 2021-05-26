package it.polimi.ingsw.cli;

public class ViewState {
    private static boolean turn_ended;
    private static boolean action_aborted;
    private static boolean mainActionCompleted;


    public static boolean isAction_aborted() {
        return action_aborted;
    }

    public static boolean isTurn_ended() {
        return turn_ended;
    }

    public static void setAction_aborted(boolean action_aborted) {
        ViewState.action_aborted = action_aborted;
    }

    public static void setTurn_ended(boolean turn_ended) {
        ViewState.turn_ended = turn_ended;
    }

    public static boolean isMainActionCompleted() {
        return mainActionCompleted;
    }

    public static void setMainActionCompleted(boolean mainActionCompleted) {
        ViewState.mainActionCompleted = mainActionCompleted;
    }

    public static void resetTurn(){
        turn_ended=false;
        action_aborted=false;
        mainActionCompleted=false;
    }

}
