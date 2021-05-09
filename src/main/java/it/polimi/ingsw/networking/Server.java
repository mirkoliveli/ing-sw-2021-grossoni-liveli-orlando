package it.polimi.ingsw.networking;
//import it.polimi.ingsw.message.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;

    public Server(int PORT) throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void execute() throws IOException {

        System.out.println("Server: started ");
        System.out.println("Server Socket: " + serverSocket);

        Socket clientSocket = null;
        BufferedReader in_fromClient = null;
        PrintWriter out_toClient = null;


        try {

//                Per inviare/ricevere un oggetto, creare un stream input di oggetti;
//                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
//                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
//                InputStream inputStream = clientSocket.getInputStream();

            while (true) {
//                in questo modo la connessione non è persistente, si accetta un solo client alla volta
//                ad ogni ciclo viene aperto un socket, gestito e chiuso
//                tutte le richieste di servizio vengono scodate nell'ordine in cui vengono effettuate (FIFO)
//                non necessari thread per la gestione. Possibilità di implementazione

                //Accetta la prima connessione in coda
                clientSocket = serverSocket.accept();
                System.out.println("Connection accepted: " + clientSocket);

//                 Per ricevere una stringa aprire un input stream reader
                InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                in_fromClient = new BufferedReader(isr);

//                 Per inviare una stringa aprire un output stream writer
                OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
                BufferedWriter bw = new BufferedWriter(osw);
                out_toClient = new PrintWriter(bw, true);


/*                Codice usato per testare la connessiona WAN
                Server -> Legge la stringa dallo stream di ingresso
                La ripete sullo stream di uscita
*/

                //--------------SOSTITUIRE CON L'INVIO DI MESSAGGIO APPROPRIATO---------------------------------//
                String str = in_fromClient.readLine();
                System.out.println("ANSWERING: " + str);
                out_toClient.println(str);

//                "CLOSE_SERVER" per chiudere il processo server
//                if (str.equals("CLOSE_SERVER")) break;
//
//                Chiudo tutti gli stream, che verranno riaperti nel nuovo ciclo
//                Consente di scodare le richieste di accesso e servizio del server
                clientSocket.close();
                in_fromClient.close();
                out_toClient.close();
            }
        } catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
        }
        System.out.println("Server: closing...");
        out_toClient.close();
        in_fromClient.close();
        clientSocket.close();
        serverSocket.close();
    }


    public void GestisciComando(String in) {
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
}
