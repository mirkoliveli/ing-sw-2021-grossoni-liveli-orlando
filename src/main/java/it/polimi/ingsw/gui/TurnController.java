package it.polimi.ingsw.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.swing.*;

//provvisorio, è il controller di TurnAction che serve per muoversi tra le schermate durante il testing

public class TurnController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ImageView leadershown1, leadershown2;
    @FXML
    private Label labelleader1, labelleader2;
    @FXML
    private ImageView board;
    @FXML
    private AnchorPane pane; //850x621
    @FXML
    private ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12;
    @FXML
    private Circle c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12;
    Color red = new Color(1.0, 0.0, 0.0, 1.0); //FAITH POINTS
    Color yellow = new Color(1.0, 1.0, 0.0, 1.0); //COINS
    Color white = new Color(1.0, 1.0, 1.0, 1.0); //NULL
    Color blue = new Color(0.0, 1.0, 1.0, 1.0); //SHIELDS
    Color grey = new Color(0.5, 0.5, 0.5, 1.0); //STONES
    Color purple = new Color(1.0, 0.0, 1.0, 1.0); //SERVANTS
    private AnchorPane temp;
    private TurnController controller;
    private AnchorPane popup;

    public void switchToProduction(ActionEvent event) throws Exception {
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/production.fxml"));
        root = loader.load();
        Image image = new Image("img/board/Masters of Renaissance_PlayerBoard (11_2020)-1.png");
        ProductionController sceneController = loader.getController();
        sceneController.setImage(image);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/production.fxml"));
        temp = loader.load();
        Image image = new Image("img/board/Masters of Renaissance_PlayerBoard (11_2020)-1.png");
        ProductionController sceneController = loader.getController();
        sceneController.setImage(image);
        pane.getChildren().add(temp);
    }

    public void switchToMarketboard (ActionEvent event) throws Exception {
        /*root = FXMLLoader.load(getClass().getResource("/fxml/marketboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        temp = FXMLLoader.load(getClass().getResource("/fxml/marketboard.fxml"));
        pane.getChildren().add(temp);

    }

    public void switchToSwapDepots(ActionEvent event) throws Exception {

        /*root = FXMLLoader.load(getClass().getResource("/fxml/swapdepots.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        temp = FXMLLoader.load(getClass().getResource("/fxml/swapdepots.fxml"));
        pane.getChildren().add(temp);
    }

    public void switchToCardMarket(ActionEvent event) throws Exception {
        /*root = FXMLLoader.load(getClass().getResource("/fxml/cardmarket.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        temp = FXMLLoader.load(getClass().getResource("/fxml/cardmarket.fxml"));
        pane.getChildren().add(temp);

    }

    public void removePane() {
        pane.getChildren().remove(temp);
    }

    //non funziona il setter delle immagini leader (nullPointerException sulle due imageview)
    public void showleaders(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menuleaders.fxml"));
            AnchorPane popup = popupLoader.load();

            Image leader1 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-61-1.png");
            Image leader2 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-62-1.png");
            controller = popupLoader.getController();
            controller.setLeaders(leader1, leader2);

            loadPopup(popup, "Leader Cards");

            /*Scene popupScene = new Scene(popup);
            Stage popupStage = new Stage();
            popupStage.setScene(popupScene);
            Image icon = new Image("/img/punchboard/retro cerchi.png");
            popupStage.getIcons().add(icon);
            popupStage.setTitle("Leader Cards");
            popupStage.setResizable(false);
            popupStage.show();*/
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void exitWindow(ActionEvent event) {
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void showMarketBoard(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menumarketboard.fxml"));
            AnchorPane popup = popupLoader.load();
            Color[] colors = {red, blue, yellow, grey, red, blue, yellow, grey, red, blue, yellow, grey};
            controller = popupLoader.getController();
            controller.fillMarbleColors(colors);

            loadPopup(popup, "Market Board");
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void showCardMarket(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menucardmarket.fxml"));
            AnchorPane popup = popupLoader.load();
            Image[] cards = new Image[12];
            cards[0] = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-33-1.png");
            cards[1] = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-35-1.png");
            cards[2] = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-36-1.png");
            cards[3] = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-34-1.png");
            cards[4] = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-17-1.png");
            cards[5] = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-19-1.png");
            cards[6] = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-20-1.png");
            cards[7] = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-18-1.png");
            cards[8] = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png");
            cards[9] = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-3-1.png");
            cards[10] = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-4-1.png");
            cards[11] = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-2-1.png");

            controller = popupLoader.getController();
            controller.fill(cards);
            loadPopup(popup, "Card Market");
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void showBoard(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menuboard.fxml"));
            AnchorPane popup = popupLoader.load();
            Image image = new Image("img/board/Masters of Renaissance_PlayerBoard (11_2020)-1.png");
            controller = popupLoader.getController();
            controller.setBoard(image);
            loadPopup(popup, "Your Board");
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void setLeaders(Image img1, Image img2) {
        leadershown1.setImage(img1);
        leadershown2.setImage(img2);
    }

    public void setBoard(Image img) {
        board.setImage(img);
    }

    public void loadPopup (AnchorPane pane, String title) {
        Scene popupScene = new Scene(pane);
        Stage popupStage = new Stage();
        popupStage.setScene(popupScene);
        Image icon = new Image("/img/punchboard/retro cerchi.png");
        popupStage.getIcons().add(icon);
        popupStage.setTitle(title);
        popupStage.setResizable(false);
        popupStage.show();
    }

    // metodo provvisorio per riempire Card Market nel menu
    public void fill(Image[] cards) {
        img1.setImage(cards[0]);
        img2.setImage(cards[1]);
        img3.setImage(cards[2]);
        img4.setImage(cards[3]);
        img5.setImage(cards[4]);
        img6.setImage(cards[5]);
        img7.setImage(cards[6]);
        img8.setImage(cards[7]);
        img9.setImage(cards[8]);
        img10.setImage(cards[9]);
        img11.setImage(cards[10]);
        img12.setImage(cards[11]);
    }

    public void fillMarbleColors(Color[] colors) {
        c1.setFill(colors[0]);
        c2.setFill(colors[1]);
        c3.setFill(colors[2]);
        c4.setFill(colors[3]);
        c5.setFill(colors[4]);
        c6.setFill(colors[5]);
        c7.setFill(colors[6]);
        c8.setFill(colors[7]);
        c9.setFill(colors[8]);
        c10.setFill(colors[9]);
        c11.setFill(colors[10]);
        c12.setFill(colors[11]);
    }

}
