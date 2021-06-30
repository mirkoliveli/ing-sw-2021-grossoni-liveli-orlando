package it.polimi.ingsw.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
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
    private boolean[] production = new boolean[6];
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
        if (prod0.isSelected()) { production[0] = true; }
        else { production[0] = false; }
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


    public void setLeaders(int l1) {
        prod4.setVisible(true);
        leader1.setImage(leadercards[l1]);
    }

    public void setLeaders (int l1, int l2) {
        prod4.setVisible(true);
        prod5.setVisible(true);
        leader1.setImage(leadercards[l1]);
        leader2.setImage(leadercards[l2]);
    }

    public void setProductionCards(int c1, int c2, int c3) {
        if (c1 != 0) {
            Image firstcard = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + c1 + "-1.png");
            card1.setImage(firstcard);
        }
        if (c2 != 0) {
            Image secondcard = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + c2 + "-1.png");
            card2.setImage(secondcard);
        }
        if (c3 != 0) {
            Image thirdcard = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + c3 + "-1.png");
            card3.setImage(thirdcard);
        }
    }

    //metodo provvisorio
    public void printProduction(ActionEvent event) {
        for (int i=0; i<6; i++) { System.out.println("Production " + i + ": " + production[i]); }
    }


}
