package it.polimi.ingsw.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.ActionMessage;
import it.polimi.ingsw.messages.TypeOfAction;
import it.polimi.ingsw.model.TypeOfResource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ProductionController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private CheckBox prod0, prod1, prod2, prod3, prod4, prod5;
    @FXML
    private ImageView leader1, leader2;
    @FXML
    private ImageView card1, card2, card3;
    @FXML
    private Label baselabel;
    @FXML
    private ChoiceBox<String> base1, base2, base3, leaderproduction1, leaderproduction2;
    private final ArrayList<String> resources = new ArrayList<String>(Arrays.asList("Coin","Servant","Shield","Stone"));
    private boolean[] production = new boolean[6];
    private String[] res = new String[3];
    // 0: costo shields; 1: costo servants; 2: costo stones; 3: costo coins
    private Image[] leadercards = {
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-61-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-62-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-63-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-64-1.png")
    };

    public void backToActionTurn(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/fxml/turnaction.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
        }
        else {
            production[0] = false;
            baselabel.setVisible(false);
            base1.setVisible(false);
            base2.setVisible(false);
            base3.setVisible(false); }
        if (prod1.isSelected()) { production[1] = true; }
        else { production[1] = false; }
        if (prod2.isSelected()) { production[2] = true; }
        else { production[2] = false; }
        if (prod3.isSelected()) { production[3] = true; }
        else { production[3] = false; }
        if (prod4.isSelected()) { production[4] = true; }
        else { production[4] = false; }
        if (prod5.isSelected()) { production[5] = true; }
        else { production[5] = false; }
    }

    public void setLeaders (int l1, int l2, boolean b1, boolean b2) {
        if (b1 && l1 > 60) {
            prod4.setVisible(true);
            leaderproduction1.setVisible(true);
            leader1.setImage(leadercards[l1-61]);
        }
        if (b2 && l2 > 60) {
            prod5.setVisible(true);
            leaderproduction2.setVisible(true);
            leader2.setImage(leadercards[l2-61]);
        }
    }

    public void setProductionCards(int[] activatable) {
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

        /*
        if (production[0]) {
            res[0] = base1.getItems();
            res[1] = base2.getItems();
            res[2] = base3.getItems();
        }

        */



        ActionMessage action=new ActionMessage(TypeOfAction.ACTIVATE_PRODUCTION);
        // mettere a posto qui
        // action.ActivateProduction(production, ???);
        Gson gson=new Gson();
        ConnectionHandlerForGui.sendMessage(gson.toJson(action));




        // stampa per controllo
        for (int i=0; i<6; i++) { System.out.println("Production " + i + ": " + production[i]); }
    }


}
