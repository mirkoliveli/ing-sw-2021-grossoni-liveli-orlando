package it.polimi.ingsw.model;

public class DepotLevelLeader extends it.polimi.ingsw.model.DepotLevel {



    private final TypeOfResource resourceTypeLeader; //stai creando un enum dentro una classe. fixa qui

    public DepotLevelLeader() {
        resourceTypeLeader = null;
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
        Overriding di increaseQuantity di DepotLevel, da cui differisce perché:
        - viene utilizzato resourceTypeLeader al posto di resourceType
        - viene controllato che non si ecceda 2, valore fisso del deposito dei leader
        - se quantity = 0 non viene cambiato il tipo di risorsa contenuto, essendo un attributo finale
         */

        if (ToR == this.resourceTypeLeader) {
            if ((this.getQuantity() + q) <= 2) {
                this.setQuantity(this.getQuantity() + q);
                return true;
            } else {
                System.out.println("Sorry, you can only store" + (2 - this.getQuantity()) + "more" + ToR + "in this depot!");
                return false;
            }
        } else {
            System.out.println("Sorry, you cannot store" + ToR + "in this depot!");
            return false;
        }
    }


    public boolean decreaseQuantity(it.polimi.ingsw.model.TypeOfResource ToR, int q) {

        /*
        Overriding di decreaseQuantity di DepotLevel, usa resourceTypeLeader al posto di resourceType
         */

        if (ToR == this.resourceTypeLeader) {
            if ((this.getQuantity() - q) >= 0) {
                this.setQuantity(this.getQuantity() - q) ;
                return true;
            } else {
                System.out.println("Sorry, you don't have enough" + ToR + "!");
                return false;
            }
        } else {
            System.out.println("Sorry, this depot does not contain" + ToR + "!");
            return false;
        }

    }
}