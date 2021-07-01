package it.polimi.ingsw.gui;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.ActionMessage;
import it.polimi.ingsw.messages.TypeOfAction;
import it.polimi.ingsw.messages.chooseDepotMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class MarketboardController extends AnchorPane {

    private Stage stage;
    private Scene scene;
    private Parent root;
    Color red = new Color(1.0, 0.0, 0.0, 1.0); //FAITH POINTS
    Color yellow = new Color(1.0, 1.0, 0.0, 1.0); //COINS
    Color white = new Color(1.0, 1.0, 1.0, 1.0); //NULL
    Color blue = new Color(0.0, 1.0, 1.0, 1.0); //SHIELDS
    Color grey = new Color(0.5, 0.5, 0.5, 1.0); //STONES
    Color purple = new Color(1.0, 0.0, 1.0, 1.0); //SERVANTS
    @FXML
    private Circle c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13;
    @FXML
    private RadioButton row1, row2, row3, column1, column2, column3, column4;
    @FXML
    private AnchorPane background;
    @FXML
    private AnchorPane pane; //850x621
    @FXML
    private CheckBox leaderbonus1, leaderbonus2;
    private int line;
    private boolean rowcolumn;

    //prima di visualizzare choosedepot settare immagini in base al depot e lanciare setResource
    @FXML
    private ImageView img1, img2, img3, img4, img5, img6;
    @FXML
    private RadioButton depot1, depot2, depot3, depotdisc;
    @FXML
    private Label resource;
    private int depot;



    public void backToActionTurn(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/fxml/turnaction.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }





    //metodo da modificare
    public void fill() {
        background.setStyle("-fx-background-color: black");
        c1.setFill(red);
        c2.setFill(blue);
        c3.setFill(yellow);
        c4.setFill(grey);
        c5.setFill(white);
        c6.setFill(white);
        c7.setFill(white);
        c8.setFill(white);
        c9.setFill(purple);
        c10.setFill(purple);
        c11.setFill(red);
        c12.setFill(red);
        c13.setFill(grey);
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
        if (c13 != null) { c13.setFill(colors[12]); }
    }


    //per ora stampa la scelta e basta, da modificare
    public void getChoice(ActionEvent event) {
        if (row1.isSelected()) { line=1; rowcolumn=false; }
        if (row2.isSelected()) { line=2; rowcolumn=false; }
        if (row3.isSelected()) { line=3; rowcolumn=false; }
        if (column1.isSelected()) { line=1; rowcolumn=true; }
        if (column2.isSelected()) { line=2; rowcolumn=true; }
        if (column3.isSelected()) { line=3; rowcolumn=true; }
        if (column4.isSelected()) { line=4; rowcolumn=true; }
    }

    public void exitWindow(ActionEvent event) {
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void setDepot(ActionEvent event) {
        if (depot1.isSelected()) { depot=1; }
        if (depot2.isSelected()) { depot=2; }
        if (depot3.isSelected()) { depot=3; }
        if (depotdisc.isSelected()) { depot=4; }
    }

    public void setResource(int r) {
        switch (r) {
            case 0:
                resource.setText("coins");
                break;
            case 1:
                resource.setText("servants");
                break;
            case 2:
                resource.setText("shields");
                break;
            case 3:
                resource.setText("stones");
                break;
            default:
                break;
        }
    }


    public void confirm(ActionEvent event) throws Exception {

        ActionMessage action=new ActionMessage(TypeOfAction.GO_TO_MARKET);
        action.MarbleMarketAction(line, rowcolumn);
        Gson gson=new Gson();
        ConnectionHandlerForGui.sendMessage(gson.toJson(action));
        String answerFromServer = ConnectionHandlerForGui.getMessage();
        try {

            while(answerFromServer.contains("resourceStillToBeStored")){
                chooseDepotMessage subMessage=gson.fromJson(answerFromServer, chooseDepotMessage.class);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/choosedepot.fxml"));
                AnchorPane temp = loader.load();
                // settare immagini depot e stato
                pane.getChildren().add(temp);


                /*
                da finire di gestire

                printDepotChoice(subMessage.getDepotStateOfEmptyness(), subMessage.getResourceStillToBeStored());
                System.out.println("Select 0 if you wish to discard the resources, otherwise select the level where you wish them to be stored: ");
                do {
                    inputFromC = scanner.nextLine();
                    ans=checkForDepotChoice(inputFromC, subMessage.getDepotStateOfEmptyness());
                }while(ans<0);
                client.messageToServer(String.valueOf(ans));
                answerFromServer= client.messageFromServer();

                 */
            }





        }
        catch (Exception e) { System.out.println(e); }

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

    public void confirmDepot(ActionEvent event) {
        if (depot!=0) {

            //comunica slot
        }
    }


}
