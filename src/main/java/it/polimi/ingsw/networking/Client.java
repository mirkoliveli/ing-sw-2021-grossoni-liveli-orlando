package it.polimi.ingsw.networking;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
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

        BufferedReader in = null;
        BufferedReader stdIn = null;
        PrintWriter out = null;

        //Creo uno stream di input per prendere le stringhe da tastiera
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        in = new BufferedReader(isr);

        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        BufferedWriter bw = new BufferedWriter(osw);
        out = new PrintWriter(bw, true);

        stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        System.out.println("TYPE:\n[0] Create new game\n[1] Enter into existing game\n[2] Quit game");
        userInput = stdIn.readLine();
        switch (userInput){
            case "0":
                //0 invia messaggio per creare una nuova partita
                break;
            case "1":
                //1 invia messaggio per unirsi ad una partita preesistente
                break;
            case "2":
                // Esci dal gioco
                break;
            default:
                //caso default
                break;
        }
        out.println(userInput);
        System.out.println("Answer from server: " + in.readLine());
        in.close();
        out.close();
        stdIn.close();
        setLoggedInGame(true);
    }
}

