package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.messages.ActionMessage;
import it.polimi.ingsw.messages.ProductionActionMessage;
import it.polimi.ingsw.messages.TypeOfAction;
import it.polimi.ingsw.utils.StaticMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ProductionController {
    private final ArrayList<String> resources = new ArrayList<String>(Arrays.asList("Coin", "Servant", "Shield", "Stone"));
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private CheckBox prod0, prod1, prod2, prod3, prod4, prod5;
    @FXML
    private ImageView leader1, leader2, card1, card2, card3;
    //@FXML
    //private ImageView card1, card2, card3;
    @FXML
    private Label baselabel;
    @FXML
    private ChoiceBox<String> base1, base2, base3, leaderproduction1, leaderproduction2;
    private final boolean[] production = new boolean[6];
    private final String[] res = new String[3];


    public void backToActionTurn(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/fxml/turnaction.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void selectProd(ActionEvent event) throws Exception {
        if (prod0.isSelected()) {
            production[0] = true;
            baselabel.setVisible(true);
            base1.setVisible(true);
            base2.setVisible(true);
            base3.setVisible(true);
        } else {
            production[0] = false;
            baselabel.setVisible(false);
            base1.setVisible(false);
            base2.setVisible(false);
            base3.setVisible(false);
        }
        production[1] = prod1.isSelected();
        production[2] = prod2.isSelected();
        production[3] = prod3.isSelected();
        production[4] = prod4.isSelected();
        production[5] = prod5.isSelected();
    }


    public void setProductionCards(int[] activatable, int l1, int l2, boolean b1, boolean b2) {
        if (activatable[0] != 0) {
            Image firstcard = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + activatable[0] + "-1.png");
            card1.setImage(firstcard);
        }
        if (activatable[1] != 0) {
            Image secondcard = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + activatable[1] + "-1.png");
            card2.setImage(secondcard);
        }
        if (activatable[2] != 0) {
            Image thirdcard = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + activatable[2] + "-1.png");
            card3.setImage(thirdcard);
        }
        if (b1 && l1 > 60) {
            prod4.setVisible(true);
            leaderproduction1.setVisible(true);
            Image first = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + l1 + "-1.png");
            leader1.setImage(first);
        }
        if (b2 && l2 > 60) {
            prod5.setVisible(true);
            leaderproduction2.setVisible(true);
            Image second = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + l2 + "-1.png");
            leader2.setImage(second);
        }
    }

    public void fillBaseProd() {
        ObservableList<String> list = FXCollections.observableArrayList(resources);
        base1.setItems(list);
        base2.setItems(list);
        base3.setItems(list);
        leaderproduction1.setItems(list);
        leaderproduction2.setItems(list);
    }

    //metodo provvisorio
    public void printProduction(ActionEvent event) {
        boolean actionDone = false;
        /*
        if (production[0]) {
            res[0] = base1.getItems();
            res[1] = base2.getItems();
            res[2] = base3.getItems();
        }

        */
        if (StaticMethods.AreAnyTrue(production)) {
            ActionMessage action = new ActionMessage(TypeOfAction.ACTIVATE_PRODUCTION);
            ConnectionHandlerForGui.sendMessage(action);
            try {
                String messageFromServer = ConnectionHandlerForGui.getMessage();
                if (!messageFromServer.contains("UNAVAILABLE_ACTION")) {
                    ProductionActionMessage messageFS = ConnectionHandlerForGui.getGson().fromJson(messageFromServer, ProductionActionMessage.class);
                    boolean[] productionsAvailable = ConnectionHandlerForGui.getGson().fromJson(messageFS.getObjectToSend(), boolean[].class);
                    int[] vectorSelections = {-1, -1, -1, -1, -1, -1, -1, -1};
                    for (int i = 1; i < 4; i++) {
                        if (production[i] && productionsAvailable[i]) vectorSelections[i + 1] = 1;
                    }
                    if (production[4] && productionsAvailable[4] && leaderproduction1.getValue() != null) {
                        vectorSelections[5] = ConnectionHandlerForGui.fromStringToIntResource(leaderproduction1.getValue());
                    }
                    if (production[5] && productionsAvailable[5] && leaderproduction2.getValue() != null) {
                        vectorSelections[6] = ConnectionHandlerForGui.fromStringToIntResource(leaderproduction2.getValue());
                    }
                    if (production[0] && productionsAvailable[0] && base1.getValue() != null && base2.getValue() != null && base3.getValue() != null) {
                        vectorSelections[0] = ConnectionHandlerForGui.fromStringToIntResource(base1.getValue());
                        vectorSelections[1] = ConnectionHandlerForGui.fromStringToIntResource(base2.getValue());
                        vectorSelections[7] = ConnectionHandlerForGui.fromStringToIntResource(base3.getValue());
                    }
                    ConnectionHandlerForGui.sendMessage(vectorSelections);

                    messageFromServer = ConnectionHandlerForGui.getMessage();
                    if (messageFromServer.contains("UNAVAILABLE_ACTION")) System.out.println("impossibile produrre");
                    if (messageFromServer.contains("ACTION_SUCCESS")) {
                        actionDone = true;
                        System.out.println("produzione effettuata");
                    }
                }

                messageFromServer = ConnectionHandlerForGui.getMessage();
                GameStatusUpdate status = ConnectionHandlerForGui.getGson().fromJson(messageFromServer, GameStatusUpdate.class);
                LastGameStatus.update(status);
            } catch (IOException e) {
                System.out.println("disconnected, quitting...");
                System.exit(1);
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/turnaction.fxml"));
            root = loader.load();

            if (actionDone) {
                TurnController controller = loader.getController();
                controller.actionDone();
            }

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("error while loading new window");
        }
    }


}
