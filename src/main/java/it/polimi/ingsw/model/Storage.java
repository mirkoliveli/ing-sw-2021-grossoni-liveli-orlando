package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.utils.StaticMethods;


public class Storage {

    private final DepotLevel level1;
    private final DepotLevel level2;
    private final DepotLevel level3;
    private DepotLevelLeader firstLeader = null;
    private DepotLevelLeader secondLeader = null;


    public Storage() {
        this.level1 = new DepotLevel(1);
        this.level2 = new DepotLevel(2);
        this.level3 = new DepotLevel(3);
        this.firstLeader = null;
        this.secondLeader = null;
    }

    public void addLeader(TypeOfResource resource) {
        if (firstLeader == null) firstLeader = new DepotLevelLeader(resource);
        else secondLeader = new DepotLevelLeader(resource);
    }


    /**
     * method that swaps levels, return false if it can't be done, or true after the swap is done
     *
     * @param index1 1-2-3
     * @param index2 1-2-3
     * @return true if action done, false otherwise
     */
    public boolean swapLevels(int index1, int index2) {

        int temp;
        TypeOfResource temp_resource;

        if (getLevel(index1).getQuantity() > getLevel(index2).getMaxQuantity() || getLevel(index2).getQuantity() > getLevel(index1).getMaxQuantity()) {
            System.out.println("Sorry, you cannot swap these depots!");
            return false;
        } else {
            temp_resource = getLevel(index1).getResourceType();
            getLevel(index1).setResourceType(getLevel(index2).getResourceType());
            getLevel(index2).setResourceType(temp_resource);
            temp = getLevel(index1).getQuantity();
            getLevel(index1).setQuantity(getLevel(index2).getQuantity());
            getLevel(index2).setQuantity(temp);
            return true;
        }
    }

    /**
     * @param index 1/2/3 come livello
     * @return livello richiesto
     */
    public DepotLevel getLevel(int index) {
        switch (index) {
            case 1:
                return level1;
            case 2:
                return level2;
            case 3:
                return level3;
            default:
                return null;
        }
    }

    public boolean checkDifferentTypes() {
        /* Returns true if all the depots store different types of resources */

        return (level1.getResourceType() != level2.getResourceType()) && (level1.getResourceType() != level3.getResourceType()) && (level2.getResourceType() != level3.getResourceType());
    }

