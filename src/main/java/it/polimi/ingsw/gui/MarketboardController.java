package it.polimi.ingsw.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.messages.ActionMessage;
import it.polimi.ingsw.messages.TypeOfAction;
import it.polimi.ingsw.messages.chooseDepotMessage;
import it.polimi.ingsw.utils.StaticMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;


public class MarketboardController extends AnchorPane {

    Color red = new Color(1.0, 0.0, 0.0, 1.0); //FAITH POINTS
    Color yellow = new Color(1.0, 1.0, 0.0, 1.0); //COINS
    Color white = new Color(1.0, 1.0, 1.0, 1.0); //NULL
    Color blue = new Color(0.0, 1.0, 1.0, 1.0); //SHIELDS
    Color grey = new Color(0.5, 0.5, 0.5, 1.0); //STONES
    Color purple = new Color(1.0, 0.0, 1.0, 1.0); //SERVANTS
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Circle c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13;
    @FXML
    private RadioButton row1, row2, row3, column1, column2, column3, column4;
    @FXML
    private AnchorPane pane; //850x621
    @FXML
    private CheckBox leaderbonus1, leaderbonus2;
    private int line;
    private boolean rowcolumn;

    //prima di visualizzare choosedepot settare immagini in base al depot e lanciare setResource
    @FXML
    private ImageView img1, img2, img3, img4, img5, img6;
    @FXML
    private RadioButton depot1, depot2, depot3, depotdisc;
    @FXML
    private Label resource;
    private int depot;


