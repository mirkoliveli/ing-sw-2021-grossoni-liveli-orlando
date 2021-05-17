package it.polimi.ingsw.controller;

/**
 * class that manages the order of operations and creates some utils for making a queue
 * e.g. SomeoneLoggingIn is used as someone is joining the game, so that the server will wait before accepting a new player
 */
public class StagesQueue {
    private static boolean SomeoneLoggingIn;
    private static boolean SomeoneCrashed = false;

    public StagesQueue() {
        SomeoneLoggingIn = false;
    }

    public static boolean isSomeoneLoggingIn() {
        return SomeoneLoggingIn;
    }

    public static void setSomeoneLoggingIn(boolean someoneLoggingIn) {
        SomeoneLoggingIn = someoneLoggingIn;
    }

    public static boolean isSomeoneCrashed() {
        return SomeoneCrashed;
    }

    public static void setSomeoneCrashed(boolean someoneCrashed) {
        SomeoneCrashed = someoneCrashed;
    }
}
