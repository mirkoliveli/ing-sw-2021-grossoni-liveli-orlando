package it.polimi.ingsw;

import it.polimi.ingsw.networking.Client;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        Scanner scn = new Scanner(System.in);
        System.out.println("--- MAESTRI DEL RINASCIMENTO ---\n          W E L C O M E\n");
        System.out.print("Please type in server's ip address: ");
        String ip = scn.nextLine();
        System.out.print("\nPlease type in port number: ");
        int portNumber = Integer.parseInt(scn.nextLine());
        Client client = new Client(ip, portNumber);
        client.StartingConnection();
    }

}