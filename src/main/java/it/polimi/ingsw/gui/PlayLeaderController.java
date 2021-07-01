package it.polimi.ingsw.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.ActionMessage;
import it.polimi.ingsw.messages.TypeOfAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class PlayLeaderController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ImageView leader1, leader2;
    @FXML
    private RadioButton play1, discard1, play2, discard2;
    @FXML
    private Label label;
    private boolean extra;
    private int playordiscard;
    private int idl1, idl2, chosen;

    public void getChoice(ActionEvent event) {
        if (play1.isSelected()) { playordiscard=0; chosen=idl1; }
        if (discard1.isSelected()) { playordiscard=1; chosen=idl1; }
        if (play2.isSelected()) { playordiscard=0; chosen=idl2; }
        if (discard2.isSelected()) { playordiscard=1; chosen=idl2; }
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

    public void confirm(ActionEvent event) {
        if (chosen == 0) { label.setText("You have to choose an action to perform!"); }
        else {

            ActionMessage action=new ActionMessage(TypeOfAction.PLAY_OR_DISCARD_LEADER);
            action.PlayOrDiscardLeaders(chosen, playordiscard);
            Gson gson=new Gson();
            ConnectionHandlerForGui.sendMessage(gson.toJson(action));

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
        }

    }

    // l1 and l2 are the leaders' IDs, played1 and played2 indicate if they have been played/discarded
    public void setLeaders(int l1, int l2, boolean played1, boolean played2) {

        Image image1 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + l1 + "-1.png");
        Image image2 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + l2 + "-1.png");
        idl1 = l1;
        idl2 = l2;

        if (played1) {
            play1.setDisable(true);
            discard1.setDisable(true);
        }
        else { leader1.setImage(image1); }
        if (played2) {
            play2.setDisable(true);
            discard2.setDisable(true);
        }
        else { leader2.setImage(image2); }
    }

    public void setExtra(boolean b) { extra = b; }
}
