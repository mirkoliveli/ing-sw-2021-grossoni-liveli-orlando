package model;

public class DepotLevel {

    private TypeOfResource resourceType;
    private int quantity;
    private int maxQuantity;


    public TypeOfResource getResourceType() {
        return resourceType;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setResourceType(TypeofResource ToR) {
        /*
        aggiungere eccezione nel caso in cui ToR sia diverso da resourceType e quantity sia maggiore di 0
         */
        this.resourceType = ToR;
    }

    public void setMaxQuantity(int q) {
        this.maxQuantity = q;
    }

    public void increaseQuantity(TypeofResource ToR, int q) {

        /*
        aggiungere eccezione nel caso in cui (quantity + q) superi maxQuantity
        aggiungere eccezione nel caso in cui ToR sia diverso da TypeofResource (a meno che quantity = 0)
        se ToR != TypeofResource e quantity = 0 invocare setResourceType
         */

        this.quantity = this.quantity + q;
    }

    public void decreaseQuantity(TypeofResource ToR, int q) {

        /*
        aggiungere eccezione nel caso in cui (quantity - q) sia inferiore di 0
        aggiungere eccezione nel caso in cui ToR sia diverso da TypeofResource
         */

        this.quantity = this.quantity - q;
    }
}
