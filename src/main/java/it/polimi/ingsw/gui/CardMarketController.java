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
import javafx.stage.Stage;
import javafx.util.Duration;

public class CardMarketController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private RadioButton rad1, rad2, rad3, rad4, rad5, rad6, rad7, rad8, rad9, rad10, rad11, rad12;
    @FXML
    ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12;
    ScaleTransition scale;

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
    

    public void fill(ActionEvent event) {
        Image test1 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-33-1.png");
        Image test2 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-35-1.png");
        Image test3 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-36-1.png");
        Image test4 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-34-1.png");
        Image test5 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-17-1.png");
        Image test6 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-19-1.png");
        Image test7 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-20-1.png");
        Image test8 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-18-1.png");
        Image test9 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png");
        Image test10 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-3-1.png");
        Image test11 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-4-1.png");
        Image test12 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-2-1.png");
        img1.setImage(test1);
        img2.setImage(test2);
        img3.setImage(test3);
        img4.setImage(test4);
        img5.setImage(test5);
        img6.setImage(test6);
        img7.setImage(test7);
        img8.setImage(test8);
        img9.setImage(test9);
        img10.setImage(test10);
        img11.setImage(test11);
        img12.setImage(test12);

    }

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

}
