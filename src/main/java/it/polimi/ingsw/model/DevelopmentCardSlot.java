package it.polimi.ingsw.model;

public class DevelopmentCardSlot {

    // In each slot, the max number of DevelopmentCard is 3
    // The slot is managed as an array
    // where the cards are overlapped based on their level

    private final DevelopmentCard[] vectorSlot;
    private int slotPV;

    public int getSlotPV() {
        return slotPV;
    }

    public void setSlotPV(int slotPV) {
        this.slotPV = slotPV;
    }


    public DevelopmentCardSlot() {
        this.vectorSlot = new DevelopmentCard[3];
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

    public DevelopmentCard get_top() {

        int i = 2;
        //scorro le carte a partie dalla cima (presunta posizione 2)
        //fin quando non trovo una carta. Ritorno la prima trovata
        while (vectorSlot[i] == null) {
            i--;
        }

        return vectorSlot[i];
    }

    public boolean placeCard(DevelopmentCard CardToPlace) {
        int i = 0;

        //if the slot is full or the level of the card to place is lower than the one of the card on the top
        //it's been reported to the user and the method returns false

        if (get_top() != null && (this.get_top().getLevel() == 3 || CardToPlace.getLevel() <= this.get_top().getLevel())) {
            System.out.println("Operation not allowed!");
            if (this.get_top().getLevel() == 3) {
                System.out.println("The slot is FULL!\nPlease select another one");
            }
            else {
                System.out.println("The card on the top has a higher level.\nPlease select another slot");
            }
            return false;

        } else {
            while (vectorSlot[i] != null && i < 3) {
                i++;
            }
            vectorSlot[i] = CardToPlace;
            return true;
        }
    }

    // Method that set the slot's total amount of victory point
    public void pvSlot (){
        int totpv = getSlotPV();
        int position = 0;
        while (vectorSlot[position] != null){
            totpv = totpv + vectorSlot[position].getPv();
            position++;
        }
        setSlotPV(totpv);
    }

}

