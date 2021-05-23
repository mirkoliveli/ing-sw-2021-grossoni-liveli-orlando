package it.polimi.ingsw.networking;

import com.google.gson.Gson;
import it.polimi.ingsw.cli.CommandLine;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.utils.StaticMethods;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
//import it.polimi.ingsw.message.*;

public class Client {
    private final String ip;
    private final int port;
    private boolean loggedInGame = false;
    private Socket connection = null;
    private String nickname;
    private InputStreamReader inputFromServer;
    private OutputStreamWriter outputToServer;
    private BufferedReader in = null;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    public boolean isLoggedInGame() {
        return loggedInGame;
    }

    public void setLoggedInGame(boolean loggedInGame) {
        this.loggedInGame = loggedInGame;
    }


    public void StartingConnection() throws IOException {

        Socket socket = null;

        try {
            //Apro un socket nel client
            socket = new Socket(ip, port);
            System.out.println("Client: started");
            System.out.println("Client Socket: " + socket);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host" + ip);
            System.exit(1);
        }
        this.connection = socket;
        this.inputFromServer = new InputStreamReader(connection.getInputStream());
        this.outputToServer = new OutputStreamWriter(connection.getOutputStream());
        this.in = new BufferedReader(inputFromServer);

        try {
            LoginRequest(connection);
        } catch (IOException e) {
            loggedInGame = false;
            System.out.println("connessione fallita, nuovo tentativo in corso...");
        }


    }

    /**
     * method that handles the login phase with the server
     *
     * @param socket socket needed to start the connection with the server
     * @throws IOException if at any point an error occurs an IOE is thrown
     */
    public void LoginRequest(Socket socket) throws IOException {
        //METODO PER RICHIEDERE LOGIN ALLA PARTITA
        Gson gson = new Gson();
        LoginMessage loginMessage = null;

        BufferedReader in = null;
        BufferedReader stdIn = null;
        PrintWriter out = null;

        //Creo uno stream dal server
        InputStreamReader inputFromServer = new InputStreamReader(socket.getInputStream());
        in = new BufferedReader(inputFromServer);


        OutputStreamWriter outputToServer = new OutputStreamWriter(socket.getOutputStream());
        BufferedWriter bufferToServer = new BufferedWriter(outputToServer);
        out = new PrintWriter(bufferToServer, true);

        stdIn = new BufferedReader(new InputStreamReader(System.in));


        //first message
        String messageFromServer = in.readLine();
        loginMessage = gson.fromJson(messageFromServer, LoginMessage.class);

        //qua va primo user login
        if (loginMessage.getNumOfPlayersInRoom() == 0 && loginMessage.isSuccessfulLogin()) {
            System.out.println("Welcome! You are the first player of this lobby Multiplayer\nChoose how many players will join this game (write a number between 2 and 4:");
            Scanner input = new Scanner(System.in);
            int value = -2345;
            while (true) {
                if (value != -2345) {
                    System.out.println("Not a valid number!");
                }
                value = input.nextInt();
                System.out.println(value);
                if (value == 2 || value == 3 || value == 4) break;
            }
            String name = input.nextLine();//serve sennò mi legge subito il primo spazio!!!!!!!!!!!!!!!!
            System.out.println("good, the server will wait " + value + " players before starting the game.");
            System.out.println("now please enter a username: ");
            name = input.nextLine();
            System.out.println("note that if the username is invalid a automatically generated one will be assigned to you");
            if (name.equals("")) {
                name = "player1";
            }
            this.nickname = name;
            FirstLoginMessage message = new FirstLoginMessage(name, value);
            out.println(message.CreateMessage());

        }

        //joino come giocatore non 1
        else {

            System.out.println("\nWelcome, this lobby has, at the moment, " + loginMessage.getNumOfPlayersInRoom() + " active players, so you will be the " + (loginMessage.getNumOfPlayersInRoom() + 1) + "player.");
            System.out.println("Please enter a username: ");
            Scanner input = new Scanner(System.in);
            String name;
            name = input.nextLine();
            System.out.println("note that if the username is invalid a automatically generated one will be assigned to you");
            if (name.equals("")) name = "player" + loginMessage.getNumOfPlayersInRoom() + 1;
            this.nickname = name;
            Message message = new Message(name);

            out.println(StaticMethods.objToJson(message));
        }

        System.out.println();

        try {
            messageFromServer = in.readLine();
        } catch (IOException e) {
            System.out.println("connection error!");
            throw e;
        }

        System.out.println(messageFromServer + "\n");

        //roba a random che va tolta
        while (true) {
            try {
                System.out.print("w");
                Thread.sleep(400);
                System.out.print("a");
                Thread.sleep(400);
                System.out.print("i");
                Thread.sleep(400);
                System.out.print("t");
                Thread.sleep(400);
                System.out.print("i");
                Thread.sleep(400);
                System.out.print("n");
                Thread.sleep(400);
                System.out.print("g");
                Thread.sleep(400);
                System.out.print(".");
                Thread.sleep(400);
                System.out.print(".");
                Thread.sleep(400);
                System.out.print(".");
                Thread.sleep(400);
                System.out.println("\n");
                Thread.sleep(400);
            } catch (InterruptedException e) {
                System.out.println("error");
            }
            break;
        }
        setLoggedInGame(true);
        System.out.println("login successful!");
    }


    /**
     * method used when players need to wait other players before continuing to play, needs to be synced with the server
     *
     * @throws IOException
     */
    public void waitingPhase() throws IOException {
        String message;
        do {
            try {
                message = messageFromServer();
            } catch (IOException e) {
                System.out.println("connection lost!");
                throw e;
            }
            if (!message.equals("next")) {
                System.out.println(message);
            }
        } while (!message.equals("next"));

        messageToServer("Still_Connected");

    }

