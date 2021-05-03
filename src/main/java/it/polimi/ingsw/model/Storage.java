package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.AlreadyInOtherLevel;
import it.polimi.ingsw.model.exceptions.DiscardAllResources;
import it.polimi.ingsw.model.exceptions.NotAvalidLevel;
import it.polimi.ingsw.model.exceptions.TooManyResources;



public class Storage {

    private DepotLevel level1;
    private DepotLevel level2;
    private DepotLevel level3;


    public Storage() {
        this.level1=new DepotLevel(1);
        this.level2=new DepotLevel(2);
        this.level3=new DepotLevel(3);
    }

    public boolean swapLevels(int index1, int index2) {

        int temp;
        TypeOfResource temp_resource;

        if ( getLevel(index1).getQuantity() > getLevel(index2).getMaxQuantity() || getLevel(index2).getQuantity() > getLevel(index1).getMaxQuantity() ) {
            System.out.println("Sorry, you cannot swap these depots!");
            return false; }
        else {
            temp_resource = getLevel(index1).getResourceType();
            getLevel(index1).setResourceType(getLevel(index2).getResourceType());
            getLevel(index2).setResourceType(temp_resource);
            temp = getLevel(index1).getQuantity();
            getLevel(index1).setQuantity(getLevel(index2).getQuantity());
            getLevel(index2).setQuantity(temp);
            return true; }
    }


    public DepotLevel getLevel(int index) {
        switch (index){
            case 1: return level1;
            case 2: return level2;
            case 3: return level3;
            default: return null;
        }
    }

    public boolean checkDifferentTypes() {
        /* Returns true if all the depots store different types of resources */

        if ((level1.getResourceType() != level2.getResourceType()) && (level1.getResourceType() != level3.getResourceType()) && (level2.getResourceType() != level3.getResourceType())) {
            return true; }
        else { return false; }
    }

    /**
     *
     * @param resources resources gained:([0]-> number of coins, [1]-> number of servants, [2]-> number of shields, [3]-> number of stones)
     * @throws TooManyResources
     * @throws AlreadyInOtherLevel
     * doesn't allow to change the layout of the resources on different levels, you either do it before or after the "gain of resources"
     */
    public int IncreaseResources(int[] resources){
        int discarded = 0;
        int temp = 0;

        boolean[] NullLevel = {true, true, true, true};

        DepotLevel DepotTemp = new DepotLevel();
        for (int i = 0; i < 4; i++) {
            if (resources[i] != 0) {
                switch (i) {
                    case 0:
                        DepotTemp = seekerOfResource(TypeOfResource.coins);
                        if (DepotTemp != null) {
                            discarded += ResourceAdder(DepotTemp, resources[i]);
                        } else {
                            NullLevel[i] = false;
                        }
                        break;

                    case 1:
                        DepotTemp = seekerOfResource(TypeOfResource.servants);
                        if (DepotTemp != null) {
                            discarded += ResourceAdder(DepotTemp, resources[i]);
                        } else {
                            NullLevel[i] = false;
                        }
                        break;

                    case 2:
                        DepotTemp = seekerOfResource(TypeOfResource.shields);
                        if (DepotTemp != null) {
                            discarded += ResourceAdder(DepotTemp, resources[i]);
                        } else {
                            NullLevel[i] = false;
                        }
                        break;

                    case 3:
                        DepotTemp = seekerOfResource(TypeOfResource.stones);
                        if (DepotTemp != null) {
                            discarded += ResourceAdder(DepotTemp, resources[i]);
                        } else {
                            NullLevel[i] = false;
                        }
                        break;
                }
            }
        }
        for (int j = 0; j < 4; j++) {
            if (!NullLevel[j] && EmptyDepot()) {
                boolean repeat=false;
                do{
                repeat=false;
                try{
                    //metodo deve ricevere tipo di risorsa e quantità
                }catch (Exception e){
                    repeat=true;
                }

            }while(repeat);}
            else if (!NullLevel[j] && !EmptyDepot()) {
                //scarta tutte le risorse perchè non c'è spazio libero dove metterle
                discarded += resources[j];
            }
        }
            return discarded;

    }

    /**
     * method that returns a Level if the resource of that type is already stored somewhere, null if it's not present.
     * @param resource resource seeked
     * @return level where the resource is stored, null if it's not present in the storage
     */
    public DepotLevel seekerOfResource(TypeOfResource resource) {
        if (level1.getResourceType() == resource) {
        return level1;
        }
        else if (level2.getResourceType() == resource) {
        return level2;
        }
        else if (level3.getResourceType() == resource) {
        return level3;
        }
        return null;
    }

    /*+
     * Method that returns False if the Decrease operation isn't allowed, True the resource can be decreased
     * @param int numdec, the amount to decrease
     * @param TypeOfResource, the resource to decrease
     */
    public boolean ResourceDecreaser (int numdec, TypeOfResource resdec){
        DepotLevel depotTemp = new DepotLevel();
        depotTemp = seekerOfResource(resdec);
        int numactual = 0;
        numactual = depotTemp.getQuantity();
        if (depotTemp == null || numdec > numactual){
            return false;
        }
        else{
            depotTemp.setQuantity(numactual-numdec);
            return true;
        }

    }

    /**
     * method that, given the DepotLevel and the quantity of resources that need to be added,
     * adds the resources to the Level and returns the quantity of resources discarded
     * @param DepotTemp DepotLevel where the resources are going to be added
     * @param Resource quantity of resources to be added
     * @return quantity of resources discarded
     */
    public int ResourceAdder(DepotLevel DepotTemp, int Resource){
        int discarded=0;
        int temp=DepotTemp.getMaxQuantity()-DepotTemp.getQuantity();
        if(Resource>temp){
             discarded=Resource-temp;
             DepotTemp.setQuantity(DepotTemp.Add());
        }
        else{
             DepotTemp.setQuantity(DepotTemp.getQuantity()+Resource);
        }
        return discarded;
    }

    public boolean EmptyDepot(){
        if(level1.getResourceType()==null){
            return true;
        }
        if(level2.getResourceType()==null){
            return true;
        }
        if(level3.getResourceType()==null){
            return true;
        }
        return false;
    }

    public int UserChoiceForDepot(TypeOfResource resource, int quantity)throws NotAvalidLevel, DiscardAllResources {
        int discarded=0;
        boolean var=true;
        DepotLevel temp;
       /* temp=//metodo che chiede ad utente livello e mi restituisce il livello
        temp.setResourceType(resource);
        temp.setQuantity(quantity);*/
        return discarded;
    }


}

