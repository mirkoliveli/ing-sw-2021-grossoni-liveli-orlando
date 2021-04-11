package it.polimi.ingsw.model;

import static it.polimi.ingsw.model.TypeOfResource.coins;

public class Storage {
    private DepotLevel[] levels;
    private int temp = 0;

    public Storage() {
        DepotLevel[] levels = new DepotLevel[3];
        levels[0] = new DepotLevel(null, 0, 1);
        levels[1] = new DepotLevel(null, 0, 2);
        levels[2] = new DepotLevel(null, 0, 3);
    }

    public boolean swapLevels(int index1, int index2) {

        /*
        Nel caso in cui la quantità di un livello ecceda la quantità massima dell'altro viene mostrato un messaggio di errore
        Altrimenti per scambiare il contenuto di due DepotLevel è sufficiente invertire i rispettivi valori di maxQuantity
         */


        if ( levels[index1].getQuantity() > levels[index2].getMaxQuantity() || levels[index2].getQuantity() > levels[index1].getMaxQuantity() ) {
            System.out.println("Sorry, you cannot swap these depots!");
            return false; }
        else {
            temp = levels[index1].getMaxQuantity();
            levels[index1].setMaxQuantity(levels[index2].getMaxQuantity());
            levels[index2].setMaxQuantity(temp);
            return true; }
    }

    public boolean add(TypeOfResource ToR, int q, int index) {
        return levels[index].increaseQuantity(ToR, q);
    }

    public boolean remove(TypeOfResource ToR, int q, int index) {
        return levels[index].decreaseQuantity(ToR, q);
    }

    public TypeOfResource getType(int index) {
        return levels[index].getResourceType();
    }

    public int getLevel(int index) {
        return levels[index].getMaxQuantity();
    }

    public int getStorage(int index) {
        return levels[index].getQuantity();
    }

    public boolean checkDifferentTypes() {
        /* Returns true if all the depots store different types of resources */

        if ((getType(0) != getType (1)) && (getType(0) != getType(2)) && (getType(1) != getType(2))) {
            return true; }
        else { return false; }
    }

}
