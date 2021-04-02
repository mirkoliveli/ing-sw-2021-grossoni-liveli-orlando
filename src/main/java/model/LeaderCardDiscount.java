package model;

public class LeaderCardDiscount extends LeaderCard{

    private Resource typeOfResource;
    /**
     * il costo di una carta leader whiteball è SEMPRE 1 banner di qualsiasi livello di un colore e 1 banner di qualsiasi livello di un altro colore
     * color1 indica il colore del PRIMO banner nel costo
     * color2 indica il colore del SECONDO banner nel costo
     */
    private Color color1;
    private Color color2;


    public Color getColor1() {
        return color1;
    }

    public Color getColor2() {
        return color2;
    }

    public Resource getTypeOfResource() {
        return typeOfResource;
    }


}
