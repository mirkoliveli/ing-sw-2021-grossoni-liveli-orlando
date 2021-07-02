package it.polimi.ingsw.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class GuiMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Image icon = new Image("/img/punchboard/calamaio.png");
        stage.getIcons().add(icon);
        stage.setTitle("Masters of Renaissance");
        stage.setResizable(false);
        //stage.setFullScreen(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root2 = loader.load();

        Scene productionScene = new Scene(root2);
        stage.setScene(productionScene);
        stage.show();



    }
}
