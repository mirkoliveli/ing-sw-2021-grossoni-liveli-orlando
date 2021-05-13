package it.polimi.ingsw.networking;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.FirstLoginMessage;
import it.polimi.ingsw.messages.LoginMessage;
import it.polimi.ingsw.messages.Message;
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

        while (!isLoggedInGame()) {
            LoginRequest(socket);
        }
        System.out.println("Client: closing...");
        socket.close();
        System.out.println("Closed!");
    }

    public void LoginRequest(Socket socket) throws IOException {
        //METODO PER RICHIEDERE LOGIN ALLA PARTITA
        Gson gson=new Gson();
        LoginMessage loginMessage=null;
        BufferedReader in = null;
        BufferedReader stdIn = null;
        PrintWriter out = null;

        //Creo uno stream di input per prendere le stringhe da tastiera
        InputStreamReader inputFromServer = new InputStreamReader(socket.getInputStream());
        in = new BufferedReader(inputFromServer);


        OutputStreamWriter outputToServer = new OutputStreamWriter(socket.getOutputStream());
        BufferedWriter bufferToServer = new BufferedWriter(outputToServer);
        out = new PrintWriter(bufferToServer, true);

        stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        String messageFromServer=in.readLine();
        loginMessage=gson.fromJson(messageFromServer, LoginMessage.class);

        //qua va primo user login
        if(loginMessage.getNumOfPlayersInRoom()==0 && loginMessage.isSuccessfulLogin()){
            System.out.println("Benvenuto! sei il primo giocatore di questa lobby Multiplayer\nScegli quanti giocatori parteciperanno alla partita digitando un numero da 2 a 4:");
            Scanner input=new Scanner(System.in);
            int value=-2345;
            while(true){
                if(value!=-2345){ System.out.println("numero non valido!");}
                value=input.nextInt();
                System.out.println(value);
                if(value==2 || value==3 || value==4) break;
            }
            String name=input.nextLine();//serve sennò mi legge subito il primo spazio!!!!!!!!!!!!!!!!
            System.out.println("bene, il server attenderà l'ingresso di " + value +" giocatori prima di iniziare la partita.");
            System.out.println("inserisci il tuo nickname desiderato prima di entrare in attesa dei giocatori: ");
            name=input.nextLine();
            System.out.println("nota che se il nome inserito non è valido ti verrà assegnato il nome giocatore1 automaticamente");
            if(name.equals(""))name="giocatore1";
            FirstLoginMessage message=new FirstLoginMessage(name, value);
            out.println(message.CreateMessage());

        }

        //joino come giocatore non 1
        else{

            System.out.println("\nBenvenuto, questa lobby al momento ha: " + loginMessage.getNumOfPlayersInRoom() + " quindi tu sarai il giocatore numero " + loginMessage.getNumOfPlayersInRoom()+1);
            System.out.println("per favore entra un nickname con cui vorrai giocare: ");
            Scanner input=new Scanner(System.in);
            String name;
            name=input.nextLine();
            System.out.println("nota che se il nome inserito non è valido ti verrà assegnato il nome " + loginMessage.getNumOfPlayersInRoom()+1 +" giocatore automaticamente");
            if(name.equals(""))name="giocatore" + loginMessage.getNumOfPlayersInRoom()+1;
            Message message=new Message(name);

            out.println(StaticMethods.objToJson(message));
        }

        System.out.println();

        //roba a random che va tolta
        while(true){
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
            }catch(InterruptedException e){
                System.out.println("error");
            }
            break;
        }


        System.out.println("TYPE:\n[0] Create new game\n[1] Enter into existing game\n[2] Quit game");
        userInput = stdIn.readLine();

        out.println(userInput);
        System.out.println("Answer from server: " + in.readLine());
        in.close();
        out.close();
        stdIn.close();
        setLoggedInGame(true);
    }
}

