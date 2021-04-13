package it.polimi.ingsw.model;

public class SoloFaithTrack extends FaithTrack {
    private int enemy;

    public SoloFaithTrack(){
        super();
        enemy=0;
    }

    public int getEnemy() {
        return enemy;
    }

    public int  moveEnemy(int move){
        enemy+=move;
        if(enemy==25) enemy--;

        if(this.enemy==8 || (this.enemy==9 && move==2)){
            return 1;
        }
        else if((this.enemy==16) || (this.enemy==17 && move==2)) {
            return 2;
        }
        else if (this.enemy==24){ return 3;}

        else {return 0;}

    }



}
