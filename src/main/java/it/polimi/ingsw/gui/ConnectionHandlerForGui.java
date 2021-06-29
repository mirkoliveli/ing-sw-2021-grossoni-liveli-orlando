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

    public static void setConnection(Socket connection) throws IOException {
        ConnectionHandlerForGui.connection = connection;
        inputFromServer=new InputStreamReader(connection.getInputStream());
        bufferFromServer=new BufferedReader(inputFromServer);
        outputToServer=new OutputStreamWriter(connection.getOutputStream());
        gson=new Gson();
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

    public static void sendMessage(String message){
        PrintWriter out = null;
        BufferedWriter bufferToServer = new BufferedWriter(outputToServer);
        out = new PrintWriter(bufferToServer, true);
        out.println(message);
    }

    public static void sendMessage(Object object){
        PrintWriter out = null;
        BufferedWriter bufferToServer = new BufferedWriter(outputToServer);
        out = new PrintWriter(bufferToServer, true);
        out.println(gson.toJson(object));
    }

    public static String getMessage() throws IOException{
        String message=bufferFromServer.readLine();
        while(message.equals("waiting other players...") || message.equals("Successful connection!")){
            System.out.println("message");
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
}
