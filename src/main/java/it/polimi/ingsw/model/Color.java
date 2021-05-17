package it.polimi.ingsw.model;

public enum Color {
    green("\u001B[32mgreen\u001B[0m"),
    yellow("\u001B[33myellow\u001B[0m"),
    blue("\u001B[34mblue\u001B[0m"),
    purple("\u001B[35mpurple\u001B[0m");

    private final String escape;

    Color(String escape) {
        this.escape = escape;
    }

    public String getEscape() {
        return escape;
    }

    @Override
    public String toString() {
        return escape;
    }

}
