package it.polimi.ingsw;

import it.polimi.ingsw.networking.Client;
import it.polimi.ingsw.singlePlayer.controller.SinglePlayerHandler;

import java.io.IOException;
import java.util.Scanner;


public class ClientMain {


    public static void main(String[] args) throws IOException {
//        GuiMain.main(args);
        Scanner scn = new Scanner(System.in);
        boolean selection = spOrMp(scn);

        if (selection) {
            //MULTIPLAYER
            System.out.println("--- MAESTRI DEL RINASCIMENTO ---\n          W E L C O M E\n");
            System.out.print("Please type in server's ip address: ");
            String ip = scn.nextLine();
            System.out.print("\nPlease type in port number: ");
            int portNumber = Integer.parseInt(scn.nextLine());
            Client client = new Client(ip, portNumber);
            //login phase

            //
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

            System.out.println("game finished, exiting client");

            client.getConnection().close();
        } else {
            //SINGLEPLAYER
            SinglePlayerHandler singlePlayerGame = new SinglePlayerHandler(welcomeToSp(scn), false);
            singlePlayerGame.startMatch();
        }


    }

    /**
     * asks the client if he wants to play single player of multi player
     *
     * @param scn System.in scanner
     * @return true if multiplayer, false otherwise
     */
    public static boolean spOrMp(Scanner scn) {
        System.out.println("hello! please type 1 if you want to play a singlePlayer game, 2 for a multiplayer game:");
        String selection;
        int value;
        while (true) {
            selection = scn.nextLine();
            try {
                value = Integer.parseInt(selection);
                if (value != 1 && value != 2) {
                    throw new NumberFormatException();
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("please insert a valid number!");
            }
        }
        return value != 1;
    }

    /**
     * welcoming message for single player
     *
     * @param scn scanner for username input
     * @return name of the player, "player1" if the player presses enter directly (without typing anything)
     */
    public static String welcomeToSp(Scanner scn) {
        System.out.println("Hello and welcome to this game of MASTERS OF THE RENAISSANCE");
        System.out.println("please type your username:");
        String username = scn.nextLine();
        if (username.equals("")) username = "player1";
        return username;
    }


}
