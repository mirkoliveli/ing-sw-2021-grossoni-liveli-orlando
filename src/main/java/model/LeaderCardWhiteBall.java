package model;

public class LeaderCardWhiteBall extends LeaderCard{
    private Resource typeOfResource;
    /**
     * il costo di una carta leader whiteball è SEMPRE 2 banner di qualsiasi livello di un colore, 1 banner di qualsiasi livello di un altro colore
     * color1 e color2 indicano i colori dei due banner del costo
     * color1 indica il colore del banner singolo nel costo
     * color2 indica il colore del banner doppio nel costo
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
