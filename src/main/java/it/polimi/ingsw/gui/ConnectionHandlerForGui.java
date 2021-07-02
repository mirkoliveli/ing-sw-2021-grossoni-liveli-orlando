package it.polimi.ingsw.gui;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class ConnectionHandlerForGui {
    private static Socket connection;
    private static int idOfPlayer;
    private static InputStreamReader inputFromServer;
    private static BufferedReader bufferFromServer;
    private static OutputStreamWriter outputToServer;
    private static String username;
    private static Gson gson;
    private static int[] leaders;
    private static int resource;

    private static boolean isItMyTurn;
    private static boolean isMyTurnEnded;

    /**
     * generates connection with server and input and output streams
     * @param connection Socket connection
     * @throws IOException connection error
     */
    public static void setConnection(Socket connection) throws IOException {
        ConnectionHandlerForGui.connection = connection;
        inputFromServer=new InputStreamReader(connection.getInputStream());
        bufferFromServer=new BufferedReader(inputFromServer);
        outputToServer=new OutputStreamWriter(connection.getOutputStream());
        gson=new Gson();
    }

    public static void setIsMyTurnEnded(boolean isMyTurnEnded) {
        ConnectionHandlerForGui.isMyTurnEnded = isMyTurnEnded;
    }

    public static boolean IsMyTurnEnded() {
        return isMyTurnEnded;
    }

    public static void setLeaders(int[] leaders) {
        ConnectionHandlerForGui.leaders = leaders;
    }

    public static int[] getLeaders() {
        return leaders;
    }

    public static void setIdOfPlayer(int idOfPlayer) {
        ConnectionHandlerForGui.idOfPlayer = idOfPlayer;
    }

    public static void setUsername(String username) {
        ConnectionHandlerForGui.username = username;
    }

    public static Gson getGson() {
        return gson;
    }

    public static String getUsername() {
        return username;
    }

    public static Socket getConnection() {
        return connection;
    }

    public static int getIdOfPlayer() {
        return idOfPlayer;
    }

    public static void setIsItMyTurn(boolean isItMyTurn) {
        ConnectionHandlerForGui.isItMyTurn = isItMyTurn;
    }

    public static boolean IsItMyTurn() {
        return isItMyTurn;
    }

    /**
     * sends a message to the server, gets a string as input
     * @param message send a "raw" message to the server
     */
    public static void sendMessage(String message){
//        try {
//            outputToServer.write(message, 0, message.length());
//            outputToServer.flush();
//        } catch (Exception e) { System.out.println(e);
//            System.out.println("Lost connection from the server.\n"); }
        PrintWriter out = null;
        BufferedWriter bufferToServer = new BufferedWriter(outputToServer);
        out = new PrintWriter(bufferToServer, true);
        out.println(message);
    }

    public static synchronized void resetWait(){
        isItMyTurn=false;
        isMyTurnEnded=false;
    }

    /**
     * sends a message to the server, gets an object as input
     * @param object message to send (converted to json before sending)
     */
    public static void sendMessage(Object object){

//        try {
//            String message = gson.toJson(object);
//            outputToServer.write(message, 0, message.length());
//            outputToServer.flush();
//        } catch (Exception e) { System.out.println(e);
//            System.out.println("Lost connection from the server.\n"); }
        PrintWriter out = null;
        BufferedWriter bufferToServer = new BufferedWriter(outputToServer);
        out = new PrintWriter(bufferToServer, true);
        out.println(gson.toJson(object));
    }

    /**
     * adaptation of the connection method used for the cli, need to avoid reading some useless messages
     * @return message string
     * @throws IOException connection error with the server
     */
    public static String getMessage() throws IOException{
        String message=bufferFromServer.readLine();
        while (message.equals(null)){
            try {
                System.out.println("MESSAGE: Null\n");
                Thread.sleep(1000);
            } catch (Exception e) { System.out.println(e);}
        }
        while(message.equals("waiting other players...") || message.equals("Successful connection!") || message.equals("Everything was set up! Now please wait for the other players to finish!") ||
        message.equals("The game Will start soon...")){
            System.out.println(message);
            message=bufferFromServer.readLine();
        }
        if(message.equals("next")) {
            System.out.println(message);
            sendMessage("still connected");
            message=bufferFromServer.readLine();
            System.out.println(message);
        }
        return message;
    }

    public static int getResource() {
        return resource;
    }

    public static void setResource(int resource) {
        ConnectionHandlerForGui.resource = resource;
    }

    /**
     * used to get the real resource value from the output generated by the gui
     * @param resource
     */
    public static void getResourceFromString(String resource){
        switch(resource){
            case "Coin":
                ConnectionHandlerForGui.resource=1;
                break;
            case "Servant":
                ConnectionHandlerForGui.resource=2;
                break;
            case "Shield":
                ConnectionHandlerForGui.resource=3;
                break;
            case "Stone":
                ConnectionHandlerForGui.resource=4;
                break;
        }
    }

}
