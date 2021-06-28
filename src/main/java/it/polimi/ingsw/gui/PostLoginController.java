package it.polimi.ingsw.gui;

import it.polimi.ingsw.messages.FirstLoginMessage;
import it.polimi.ingsw.messages.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;


public class PostLoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean firstPlayer;
    @FXML
    private Label label;
    @FXML
    private ChoiceBox<String> choice;
    private final ArrayList<String> numberofplayers = new ArrayList<String>(Arrays.asList("1","2","3","4"));
    private final ArrayList<String> resources = new ArrayList<String>(Arrays.asList("Coin","Servant","Shield","Stone"));

    public void postlogin(int player, String username) {

        try {

            if (player == 1) {
                label.setText("Hello " + username + ", select how many players this game will have");
                ObservableList<String> list = FXCollections.observableArrayList(numberofplayers);
                choice.setItems(list);
                firstPlayer = true;
            }
            else {
                // per ora una sola risorsa bonus, aggiungere la seconda per il giocatore 4
                label.setText("Hello " + username + ", you are the player no. " + player + ". Select your bonus resources:");
                ObservableList<String> list = FXCollections.observableArrayList(resources);
                choice.setItems(list);
            }

        }
        catch (Exception e) { System.out.println(e); }
    }

    public void confirm(ActionEvent event) {
        if (firstPlayer) {
            String p = choice.getValue();
            int players = Integer.parseInt(p);
            FirstLoginMessage message=new FirstLoginMessage(ConnectionHandlerForGui.getUsername(), players);
            ConnectionHandlerForGui.sendMessage(message);

        }
        else {
            //COMUNICARE RISORSE A SERVER
        }
        try {
            ConnectionHandlerForGui.sendMessage(new Message(ConnectionHandlerForGui.getUsername()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/leaderchoice.fxml"));
            root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) { System.out.println(e); }

    }

}
