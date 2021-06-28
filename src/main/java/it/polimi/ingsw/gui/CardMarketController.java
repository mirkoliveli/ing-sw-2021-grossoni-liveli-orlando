package it.polimi.ingsw.gui;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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


        /*
        // non funziona, a runtime controller è null
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/turnaction.fxml"));
        TurnController controller = loader.getController();
        controller.removePane();
        */

    }


    /**
     * this methods fills the available cards in the market using an array of card IDs in the correct order
     * @param available
     */
    public void fill(int[] available) {
        img1.setImage(cards[available[0]-1]);
        img2.setImage(cards[available[1]-1]);
        img3.setImage(cards[available[2]-1]);
        img4.setImage(cards[available[3]-1]);
        img5.setImage(cards[available[4]-1]);
        img6.setImage(cards[available[5]-1]);
        img7.setImage(cards[available[6]-1]);
        img8.setImage(cards[available[7]-1]);
        img9.setImage(cards[available[8]-1]);
        img10.setImage(cards[available[9]-1]);
        img11.setImage(cards[available[10]-1]);
        img12.setImage(cards[available[11]-1]);
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
        if (rad1.isSelected()) { zoom(img1, rad1); }
        if (rad2.isSelected()) { zoom(img2, rad2); }
        if (rad3.isSelected()) { zoom(img3, rad3); }
        if (rad4.isSelected()) { zoom(img4, rad4); }
        if (rad5.isSelected()) { zoom(img5, rad5); }
        if (rad6.isSelected()) { zoom(img6, rad6); }
        if (rad7.isSelected()) { zoom(img7, rad7); }
        if (rad8.isSelected()) { zoom(img8, rad8);; }
        if (rad9.isSelected()) { zoom(img9, rad9); }
        if (rad10.isSelected()) { zoom(img10, rad10); }
        if (rad11.isSelected()) { zoom(img11, rad11); }
        if (rad12.isSelected()) { zoom(img12, rad12); }
    }

    public void exitWindow(ActionEvent event) {
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

}
