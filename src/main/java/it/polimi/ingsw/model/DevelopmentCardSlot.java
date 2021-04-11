package it.polimi.ingsw.model;

public class DevelopmentCardSlot {

    //Le carte sviluppo in ogni slot possono essere max 3
    //Viene quindi gestito come un array
    //dove le carte vengono ovrapposte in base al loro livello
    private final DevelopmentCard[] vectorSlot;


    public DevelopmentCardSlot() {
        this.vectorSlot = new DevelopmentCard[3];
    }


    //metodo che ritorna la lista dei colori apparsi nello slot
    public Color[] get_colors() {
        Color[] ColorAppeared = new Color[3];

        for (int i = 0; i < 3; i++) {
            ColorAppeared[i] = vectorSlot[i].getColor();
        }

        return ColorAppeared;
    }

    //metodo che ritorna il colore della carta sviluppo di livello 2
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

    public void placeCard(DevelopmentCard CardToPlace) {
        int i = 0;
        //contorllo che il livello della carta da piazzare sia superiore al livello della carta già presente
        if (get_top() != null && CardToPlace.getLevel() <= this.get_top().getLevel()) {
            System.out.println("Operation not allowed!");
        } e                                                                                                                                                                                                                                                               lse {
            while (vectorSlot[i] != null && i < 3) {
                i++;
            }
            vectorSlot[i] = CardToPlace;
        }
    }

}

