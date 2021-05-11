package it.polimi.ingsw.model;

public enum MarbleColor {
    white("\u001B[30mwhite\u001B[0m"),
    grey("\u001B[37mgrey\u001B[0m"),
    red("\u001B[31mred\u001B[0m"),
    purple("\u001B[35mpurple\u001B[0m"),
    blue("\u001B[34mblue\u001B[0m"),
    yellow("\u001B[33myellow\u001B[0m");

    private String escape;
    MarbleColor (String escape) { this.escape = escape; }
    public String getEscape() { return escape; }
    @Override
    public String toString() { return escape; }
}
