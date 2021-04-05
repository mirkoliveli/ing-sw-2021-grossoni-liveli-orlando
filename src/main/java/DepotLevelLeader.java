package model;

public class DepotLevelLeader extends model.DepotLevel {

    private final model.TypeOfResource resourceTypeLeader;

    public int getMaxQuantity() {
        /*DepotLevelLeader non ha MaxQuantity*/
    }

    public void setMaxQuantity(int q) {
        /*...*/
    }

    public model.TypeOfResource getResourceType() {
        return resourceTypeLeader;
    }

    public boolean increaseQuantity(TypeofResource ToR, int q) {

        /*
        Overriding di increaseQuantity di DepotLevel, da cui differisce perché:
        - viene utilizzato resourceTypeLeader al posto di resourceType
        - non viene controllato che non si ecceda maxQuantity, perché il deposito dei leader è illimitato
        - se quantity = 0 non viene cambiato il tipo di risorsa contenuto, essendo un attributo finale
         */

        if(ToR == this.resourceTypeLeader) {
            this.quantity = this.quantity + q;
            return true; }

        else { System.out.println("Sorry, you cannot store" + ToR + "in this depot!");
            return false; }
    }

    public boolean decreaseQuantity(TypeofResource ToR, int q) {

        /*
        Overriding di decreaseQuantity di DepotLevel, usa resourceTypeLeader al posto di resourceType
         */

        if(ToR == this.resourceTypeLeader) {
            if ((this.quantity - q) > 0) { this.quantity = this.quantity - q;
                return true; }
            else { System.out.println("Sorry, you don't have enough" + ToR + "!");
                return false; }
        }
        else { System.out.println("Sorry, this depot does not contain" + ToR + "!");
            return false; }

}
