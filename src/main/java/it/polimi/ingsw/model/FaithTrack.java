package it.polimi.ingsw.model;

public class FaithTrack {

    private int faithMarker;
    private PopesFavorCard first;
    private PopesFavorCard second;
    private PopesFavorCard third;
    private int totalpoints;


    public FaithTrack(){
        faithMarker=0;
        first = new PopesFavorCard(2);
        second = new PopesFavorCard(3);
        third = new PopesFavorCard(4);
        totalpoints = 0;
    }

    /**
     * costruttore per velocizzare tests
     * @param initialstate
     */
    public FaithTrack(int initialstate){
        faithMarker=initialstate;
        first = new PopesFavorCard(2);
        second = new PopesFavorCard(3);
        third = new PopesFavorCard(4);
        totalpoints = 0;
    }

    public int getFaithMarker() {
        return faithMarker;
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


    public int getTotalpoints(){
        return this.totalpoints;
    }


    public void activatePopeSpace(int zone){
        switch (zone){
            case 1: if(this.checkZone(zone)){
                    this.first.flip();
                    }
                    else{
                    this.first.discard();
                    }
                    break;
            case 2: if(this.checkZone(zone)){
                    this.second.flip();
                    }
                    else{
                    this.second.discard();
                    }
                    break;
            case 3: if(this.checkZone(zone)){
                    this.third.flip();
                    }
                    else{
                    this.third.discard();
                    }
                    break;

            }
    }

    public PopesFavorCard getFirst() {
        return first;
    }
    public PopesFavorCard getSecond() {
        return second;
    }
    public PopesFavorCard getThird() {
        return third;
    }

    //----------------------------------------------------------------------------------------------------------------------
    /**
     * util per debug
     */
    public void printstate(){
        System.out.println("faithmarker: " + this.getFaithMarker());
        if(this.first.isDiscarded()){
            System.out.println("primo scarato!");
        }
        else{
            System.out.println("primo NON scarato!");
        }
        if(this.second.isDiscarded()){
            System.out.println("secondo scarato!");
        }
        else{
            System.out.println("secondo NON scarato!");
        }
        if(this.third.isDiscarded()){
            System.out.println("terzo scarato!");
        }
        else{
            System.out.println("terzo NON scarato!");
        }

        if(this.first.isObtained()){
            System.out.println("primo ottenuto!");
        }
        else{
            System.out.println("primo NON ottenuto!");
        }
        if(this.second.isObtained()){
            System.out.println("secondo ottenuto!");
        }
        else{
            System.out.println("secondo NON ottenuto!");
        }
        if(this.third.isObtained()){
            System.out.println("terzo ottenuto!");
        }
        else{
            System.out.println("terzo NON ottenuto!");
        }
    }

}
