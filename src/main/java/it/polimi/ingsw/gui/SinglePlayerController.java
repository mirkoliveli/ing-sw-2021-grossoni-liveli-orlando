package it.polimi.ingsw.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SinglePlayerController {
    @FXML
    private ImageView action;
    private Image[] tokens = {
            new Image("/img/punchboard/cerchio2.png"),
            new Image("/img/punchboard/cerchio3.png"),
            new Image("/img/punchboard/cerchio1.png"),
            new Image("/img/punchboard/cerchio4.png"),
            new Image("/img/punchboard/cerchio7.png"),
            new Image("/img/punchboard/cerchio5.png")
    };


    /**
     * this method receives a number between 0 and 5 and sets the corresponding action token in the lorenzoaction.fxml
     * @param token
     */
    /*
        0: greenToken
        1: purpleToken
        2: blueToken
        3: yellowToken
        4: moveCrossAndShuffle
        5: twoSpaceMovement
     */
    public void setAction(int token) { action.setImage(tokens[token]); }

    public void confirm(ActionEvent event) {
        //viene lanciato cliccando il bottone continue, da gestire
    }
}
