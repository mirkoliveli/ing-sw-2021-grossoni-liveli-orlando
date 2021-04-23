package it.polimi.ingsw.model;

//manca interazione con giocatori per il rapporto in vaticano
//manca un conteggio pv per le carte favore papale

public class FaithTrack {

    private int faithMarker;
    private PopesFavorCard first;
    private PopesFavorCard second;
    private PopesFavorCard third;
    private int totalpoints;
    private int popepoints;


    /**
     * constructor that should be used for the game
     * sets the marker at 0, sets the points used as tracking at 0, generates the PopesFavorCards
     * @author Riccardo Grossoni
     */

    public FaithTrack(){
        faithMarker=0;
        first = new PopesFavorCard(2);
        second = new PopesFavorCard(3);
        third = new PopesFavorCard(4);
        totalpoints = 0;
        popepoints = 0;
    }

    /**
     * constructor used for testing
     * @param initialstate the position of the marker at the start could not be 0
     * @author Riccardo Grossoni
     */
    public FaithTrack(int initialstate){
        faithMarker=initialstate;
        first = new PopesFavorCard(2);
        second = new PopesFavorCard(3);
        third = new PopesFavorCard(4);
        totalpoints = 0;
        popepoints = 0;
    }

    public int getFaithMarker() {
        return faithMarker;
    }

    public int getPopepoints(){ return popepoints;}

    /**
     * method used as QoL to make more simple understanding what it does inside other methods
     */
    public void increasePosition(){
        this.faithMarker++;
    }

    /**
     * method used to calculate the total points gained from the faith track
     * @return an int which represents the total points gained (from the PopesFavorCards and from the points on the grid)
     * @author Riccardo Grossoni
     */
    public int TotalVictoryPointsFaithTrack(){ return (this.getPopepoints()+this.getTotalpoints());}




    public void increasePosition(int faithPoints){
        for(int i=0; (i<faithPoints)||(this.faithMarker<24); i++){
            this.increasePosition();
        }


    }

    /**
     * method used inside the movement and the predict method
     * checks if a popeSpace is being crossed returns the position of the popeSpace or 0 if none is being crossed
     * @return returns the position of the popeSpace or 0 if none is being crossed
     * @author Riccardo Grossoni
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
                this.activatePopeSpace(popespace);
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
                    this.popepoints += 2;
                    }
                    else{
                    this.first.discard();
                    }
                    break;
            case 2: if(this.checkZone(zone)){
                    this.second.flip();
                    this.popepoints += 3;
                    }
                    else{
                    this.second.discard();
                    }
                    break;
            case 3: if(this.checkZone(zone)){
                    this.third.flip();
                    this.popepoints += 4;
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


    //serve a prevedere se con la prossima azione (viene passato il movimento totale come parametro) viene attivata
    //qualche zona papale, viene usato un parametro vettore nel remoto caso vengano attivate più zone con un solo movimento
    //questo


    public boolean[] predict(int move){
        boolean[] PopesZonesActivated = new boolean[3];
        int futurePosition=this.getFaithMarker() + move;
        if ((futurePosition>=8) && (!this.getFirst().isObtained()) && (this.getFirst().isDiscarded())) PopesZonesActivated[0]=true;
        if ((futurePosition>=16) && (!this.getSecond().isObtained()) && (this.getSecond().isDiscarded())) PopesZonesActivated[1]=true;
        if((futurePosition>=24) && (!this.getFirst().isObtained()) && (this.getSecond().isDiscarded())) PopesZonesActivated[2]=true;
        return PopesZonesActivated;
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
