package it.polimi.ingsw.gui;

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

public class CardMarketController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private RadioButton rad1, rad2, rad3, rad4, rad5, rad6, rad7, rad8, rad9, rad10, rad11, rad12;
    @FXML
    ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12;

    public void backToActionTurn(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/fxml/turnaction.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

    //per ora stampa la scelta e basta, da modificare
    public void getChoice(ActionEvent event) {
        if (rad1.isSelected()) { System.out.println("Card selected: 1"); }
        if (rad2.isSelected()) { System.out.println("Card selected: 2"); }
        if (rad3.isSelected()) { System.out.println("Card selected: 3"); }
        if (rad4.isSelected()) { System.out.println("Card selected: 4"); }
        if (rad5.isSelected()) { System.out.println("Card selected: 5"); }
        if (rad6.isSelected()) { System.out.println("Card selected: 6"); }
        if (rad7.isSelected()) { System.out.println("Card selected: 7"); }
        if (rad8.isSelected()) { System.out.println("Card selected: 8"); }
        if (rad9.isSelected()) { System.out.println("Card selected: 9"); }
        if (rad10.isSelected()) { System.out.println("Card selected: 10"); }
        if (rad11.isSelected()) { System.out.println("Card selected: 11"); }
        if (rad12.isSelected()) { System.out.println("Card selected: 12"); }
    }
}
