package it.polimi.ingsw.messages;

public class LastMessage {
    private final String winnerName;
    private final int winnerVP;
    private final int playerVP;
    private final boolean youWin;

    public LastMessage(String winnerName, int winnerVP, int playerVP, boolean youWin){
        this.playerVP=playerVP;
        this.winnerName=winnerName;
        this.winnerVP=winnerVP;
        this.youWin=youWin;
    }

    public int getPlayerVP() {
        return playerVP;
    }

    public int getWinnerVP() {
        return winnerVP;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public boolean doYouWin() {
        return youWin;
    }
}
