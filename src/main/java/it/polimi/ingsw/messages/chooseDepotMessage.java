package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Storage;

public class chooseDepotMessage {
    boolean[] depotStateOfEmptyness;
    int[] resourceStillToBeStored;

    public chooseDepotMessage(Storage storage, int typeOfResource, int quantity) {
        depotStateOfEmptyness = storage.emptyStatus();
        resourceStillToBeStored = new int[]{typeOfResource, quantity};
    }

    public boolean[] getDepotStateOfEmptyness() {
        return depotStateOfEmptyness;
    }

    public void setDepotStateOfEmptyness(boolean[] depotStateOfEmptyness) {
        this.depotStateOfEmptyness = depotStateOfEmptyness;
    }

    public int[] getResourceStillToBeStored() {
        return resourceStillToBeStored.clone();
    }

    public void setResourceStillToBeStored(int resourceStillToBeStored, int quantity) {
        this.resourceStillToBeStored = new int[]{resourceStillToBeStored, quantity};
    }
}
