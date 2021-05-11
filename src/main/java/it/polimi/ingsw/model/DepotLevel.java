package it.polimi.ingsw.model;

public class DepotLevel {

    private TypeOfResource resourceType;
    private int quantity;
    private int maxQuantity;

    public DepotLevel() {
        resourceType = null;
        quantity = 0;
        maxQuantity = 0;
    }

    public DepotLevel(int max) {
        resourceType = null;
        quantity = 0;
        maxQuantity = max;
    }

    public DepotLevel(TypeOfResource resourceType, int quantity, int maxQuantity) {
        this.resourceType = resourceType;
        this.quantity = quantity;
        this.maxQuantity = maxQuantity;
    }

    public TypeOfResource getResourceType() {
        if (this.getQuantity() == 0) {
            return null;
        } else {
            return resourceType;
        }
    }


    public void setResourceType(TypeOfResource ToR) {
        this.resourceType = ToR;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        if (quantity == 0) resourceType = null;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int q) {
        this.maxQuantity = q;
    }

    public boolean increaseQuantity(TypeOfResource ToR, int q) {

        /*
        This methods adds q resources of the TypeOfResource ToR
        It guarantees that ToR corresponds to the depot's resource type and quantity+q is less than or equal to maxQuantity before doing so
        If ToR doesn't correspond to the depot's resource type and quantity = 0, it sets a new resourceType
        If the increase ends well the method returns true, otherwise it prints an error message and returns false
         */

        if (ToR == this.getResourceType()) {
            if ((this.getQuantity() + q) <= this.getMaxQuantity()) {
                this.setQuantity(this.getQuantity() + q);
                return true;
            } else {
                System.out.println("Sorry, you can only store " + (this.getMaxQuantity() - this.getQuantity()) + " more " + ToR + " in this depot!");
                return false;
            }
        } else {
            if (this.getQuantity() == 0) {
                this.setResourceType(ToR);
                if ((this.getQuantity() + q) <= this.getMaxQuantity()) {
                    this.setQuantity(this.getQuantity() + q);
                    return true;
                } else {
                    System.out.println("Sorry, you can only store " + (this.getMaxQuantity() - this.getQuantity()) + " more " + ToR + " in this depot!");
                    return false;
                }
            } else {
                System.out.println("Sorry, you cannot store " + ToR + " in this depot!");
                return false;
            }
        }
    }

    public boolean decreaseQuantity(TypeOfResource ToR, int q) {

        /*
        This methods removes q resources of the TypeOfResource ToR
        It guarantees that ToR corresponds to the depot's resource type and quantity is greater than q before doing so
        If the decrease ends well the method returns true, otherwise it prints an error message and returns false
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
