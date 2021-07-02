package it.polimi.ingsw.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameStatusUpdate;
import it.polimi.ingsw.messages.ActionMessage;
import it.polimi.ingsw.messages.TypeOfAction;
import it.polimi.ingsw.networking.Client;
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

import java.io.IOException;
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
    private String[] depots = {"1", "2", "3"};
    @FXML
    ImageView img1, img2, img3, img4, img5, img6;
    Image coins = new Image("/img/punchboard/coin2.png");
    Image servants = new Image("/img/punchboard/servant2.png");
    Image shields = new Image("/img/punchboard/shield2.png");
    Image stones = new Image("/img/punchboard/stone2.png");
    private boolean extra;



    public void setStorage(int[][] sto) {
        if (sto[0][0] != 0) {
            if (sto[0][1] > 0) { setResourceInStorage(img1, sto[0][0]); }
        }
        if (sto[1][0] != 0) {
            if (sto[1][1] > 0) { setResourceInStorage(img2, sto[1][0]); }
            if (sto[1][1] > 1) { setResourceInStorage(img3, sto[1][0]); }
        }
        if (sto[2][0] != 0) {
            if (sto[2][1] > 0) { setResourceInStorage(img4, sto[2][0]); }
            if (sto[2][1] > 1) { setResourceInStorage(img5, sto[2][0]); }
            if (sto[2][1] > 2) { setResourceInStorage(img6, sto[2][0]); }
        }
    }

    public void setResourceInStorage(ImageView place, int type) {
        switch (type) {
            case 1:
                place.setImage(coins);
                break;
            case 2:
                place.setImage(servants);
                break;
            case 3:
                place.setImage(shields);
                break;
            case 4:
                place.setImage(stones);
                break;
            default:
                break;
        }
    }

    public void confirm(ActionEvent event) {
        String choice1 = firstdepot.getValue();
        String choice2 = seconddepot.getValue();
        if (choice1 == null || choice2 == null) { title.setText("You have to choose your depots first!"); }
        else if (choice1 == choice2) { title.setText("You have to choose different depots!"); }
        else {


            ActionMessage action=new ActionMessage(TypeOfAction.SWAP_DEPOTS);
            action.SwapDepotsMessage(Integer.parseInt(choice1), Integer.parseInt(choice2));
            Gson gson=new Gson();
            ConnectionHandlerForGui.sendMessage(gson.toJson(action));
            try{
                String messageFromServer=ConnectionHandlerForGui.getMessage();
                System.out.println("messageFromServer during swap action: " + messageFromServer);
                messageFromServer=ConnectionHandlerForGui.getMessage();
                System.out.println(messageFromServer);
                GameStatusUpdate status=ConnectionHandlerForGui.getGson().fromJson(messageFromServer, GameStatusUpdate.class);
                LastGameStatus.update(status);
            }catch (IOException e){
                System.out.println("disconnected, quitting");
                System.exit(1);
            }


            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/turnaction.fxml"));
                root = loader.load();

                if (extra) {
                    TurnController controller = loader.getController();
                    controller.actionDone();
                }

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            catch (Exception e) { System.out.println(e); }
            //choice1 e choice2 convertiti in interi
            //goToController
        }


    }

    public void goToController(Client client, int depot1, int depot2) {
        Gson gson= new Gson();
        ActionMessage action= new ActionMessage(TypeOfAction.SWAP_DEPOTS);
        action.SwapDepotsMessage(depot1, depot2);
        client.messageToServer(gson.toJson(action));
        try{
            if(client.messageFromServer().equals("Operation successful")) System.out.println("Levels swapped successfully!");
            else  {
                System.out.println("Request cannot be completed, you cannot swap this levels!");
            }
        }catch(IOException e){
            System.out.println("disconnected during turn phase while swapping resources");
        }
    }

    public void backToActionTurn(ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/turnaction.fxml"));
        root = loader.load();

        if (extra) {
            TurnController controller = loader.getController();
            controller.actionDone();
        }

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

    //true if Swap Depots is an extra action
    public void setExtra(boolean b) { extra = b; }
}
