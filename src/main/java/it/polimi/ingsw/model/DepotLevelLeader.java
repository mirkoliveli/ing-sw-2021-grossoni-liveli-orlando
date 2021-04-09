package it.polimi.ingsw.model;

public class DepotLevelLeader extends it.polimi.ingsw.model.DepotLevel {



    private final it.polimi.ingsw.model.TypeOfResource resourceTypeLeader; //stai creando un enum dentro una classe. fixa qui

    /*public DepotLevelLeader() {
        resourceTypeLeader = null;
    } */

    public DepotLevelLeader(it.polimi.ingsw.model.TypeOfResource resourceType) {
        this.resourceTypeLeader=resourceType;
    }


    public int getMaxQuantity() {
        return 2;
    }

    public void setMaxQuantity(int q) { // cosa vuol dire che non fa nulla? scopo?
        /* Non fa nulla */
    }


    public it.polimi.ingsw.model.TypeOfResource getResourceType() {
        return resourceTypeLeader;
    }

    public boolean increaseQuantity(it.polimi.ingsw.model.TypeOfResource ToR, int q) {

        /*
        Overriding of increaseQuantity from DepotLevel, with the following differences:
        - it uses resourceTypeLeader instead of resourceType
        - it guarantees that quantity doesn't exceed 2, which is the maximum value of leader depots
        - if quantity = 0 it doesn't set a new resource type, as resourceTypeLeader is final
         */

        if (ToR == this.getResourceType()) {
            if ((this.getQuantity() + q) <= 2) {
                this.setQuantity(this.getQuantity() + q);
                return true;
            } else {
                System.out.println("Sorry, you can only store " + (2 - this.getQuantity()) + " more " + ToR + " in this depot!");
                return false;
            }
        } else {
            System.out.println("Sorry, you cannot store " + ToR + " in this depot!");
            return false;
        }
    }


    public boolean decreaseQuantity(it.polimi.ingsw.model.TypeOfResource ToR, int q) {

        /*
        Overriding of decreaseQuantity from DepotLevel, using resourceTypeLeader instead of resourceType
         */

        if (ToR == this.getResourceType()) {
            if ((this.getQuantity() - q) >= 0) {
                this.setQuantity(this.getQuantity() - q) ;
                return true;
            } else {
                System.out.println("Sorry, you don't have enough " + ToR + "!");
                return false;
            }
        } else {
            System.out.println("Sorry, this depot does not contain " + ToR + "!");
            return false;
        }

    }
}