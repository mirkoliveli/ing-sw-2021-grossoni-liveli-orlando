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
    private CheckBox prod0, prod1, prod2, prod3;
    @FXML
    ImageView board;
    private boolean[] production = new boolean[6];

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
    }

    public void setImage(Image image) {
        board.setImage(image);
    }

    //metodo provvisorio
    public void printProduction(ActionEvent event) {
        for (int i=0; i<6; i++) { System.out.println("Production " + i + ": " + production[i]); }
    }


}
