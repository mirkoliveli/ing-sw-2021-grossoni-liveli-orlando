package it.polimi.ingsw.model;

public class SoloFaithTrack extends FaithTrack {
    private int enemy;

    public SoloFaithTrack() {
        super();
        enemy = 0;
    }

    public int getEnemy() {
        return enemy;
    }

    public void setEnemy(int enemy) {
        this.enemy = enemy;
    }

    public void moveEnemy(int move) {
        enemy += move;
        if (enemy == 25) enemy--;

        if ((this.enemy == 8 || (this.enemy == 9 && move == 2)) & !this.getFirst().isDiscarded()) {
            activatePopeSpace(1);
        } else if (((this.enemy == 16) || (this.enemy == 17 && move == 2)) & !this.getSecond().isDiscarded()) {
            activatePopeSpace(2);
        } else if (this.enemy == 24 & !this.getThird().isDiscarded()) {
            activatePopeSpace(3);
        }
    }

    public int Action(int move) {
        enemy += move;
        if (enemy == 25) enemy--;

        if ((this.enemy == 8 || (this.enemy == 9 && move == 2))) {
            return 1;
        } else if (((this.enemy == 16) || (this.enemy == 17 && move == 2))) {
            return 2;
        } else if (this.enemy == 24) {
            return 3;
        }
        return 0;
    }

    public void CoolPrint() {
        System.out.println("\nLorenzo's faithTrack:\n");
        int tempPosition = this.enemy;
        int pos = 0;
        while (pos < tempPosition) {
            System.out.print("  ");
            pos++;
        }
        System.out.print("\u001B[1;31mL\u001B[0m\n");
        System.out.println("_ _ _ _ _ \u001B[1;32m_ _ _ _\u001B[0m _ _ _ \u001B[1;32m_ _ _ _ _\u001B[0m _ _ \u001B[1;32m_ _ _ _ _ _\u001B[0m");
        System.out.println("                P               P               P");
    }
}
