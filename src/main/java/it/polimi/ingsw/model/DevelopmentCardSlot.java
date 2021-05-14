package it.polimi.ingsw.model;

public class DevelopmentCardSlot {

    // In each slot, the max number of DevelopmentCard is 3
    // The slot is managed as an array
    // where the cards are overlapped based on their level

    private final DevelopmentCard[] vectorSlot;
    private int slotPV;

    public DevelopmentCardSlot() {
        this.vectorSlot = new DevelopmentCard[3];
    }

    public int getSlotPV() {
        return slotPV;
    }

    public void setSlotPV(int slotPV) {
        this.slotPV = slotPV;
    }

    // Method that returns the list of colours appeared in the slot
    public Color[] get_colors() {
        Color[] ColorAppeared = new Color[3];

        for (int i = 0; i < 3; i++) {
            ColorAppeared[i] = vectorSlot[i].getColor();
        }

        return ColorAppeared;
    }

    // Method that returns the colour of the level 2 DevelopmentCard
    // This method could be used to activate Leaders effect
    public Color get_secondLevelColor() {
        return vectorSlot[2].getColor();
    }


    /**
     * method that returns the "active" card on the slot, returns null if the slot is empty
     * @return null if slot is empty, the card otherwise
     */
    public DevelopmentCard get_top() {

        int i = 2;

        /* The method skims the cards starting from the top (supposed 2nd postion)
            until it finds a card. Returns it
         */
        while (vectorSlot[i] == null && i > 0) {
            i--;
        }

        if (vectorSlot[0] == null) return null;

        return vectorSlot[i];
    }

    /**
     * method that places the card on the slot, returns true if the operation is done correctly, false otherwise
     * @param CardToPlace card "acquired"
     * @return true if operation is done successfully, false otherwise
     */
    public boolean placeCard(DevelopmentCard CardToPlace) {
        int i = 0;

        //if the slot is full or the level of the card to place is lower than the one of the card on the top
        //it's been reported to the user and the method returns false

        if (get_top() != null && (this.get_top().getLevel() == 3 || CardToPlace.getLevel() <= this.get_top().getLevel())) {
            System.out.println("Operation not allowed!");
            if (this.get_top().getLevel() == 3) {
                System.out.println("The slot is FULL!\nPlease select another one");
            } else {
                System.out.println("The card on the top has a higher level.\nPlease select another slot");
            }
            return false;

        } else {
            while (vectorSlot[i] != null && i < 3) {
                i++;
            }
            vectorSlot[i] = new DevelopmentCard(CardToPlace);
            return true;
        }
    }

    /**
     * method that updates the victory points of the slot
     */
    // Method that set the slot's total amount of victory point
    public void pvSlot() {
        int totpv = getSlotPV();
        int position = 0;
        while (vectorSlot[position] != null) {
            totpv = totpv + vectorSlot[position].getPv();
            position++;
        }
        setSlotPV(totpv);
    }


    /**
     * method that returns the level of the top card, if the slot is empty returns zero.
     * @return 0-1-2-3
     */
    public int levelOfTop() {
        if (this.get_top() != null) return this.get_top().getLevel();
        else return 0;
    }

    public DevelopmentCard getCard(int stacked) {
        if (vectorSlot[stacked] != null) return vectorSlot[stacked];
        else return null;
    }
}

