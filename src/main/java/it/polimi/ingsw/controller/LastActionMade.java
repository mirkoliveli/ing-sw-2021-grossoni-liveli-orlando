package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.TypeOfAction;

public class LastActionMade {
    private static TypeOfAction action;
    private static String Username;
    private static int idOrZone; //contains the leader id, or the card id, or the pope zone activated
    private final int idOrzoneLocal;
    private final String usernameLocal;
    private final TypeOfAction actionLocal;


    public LastActionMade() {

        idOrzoneLocal = idOrZone;
        if (Username != null) usernameLocal = Username;
        else {
            Username = "System";
            usernameLocal = Username;
        }
        if (action != null) actionLocal = action;
        else {
            action = TypeOfAction.DEBUG_MODE;
            actionLocal = TypeOfAction.DEBUG_MODE;
        }
    }

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static TypeOfAction getAction() {
        return action;
    }

    public static void setAction(TypeOfAction action) {
        LastActionMade.action = action;
    }

    public static int getIdOrZone() {
        return idOrZone;
    }

    public static void setIdOrZone(int idOrZone) {
        LastActionMade.idOrZone = idOrZone;
    }

    public static void setAction(TypeOfAction action, String username, int id) {
        setAction(action);
        setIdOrZone(id);
        setUsername(username);
    }

    public static void PopeActivated(TypeOfAction action, int id) {
        setAction(action);
        setIdOrZone(id);
        setUsername("System");
    }

    public static boolean actionChanged(LastActionMade lastAction) {
        return !action.equals(lastAction.getActionLocal()) || !Username.equals(lastAction.getUsernameLocal()) || idOrZone != lastAction.getIdOrzoneLocal();
    }

    public int getIdOrzoneLocal() {
        return idOrzoneLocal;
    }

    public String getUsernameLocal() {
        return usernameLocal;
    }

    public TypeOfAction getActionLocal() {
        return actionLocal;
    }
}