    public void backToActionTurn(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/fxml/turnaction.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void fillMarbleColors(int[][] colors, int slide) {
        c1.setFill(intToColor(colors[0][0]));
        c2.setFill(intToColor(colors[0][1]));
        c3.setFill(intToColor(colors[0][2]));
        c4.setFill(intToColor(colors[0][3]));
        c5.setFill(intToColor(colors[1][0]));
        c6.setFill(intToColor(colors[1][1]));
        c7.setFill(intToColor(colors[1][2]));
        c8.setFill(intToColor(colors[1][3]));
        c9.setFill(intToColor(colors[2][0]));
        c10.setFill(intToColor(colors[2][1]));
        c11.setFill(intToColor(colors[2][2]));
        c12.setFill(intToColor(colors[2][3]));
        if (c13 != null) {
            c13.setFill(intToColor(slide));
        }
    }

    public Color intToColor(int n) {
        switch (n) {
            case 0:
                return yellow;
            case 1:
                return purple;
            case 2:
                return blue;
            case 3:
                return grey;
            case 4:
                return red;
            default:
                return white;
        }
    }


    public void getChoice(ActionEvent event) {
        if (row1.isSelected()) {
            line = 1;
            rowcolumn = false;
        }
        if (row2.isSelected()) {
            line = 2;
            rowcolumn = false;
        }
        if (row3.isSelected()) {
            line = 3;
            rowcolumn = false;
        }
        if (column1.isSelected()) {
            line = 1;
            rowcolumn = true;
        }
        if (column2.isSelected()) {
            line = 2;
            rowcolumn = true;
        }
        if (column3.isSelected()) {
            line = 3;
            rowcolumn = true;
        }
        if (column4.isSelected()) {
            line = 4;
            rowcolumn = true;
        }
    }

    public void exitWindow(ActionEvent event) {
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void setDepot(ActionEvent event) {
        if (depot1.isSelected()) {
            depot = 1;
        }
        if (depot2.isSelected()) {
            depot = 2;
        }
        if (depot3.isSelected()) {
            depot = 3;
        }
        if (depotdisc.isSelected()) {
            depot = 4;
        }
    }


    public void confirm(ActionEvent event) throws Exception {
        boolean stopSending = false;
        ActionMessage action = new ActionMessage(TypeOfAction.GO_TO_MARKET);
        action.MarbleMarketAction(line, rowcolumn);
        Gson gson = new Gson();
        ConnectionHandlerForGui.sendMessage(gson.toJson(action));
        String answerFromServer = ConnectionHandlerForGui.getMessage();
        try {
            if (!answerFromServer.contains("resourceStillToBeStored") && answerFromServer.contains("true")) {
                System.out.println("You can receive additional resources from the market thanks to your leaders!");
                if (leaderbonus1.isSelected()) ConnectionHandlerForGui.sendMessage(1);
                if (leaderbonus2.isSelected() && !leaderbonus1.isSelected()) ConnectionHandlerForGui.sendMessage(2);
                answerFromServer = ConnectionHandlerForGui.getMessage();
            }
            while (answerFromServer.contains("resourceStillToBeStored")) {
                System.out.println("setting storage atumatically");
                chooseDepotMessage subMessage = gson.fromJson(answerFromServer, chooseDepotMessage.class);
                boolean[] possibleChoices = subMessage.getDepotStateOfEmptyness().clone();
                for (int i = 0; i < 3; i++) {
                    if (possibleChoices[2 - i] && !stopSending) {
                        ConnectionHandlerForGui.sendMessage(3 - i);
                        System.out.println("waiting mex from server");
                        System.out.println("mex arrived");
                        stopSending = true;
                    }
                }
                stopSending = false;
                answerFromServer = ConnectionHandlerForGui.getMessage();
            }

            System.out.println("azione finita");
            ConnectionHandlerForGui.getMessage();
            answerFromServer = ConnectionHandlerForGui.getMessage();
            GameStatusUpdate status = ConnectionHandlerForGui.getGson().fromJson(answerFromServer, GameStatusUpdate.class);
            LastGameStatus.update(status);


                /*
                da finire di gestire

                printDepotChoice(subMessage.getDepotStateOfEmptyness(), subMessage.getResourceStillToBeStored());
                System.out.println("Select 0 if you wish to discard the resources, otherwise select the level where you wish them to be stored: ");
                do {
                    inputFromC = scanner.nextLine();
                    ans=checkForDepotChoice(inputFromC, subMessage.getDepotStateOfEmptyness());
                }while(ans<0);
                client.messageToServer(String.valueOf(ans));
                answerFromServer= client.messageFromServer();

                 */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/turnaction.fxml"));
            root = loader.load();


            TurnController controller = loader.getController();
            controller.actionDone();


            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            System.out.println(e);
        }

        //try {
        //    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/turnaction.fxml"));
        //    root = loader.load();
        //    TurnController controller = loader.getController();
        //    controller.actionDone();
        //    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //    scene = new Scene(root);
        //    stage.setScene(scene);
        //    stage.show();
        //}
        //catch (Exception e) { System.out.println(e); }
    }

    public void setBonus(int l1, int l2, boolean b1, boolean b2) {
        if (l1 > 56 && l1 < 61 && b1) {
            leaderbonus1.setDisable(false);
        }
        if (l2 > 56 && l2 < 61 && b2) {
            leaderbonus2.setDisable(false);
        }
    }

    public void setDepotChoice(boolean[] depots, int[] toStore) {
        resource.setText(toStore[1] + "x " + StaticMethods.IntToTypeOfResource(toStore[0]));
        depot1.setDisable(!depots[0]);
        depot2.setDisable(!depots[1]);
        depot3.setDisable(!depots[2]);
    }


    public void confirmDepot(ActionEvent event) {
        System.out.println("sout prima di sendmessage");
        ConnectionHandlerForGui.sendMessage(depot);
        System.out.println("sout dopo message");
        try {
            System.out.println("sout nel blocco try");
            String answerFromServer = ConnectionHandlerForGui.getMessage();
            System.out.println("messaggio arrivato dopo depot placement: " + answerFromServer);
            if (answerFromServer.contains("resourceStillToBeStored")) {
                chooseDepotMessage subMessage = ConnectionHandlerForGui.getGson().fromJson(answerFromServer, chooseDepotMessage.class);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/turnaction.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                TurnController controller = loader.getController();
                controller.goToChooseDepot(subMessage.getDepotStateOfEmptyness(), subMessage.getResourceStillToBeStored());
                stage.setScene(scene);
                stage.show();
            } else {
                ConnectionHandlerForGui.getMessage();
                answerFromServer = ConnectionHandlerForGui.getMessage();
                GameStatusUpdate status = ConnectionHandlerForGui.getGson().fromJson(answerFromServer, GameStatusUpdate.class);
                LastGameStatus.update(status);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/turnaction.fxml"));
                root = loader.load();
                TurnController controller = loader.getController();
                controller.actionDone();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            System.out.println("disconnected, quitting");
            System.exit(1);
        }
    }


}
