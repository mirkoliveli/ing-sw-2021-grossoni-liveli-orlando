package it.polimi.ingsw.model;

//manca interazione con giocatori per il rapporto in vaticano
//manca un conteggio pv per le carte favore papale

public class FaithTrack {

    private final PopesFavorCard first;
    private final PopesFavorCard second;
    private final PopesFavorCard third;
    private int faithMarker;
    private int onlyTrackPoints;
    private int popepoints;


    /**
     * constructor that should be used for the game
     * sets the marker at 0, sets the points used as tracking at 0, generates the PopesFavorCards
     *
     * @author Riccardo Grossoni
     */

    public FaithTrack() {
        faithMarker = 0;
        first = new PopesFavorCard(2);
        second = new PopesFavorCard(3);
        third = new PopesFavorCard(4);
        onlyTrackPoints = 0;
        popepoints = 0;
    }

    /**
     * constructor used for testing
     *
     * @param initialstate the position of the marker at the start could not be 0
     * @author Riccardo Grossoni
     */
    public FaithTrack(int initialstate) {
        faithMarker = initialstate;
        first = new PopesFavorCard(2);
        second = new PopesFavorCard(3);
        third = new PopesFavorCard(4);
        onlyTrackPoints = 0;
        popepoints = 0;
    }

    public int getFaithMarker() {
        return faithMarker;
    }

    public int getPopepoints() {
        return popepoints;
    }

    /**
     * method used as QoL to make more simple to understands what it does inside other methods
     */
    public void increasePosition() {
        this.faithMarker++;
    }

    /**
     * method used to calculate the total points gained from the faith track
     *
     * @return an int which represents the total points gained (from the PopesFavorCards and from the points on the grid)
     * @author Riccardo Grossoni
     */
    public int TotalVictoryPointsFaithTrack() {
        return (this.getPopepoints() + this.getOnlyTrackPoints());
    }

    public void increasePopePoints(int points) {
        this.popepoints = this.popepoints + points;
    }

    /**
     * override that isn't used anymore.
     *
     * @param faithPoints
     */
    public void increasePosition(int faithPoints) {
        for (int i = 0; (i < faithPoints) || (this.faithMarker < 24); i++) {
            this.increasePosition();
        }
    }

    /**
     * method used inside the movement and the predict method
     * checks if a popeSpace is being crossed returns the position of the popeSpace or 0 if none is being crossed
     *
     * @return returns the position of the popeSpace or 0 if none is being crossed
     * @author Riccardo Grossoni
     */
    public int checkPopeSpace() {
        if (this.faithMarker == 8) return 1;
        else if (this.faithMarker == 16) return 2;
        else if (this.faithMarker == 24) return 3;
        else return 0;
    }

    /**
     * method used to check if we can "activate" or we have to discard the Pope Card after a vatican report is activated
     *
     * @param index specifies what "pope zone" is being checked
     * @return returns true if the markers meets the requirements for unlocking the Pope's favor card, false if it doesn't
     * @author Riccardo Grossoni
     */
    public boolean checkZone(int index) {
        switch (index) {
            case 1:
                return (faithMarker >= 5);
            case 2:
                return (faithMarker >= 12);
            case 3:
                return (faithMarker >= 19);
            default:
                return false;
        }
    }


    /**
     * method that returns the actual victory points reached while the marker is moving
     *
     * @return total points gained from advancing in the faith track, -1 if after the movement the score hasn't changed
     * @author Riccardo Grossoni
     */
    public int updateScore() {
        switch (faithMarker) {
            case 24:
                return 20;

            case 21:
                return 16;

            case 18:
                return 12;

            case 15:
                return 9;

            case 12:
                return 6;

            case 9:
                return 4;

            case 6:
                return 2;

            case 3:
                return 1;

            default:
                return -1;
        }
    }

    /**
     * method used to perform the movement after gaining faith points.
     * at the moment it only "activate" the Pope card of the player performing the movement, so it needs to be updated
     *
     * @param move specifies how much the faithmarker needs to advance
     * @author Riccardo Grossoni
     */

    // Ho aggiornato il metodo controllando che PopesFavorCard non sia già stata scartata prima di lanciare activatePopeSpace
    // Com'era scritto prima era possibile ottenere la prima dopo averla scartata, durante il favore papale della seconda zona
    public void Movement(int move) {
        int popespace;
        int temp_pv = 0;
        for (int i = 0; (i < move) && (faithMarker < 24); i++) {
            temp_pv = 0;
            this.increasePosition();
            popespace = this.checkPopeSpace();
            if (popespace == 1 && !this.getFirst().isDiscarded() && !this.getFirst().isObtained()) {
                this.activatePopeSpace(1);
            }
            if (popespace == 2 && !this.getSecond().isDiscarded() && !this.getSecond().isObtained()) {
                this.activatePopeSpace(2);
            }
            if (popespace == 3 & !this.getThird().isDiscarded() && !this.getThird().isObtained()) {
                this.activatePopeSpace(3);
            }
            temp_pv = this.updateScore();

            if (temp_pv > 0) {
                this.onlyTrackPoints = temp_pv;
            }
        }
    }


