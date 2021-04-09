package it.polimi.ingsw.model;

//manca costruttore!!!!

public class MarketBoard {
    private Marble[][] board;
    private Marble slideMarble;

    public Marble getSlideMarble() {
        return slideMarble;
    }

    public Marble[][] getBoard() {
        return board;
    }


    /**
     * imposta la marketboard all'inizio della partita in modo randomizzato
     *
     */
    private Marble setBoard() {
        Marble[][] temp=null;
        Marble side=null;
        //...
        this.board = temp;
        return side;
    }

    /**
     * imposta la slidemarble iniziale
     *
     */
    private void setSlideMarble(Marble side) {
        this.slideMarble = side;
    }

    /**
     * metodo da chiamare a inizio partita per impostare la MarketBoard iniziale
     */
    public void initialize(){
      this.setSlideMarble(this.setBoard());
    }


    /**
     * selectRC serve per selezionare e cambiare la marketBoard
     */
    public int[] selectRC(){
       int[] selection =new int[2];
       //impostare come selezionare la riga o colonna

        return selection;
    }

    /*/**
     * getRC serve quando si deve convertire la riga/colonna in risorse

    public Marble[] getRC(int[] selection){
        // selection[0]==0 significa che vogliamo ritornare una riga
        if(selection[0]==0){
            return board[selection[1]];
        }
        // else vogliamo ritornare una colonna


    }*/


    /**
     * dalla riga/colonna selezionata in selectRC ritorna la quantità di materiale che si ottiene
     * possibile necessità di rivisionare il metodo per gestire più velocemente (senza step di vettore intermedio) le risorse che vanno assegnate
     */
    public Resource[] ConvertionToResource(int[] selection) {
        Resource[] risorseOttenute=null;

        //se selection[0]==0 allora vogliamo convertire una riga
        if (selection[0] == 0) {
            risorseOttenute = new Resource[4];

            for (int i = 0; i < 4; i++) {
                switch (board[selection[1]][i].getColore()) {
                    case white:
                        break;
                    case red:
                        risorseOttenute[i].setTypeResource(TypeOfResource.faith);
                        break;
                    case purple:
                        risorseOttenute[i].setTypeResource(TypeOfResource.servants);
                        break;
                    case grey:
                        risorseOttenute[i].setTypeResource(TypeOfResource.stones);
                        break;
                    case blue:
                        risorseOttenute[i].setTypeResource(TypeOfResource.shields);
                        break;
                    case yellow:
                        risorseOttenute[i].setTypeResource(TypeOfResource.coins);
                        break;
                }
            }


        }
    return risorseOttenute;
    }



    /**
     * overload di ConvertionToResource nel caso si abbia il potere di una carta leader
     * possibile necessità di rivisionare il metodo per gestire più velocemente (senza step di vettore intermedio) le risorse che vanno assegnate
     */
   /* public Resource[] ConvertionToResource(int[] selection, Resource power) {


    }*/

    /**
     * cambia lo stato della MarketBoard in base alla selezione di riga o colonna fatta.
     * non viene aggiornato nella visualizzazione, gestire dopo questa parte
     */
    public void ChangeBoard(int[] selection){
        if (selection[0]==0){
            //cambia la riga
        }
        else {

        }
            //cambia la colonna

    }



}

