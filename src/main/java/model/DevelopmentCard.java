package model;

import java.awt.*;

public class DevelopmentCard extends  Card {

    private Color color;
    /**
     * cost di tipo vettore, conterrà per ogni indice la quantità di quella risorsa richiesta dalla carta
     * coins -> spazio 0
     * servants -> spazio 1
     * shields -> spazio 2
     * stones -> spazio 3
     */
    private int[] cost;
    private int level;
    private int[] productionCost;
    /**
     * stessa cosa di cost, l'ultimo spazio indica la fede
     * faith -> spazio 4
     */
    private int[] product;

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
