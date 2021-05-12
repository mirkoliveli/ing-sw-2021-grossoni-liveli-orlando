package it.polimi.ingsw.controller;

public class PlayerUpdate {
    private String name;
    private int id;
    private int pv;
    private int faithTrackProgress;
    private boolean[] popesFavorCards;
    private int[] strongBox;
    private int[][] storage;

    private int[][] developMentSlots; // contengono gli id delle carte!
    private int firstLeader; //id della carta
    private int secondLeader; //id della carta
    private boolean firstLeaderIsPlayed;
    private boolean secondLeaderIsPlayed;

    //tutto quello che riguarda carte avrà 0 come id se non è presente la carta

    public PlayerUpdate(String nome, int id) {
        name=nome;
        this.id=id;
        pv=0;
        faithTrackProgress=0;
        popesFavorCards=new boolean[3];
        strongBox=new int[4];

        //OCCHIO A QUESTO, 0 fino a 2 sono i Depot normali, 3 e 4 sono i depotLeader! hanno valore zero
        //quando il giocarore non li ha
        storage=new int[5][2];


        developMentSlots=new int[3][3];
        firstLeader=0;
        secondLeader=0;
        firstLeaderIsPlayed=false;
        secondLeaderIsPlayed=false;
    }


    //getters e setters :O

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

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getFaithTrackProgress() {
        return faithTrackProgress;
    }

    public void setFaithTrackProgress(int faithTrackProgress) {
        this.faithTrackProgress = faithTrackProgress;
    }

    public boolean[] getPopesFavorCards() {
        return popesFavorCards;
    }

    public void setPopesFavorCards(boolean[] popesFavorCards) {
        this.popesFavorCards = popesFavorCards;
    }

    public int[] getStrongBox() {
        return strongBox;
    }

    public void setStrongBox(int[] strongBox) {
        this.strongBox = strongBox;
    }

    public int[][] getStorage() {
        return storage;
    }

    public void setStorage(int[][] storage) {
        this.storage = storage;
    }

    public int[][] getDevelopMentSlots() {
        return developMentSlots;
    }

    public void setDevelopMentSlots(int[][] developMentSlots) {
        this.developMentSlots = developMentSlots;
    }

    public int getFirstLeader() {
        return firstLeader;
    }

    public void setFirstLeader(int firstLeader) {
        this.firstLeader = firstLeader;
    }

    public int getSecondLeader() {
        return secondLeader;
    }

    public void setSecondLeader(int secondLeder) {
        this.secondLeader = secondLeder;
    }

    public boolean isFirstLeaderIsPlayed() {
        return firstLeaderIsPlayed;
    }

    public void setFirstLeaderIsPlayed(boolean firstLeaderIsPlayed) {
        this.firstLeaderIsPlayed = firstLeaderIsPlayed;
    }

    public boolean isSecondLeaderIsPlayed() {
        return secondLeaderIsPlayed;
    }

    public void setSecondLeaderIsPlayed(boolean secondLeaderIsPlayed) {
        this.secondLeaderIsPlayed = secondLeaderIsPlayed;
    }
}
