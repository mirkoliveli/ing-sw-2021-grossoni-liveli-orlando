package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.TypeOfResource;

public class ProductionMessageSetup {
    private final boolean[] choices;
    private final TypeOfResource[] SelectableResources; //from leader cards or base production

    public ProductionMessageSetup(boolean[] choices, TypeOfResource[] resources) {
        this.choices = choices.clone();
        this.SelectableResources = resources.clone();
    }

    public boolean[] getChoices() {
        return choices;
    }

    public TypeOfResource[] getSelectableResources() {
        return SelectableResources;
    }
}