    public int getOnlyTrackPoints() {
        return this.onlyTrackPoints;
    }

    /**
     * method used to activate the zone (or discard the card). it doesn't check if the zone is already activated, as it
     * should be done before invoking this method
     *
     * @param zone specifies which of the three zones is being activated
     * @author Riccardo Grossoni
     */
    public void activatePopeSpace(int zone) {
        switch (zone) {
            case 1:
                if (this.checkZone(zone)) {
                    this.first.flip();
                    this.popepoints += 2;
                } else {
                    this.first.discard();
                }
                break;
            case 2:
                if (this.checkZone(zone)) {
                    this.second.flip();
                    this.popepoints += 3;
                } else {
                    this.second.discard();
                }
                break;
            case 3:
                if (this.checkZone(zone)) {
                    this.third.flip();
                    this.popepoints += 4;
                } else {
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

    /**
     * method used to "predict" before making the movement, if a vatican report is being activated with this move. it also checks if
     * it was already active.
     * at the moment it verifies if even two or three zones are being activated at once, which is impossible in the normal game
     * but if an extention to the game is made this section is already covered
     *
     * @param move specifies the move the marker is going to make
     * @return a boolean vector of length 3, where true in any of the spaces means that this zone is being activated
     * @author Riccardo Grossoni
     */
    public boolean[] predict(int move) {
        boolean[] PopesZonesActivated = new boolean[3];
        int futurePosition = this.getFaithMarker() + move;
        if ((futurePosition >= 8) && (!this.getFirst().isObtained()) && (!this.getFirst().isDiscarded()))
            PopesZonesActivated[0] = true;
        if ((futurePosition >= 16) && (!this.getSecond().isObtained()) && (!this.getSecond().isDiscarded()))
            PopesZonesActivated[1] = true;
        if ((futurePosition >= 24) && (!this.getFirst().isObtained()) && (!this.getSecond().isDiscarded()))
            PopesZonesActivated[2] = true;
        return PopesZonesActivated;
    }


    /**
     * method that moves the marker on the track, updates the score given by the track and checks if any popespace han been activated, returns the activated space
     * @param move movement made (faith points received)
     * @return  vatican report to be activated, zero if none
     */
    public int MultiPlayerMovement(int move) {
        int popespace;
        int temp_pv = 0;
        int activezone = 0;
        for (int i = 0; (i < move) && (faithMarker < 24); i++) {
            temp_pv = 0;
            this.increasePosition();
            popespace = this.checkPopeSpace();
            if (popespace == 1 && !this.getFirst().isDiscarded() && !this.getFirst().isObtained()) {
                activezone = 1;
            }
            if (popespace == 2 && !this.getSecond().isDiscarded() && !this.getSecond().isObtained()) {
                activezone = 2;
            }
            if (popespace == 3 & !this.getThird().isDiscarded() && !this.getThird().isObtained()) {
                activezone = 3;
            }
            temp_pv = this.updateScore();

            if (temp_pv > 0) {
                this.onlyTrackPoints = temp_pv;
            }
        }

        return activezone;
    }

    //----------------------------------------------------------------------------------------------------------------------

    /**
     * useful for debug, prints a state of the track
     */
    public void printstate() {
        System.out.println("faithmarker: " + this.getFaithMarker());
        if (this.first.isDiscarded()) {
            System.out.println("primo scartato!");
        } else {
            System.out.println("primo NON scartato!");
        }
        if (this.second.isDiscarded()) {
            System.out.println("secondo scartato!");
        } else {
            System.out.println("secondo NON scartato!");
        }
        if (this.third.isDiscarded()) {
            System.out.println("terzo scartato!");
        } else {
            System.out.println("terzo NON scartato!");
        }

        if (this.first.isObtained()) {
            System.out.println("primo ottenuto!");
        } else {
            System.out.println("primo NON ottenuto!");
        }
        if (this.second.isObtained()) {
            System.out.println("secondo ottenuto!");
        } else {
            System.out.println("secondo NON ottenuto!");
        }
        if (this.third.isObtained()) {
            System.out.println("terzo ottenuto!");
        } else {
            System.out.println("terzo NON ottenuto!");
        }
    }

    public void CoolPrint() {
        int tempPosition = this.getFaithMarker();
        int pos = 0;
        while (pos < tempPosition) {
            System.out.print("  ");
            pos++;
        }
        System.out.print("M\n");
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
        System.out.println("                P               P               P");
    }


    /**
     * method that is used to update the status inside a PlayerUpdate
     * <br><br>
     * note that the method does NOT signal a difference between discarded and not reached zones, the only difference that is reported is if the card has been acquired or not.
     *
     * @return a boolean array stating which popeCards have been activated
     */
    public boolean[] popeCardsStatus() {
        boolean[] status = new boolean[3];
        status[0] = this.getFirst().isObtained();
        status[1] = this.getSecond().isObtained();
        status[2] = this.getThird().isObtained();
        return status;
    }


    //----------------------------------------------------------------------------------------------------------------------
    //singleplayer methods
    public boolean DoIActivateTheZone(int zone) {
        return this.getFaithMarker() < (zone * 8);
    }


}
