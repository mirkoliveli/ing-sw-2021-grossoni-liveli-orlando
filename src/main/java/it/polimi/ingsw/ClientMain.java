package it.polimi.ingsw;

import it.polimi.ingsw.gui.hello_world;
import it.polimi.ingsw.networking.Client;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {

    //79.36.195.214

    public static void main(String[] args) throws IOException {

//        hello_world.main(args);

        Scanner scn = new Scanner(System.in);
        System.out.println("--- MAESTRI DEL RINASCIMENTO ---\n          W E L C O M E\n");
        System.out.print("Please type in server's ip address: ");
        String ip = scn.nextLine();
        System.out.print("\nPlease type in port number: ");
        int portNumber = Integer.parseInt(scn.nextLine());
        Client client = new Client(ip, portNumber);

        //login phase
        while (!client.isLoggedInGame()) {
            client.StartingConnection();
        }

        //waiting login of other players
        client.waitingPhase();

        //getting started phase.
        client.GettingStartedPhaseSection();

        //waiting the start of the game

        client.waitingForGameToStart();

        client.GameStarted();

        System.out.println("end_waiting");

    }

}
