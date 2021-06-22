package it.polimi.ingsw.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField user;
    @FXML
    private TextField ip;
    @FXML
    private TextField port;
    private int portnumber;
    @FXML
    private Label title;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane mainpane;


    public void login(ActionEvent event) throws Exception {
        String username = user.getText();
        String ipaddress = ip.getText(); //da gestire
        String chosenport = port.getText(); //da gestire, diventa l'intero portnumber nel blocco try

        try {
            portnumber = Integer.parseInt(chosenport);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/leaderchoice.fxml"));

            root = loader.load();
            LeaderChoiceController sceneController = loader.getController();
            sceneController.displayName(username);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (NumberFormatException e) { title.setText("Your port number should be a number!"); }


    }

    public void logout(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit confirmation");
        alert.setHeaderText("You have chosen to exit the game");
        alert.setContentText("Are you sure you want to quit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) mainpane.getScene().getWindow();
            System.out.println("Thank you for playing!");
            stage.close();
        }
    }



}
