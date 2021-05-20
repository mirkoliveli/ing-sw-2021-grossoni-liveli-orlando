package it.polimi.ingsw.utils;

public class Generic_intANDboolean {
    private int number;
    private boolean choice;

    public Generic_intANDboolean(int num, boolean choice){
        this.choice=choice;
        this.number=num;
    }

    public int getNumber() {
        return number;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isChoice() {
        return choice;
    }
}
