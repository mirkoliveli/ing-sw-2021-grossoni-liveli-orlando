package it.polimi.ingsw.model;

public class Player {

    private String name;
    private int id;
    private final boolean inkwell;
    private final PersonalBoard board;
    private LeaderCard leaderCard1;
    private LeaderCard leaderCard2;
    private int victoryPoints;

    public Player(String name, int id) {
        this.name = name;
        this.setId(id);
        this.inkwell = false;
        this.leaderCard1 = null;
        this.leaderCard2 = null;
        this.victoryPoints = 0;
        this.board = new PersonalBoard(id);

    }

    public PersonalBoard getBoard() {
        return board;
    }

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

    public void setVictoryPoints(int value) {
        this.victoryPoints = value;
    }

    public LeaderCard getLeaderCard1() {
        return leaderCard1;
    }

    public LeaderCard getLeaderCard2() {
        return leaderCard2;
    }

    public void setPvTotal() {
        int pvtemp = 0;
        if (leaderCard1 != null) {
            if (leaderCard1.checkIfPlayed()) pvtemp += leaderCard1.getPv();
        }
        if (leaderCard2 != null) {
            if (leaderCard2.checkIfPlayed()) pvtemp += leaderCard2.getPv();
        }
        pvtemp += getBoard().getBoardVictoryPoints();

        this.setVictoryPoints(pvtemp);
    }

    public void setLeaderCard1(LeaderDeck deck, int id){
        leaderCard1=new LeaderCard(deck.getCardById(id));
    }

    public void setLeaderCard2(LeaderDeck deck, int id){
        leaderCard2=new LeaderCard(deck.getCardById(id));
    }

}
