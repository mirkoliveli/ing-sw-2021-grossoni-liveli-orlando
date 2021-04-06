package model;

public class FaithTrack {

    private int faithMarker;
    private PopesFavorCard[] popesFavorCards;
    private int totalpoints;
    //mettere costrutture U.U


    public int getFaithMarker() {
        return faithMarker;
    }

    //prob useless
    public PopesFavorCard[] getPopesFavorCards() {
        return popesFavorCards;
    }

    public void increasePosition(){
        this.faithMarker++;
    }

    /**
     * overload prob inutile?
     * @param faithPoints
     * @return
     */
    public void increasePosition(int faithPoints){
        for(int i=0; (i<faithPoints)||(this.faithMarker<24); i++){
            this.increasePosition();
        }


    }

    /**
     * ritorna quale popespace è stato superato, 0 nel caso di nessuno
     * @return
     */
    public int checkPopeSpace(){
        if(this.faithMarker==8) return 1;
        else if(this.faithMarker==16) return 2;
        else if(this.faithMarker==24) return 3;
        else return 0;
    }

    /**
     * controlla se dobbiamo scartare o girare la carta favore papale
     * @param index
     * @return
     */
    public boolean checkZone(int index){
        switch (index){
            case 1: return (faithMarker>=5);
            case 2: return (faithMarker>=12);
            case 3: return (faithMarker>=19);
            default: return false;
        }
    }


    /**
     * funzione che controlla se il marker ha appena superato una casella che fornisce punti vittoria aggiuntivi
     * @return
     */
    public int updateScore(){
        switch (faithMarker){
            case 24: return 20;

            case 21: return 16;

            case 18: return 12;

            case 15: return 9;

            case 12: return 6;

            case 9: return 4;

            case 6: return 2;

            case 3: return 1;

            default: return -1;
        }
    }

    public void Movement(int move){
        int popespace;
        int temp_pv=0;
        for(int i=0; (i < move)&&(faithMarker<24); i++){
            temp_pv=0;
            this.increasePosition();
            popespace=this.checkPopeSpace();
            if(popespace!=0){
                //attiva pope's favor, controllando prima se non è già stata attivata
            }

            temp_pv=this.updateScore();

            if(temp_pv>0){
                this.totalpoints=temp_pv;
            }


        }



    }



}
