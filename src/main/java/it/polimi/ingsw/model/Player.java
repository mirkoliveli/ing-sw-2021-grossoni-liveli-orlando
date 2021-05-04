package it.polimi.ingsw.model;

public class Player {

    private String name;
    private int id;
    private boolean inkwell;
    private PersonalBoard board;
    private LeaderCard leaderCard1;
    private LeaderCard leaderCard2;
    private int victoryPoints;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(){
        this.victoryPoints = board.getBoardVictoryPoints();
    }




}
