package it.polimi.ingsw.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EndgameController {
    @FXML
    private Label winnerlabel;
    @FXML
    private ImageView goldmedal;
    @FXML
    private Label bottomtext;

    public void loadscore(int points, String winnername, boolean winner) {
        winnerlabel.setText(winnername);
        if (winner) {
            Image medal = new Image("/img/extra/goldmedal.png");
            goldmedal.setImage(medal);
            bottomtext.setText("Congratulations! You won the game with " + points + " points!");
        }
        else {
            bottomtext.setText("You didn't win the game, but you still scored " + points + " points!");
        }
    }

}
