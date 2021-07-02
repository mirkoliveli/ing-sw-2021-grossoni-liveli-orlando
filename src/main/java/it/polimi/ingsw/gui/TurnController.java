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

import java.io.IOException;


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
    private AnchorPane temp;
    private AnchorPane popup;
    @FXML
    private Button finishbutton, cmbutton, marketbutton, prodbutton;
    @FXML
    private Label coinslabel, servantslabel, shieldslabel, stoneslabel;
    @FXML
    private ImageView card1, card2, card3, img1, img2, img3, img4, img5, img6;
    Image coins = new Image("/img/punchboard/coin2.png");
    Image servants = new Image("/img/punchboard/servant2.png");
    Image shields = new Image("/img/punchboard/shield2.png");
    Image stones = new Image("/img/punchboard/stone2.png");

    public void switchToProduction(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/production.fxml"));
        temp = loader.load();
        ProductionController controller = loader.getController();
        controller.fillBaseProd();
        controller.setProductionCards(LastGameStatus.activatableCards);
        controller.setLeaders(LastGameStatus.leader1, LastGameStatus.leader2);
        pane.getChildren().add(temp);
    }

    public void switchToMarketboard (ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/marketboard.fxml"));
        temp = loader.load();
        MarketboardController controller = loader.getController();
        controller.fillMarbleColors(LastGameStatus.marketBoardStatus, LastGameStatus.sideMarbleStatus);
        pane.getChildren().add(temp);

    }

    public void switchToSwapDepots(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/swapdepots.fxml"));
        temp = loader.load();
        if (finishbutton.isVisible()) {
            SwapDepotsController controller = loader.getController();
            controller.setExtra(true);
        }
        SwapDepotsController controller = loader.getController();
        controller.setStorage(LastGameStatus.storageState);
        pane.getChildren().add(temp);
    }

    public void switchToPlayLeader(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/playleader.fxml"));
        temp = loader.load();
        PlayLeaderController controller = loader.getController();

        controller.setLeaders(LastGameStatus.leader1, LastGameStatus.leader2, LastGameStatus.leader1Played, LastGameStatus.leader2Played);

        if (finishbutton.isVisible()) {
            controller.setExtra(true);
        }
        pane.getChildren().add(temp);
    }

    public void switchToCardMarket(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cardmarket.fxml"));
        temp = loader.load();
        CardMarketController cardmarketcontroller = loader.getController();
        cardmarketcontroller.fill(LastGameStatus.cardMarketStatus);

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

    /**
     * sends message to the server requesting to end the turn, if the answer is positive the turn will end, the view for the actions will be blocked and MessageController thread will restart
     * if the answer is negative nothing will happen.
     * @param event
     */
    public void finishTurn(ActionEvent event) {

        ActionMessage action=new ActionMessage(TypeOfAction.END_TURN);
        action.EndTurn();
        Gson gson=new Gson();
        ConnectionHandlerForGui.sendMessage(gson.toJson(action));
        try{
        String message=ConnectionHandlerForGui.getMessage();
        if(message.equals("Operation successful")){
            setWaiting();
            ConnectionHandlerForGui.setIsItMyTurn(false);
            ConnectionHandlerForGui.setIsMyTurnEnded(true);
        }
        }catch (IOException e){
            System.out.println("DISCONNECTED");
            System.exit(0);
        }
    }

    public void removePane() {
        pane.getChildren().remove(temp);
    }

    public void showleaders(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menuleaders.fxml"));
            AnchorPane popup = popupLoader.load();

            Image leader1 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + LastGameStatus.leader1 + "-1.png");
            Image leader2 = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + LastGameStatus.leader2 + "-1.png");
            TurnController controller = popupLoader.getController();
            controller.setLeaders(leader1, leader2, LastGameStatus.leader1Played, LastGameStatus.leader2Played);

            loadPopup(popup, "Leader Cards");

        }
        catch (Exception e) { System.out.println(e); }
    }

    public void showStorage(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menustorage.fxml"));
            AnchorPane popup = popupLoader.load();
            TurnController controller = popupLoader.getController();
            controller.setStorage(LastGameStatus.storageState);
            controller.setStrongbox(LastGameStatus.strongboxStatus);
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
            MarketboardController controller = popupLoader.getController();
            controller.fillMarbleColors(LastGameStatus.marketBoardStatus, LastGameStatus.sideMarbleStatus);
            loadPopup(popup, "Market Board");
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void showCardMarket(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menucardmarket.fxml"));
            AnchorPane popup = popupLoader.load();
            CardMarketController cardmarketcontroller = popupLoader.getController();
            cardmarketcontroller.fill(LastGameStatus.cardMarketStatus);
            loadPopup(popup, "Card Market");
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void showBoard(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menuboard.fxml"));
            AnchorPane popup = popupLoader.load();
            setProductionCards(LastGameStatus.activatableCards);
            loadPopup(popup, "Your Board");
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void showFaithTrack(ActionEvent event) {
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getResource("/fxml/menufaithtrack.fxml"));
            AnchorPane popup = popupLoader.load();
            FaithTrackController controller = popupLoader.load();
            controller.increasePosition(LastGameStatus.faithMarkerStatus);
            controller.setPoints(LastGameStatus.victoryPoints);
            controller.setBonuses(LastGameStatus.popeCards);
            loadPopup(popup, "Your Faith Track");
        }
        catch (Exception e) { System.out.println(e); }

    }

    public void setLeaders(Image img1, Image img2, boolean isplayed1, boolean isplayed2) {
        leadershown1.setImage(img1);
        leadershown2.setImage(img2);
        if (isplayed1) { labelleader1.setText("Played/Discarded"); }
        if (isplayed2) { labelleader2.setText("Played/Discarded"); }
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

    public void setProductionCards(int[] activatable) {
        if (activatable[0] != 0) {
            Image firstcard = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + activatable[0] + "-1.png");
            card1.setImage(firstcard);
        }
        if (activatable[1] != 0) {
            Image secondcard = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + activatable[1] + "-1.png");
            card2.setImage(secondcard);
        }
        if (activatable[2] != 0) {
            Image thirdcard = new Image("/img/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + activatable[2] + "-1.png");
            card3.setImage(thirdcard);
        }
    }

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

    public void setStrongbox(int[] box) {
        coinslabel.setText("x" + box[0]);
        servantslabel.setText("x" + box[1]);
        shieldslabel.setText("x" + box[2]);
        stoneslabel.setText("x" + box[3]);
    }

    public void actionDone() {
        cmbutton.setDisable(true);
        marketbutton.setDisable(true);
        prodbutton.setDisable(true);
        finishbutton.setVisible(true);
        label.setText("You already performed your main action! You can swap depots, play/discard a leader or finish your turn");
    }

    public void isMyTurnNow(ActionEvent event){
        if(ConnectionHandlerForGui.IsItMyTurn()){
            removePane();
        }
        else{
            //non è il mio turno!
        }
    }

}
