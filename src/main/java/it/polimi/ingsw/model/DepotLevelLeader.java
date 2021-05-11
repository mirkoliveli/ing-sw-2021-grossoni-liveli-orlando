package it.polimi.ingsw.model;

public class DepotLevelLeader extends DepotLevel {

    private TypeOfResource resourceTypeLeader;


    public DepotLevelLeader(TypeOfResource resourceType) {
        this.resourceTypeLeader = resourceType;
        setMaxQuantity(2);
        setQuantity(0);
        setResourceType(resourceType);
    }

    @Override
    public void setQuantity(int quantity) {
        super.setQuantity(quantity);
        setResourceType(resourceTypeLeader);
    }

    public int getMaxQuantity() {
        return 2;
    }

    public void setMaxQuantity(int q) {
        /* override */
    }

    public TypeOfResource getResourceTypeLeader() {
        return resourceTypeLeader;
    }

    public void setResourceTypeLeader(TypeOfResource resourceTypeLeader) {
        this.resourceTypeLeader = resourceTypeLeader;
    }

    public TypeOfResource getResourceType() {
        return resourceTypeLeader;
    }

    public boolean increaseQuantity(TypeOfResource ToR, int q) {

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


    public boolean decreaseQuantity(TypeOfResource ToR, int q) {

        /*
        Overriding of decreaseQuantity from DepotLevel, using resourceTypeLeader instead of resourceType
         */

        if (ToR == this.getResourceType()) {
            if ((this.getQuantity() - q) >= 0) {
                this.setQuantity(this.getQuantity() - q);
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