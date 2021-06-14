package it.polimi.ingsw.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SwapDepotsController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label title;
    @FXML
    private ChoiceBox<String> firstdepot, seconddepot;
    private String[] depots = {"Depot 1", "Depot 2", "Depot 3"};
    @FXML
    ImageView img1, img2, img3, img4, img5, img6;
    Image coins = new Image("/img/punchboard/coin2.png");
    Image servants = new Image("/img/punchboard/servant2.png");
    Image shields = new Image("/img/punchboard/shield2.png");
    Image stones = new Image("/img/punchboard/stone2.png");



    public void fill(ActionEvent event) {
        img1.setImage(coins);
        img2.setImage(shields);
        img3.setImage(shields);
        img4.setImage(stones);
        img5.setImage(stones);
        img6.setImage(servants);

    }

    public void confirm(ActionEvent event) {
        String choice1 = firstdepot.getValue();
        String choice2 = seconddepot.getValue();
        if (choice1 == null || choice2 == null) { title.setText("You have to choose your depots first!"); }
        else if (choice1 == choice2) { title.setText("You have to choose different depots!"); }
        else { title.setText("Okay!"); }
    }

    public void backToActionTurn(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/fxml/turnaction.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstdepot.getItems().addAll(depots);
        seconddepot.getItems().addAll(depots);
    }
}
