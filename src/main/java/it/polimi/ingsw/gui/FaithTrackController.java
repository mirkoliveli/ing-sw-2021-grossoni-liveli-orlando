package it.polimi.ingsw.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class FaithTrackController implements Initializable {
    @FXML
    private Label points;
    @FXML
    private ImageView cross;
    private int position = 0;
    private double x;
    private double y;
    @FXML
    private ImageView bonus1, bonus2, bonus3;

    public void increasePosition(int faithmarker){
        while (faithmarker > 0) {
            if (position == 9 || position == 10) { y+=27.3; cross.setY(y); }
            else if (position == 2 || position == 3 || position == 16 || position == 17) { y-=27.3; cross.setY(y); }
            else { x+=27.3; cross.setX(x); }
            position++;
            faithmarker--;
        }
    }

    public void setPoints(int p) { points.setText(Integer.toString(p)); }

    public void setBonuses(boolean[] bonus) {
        if (bonus[0]) {
            Image b1 = new Image("/img/punchboard/pope_favor1_front.png");
            bonus1.setImage(b1);
        }
        if (bonus[1]) {
            Image b2 = new Image("/img/punchboard/pope_favor2_front.png");
            bonus2.setImage(b2);
        }
        if (bonus[2]) {
            Image b3 = new Image("/img/punchboard/pope_favor3_front.png");
            bonus3.setImage(b3);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image pope1 = new Image("/img/punchboard/pope_favor1_back.png");
        Image pope2 = new Image("/img/punchboard/pope_favor2_back.png");
        Image pope3 = new Image("/img/punchboard/pope_favor3_back.png");
        bonus1.setImage(pope1);
        bonus2.setImage(pope2);
        bonus3.setImage(pope3);
    }
}
