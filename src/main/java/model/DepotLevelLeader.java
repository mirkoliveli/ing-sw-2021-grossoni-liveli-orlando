package model;

public class DepotLevelLeader extends model.DepotLevel {

    private final model.TypeOfResource resourceTypeLeader;

    public int getMaxQuantity() {
        return 2;
    }

    public void setMaxQuantity(int q) {
        /* Non fa nulla */
    }

    public model.TypeOfResource getResourceType() {
        return resourceTypeLeader;
    }

    public boolean increaseQuantity(model.TypeOfResource ToR, int q) {

        /*
        Overriding di increaseQuantity di DepotLevel, da cui differisce perché:
        - viene utilizzato resourceTypeLeader al posto di resourceType
        - viene controllato che non si ecceda 2, valore fisso del deposito dei leader
        - se quantity = 0 non viene cambiato il tipo di risorsa contenuto, essendo un attributo finale
         */

        if(ToR == this.resourceTypeLeader) {
            if((this.quantity+q) <= 2) { this.quantity = this.quantity + q;
                return true; }
            else { System.out.println("Sorry, you can only store" + (2 - this.quantity) + "more" + ToR + "in this depot!");
                return false; }
        }

        else { System.out.println("Sorry, you cannot store" + ToR + "in this depot!");
            return false; }
    }
}

    public boolean decreaseQuantity(model.TypeOfResource ToR, int q) {

        /*
        Overriding di decreaseQuantity di DepotLevel, usa resourceTypeLeader al posto di resourceType
         */

        if(ToR == this.resourceTypeLeader) {
            if ((this.quantity - q) >= 0) { this.quantity = this.quantity - q;
                return true; }
            else { System.out.println("Sorry, you don't have enough" + ToR + "!");
                return false; }
        }
        else { System.out.println("Sorry, this depot does not contain" + ToR + "!");
            return false; }

    }