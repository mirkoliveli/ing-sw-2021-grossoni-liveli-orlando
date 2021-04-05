package model;

public class DepotLevel {

    private model.TypeOfResource resourceType;
    private int quantity;
    private int maxQuantity;


    public model.TypeOfResource getResourceType() {
        /*aggiungere che se quantity = 0 ritorna NULL*/
        return resourceType; }

    public int getQuantity() { return quantity; }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setResourceType(TypeofResource ToR) { this.resourceType = ToR; }

    public void setMaxQuantity(int q) {
        this.maxQuantity = q;
    }

    public boolean increaseQuantity(TypeofResource ToR, int q) {

        /*
        Controlla che ToR corrisponda al tipo di risorsa del deposito e la quantità da aggiungere non ecceda le dimensioni massime consentite
        Se queste condizioni sono rispettate, la quantità viene incrementata di q
        Se ToR non corrisponde al tipo di risorsa del deposito ma la quantità vale 0, il tipo di risorsa del deposito diventa ToR e si controlla se la quantità da aggiungere non eccede le dimensioni massime consentite
        Se si prova ad aggiungere una risorsa di un tipo non consentito o se si eccede nella quantità viene mostrato il messaggio d'errore corrispondente e si restituisce false
         */

        if(ToR == this.resourceType) {
            if((this.quantity+q) <= this.maxQuantity) { this.quantity = this.quantity + q;
                return true; }
            else { System.out.println("Sorry, you can only store" + (this.maxQuantity - this.quantity) + "more" + ToR + "in this depot!");
                return false; }
        }
        else { if (this.quantity==0) {
                this.setResourceType(ToR);
                if ((this.quantity + q) <= this.maxQuantity) {
                    this.quantity = this.quantity + q;
                    return true; }
                else { System.out.println("Sorry, you can only store" + (this.maxQuantity - this.quantity) + "more" + ToR + "in this depot!");
                    return false; }
            }
            else { System.out.println("Sorry, you cannot store" + ToR + "in this depot!");
            return false; }
        }
    }

    public boolean decreaseQuantity(TypeofResource ToR, int q) {

        /*
        Controlla che ToR corrisponda al tipo di risorsa del deposito e la quantità sia maggiore di quanto si vuole decrementare il numero di risorse
        Se queste condizioni sono rispettate, la quantità viene decrementata di q e viene restituito true
        altrimenti viene mostrato il messaggio di errore corrispondente e viene restituito false
         */

        if(ToR == this.resourceType) {
            if ((this.quantity - q) >= 0) { this.quantity = this.quantity - q;
            return true; }
            else { System.out.println("Sorry, you don't have enough" + ToR + "!");
            return false; }
        }
        else { System.out.println("Sorry, this depot does not contain" + ToR + "!");
            return false; }

}
