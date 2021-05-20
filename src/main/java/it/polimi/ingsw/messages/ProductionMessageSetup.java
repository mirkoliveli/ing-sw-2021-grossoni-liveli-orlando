package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.TypeOfResource;
import it.polimi.ingsw.utils.StaticMethods;

public class ProductionMessageSetup {
    private boolean[] choices;
    private TypeOfResource[] SelectableResources; //from leader cards or base production

    public ProductionMessageSetup(boolean[] choices, TypeOfResource[] resources){
        this.choices = choices.clone();
        this.SelectableResources=resources.clone();
    }

    public boolean[] getChoices() {
        return choices;
    }

    public TypeOfResource[] getSelectableResources() {
        return SelectableResources;
    }
}
