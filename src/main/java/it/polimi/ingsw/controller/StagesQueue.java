package it.polimi.ingsw.controller;


public class StagesQueue {
    private static boolean SomeoneLoggingIn;


    public StagesQueue(){
        SomeoneLoggingIn=false;
    }

    public static boolean isSomeoneLoggingIn() {
        return SomeoneLoggingIn;
    }

    public static void setSomeoneLoggingIn(boolean someoneLoggingIn) {
        SomeoneLoggingIn = someoneLoggingIn;
    }
}
