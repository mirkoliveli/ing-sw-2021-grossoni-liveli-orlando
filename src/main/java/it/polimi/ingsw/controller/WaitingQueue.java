package it.polimi.ingsw.controller;

public class WaitingQueue {

    private static boolean[] queue;
    private static int nOfPlayers;

    public WaitingQueue(int nOfPlayers) {
        queue = new boolean[nOfPlayers];
        WaitingQueue.nOfPlayers = nOfPlayers;
        resetQueue();
    }

    /**
     * resets the queue, all players have finished
     */
    public void resetQueue() {
        for (int i = 0; i < nOfPlayers; i++) {
            queue[i] = false;
        }
    }

    /**
     * sets the tracker for this player to true
     * @param idPlayer
     */
    public synchronized void PlayerFinished(int idPlayer){
        queue[idPlayer-1]=true;
    }

    /**
     * method that checks if all players have finished
     * @return true if all players finished false otherwise
     */
    public synchronized boolean allFinished(){
        for (int i = 0; i < nOfPlayers; i++) {
            if(queue[i]=false) return false;
        }
        return true;
    }


}
