package it.polimi.ingsw.model;

public class DevelopmentCard extends Card {

    private Color color;
    /**
     * cost and production cost are int vectors, each space represents a quantity for a specific type of resource, the resources
     * are represented in alphabetical order:
     * <br><br>
     * coins -> space 0
     * <br><br>
     * servants -> space 1
     * <br><br>
     * shields -> space 2
     * <br><br>
     * stones -> space 3
     */
    private int[] cost;
    private int level;
    /**
     * cost and production cost are int vectors, each space represents a quantity for a specific type of resource, the resources
     * are represented in alphabetical order:
     * <br><br>
     * coins -> space 0
     * <br><br>
     * servants -> space 1
     * <br><br>
     * shields -> space 2
     * <br><br>
     * stones -> space 3
     */
    private int[] productionCost;
    /**
     * product is an int vector of length 5, each space represents a resource
     * * <br><br>
     * coins -> space 0
     * <br><br>
     * servants -> space 1
     * <br><br>
     * shields -> space 2
     * <br><br>
     * stones -> space 3
     * <br><br>
     * faith -> space 4
     */
    private int[] product;

    public DevelopmentCard() {

    }


    /**
     * constructor used to CLONE a card, useful when getting a card from the cardMarket
     *
     * @param cloned the DevelopmentCard that is going to be cloned is needed as a parameter
     * @author Riccardo Grossoni
     */
    public DevelopmentCard(DevelopmentCard cloned) {
        this.setId(cloned.getId());
        this.setPv(cloned.getPv());
        this.color = cloned.getColor();
        this.level = cloned.getLevel();
        this.cost = new int[4];
        this.productionCost = new int[4];
        this.product = new int[5];
        for (int i = 0; i < 4; i++) {
            this.cost[i] = cloned.getCost()[i];
            this.productionCost[i] = cloned.getProductionCost()[i];
            this.product[i] = cloned.getProduct()[i];
        }
        this.product[4] = cloned.getProduct()[4];
    }


    public Color getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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


    /**
     * method that is used to set an already existing card to a different card, mainly used inside the shuffle method for
     * the CardMarket class
     *
     * @param cloned the card that is being cloned
     * @author Riccardo Grossoni
     */
    public void setAll(DevelopmentCard cloned) {
        this.setId(cloned.getId());
        this.setPv(cloned.getPv());
        this.color = cloned.getColor();
        this.level = cloned.getLevel();
        for (int i = 0; i < 4; i++) {
            this.cost[i] = cloned.getCost()[i];
            this.productionCost[i] = cloned.getProductionCost()[i];
            this.product[i] = cloned.getProduct()[i];
        }
        this.product[4] = cloned.getProduct()[4];
    }


    /**
     * test method, prints the cost of a card
     */
    public void stampCost() {
        for (int i : this.getCost()) {
            System.out.println(this.getCost()[i]);
        }
    }




}