    /**
     * @param originalCost resources gained:([0]-> number of coins, [1]-> number of servants, [2]-> number of shields, [3]-> number of stones)
     * @throws TooManyResources
     * @throws AlreadyInOtherLevel doesn't allow to change the layout of the resources on different levels, you either do it before or after the "gain of resources"
     */
    public int IncreaseResources(int[] originalCost) {
        int discarded = 0;
        int temp = 0;
        boolean[] NullLevel = {true, true, true, true}; //ogni slot indica se non esiste un deposito (tra quelli normali) di quel tipo di risorsa
        int leaderHandler = 0;
        int[] resources = StaticMethods.copyArray(originalCost);

        //prima aggiungo quello che posso ai depotLevelLeaders

        if (firstLeader != null) {
            switch (firstLeader.getResourceTypeLeader()) {
                case coins:
                    if (resources[0] > 0 && firstLeader.getQuantity() != 2) {
                        leaderHandler = 2 - firstLeader.getQuantity(); //ottengo 1 o 2
                        if (leaderHandler >= resources[0]) {
                            firstLeader.setQuantity(firstLeader.getQuantity() + resources[0]);
                            resources[0] = 0;
                        } else {
                            firstLeader.setQuantity(2);
                            resources[0] = resources[0] - leaderHandler;
                        }
                    }
                    break;
                case servants:
                    if (resources[1] > 0 && firstLeader.getQuantity() != 2) {
                        leaderHandler = 2 - firstLeader.getQuantity(); //ottengo 1 o 2
                        if (leaderHandler >= resources[1]) {
                            firstLeader.setQuantity(firstLeader.getQuantity() + resources[1]);
                            resources[1] = 0;
                        } else {
                            firstLeader.setQuantity(2);
                            resources[1] = resources[1] - leaderHandler;
                        }
                    }
                    break;
                case shields:
                    if (resources[2] > 0 && firstLeader.getQuantity() != 2) {
                        leaderHandler = 2 - firstLeader.getQuantity(); //ottengo 1 o 2
                        if (leaderHandler >= resources[2]) {
                            firstLeader.setQuantity(firstLeader.getQuantity() + resources[2]);
                            resources[2] = 0;
                        } else {
                            firstLeader.setQuantity(2);
                            resources[2] = resources[2] - leaderHandler;
                        }
                    }
                    break;
                case stones:
                    if (resources[3] > 0 && firstLeader.getQuantity() != 2) {
                        leaderHandler = 2 - firstLeader.getQuantity(); //ottengo 1 o 2
                        if (leaderHandler >= resources[3]) {
                            firstLeader.setQuantity(firstLeader.getQuantity() + resources[3]);
                            resources[3] = 0;
                        } else {
                            firstLeader.setQuantity(2);
                            resources[3] = resources[3] - leaderHandler;
                        }
                    }
                    break;
            }
            if (secondLeader != null) {
                switch (secondLeader.getResourceTypeLeader()) {
                    case coins:
                        if (resources[0] > 0 && secondLeader.getQuantity() != 2) {
                            leaderHandler = 2 - secondLeader.getQuantity(); //ottengo 1 o 2
                            if (leaderHandler >= resources[0]) {
                                secondLeader.setQuantity(secondLeader.getQuantity() + resources[0]);
                                resources[0] = 0;
                            } else {
                                secondLeader.setQuantity(2);
                                resources[0] = resources[0] - leaderHandler;
                            }
                        }
                        break;
                    case servants:
                        if (resources[1] > 0 && secondLeader.getQuantity() != 2) {
                            leaderHandler = 2 - secondLeader.getQuantity(); //ottengo 1 o 2
                            if (leaderHandler >= resources[1]) {
                                secondLeader.setQuantity(secondLeader.getQuantity() + resources[1]);
                                resources[1] = 0;
                            } else {
                                secondLeader.setQuantity(2);
                                resources[1] = resources[1] - leaderHandler;
                            }
                        }
                        break;
                    case shields:
                        if (resources[2] > 0 && secondLeader.getQuantity() != 2) {
                            leaderHandler = 2 - secondLeader.getQuantity(); //ottengo 1 o 2
                            if (leaderHandler >= resources[2]) {
                                secondLeader.setQuantity(secondLeader.getQuantity() + resources[2]);
                                resources[2] = 0;
                            } else {
                                secondLeader.setQuantity(2);
                                resources[2] = resources[2] - leaderHandler;
                            }
                        }
                        break;
                    case stones:
                        if (resources[3] > 0 && secondLeader.getQuantity() != 2) {
                            leaderHandler = 2 - secondLeader.getQuantity(); //ottengo 1 o 2
                            if (leaderHandler >= resources[3]) {
                                secondLeader.setQuantity(secondLeader.getQuantity() + resources[3]);
                                resources[3] = 0;
                            } else {
                                secondLeader.setQuantity(2);
                                resources[3] = resources[3] - leaderHandler;
                            }
                        }
                        break;
                }
            }
        }

        //ora aggiungo tutto quello che posso ai livelli con già risorse al loro interno

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

        //per finire aggiungo quello che rimane ai livelli vuoti(se ce ne sono)

        for (int j = 0; j < 4; j++) {
            if (!NullLevel[j] && EmptyDepot()) {
                boolean repeat = false;
                do {
                    repeat = false;
                    try {
                        //metodo deve ricevere tipo di risorsa e quantità
                    } catch (Exception e) {
                        repeat = true;
                    }

                } while (repeat);
            } else if (!NullLevel[j] && !EmptyDepot()) {
                //scarta tutte le risorse perchè non c'è spazio libero dove metterle
                discarded += resources[j];
            }
        }
        return discarded;

    }

    /**
     * method that returns a Level if the resource of that type is already stored somewhere, null if it's not present.
     *
     * @param resource resource seeked
     * @return level where the resource is stored, null if it's not present in the storage
     */
    public DepotLevel seekerOfResource(TypeOfResource resource) {
        if (level1.getResourceType() == resource) {
            return level1;
        } else if (level2.getResourceType() == resource) {
            return level2;
        } else if (level3.getResourceType() == resource) {
            return level3;
        }
        return null;
    }


