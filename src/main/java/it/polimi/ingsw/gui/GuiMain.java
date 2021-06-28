package it.polimi.ingsw.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
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
        //modificare il contenuto di getResource per cambiare la schermata iniziale

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root2 = loader.load();

        Scene productionScene = new Scene(root2);
        stage.setScene(productionScene);
        stage.show();

        /*
        Scene scene = new Scene(root, Color.LIGHTGREY);
        stage.setFullScreen(true);
        stage.setWidth(950);
        stage.setHeight(650);
        Text text = new Text();
        text.setText("Benvenuto in Maestri del Rinascimento!");
        text.setX(50);
        text.setY(50);
        text.setFont(Font.font("Verdana", 20));
        root.getChildren().add(text);
        Image lorenzo = new Image ("/img/punchboard/coin.png");
        Image lorenzo = new Image ("https://st.ilfattoquotidiano.it/wp-content/uploads/2019/08/07/Barack-Obama-1200-690x362.jpg");
        ImageView imageView = new ImageView(lorenzo);
        imageView.setX(200);
        imageView.setY(200);
        root.getChildren().add(imageView);*/





    }
}
