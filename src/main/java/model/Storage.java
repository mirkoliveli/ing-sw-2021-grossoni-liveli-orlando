package model;

public class Storage {

    private model.DepotLevel[] levels;
    private int temp = 0;

    public void swapLevels(int index1, int index2) {

        /*
        Nel caso in cui la quantità di un livello ecceda la quantità massima dell'altro viene mostrato un messaggio di errore
        Altrimenti per scambiare il contenuto di due DepotLevel è sufficiente invertire i rispettivi valori di maxQuantity
         */

        if ( levels[index1].getQuantity() > levels[index2].getMaxQuantity() || levels[index2].getQuantity() > levels[index1].getMaxQuantity() ) {
            System.out.println("Sorry, you cannot swap these depots!"); }
        else {
            temp = levels[index1].getMaxQuantity();
            levels[index1].setMaxQuantity(levels[index2].getMaxQuantity());
            levels[index2].setMaxQuantity(temp); }
    }

    public boolean add(model.TypeOfResource ToR, int q, int index) {
        return levels[index].increaseQuantity(ToR, q);
    }

    public boolean remove(model.TypeOfResource ToR, int q, int index) {
        return levels[index].decreaseQuantity(ToR, q);
    }

    public model.TypeOfResource getType(int index) {
        return levels[index].getResourceType();
    }

    public int getLevel(int index) {
        return levels[index].getMaxQuantity();
    }

    public int getStorage(int index) {
        return levels[index].getQuantity();
    }

}
