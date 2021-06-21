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

    public void testMenu(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/test.fxml"));
            AnchorPane popup = popupLoader.load();
            //String title = "menu test";
            loadPopup(popup, "menu test");
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

}
