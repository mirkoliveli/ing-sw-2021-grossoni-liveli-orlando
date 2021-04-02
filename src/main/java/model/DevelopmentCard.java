package model;

public class DevelopmentCard extends  Card {

    Color color;
    /**
     * cost di tipo vettore, conterrà per ogni indice la quantità di quella risorsa richiesta dalla carta
     * coins -> spazio 0
     * servants -> spazio 1
     * shields -> spazio 2
     * stones -> spazio 3
     */
    int[] cost;
    int level;
    int[] productionCost;
    /**
     * stessa cosa di cost, l'ultimo spazio indica la fede
     * faith -> spazio 4
     */
    int[] product;

    public Color getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public int[] getCost() {
        return cost;
    }

    public int[] getProduct() {
        return product;
    }

    public int[] getProductionCost() {
        return productionCost;
    }



}
