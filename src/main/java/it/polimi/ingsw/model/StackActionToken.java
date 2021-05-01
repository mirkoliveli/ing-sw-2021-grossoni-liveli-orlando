package it.polimi.ingsw.model;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static it.polimi.ingsw.model.TypeOfActionToken.*;

public class StackActionToken {

    private ActionToken[] stack;
    private int index;

    /**
     * This method creates a new stack with the proper dimension and types, then it shuffles it
     */
    public StackActionToken() {
        index = 0;
        this.stack = new ActionToken[7];
        stack[0] = new ActionToken(greenToken);
        stack[1] = new ActionToken(blueToken);
        stack[2] = new ActionToken(yellowToken);
        stack[3] = new ActionToken(purpleToken);
        stack[4] = new ActionToken(moveCrossAndShuffle);
        stack[5] = new ActionToken(twoSpaceMovement);
        stack[6] = new ActionToken(twoSpaceMovement);
        List<ActionToken> stackList = Arrays.asList(stack);
        Collections.shuffle(stackList);
        stackList.toArray(stack);
    }

    public int getIndex() { return index; }

    /**
     * @return the first token of the stack
     */
    public ActionToken getFirst() {
        return stack[index];
    }

    /**
     * This method returns Lorenzo's action for the turn, discarding the relative token and moving the index forward
     * The execution of the action itself is left to the controller
     */
    public ActionToken playFirst() {
        ActionToken playedToken = stack[index];
        stack[index].discard();
        index++;
        return playedToken;
    }

    /**
     * This method can be used to reshuffle the whole stack
     * It resets every "discard" attribute as well as the index, then shuffles the stack array
     */
    public void resetStack() {
        index = 0;
        for (int i=0; i<7; i++) {
            stack[i].reshuffle();
        }
        List<ActionToken> stackList = Arrays.asList(stack);
        Collections.shuffle(stackList);
        stackList.toArray(stack);
    }




    // METODO UTILIZZATO SOLO PER TESTARE LO SHUFFLER NEL TEST DEL COSTRUTTORE
    // STAMPA IL TIPO DI OGNI TOKEN DELLO STACK IN FILA
    public void printStack() {
        for (int i=0; i<7; i++) {
            System.out.println(stack[i].getType());
        }
    }

    // METODO UTILIZZATO SOLO PER TESTARE RESETSTACK, RESTITUISCE TRUE SE NESSUN TOKEN E' STATO SCARTATO
    public boolean zeroDiscarded() {
        for (int i=0; i<7; i++) {
            if (stack[i].isDiscarded()) {
                return false;
            }
        }
        return true;
    }

}
