package model;

public class Storage {

    private model.DepotLevel[] levels;
    private int temp = 0;

    public void swapLevels(int index1, int index2) {
        /*
        aggiungere eccezione nel caso in cui la quantità di un livello ecceda la quantità massima dell'altro
        nel caso in cui non venga lanciata l'eccezione:
        per scambiare il contenuto di due DepotLevel è sufficiente invertire i rispettivi valori di maxQuantity
         */

        temp = levels[index1].getMaxQuantity();
        levels[index1].setMaxQuantity(levels[index2].getMaxQuantity());
        levels[index2].setMaxQuantity(temp);
    }

    public void add() {
        /*
        ...
         */
    }

    public void remove() {
        /*
        ...
         */
    }

    public void getLevel() {
        /*
        ...
         */
    }

    public void getStorage() {
        /*
        ...
         */
    }
}
