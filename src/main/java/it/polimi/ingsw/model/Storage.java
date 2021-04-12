package it.polimi.ingsw.model;

import static it.polimi.ingsw.model.TypeOfResource.coins;

public class Storage {

    private DepotLevel level1 = new DepotLevel();
    private DepotLevel level2 = new DepotLevel();
    private DepotLevel level3 = new DepotLevel();


    public Storage() {
        this.level1.setMaxQuantity(1);
        this.level2.setMaxQuantity(2);
        this.level3.setMaxQuantity(3);
    }

    public boolean swapLevels(int index1, int index2) {

        int temp;
        TypeOfResource temp_resource;

        if ( getLevel(index1).getQuantity() > getLevel(index2).getMaxQuantity() || getLevel(index2).getQuantity() > getLevel(index1).getMaxQuantity() ) {
            System.out.println("Sorry, you cannot swap these depots!");
            return false; }
        else {
            temp_resource = getLevel(index1).getResourceType();
            getLevel(index1).setResourceType(getLevel(index2).getResourceType());
            getLevel(index2).setResourceType(temp_resource);
            temp = getLevel(index1).getQuantity();
            getLevel(index1).setQuantity(getLevel(index2).getQuantity());
            getLevel(index2).setQuantity(temp);
            return true; }
    }


    public DepotLevel getLevel(int index) {
        switch (index){
            case 1: return level1;
            case 2: return level2;
            case 3: return level3;
            default: return null;
        }
    }

    public boolean checkDifferentTypes() {
        /* Returns true if all the depots store different types of resources */

        if ((level1.getResourceType() != level2.getResourceType()) && (level1.getResourceType() != level3.getResourceType()) && (level2.getResourceType() != level3.getResourceType())) {
            return true; }
        else { return false; }
    }

}
