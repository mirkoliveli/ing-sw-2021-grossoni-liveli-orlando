package it.polimi.ingsw.networking;

import java.io.*;
import java.net.Socket;

public class ThreadedServer extends Thread {


    protected Socket clientSocket;
    protected int idPlayer;

    public ThreadedServer(Socket clientSocket, int id) {
        this.clientSocket = clientSocket;
        this.idPlayer = id;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void run() {


        BufferedReader inFromClient = null;
        PrintWriter outToTheClient = null;
        while (true) {

            //METORO CHE CONTROLLA SE È IL TURNO DEL GIOCATORE
//            CheckTurn();

            try {
                // Per ricevere una stringa aprire un input stream reader
                InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                inFromClient = new BufferedReader(isr);

                OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
                BufferedWriter bw = new BufferedWriter(osw);
                outToTheClient = new PrintWriter(bw, true);
                String str = inFromClient.readLine();

                if ((str == null) || str.equalsIgnoreCase("QUIT")) {
                    System.out.println("Closing " + clientSocket);
                    break;
                }

                //-----------------TEST DELLA CONNESIONE -> REPLICA DEL TESTO RICEVUTO------------------------//
                System.out.println("ANSWERING TO" + clientSocket.getInetAddress() + ":\n" + str);
                outToTheClient.println(str);
                StarterManager(str);
                str = inFromClient.readLine();


//                TurnToTheNext();

            } catch (IOException e) {
                return;
            }
        }
        try {
            clientSocket.close();
            outToTheClient.close();
            inFromClient.close();
            System.out.println("CLOSED!");
        } catch (IOException e) {
            System.out.println("ESPLOSO! KABOOM\n");
        }
    }

    public void StarterManager(String in) {
        String[] splitted = in.split("%;%");
        if (splitted.length > 0) {
            switch (splitted[0]) {
                case "0":
                    //0 crea una nuova partita
                    break;
                case "1":

                    //1 si unisce ad una partita preesistente
                    break;
                default:
                    //caso default
                    break;
            }
        }
    }


//
//    public void CheckTurn() {
//        while (Server.getTurno() != idPlayer) {
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                System.out.println("Eh vabbè!1!");
//            }
//        }
//    }
//
//    public void TurnToTheNext () {
//        Server.setTurno(idPlayer+1);
//    }
}