    /**
     * returns an int[4] with the quantity of resources stored in the storage.
     *
     * @return
     */
    public int[] conversionToArray() {
        int[] resources = new int[4];
        //conto risorse nei depot dei leaders (se ci sono)
        if (firstLeader != null) {
            switch (getFirstLeader().getResourceTypeLeader()) {
                case coins:
                    resources[0] += firstLeader.getQuantity();
                    break;
                case servants:
                    resources[1] += firstLeader.getQuantity();
                    break;
                case shields:
                    resources[2] += firstLeader.getQuantity();
                    break;
                case stones:
                    resources[3] += firstLeader.getQuantity();
                    break;
                default:
                    break;
            }
            if (secondLeader != null) {
                switch (getSecondLeader().getResourceTypeLeader()) {
                    case coins:
                        resources[0] += secondLeader.getQuantity();
                        break;
                    case servants:
                        resources[1] += secondLeader.getQuantity();
                        break;
                    case shields:
                        resources[2] += secondLeader.getQuantity();
                        break;
                    case stones:
                        resources[3] += secondLeader.getQuantity();
                        break;
                    default:
                        break;
                }
            }
        }
        for (int i = 1; i < 4; i++) {
            if (getLevel(i).getResourceType() != null) {
                switch (getLevel(i).getResourceType()) {
                    case coins:
                        resources[0] += getLevel(i).getQuantity();
                        break;
                    case servants:
                        resources[1] += getLevel(i).getQuantity();
                        break;
                    case shields:
                        resources[2] += getLevel(i).getQuantity();
                        break;
                    case stones:
                        resources[3] += getLevel(i).getQuantity();
                        break;
                    default:
                        break;
                }
            }
        }
        return resources;
    }

    /**
     * given a cost this method checks if it can be paid with the resources stored in the storage.
     *
     * @param cost
     * @return
     */
    public boolean canPay(int[] cost) {
        int[] resources = conversionToArray();
        for (int i = 0; i < 4; i++) {
            if (resources[i] < cost[i]) return false;
        }
        return true;
    }

