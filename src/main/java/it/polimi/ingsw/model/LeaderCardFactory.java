package it.polimi.ingsw.model;

public class LeaderCardFactory extends LeaderCard {
    /**
     * indica che risorsa serve per attivare la produzione
     */
    private Resource typeRequested;

    /**
     * la carta richiede UNA carta di SECONDO livello di un determinato colore
     * colorRequired indica di che colore deve essere la carta di SECONDO livello per poter giocare il leader
     */
    private Color colorRequired;

    public Color getColorRequired() {
        return colorRequired;
    }

    public Resource getTypeRequested() {
        return typeRequested;
    }

    /**
     * sceglie che risorsa ottenere quando si attiva la produzione di questo leader
     *
     */
    public Resource chooseType(){
        Resource choice;
        //...
        return choice;
    }

    /**
     * dovrà invocare faithTrack per far avanzare il marcatore
     */
    public void addFaith(){
        //... ancora da decidere come gestirlo
    }

}
