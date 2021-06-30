package it.polimi.ingsw.model;


//classe teoricamente completa, forse necessità di aggiungere eccezioni per il metodo remove.


public class Strongbox {
    /**
     * contents, vector type
     * each index represents the type of resource already existing in the strongbox
     * in each element of contents is contained the amount of 'index' resource
     * <br><br>
     * coins -> space 0
     * <br><br>
     * servants -> space 1
     * <br><br>
     * shields -> space 2
     * <br><br>
     * stones -> space 3
     */
    private final int[] contents;

    /**
     * constructor used when starting the game, sets all the contents to zero
     */
    public Strongbox() {
        this.contents = new int[4];
        this.contents[0] = 0;
        this.contents[1] = 0;
        this.contents[2] = 0;
        this.contents[3] = 0;
    }

    //costruttore SOLO PER TESTING DO NOT USE
    public Strongbox(int coins, int servants, int shields, int stones) {
        this.contents = new int[4];
        this.contents[0] = coins;
        this.contents[1] = servants;
        this.contents[2] = shields;
        this.contents[3] = stones;
    }

    /**
     * returns the vector containing the resources, use carefully
     * @return
     */
    public int[] getContents() {
        return this.contents;
    }

    /**
     * returns the quantity of a specified resource
     * @param id number between 1 and 4 representing the resource
     * @return quantity of that resource
     */
    public int getSpecific(int id) {
        return this.contents[id - 1];
    }


    /**
     * verifies that a payment can be done
     * @param cost payment that has to be made
     * @return true if the resources contained in the strongbox can pay the cost, false otherwise
     */
    public boolean canBuy(int[] cost) {
        return ((cost[0] <= contents[0]) && (cost[1] <= contents[1]) && (cost[2] <= contents[2]) && (cost[3] <= contents[3]));
    }

    /**
     * method that pays a cost, it does nothing if the the cost can't be paid
     * @param cost vector containing the cost
     * @return true if the payment has been done, false otherwise
     */
    public boolean remove(int[] cost) {
        if (canBuy(cost)) {
            this.contents[0] = this.contents[0] - cost[0];
            this.contents[1] = this.contents[1] - cost[1];
            this.contents[2] = this.contents[2] - cost[2];
            this.contents[3] = this.contents[3] - cost[3];
            return true;
        } else {
            return false;
        }

    }

    /**
     * method used to store resources in the strongbox
     * @param gain resources acquired (usually from a production)
     */
    public void store(int[] gain) {
        this.contents[0] = this.contents[0] + gain[0];
        this.contents[1] = this.contents[1] + gain[1];
        this.contents[2] = this.contents[2] + gain[2];
        this.contents[3] = this.contents[3] + gain[3];
    }


    /**
     * creates a copy of the strongbox.contents vector
     * @return copy of the strongbox.contents vector
     */
    public int[] CreateCopy() {
        int[] copy = new int[4];
        for (int i = 0; i < 4; i++) {
            copy[i] = this.getSpecific(i + 1);
        }
        return copy;
    }
//----------------------------------------------------------------------------------------------------------------------

//utils che non dovrebbero servire per il gioco

    public void print() {
        System.out.printf("coins: %d\n", contents[0]);
        System.out.printf("servants: %d\n", contents[1]);
        System.out.printf("shields: %d\n", contents[2]);
        System.out.printf("stones: %d\n", contents[3]);
    }

    public void print(int id) {
        System.out.printf("%d\n", contents[id - 1]);
    }

}
