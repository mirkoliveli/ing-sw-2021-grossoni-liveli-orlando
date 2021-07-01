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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;



public class TurnController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label label;
    @FXML
    private ImageView leadershown1, leadershown2;
    @FXML
    private Label labelleader1, labelleader2;
    @FXML
    private ImageView board;
    @FXML
    private AnchorPane pane; //850x621
    @FXML
    private Circle c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12;
    Color red = new Color(1.0, 0.0, 0.0, 1.0); //FAITH POINTS
    Color yellow = new Color(1.0, 1.0, 0.0, 1.0); //COINS
    Color white = new Color(1.0, 1.0, 1.0, 1.0); //NULL
    Color blue = new Color(0.0, 1.0, 1.0, 1.0); //SHIELDS
    Color grey = new Color(0.5, 0.5, 0.5, 1.0); //STONES
    Color purple = new Color(1.0, 0.0, 1.0, 1.0); //SERVANTS
    private AnchorPane temp;
    private AnchorPane popup;
    @FXML
    private Button finishbutton, cmbutton, marketbutton, prodbutton;
    @FXML
    private Label coinslabel, servantslabel, shieldslabel, stoneslabel;
    @FXML
    private ImageView card1, card2, card3;
    Image coins = new Image("/img/punchboard/coin2.png");
    Image servants = new Image("/img/punchboard/servant2.png");
    Image shields = new Image("/img/punchboard/shield2.png");
    Image stones = new Image("/img/punchboard/stone2.png");

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
        // lanciare setProductionCards e eventualmente setLeaders in base alle carte possedute
        temp = loader.load();
        ProductionController controller = loader.getController();
        controller.fillBaseProd();
        pane.getChildren().add(temp);
    }

    public void switchToMarketboard (ActionEvent event) throws Exception {
        /*root = FXMLLoader.load(getClass().getResource("/fxml/marketboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/marketboard.fxml"));
        temp = loader.load();
        // provvisorio, colors dovrebbe contenere i colori delle biglie di marble market in fila
        Color[] colors = {red, blue, yellow, purple, red, blue, yellow, purple, red, blue, yellow, grey, grey};
        MarketboardController controller = loader.getController();
        controller.fillMarbleColors(colors);
        pane.getChildren().add(temp);

    }

    public void switchToSwapDepots(ActionEvent event) throws Exception {

        /*root = FXMLLoader.load(getClass().getResource("/fxml/swapdepots.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/swapdepots.fxml"));
        temp = loader.load();
        if (finishbutton.isVisible()) {
            SwapDepotsController controller = loader.getController();
            controller.setExtra(true);
        }

        // lanciare fill da qui
        pane.getChildren().add(temp);
    }

    public void switchToPlayLeader(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/playleader.fxml"));
        temp = loader.load();
        PlayLeaderController controller = loader.getController();

        // provvisorio, setLeaders viene lanciato con gli ID dei leader del giocatore attivo
        controller.setLeaders(51, 53, true, false);

        if (finishbutton.isVisible()) {
            controller.setExtra(true);
        }
        pane.getChildren().add(temp);
    }

    public void switchToCardMarket(ActionEvent event) throws Exception {
        /*root = FXMLLoader.load(getClass().getResource("/fxml/cardmarket.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cardmarket.fxml"));
        temp = loader.load();

        // provvisorio, cards dovrebbe contenere le carte nel mercato in base allo stato della partita
        int[] cards = {33, 35, 36, 34, 17, 19, 20, 18, 1, 3, 4, 2};

        CardMarketController cardmarketcontroller = loader.getController();
        cardmarketcontroller.fill(cards);

        pane.getChildren().add(temp);

    }

    public void setWaiting() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/waiting.fxml"));
            temp = loader.load();
            pane.getChildren().add(temp);
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void finishTurn(ActionEvent event) {

        ActionMessage action=new ActionMessage(TypeOfAction.END_TURN);
        action.EndTurn();
        Gson gson=new Gson();
        ConnectionHandlerForGui.sendMessage(gson.toJson(action));

        setWaiting();

        }

    public void removePane() {
        pane.getChildren().remove(temp);
    }

    //non funziona il setter delle immagini leader (nullPointerException sulle due imageview)
    public void showleaders(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menuleaders.fxml"));
            AnchorPane popup = popupLoader.load();

            // provvisorio, leader1 e leader2 saranno i leader posseduti dal giocatore attivo
            Image leader1 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-61-1.png");
            Image leader2 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-62-1.png");
            TurnController controller = popupLoader.getController();
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

    public void showStorage(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menustorage.fxml"));
            AnchorPane popup = popupLoader.load();

            // manca il metodo per settare contenuto storage e strongbox
            loadPopup(popup, "Storage and Strongbox");
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

            // provvisorio, colors dovrebbe contenere i colori delle biglie di marble market in fila
            Color[] colors = {red, blue, yellow, purple, red, blue, yellow, purple, red, blue, yellow, grey, grey};
            MarketboardController controller = popupLoader.getController();
            controller.fillMarbleColors(colors);

            loadPopup(popup, "Market Board");
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void showCardMarket(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menucardmarket.fxml"));
            AnchorPane popup = popupLoader.load();

            // provvisorio, cards dovrebbe contenere le carte nel mercato in base allo stato della partita
            int[] cards = {33, 35, 36, 34, 17, 19, 20, 18, 1, 3, 4, 2};

            CardMarketController cardmarketcontroller = popupLoader.getController();
            cardmarketcontroller.fill(cards);
            loadPopup(popup, "Card Market");
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void showBoard(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menuboard.fxml"));
            AnchorPane popup = popupLoader.load();
            // lanciare setProductionCards in base alle carte possedute
            loadPopup(popup, "Your Board");
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void showFaithTrack(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menufaithtrack.fxml"));
            AnchorPane popup = popupLoader.load();
            // manca il metodo per settare i punti vittoria
            loadPopup(popup, "Your Faith Track");
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

    public void setProductionCards(int c1, int c2, int c3) {
        if (c1 != 0) {
            Image firstcard = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + c1 + "-1.png");
            card1.setImage(firstcard);
        }
        if (c2 != 0) {
            Image secondcard = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + c2 + "-1.png");
            card2.setImage(secondcard);
        }
        if (c3 != 0) {
            Image thirdcard = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + c3 + "-1.png");
            card3.setImage(thirdcard);
        }
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



    public void actionDone() {
        cmbutton.setDisable(true);
        marketbutton.setDisable(true);
        prodbutton.setDisable(true);
        finishbutton.setVisible(true);
        label.setText("You already performed your main action! You can swap depots, play/discard a leader or finish your turn");
    }

}
