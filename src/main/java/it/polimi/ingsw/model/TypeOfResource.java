package it.polimi.ingsw.model;

public enum TypeOfResource {
    coins("\u001B[33mcoins\u001B[0m"),
    servants("\u001B[35mservants\u001B[0m"),
    shields("\u001B[36mshields\u001B[0m"),
    stones("\u001B[37mstones\u001B[0m"),
    faith("\u001B[31mfaith\u001B[0m");


    private String escape;
    TypeOfResource (String escape) { this.escape = escape; }
    public String getEscape() { return escape; }
    @Override
    public String toString() { return escape; }
}