    /**
     * method that checks if the cost can be paid (if not throws a NotEnoughResources exception), if it can be paid it
     * pays the cost
     *
     * @param cost cost that is going to be paid
     * @throws NotEnoughResources when the player cannot pay the cost given
     */
    //DA FINIRE
    public void ResourceDecreaser(int[] cost) throws NotEnoughResources {
        if (!canPay(cost)) throw new NotEnoughResources();
        int[] costo = StaticMethods.copyArray(cost);

        if (firstLeader != null) {
            switch (firstLeader.getResourceTypeLeader()) {
                case coins:
                    removeFromFirstLeader(costo, 0);
                    break;
                case servants:
                    removeFromFirstLeader(costo, 1);
                    break;
                case shields:
                    removeFromFirstLeader(costo, 2);
                    break;
                case stones:
                    removeFromFirstLeader(costo, 3);
                    break;
            }
            if(secondLeader!=null){
                switch (secondLeader.getResourceTypeLeader()) {
                    case coins:
                        removeFromSecondLeader(costo, 0);
                        break;
                    case servants:
                        removeFromSecondLeader(costo, 1);
                        break;
                    case shields:
                        removeFromSecondLeader(costo, 2);
                        break;
                    case stones:
                        removeFromSecondLeader(costo, 3);
                        break;
                }
            }
        }

        //tolgo dai livelli
        DepotLevel temp;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    temp = seekerOfResource(TypeOfResource.coins);
                    if (temp == null && costo[0] != 0) throw new NotEnoughResources();
                    else if (temp == null && costo[0] == 0) break;
                    else if (costo[0] > temp.getQuantity()) throw new NotEnoughResources();
                    else if (true) {
                        temp.setQuantity(temp.getQuantity() - costo[0]);
                        costo[0]=0;
                    }
                    break;
                case 1:
                    temp = seekerOfResource(TypeOfResource.servants);
                    if (temp == null && costo[1] != 0) throw new NotEnoughResources();
                    else if (temp == null && costo[1] == 0) break;
                    else if (costo[1] > temp.getQuantity()) throw new NotEnoughResources();
                    else if (true) {
                        temp.setQuantity(temp.getQuantity() - costo[1]);
                        costo[1]=0;
                    }
                    break;
                case 2:
                    temp = seekerOfResource(TypeOfResource.shields);
                    if (temp == null && costo[2] != 0) throw new NotEnoughResources();
                    else if (temp == null && costo[2] == 0) break;
                    else if (costo[2] > temp.getQuantity()) throw new NotEnoughResources();
                    else if (true) {
                        temp.setQuantity(temp.getQuantity() - costo[2]);
                        costo[2]=0;
                    }
                    break;
                case 3:
                    temp = seekerOfResource(TypeOfResource.stones);
                    if (temp == null && costo[3] != 0) throw new NotEnoughResources();
                    else if (temp == null && costo[3] == 0) break;
                    else if (costo[3] > temp.getQuantity()) throw new NotEnoughResources();
                    else if (true) {
                        temp.setQuantity(temp.getQuantity() - costo[3]);
                        costo[3]=0;
                    }
                    break;
            }
        }

        transferToLeaderDepots();
    }


    /**
     * method that, given the DepotLevel and the quantity of resources that need to be added,
     * adds the resources to the Level and returns the quantity of resources discarded
     *
     * @param DepotTemp DepotLevel where the resources are going to be added
     * @param Resource  quantity of resources to be added
     * @return quantity of resources discarded
     */
    public int ResourceAdder(DepotLevel DepotTemp, int Resource) {
        int discarded = 0;
        int temp = DepotTemp.getMaxQuantity() - DepotTemp.getQuantity();
        if (Resource > temp) {
            discarded = Resource - temp;
            DepotTemp.setQuantity(DepotTemp.getMaxQuantity());
        } else {
            DepotTemp.setQuantity(DepotTemp.getQuantity() + Resource);
        }
        return discarded;
    }


    public boolean EmptyDepot() {
        if (level1.getResourceType() == null) {
            return true;
        }
        if (level2.getResourceType() == null) {
            return true;
        }
        return level3.getResourceType() == null;
    }


    //interagisce con server e client
    public int UserChoiceForDepot(TypeOfResource resource, int quantity) throws NotAvalidLevel, DiscardAllResources {
        int discarded = 0;
        boolean var = true;
        DepotLevel temp;
       /* temp=//metodo che chiede ad utente livello e mi restituisce il livello
        temp.setResourceType(resource);
        temp.setQuantity(quantity);*/
        return discarded;
    }

    public int[][] storageStatus() {
        int[][] status = new int[5][2];
        for (int i = 1; i < 4; i++) {
            DepotLevel temp = getLevel(i);
            if (temp.getResourceType() != null) {
                switch (temp.getResourceType()) {
                    case coins:
                        status[i - 1][0] = 1;
                        break;
                    case servants:
                        status[i - 1][0] = 2;
                        break;
                    case shields:
                        status[i - 1][0] = 3;
                        break;
                    case stones:
                        status[i - 1][0] = 4;
                        break;
                }
                status[i - 1][1] = temp.getQuantity();
            } else {
                status[i - 1][0] = 0;
                status[i - 1][1] = 0;
            }
        }
        if (firstLeader != null) {
            status[3][0] = StaticMethods.TypeOfResourceToInt(firstLeader.getResourceTypeLeader()) + 1;
            status[3][1] = firstLeader.getQuantity();
        } else {
            status[3][0] = -1;
            status[3][1] = 0;
        }
        if (secondLeader != null) {
            status[4][0] = StaticMethods.TypeOfResourceToInt(secondLeader.getResourceTypeLeader()) + 1;
            status[4][1] = secondLeader.getQuantity();
        } else {
            status[4][0] = -1;
            status[4][1] = 0;
        }
        return status;
    }

    /**
     * transfer all resources available to the depotlevelLeader of that type, if there is any, until either one is full or empty.
     */
    public void transferToLeaderDepots(){
        if(firstLeader!=null){
            if(firstLeader.getQuantity()!=2){
                DepotLevel temp=seekerOfResource(firstLeader.getResourceTypeLeader());
                if(temp!=null){
                    while(temp.getQuantity()>0 && firstLeader.getQuantity()!=2){
                        temp.setQuantity(temp.getQuantity()-1);
                        firstLeader.setQuantity(firstLeader.getQuantity()+1);
                    }
                }
            }
            if(secondLeader!=null){
                if(secondLeader.getQuantity()!=2){
                    DepotLevel temp2=seekerOfResource(secondLeader.getResourceTypeLeader());
                    if(temp2!=null){
                        while(temp2.getQuantity()>0 && secondLeader.getQuantity()!=2){
                            temp2.setQuantity(temp2.getQuantity()-1);
                            secondLeader.setQuantity(secondLeader.getQuantity()+1);
                        }
                    }
                }
            }

        }
    }


    public DepotLevelLeader getFirstLeader() {
        return firstLeader;
    }

    public void setFirstLeader(DepotLevelLeader firstLeader) {
        this.firstLeader = firstLeader;
    }

    public DepotLevelLeader getSecondLeader() {
        return secondLeader;
    }

    public void setSecondLeader(DepotLevelLeader secondLeader) {
        this.secondLeader = secondLeader;
    }

    public void removeFromFirstLeader(int[] costo, int type){
        while(costo[type]>0 && firstLeader.getQuantity()> 0){
            costo[type]--;
            firstLeader.setQuantity(firstLeader.getQuantity()-1);
        }
    }

    public void removeFromSecondLeader(int[] costo, int type){
        while(costo[type]>0 && secondLeader.getQuantity()> 0){
            costo[type]--;
            secondLeader.setQuantity(secondLeader.getQuantity()-1);
        }
    }

}