    /**
     * receives a message from the server (string)
     *
     * @return String message
     * @throws IOException connection with server lost
     */
    public String messageFromServer() throws IOException {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw e;
        }

    }

    /**
     * sends a string message to the server
     *
     * @param message message sent
     * @throws IOException connection with server lost
     */
    public void messageToServer(String message) {
        PrintWriter out = null;
        BufferedWriter bufferToServer = new BufferedWriter(outputToServer);
        out = new PrintWriter(bufferToServer, true);
        out.println(message);
    }


    public void GettingStartedPhaseSection() {
        Gson gson = new Gson();
        System.out.println("\nit's now time to select the leader cards!");
        try {
            String message = messageFromServer();
            GettingStartedMessage messageFServer = gson.fromJson(message, GettingStartedMessage.class);
            //da sostituire con metodo serio
            System.out.println("4 cards will be shown in order, please refer to the 4 numbers on the bottom of the list when making the decision");
            CommandLine.printLeader(messageFServer.getCardID()[0]);
            CommandLine.printLeader(messageFServer.getCardID()[1]);
            CommandLine.printLeader(messageFServer.getCardID()[2]);
            CommandLine.printLeader(messageFServer.getCardID()[3]);
            System.out.println("the cards have the ids: " + messageFServer.getCardID()[0] + " " + messageFServer.getCardID()[1] + " " + messageFServer.getCardID()[2] + " " + messageFServer.getCardID()[3] + "\n");
            Scanner input = new Scanner(System.in);
            int id1 = 0;
            int id2 = 0;
            //scelta carte
            do {
                if (id1 != 0 || id2 != 0) System.out.println("not a valid ID! please retry: \n");
                input.reset();
                id1 = input.nextInt();
                id2 = input.nextInt();
            } while (id1 == id2 ||
                    (id1 != messageFServer.getCardID()[0] && id1 != messageFServer.getCardID()[1] && id1 != messageFServer.getCardID()[2] && id1 != messageFServer.getCardID()[3]) ||
                    (id2 != messageFServer.getCardID()[0] && id2 != messageFServer.getCardID()[1] && id2 != messageFServer.getCardID()[2] && id2 != messageFServer.getCardID()[3]));
            System.out.println("thanks for the selection!");

            //scelta risorse (se necessaria)
            if (messageFServer.getPlayerPosition() != 1) {
                int resource;
                int pos = messageFServer.getPlayerPosition();
                if (pos != 4) {
                    System.out.println("Because you are the " + pos + " player you can choose a resource as a starting bonus, insert a number between 1 and 4: ");
                    System.out.println("1 -> 1 coin");
                    System.out.println("2 -> 1 servant");
                    System.out.println("3 -> 1 shield");
                    System.out.println("4 -> 1 stone");
                    do {
                        resource = input.nextInt();
                        if (resource > 4 || resource < 1) System.out.println("not a valid resource, please retry:");
                    } while (resource > 4 || resource < 1);
                    System.out.println("the resource will be automatically placed in the third level of the storage, you can change that as soon as your turn starts");
                    GettingStartedMessage messageTOServer = new GettingStartedMessage(id1, id2, 1);
                    messageTOServer.setResources(resource, -1);
                    messageToServer(messageTOServer.getMessageAsString());
                } else {
                    int resource2;
                    System.out.println("Because you are the " + pos + "player you can choose 2 resources as a starting bonus, insert a number between 1 and 4 for the first resource: ");
                    System.out.println("1 -> 1 coin");
                    System.out.println("2 -> 1 servant");
                    System.out.println("3 -> 1 shield");
                    System.out.println("4 -> 1 stone");//va pulito
                    do {
                        resource = input.nextInt();
                        if (resource > 4 || resource < 1) System.out.println("not a valid resource, please retry:");
                    } while (resource > 4 || resource < 1);

                    System.out.println("now do the same for the second resource: "); //va pulito
                    do {
                        resource2 = input.nextInt();
                        if (resource2 > 4 || resource2 < 1) System.out.println("not a valid resource, please retry:");
                    } while (resource2 > 4 || resource2 < 1);
                    System.out.println("the resources will be automatically placed in the second and third level of the storage, you can change that as soon as your turn starts");
                    GettingStartedMessage messageTOServer = new GettingStartedMessage(id1, id2, 2);
                    messageTOServer.setResources(resource, resource2);
                    messageToServer(messageTOServer.getMessageAsString());
                }

            }
            //invio messaggio se non si scelgono risorse
            else {
                GettingStartedMessage messageTOServer = new GettingStartedMessage(id1, id2, 0);
                messageToServer(messageTOServer.getMessageAsString());
            }

            System.out.println(messageFromServer());

        } catch (IOException e) {
            System.out.println("You have disconnected!");
            System.exit(1);
        }

        System.out.println("The match will soon begin! You will be informed when your turn starts");

    }


    public void waitingForGameToStart() throws IOException {
        String message;
        do {
            try {
                message = messageFromServer();
            } catch (IOException e) {
                System.out.println("connection lost!");
                throw e;
            }
            if (message.charAt(0) != 'E' && message.charAt(0)!='Y') {
                System.out.println(message);
            }
        } while (message.charAt(0) != 'E' && message.charAt(0)!='Y');

        System.out.println(message);

        messageToServer("Still_Connected");

    }

    public void GameStarted(){
        String message="";
        do {
            try {
                message = messageFromServer();
            } catch (IOException e) {
                System.out.println("connection lost!");
            }
            if (message.contains("action")) {
                Gson gson = new Gson();
                ActionMessage starter=gson.fromJson(message, ActionMessage.class);

                //start turn

            }
            else{
                System.out.println("some problems with the server");
            }
        } while (!message.equals("Someone finished"));
    }

}

