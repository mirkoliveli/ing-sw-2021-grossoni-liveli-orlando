package it.polimi.ingsw.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.ActionMessage;
import it.polimi.ingsw.messages.TypeOfAction;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CardMarketController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane pane; //850x621
    @FXML
    private RadioButton rad1, rad2, rad3, rad4, rad5, rad6, rad7, rad8, rad9, rad10, rad11, rad12;
    @FXML
    private ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12;
    private ScaleTransition scale;
    @FXML
    private RadioButton slot1, slot2, slot3;
    @FXML
    private Label slotlabel;
    private int[][] cardsinmarket;
    private int cardchosx, cardchosy;

    // vanno caricate card1, card2 e card3 in base alle carte possedute sulla board
    @FXML
    private ImageView card1, card2, card3;
    private int slot;
    private Image[] cards = {
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-2-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-3-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-4-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-5-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-6-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-7-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-8-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-9-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-10-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-11-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-12-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-13-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-14-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-15-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-16-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-17-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-18-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-19-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-20-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-21-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-22-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-23-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-24-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-25-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-26-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-27-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-28-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-29-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-30-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-31-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-32-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-33-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-34-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-35-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-36-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-37-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-38-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-39-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-40-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-41-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-42-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-43-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-44-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-45-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-46-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-47-1.png"),
            new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-48-1.png")
    };

    public void backToActionTurn(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/fxml/turnaction.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    /**
     * this methods fills the available cards in the market using an array of card IDs in the correct order
     * @param available
     */
    public void fill(int[][] available) {
        cardsinmarket = available;
        if (available[2][0] != 0) { img1.setImage(cards[available[2][0]-1]); } //green lv3
        if (available[2][2] != 0) { img2.setImage(cards[available[2][2]-1]); } //blue lv3
        if (available[2][3] != 0) { img3.setImage(cards[available[2][3]-1]); } //yellow lv3
        if (available[2][1] != 0) { img4.setImage(cards[available[2][1]-1]); } //purple lv3
        if (available[1][0] != 0) { img5.setImage(cards[available[1][0]-1]); } //green lv2
        if (available[1][2] != 0) { img6.setImage(cards[available[1][2]-1]); } //blue lv2
        if (available[1][3] != 0) { img7.setImage(cards[available[1][3]-1]); } //yellow lv2
        if (available[1][1] != 0) { img8.setImage(cards[available[1][1]-1]); } //purple lv2
        if (available[0][0] != 0) { img9.setImage(cards[available[0][0]-1]); } //green lv1
        if (available[0][2] != 0) { img10.setImage(cards[available[0][2]-1]); } //blue lv1
        if (available[0][3] != 0) { img11.setImage(cards[available[0][3]-1]); } //yellow lv1
        if (available[0][1] != 0) { img12.setImage(cards[available[0][1]-1]); } //purple lv1
    }

    /**
     * used to add an animation to zoom cards in the market, so that their characteristics are clearer
     * @param img
     * @param radio
     */
    public void zoom(ImageView img, RadioButton radio) {
        scale = new ScaleTransition();
        scale.setNode(img);
        scale.setDuration(Duration.millis(800));
        scale.setCycleCount(Animation.INDEFINITE);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(1);
        scale.setByY(1);
        scale.setAutoReverse(true);
        scale.play();
    }


    public void getChoice(ActionEvent event) {
        if (scale!= null) {
            scale.jumpTo(Duration.ZERO);
            scale.stop();
        }
        if (rad1.isSelected()) { zoom(img1, rad1); cardchosx=2; cardchosy=0; }
        if (rad2.isSelected()) { zoom(img2, rad2); cardchosx=2; cardchosy=2; }
        if (rad3.isSelected()) { zoom(img3, rad3); cardchosx=2; cardchosy=3; }
        if (rad4.isSelected()) { zoom(img4, rad4); cardchosx=2; cardchosy=1; }
        if (rad5.isSelected()) { zoom(img5, rad5); cardchosx=1; cardchosy=0; }
        if (rad6.isSelected()) { zoom(img6, rad6); cardchosx=1; cardchosy=2; }
        if (rad7.isSelected()) { zoom(img7, rad7); cardchosx=1; cardchosy=3; }
        if (rad8.isSelected()) { zoom(img8, rad8); cardchosx=1; cardchosy=1; }
        if (rad9.isSelected()) { zoom(img9, rad9); cardchosx=0; cardchosy=0; }
        if (rad10.isSelected()) { zoom(img10, rad10); cardchosx=0; cardchosy=2; }
        if (rad11.isSelected()) { zoom(img11, rad11); cardchosx=0; cardchosy=3; }
        if (rad12.isSelected()) { zoom(img12, rad12); cardchosx=0; cardchosy=1; }
    }

    public void setSlot(ActionEvent event) {
        if (slot1.isSelected()) { slot = 1; }
        if (slot2.isSelected()) { slot = 2; }
        if (slot3.isSelected()) { slot = 3; }
    }

    public void confirm(ActionEvent event) {


        ActionMessage action=new ActionMessage(TypeOfAction.BUY_A_CARD);
        action.BuyCard(cardsinmarket[cardchosx][cardchosy]);
        Gson gson=new Gson();
        ConnectionHandlerForGui.sendMessage(gson.toJson(action));



        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/turnaction.fxml"));
            root = loader.load();

            TurnController controller = loader.getController();
            controller.actionDone();

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void confirmSlot(ActionEvent event) {
        if (slot == 0) { slotlabel.setText("You have to select a valid slot!"); }
        else {
            //comunica slot
            }
    }

    public void exitWindow(ActionEvent event) {
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

}
