package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.MessageControllerForGui;
import it.polimi.ingsw.messages.GettingStartedMessage;
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


    public void fill() {
        String source="/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ ConnectionHandlerForGui.getLeaders()[0] + "-1.png";
        Image test1 = new Image(source);
        source="/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ ConnectionHandlerForGui.getLeaders()[1] + "-1.png";
        Image test2 = new Image(source);
        source="/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ ConnectionHandlerForGui.getLeaders()[2] + "-1.png";
        Image test3 = new Image(source);
        source="/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ ConnectionHandlerForGui.getLeaders()[3] + "-1.png";
        Image test4 = new Image(source);
        img1.setImage(test1);
        img2.setImage(test2);
        img3.setImage(test3);
        img4.setImage(test4);
    }

    public void confirm(ActionEvent event) {
        String choice1 = firstleader.getValue();
        String choice2 = secondleader.getValue();
        if (choice1 == null || choice2 == null) { title.setText("You have to choose your leaders first!"); }
        else if (choice1.equals(choice2)) { title.setText("You have to choose different leaders!"); }
        else {
            try {
                // server connection
                serverMessage(choice1, choice2);
                String messageFromServer=ConnectionHandlerForGui.getMessage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/turnaction.fxml"));
                root = loader.load();
                TurnController controller = loader.getController();

               stage = (Stage)((Node)event.getSource()).getScene().getWindow();
               scene = new Scene(root);

               if(messageFromServer.charAt(0)=='E'){
                   controller.setWaiting();
                   ConnectionHandlerForGui.setIsItMyTurn(false);
                   ConnectionHandlerForGui.sendMessage("Still_connected");
               }
                else{
                   controller.setWaiting();
                   ConnectionHandlerForGui.setIsItMyTurn(true);
                   ConnectionHandlerForGui.sendMessage("Still_connected");
               }
               stage.setScene(scene);
               stage.show();



            }
            catch (Exception e) { System.out.println(e); }
        }

    }

    /**
     * sends the getting started message to the server
     * @param choice1
     * @param choice2
     */
    public static void serverMessage(String choice1, String choice2){
        getLeaderId(choice1);
        int[] leaders={ConnectionHandlerForGui.getLeaders()[getLeaderId(choice1)-1], ConnectionHandlerForGui.getLeaders()[getLeaderId(choice2)-1]};
        ConnectionHandlerForGui.setLeaders(leaders);
        if(ConnectionHandlerForGui.getIdOfPlayer()==1){
            GettingStartedMessage mex=new GettingStartedMessage(ConnectionHandlerForGui.getLeaders()[0], ConnectionHandlerForGui.getLeaders()[1], 0);
            ConnectionHandlerForGui.sendMessage(mex);
        }
        else if(ConnectionHandlerForGui.getIdOfPlayer()==2 || ConnectionHandlerForGui.getIdOfPlayer()==3){
            GettingStartedMessage mex=new GettingStartedMessage(ConnectionHandlerForGui.getLeaders()[0], ConnectionHandlerForGui.getLeaders()[1], 1);
            mex.setResources(ConnectionHandlerForGui.getResource(), -1);
            ConnectionHandlerForGui.sendMessage(mex);
        }
        else{
            GettingStartedMessage mex=new GettingStartedMessage(ConnectionHandlerForGui.getLeaders()[0], ConnectionHandlerForGui.getLeaders()[1], 2);
            mex.setResources(ConnectionHandlerForGui.getResource(), ConnectionHandlerForGui.getResource4player());
            ConnectionHandlerForGui.sendMessage(mex);
        }

    }

    /**
     * parse the selected leader
     * @param choice selected via gui
     * @return 1-4, stating which leader was selected
     */
    public static int getLeaderId(String choice){
        return Integer.parseInt(choice.substring(7));
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstleader.getItems().addAll(leaders);
        secondleader.getItems().addAll(leaders);
    }
}