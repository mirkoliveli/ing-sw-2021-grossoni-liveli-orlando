package it.polimi.ingsw.model;


//classe teoricamente completa, forse necessità di aggiungere eccezioni per il metodo remove.



public class Strongbox {
    /**
     * contents di tipo vettore, conterrà per ogni indice indica la quantità di quel tipo di risorsa contenuta attualmente nella strongbox
     * coins -> spazio 0
     * servants -> spazio 1
     * shields -> spazio 2
     * stones -> spazio 3
     */
    private int[] contents;

    /**
     * costruttore da utilizzare a inizio partita, setta il contenuto a zero
     */

    public Strongbox(){
        this.contents= new int[4];
        this.contents[0]=0;
        this.contents[1]=0;
        this.contents[2]=0;
        this.contents[3]=0;
    }

    //costruttore SOLO PER TESTING DO NOT USE
    public Strongbox(int coins, int servants, int shields, int stones){
        this.contents= new int[4];
        this.contents[0]=coins;
        this.contents[1]=servants;
        this.contents[2]=shields;
        this.contents[3]=stones;
    }

    /**
     * ritorna il contenuto attuale di Strongbox
     * @return
     */
    public int[] getContents() {
        return this.contents;
    }

    /**
     * ritorna la quantità specifica di una risorsa, che viene indicata attraverso l'ID (1...4)
     * @param id
     * @return
     */
    public int getSpecific(int id){
        return this.contents[id-1];
    }


    /**
     * verifica se è possibile comprare una determinata carta o avviare una produzione dato il costo in ingresso
     * ritorna true se si hanno abbastanza risorse, false altrimenti
     * @param cost
     * @return
     */
    public boolean canBuy(int[] cost){
        return ((cost[0]<=contents[0])&&(cost[1]<=contents[1])&&(cost[2]<=contents[2])&&(cost[3]<=contents[3]));
    }

    /**
     * funzione che permette di pagare, necessita un parametro "costo" (int[4]) in ingresso
     * ritorna false se non è stato possibile pagare
     * @param cost
     */
    public boolean remove(int[] cost){
        if(canBuy(cost)){
            this.contents[0]=this.contents[0]-cost[0];
            this.contents[1]=this.contents[1]-cost[1];
            this.contents[2]=this.contents[2]-cost[2];
            this.contents[3]=this.contents[3]-cost[3];
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * permette di posizionare risorse nello strongbox, necessita un parametro (int[4]) in ingresso
     * @param gain
     */
    public void store(int[] gain){
        this.contents[0]=this.contents[0]+gain[0];
        this.contents[1]=this.contents[1]+gain[1];
        this.contents[2]=this.contents[2]+gain[2];
        this.contents[3]=this.contents[3]+gain[3];
    }

//----------------------------------------------------------------------------------------------------------------------

//utils che non dovrebbero servire per il gioco

    public void print(){
        System.out.printf("coins: %d\n", contents[0]);
        System.out.printf("servants: %d\n", contents[1]);
        System.out.printf("shields: %d\n", contents[2]);
        System.out.printf("stones: %d\n", contents[3]);
    }

    public void print (int id){
        System.out.printf("%d\n", contents[id-1]);
    }

}
