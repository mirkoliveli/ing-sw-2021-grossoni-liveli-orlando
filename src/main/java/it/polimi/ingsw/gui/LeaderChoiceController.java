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

public class LeaderChoiceController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label title;
    @FXML
    private ChoiceBox<String> firstleader, secondleader;
    private String[] leaders = {"Leader 1", "Leader 2", "Leader 3", "Leader 4"};
    @FXML
    ImageView img1, img2, img3, img4;


    public void fill(ActionEvent event) {
        Image test1 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-61-1.png");
        Image test2 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-62-1.png");
        Image test3 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-63-1.png");
        Image test4 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-64-1.png");
        img1.setImage(test1);
        img2.setImage(test2);
        img3.setImage(test3);
        img4.setImage(test4);
    }

    public void confirm(ActionEvent event) {
        String choice1 = firstleader.getValue();
        String choice2 = secondleader.getValue();
        if (choice1 == null || choice2 == null) { title.setText("You have to choose your leaders first!"); }
        else if (choice1 == choice2) { title.setText("You have to choose different leaders!"); }
        else {
            try {
               root = FXMLLoader.load(getClass().getResource("/fxml/turnaction.fxml"));
               stage = (Stage)((Node)event.getSource()).getScene().getWindow();
               scene = new Scene(root);
               stage.setScene(scene);
               stage.show();
            }
            catch (Exception e) { System.out.println(e); }
        }

    }

    public void displayName(String username) {
        title.setText("Welcome, " + username + "! Time to choose your leaders!");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstleader.getItems().addAll(leaders);
        secondleader.getItems().addAll(leaders);
    }
}