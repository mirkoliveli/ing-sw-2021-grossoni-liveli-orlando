package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.ClientHandler;
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

            System.out.println(temp_resource);
            getLevel(index1).setResourceType(getLevel(index2).getResourceType());
            getLevel(index2).setResourceType(temp_resource);

            System.out.println(getLevel(index2).getResourceType());

            temp = getLevel(index1).getQuantity();

            System.out.println("risorse messe nel due" + temp);
            getLevel(index1).setQuantity(getLevel(index2).getQuantity());
            getLevel(index2).setQuantity(temp);
            Gson gson=new Gson();
            System.out.println(gson.toJson(storageStatus()));
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
     * method that handles all the required actions when gaining resources from the marble market
     * @param originalCost resources gained:<br>[0]-> number of coins,<br> [1]-> number of servants,<br> [2]-> number of shields, <br>[3]-> number of stones
     * @param client it's used to start a client communication if the resources need a new level where to be stored, refer to the ClientHandler.handleNotStoredResources method for additional info
     */
    public int IncreaseResources(int[] originalCost, ClientHandler client) {
        int discarded = 0;
        int temp = 0;
        boolean[] NullLevel = {true, true, true, true}; //ogni slot indica se non esiste un deposito (tra quelli normali) di quel tipo di risorsa
        int leaderHandler = 0;
        int[] resources = StaticMethods.copyArray(originalCost);

        //prima aggiungo quello che posso ai depotLevelLeaders

        if (firstLeader != null) {
            switch (firstLeader.getResourceTypeLeader()) {
                case coins:
                    addToFirstLeaderDepot(resources, 0);
                    break;
                case servants:
                    addToFirstLeaderDepot(resources, 1);
                    break;
                case shields:
                    addToFirstLeaderDepot(resources, 2);
                    break;
                case stones:
                    addToFirstLeaderDepot(resources, 3);
                    break;
            }
            if (secondLeader != null) {
                switch (secondLeader.getResourceTypeLeader()) {
                    case coins:
                        addToSecondLeaderDepot(resources, 0);
                        break;
                    case servants:
                        addToSecondLeaderDepot(resources, 1);
                        break;
                    case shields:
                        addToSecondLeaderDepot(resources, 2);
                        break;
                    case stones:
                        addToSecondLeaderDepot(resources, 3);
                        break;
                }
            }
        }

        //ora aggiungo tutto quello che posso ai livelli con già risorse al loro interno

        DepotLevel DepotTemp;
        for (int i = 0; i < 4; i++) {
            if (resources[i] != 0) {
                switch (i) {
                    case 0:
                        DepotTemp = seekerOfResource(TypeOfResource.coins);
                        if (DepotTemp != null) {
                            resourceAdder(DepotTemp, resources, 0);
                        } else {
                            NullLevel[i] = false;
                        }
                        break;

                    case 1:
                        DepotTemp = seekerOfResource(TypeOfResource.servants);
                        if (DepotTemp != null) {
                            resourceAdder(DepotTemp, resources, 1);
                        } else {
                            NullLevel[i] = false;
                        }
                        break;

                    case 2:
                        DepotTemp = seekerOfResource(TypeOfResource.shields);
                        if (DepotTemp != null) {
                            resourceAdder(DepotTemp, resources, 2);
                        } else {
                            NullLevel[i] = false;
                        }
                        break;

                    case 3:
                        DepotTemp = seekerOfResource(TypeOfResource.stones);
                        if (DepotTemp != null) {
                            resourceAdder(DepotTemp, resources, 3);
                        } else {
                            NullLevel[i] = false;
                        }
                        break;
                }
            }
        }

        //per finire aggiungo quello che rimane ai livelli vuoti(se ce ne sono)

        ClientHandler.handleNotStoredResources(this, NullLevel, resources, client);
        System.out.println((resources[0]+ resources[1]+ resources[2]+ resources[3]));
        return (resources[0]+ resources[1]+ resources[2]+ resources[3]);

    }

    /**
     * add resources to the first leader (if it's obtained)
     * @param resources array of resources gained from the marble market
     * @param resourcetype type of resource of the leadertype
     */
    public void addToFirstLeaderDepot(int[] resources, int resourcetype){
        while(firstLeader.getQuantity()!=2 && resources[resourcetype]>0){
            resources[resourcetype]--;
            firstLeader.setQuantity((firstLeader.getQuantity()+1));
        }
    }

    /**
     * add resources to the second leader (if it's obtained)
     * @param resources array of resources gained from the marble market
     * @param resourcetype type of resource of the leadertype
     */
    public void addToSecondLeaderDepot(int[] resources, int resourcetype){
        while(secondLeader.getQuantity()!=2 && resources[resourcetype]>0){
            resources[resourcetype]--;
            secondLeader.setQuantity((secondLeader.getQuantity()+1));
        }
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
                    removeFromDepot(0, costo);
                    break;
                case 1:
                    removeFromDepot(1, costo);
                    break;
                case 2:
                    removeFromDepot(2, costo);
                    break;
                case 3:
                    removeFromDepot(3, costo);
                    break;
            }
        }

        transferToLeaderDepots();
    }

    /**
     * method that should be used only inside the resourceDecreaser method to decrease a resource in a depot. Additionally the controller SHOULD verify that all the resources can be paid.
     * @param typeOfResource type of resource that should be decreased
     * @param costo cost as an int array
     * @throws NotEnoughResources THIS SHOULD NOT BE THROWN, SINCE THROWING THIS MEANS THAT AN ILLEGAL ACTION HAS POSSIBLY HAPPENED, JUST HERE TO VERIFY THAT IT WON'T HAPPEN
     */
    public void removeFromDepot(int typeOfResource, int[] costo) throws NotEnoughResources {
        DepotLevel temp = seekerOfResource(TypeOfResource.stones);
        if (temp == null && costo[typeOfResource] != 0) throw new NotEnoughResources();
        else if (temp == null && costo[typeOfResource] == 0) {}
        else if (costo[typeOfResource] > temp.getQuantity()) throw new NotEnoughResources();
        else if (true) {
            temp.setQuantity(temp.getQuantity() - costo[typeOfResource]);
            costo[typeOfResource]=0;
        }
    }

    /**
     * OBSOLETE METHOD
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

    /**
     * method that adds all the resources it can inside a depot and decreases the resources array. used inside the IncreaseResources
     * @param temp pointer to the depot
     * @param resources array of gained resources
     * @param typeOfResource type of resource, used to select the right slot of the array
     */
    public void resourceAdder(DepotLevel temp, int[] resources, int typeOfResource){
        while(resources[typeOfResource]>0 && temp.getQuantity()<temp.getMaxQuantity()){
            resources[typeOfResource]--;
            temp.setQuantity(temp.getQuantity()+1);
        }
    }

    /**
     * method that checks of a level is empty
     * @return true if at least one level is empty, false otherwise
     */
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

    /**
     * deprecated method
     * @param resource
     * @param quantity
     * @return
     * @throws NotAvalidLevel
     * @throws DiscardAllResources
     */
    private int UserChoiceForDepot(TypeOfResource resource, int quantity) throws NotAvalidLevel, DiscardAllResources {
        int discarded = 0;
        boolean var = true;
        DepotLevel temp;
       /* temp=//metodo che chiede ad utente livello e mi restituisce il livello
        temp.setResourceType(resource);
        temp.setQuantity(quantity);*/
        return discarded;
    }

    /**
     * method that creates a readable state for the view. it's used inside the GameStateUpdate and PlayerUpdate Class
     * @return int[5][2] representing the status of the storage, refer to the PlayerUpdate class to understand how it can be read
     */
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

    /**
     * method that removes resources from the first depotLevelLeader until the cost is paid or the depot is empty
     * the method expects to be called only if the depot has already been acquired (the leader card han been played by the client)
     * @param costo array of cost
     * @param type type of resource (int version of the leader resourceType)
     */
    public void removeFromFirstLeader(int[] costo, int type){
        while(costo[type]>0 && firstLeader.getQuantity()> 0){
            costo[type]--;
            firstLeader.setQuantity(firstLeader.getQuantity()-1);
        }
    }

    /**
     * method that removes resources from the second depotLevelLeader until the cost is paid or the depot is empty
     * the method expects to be called only if the depot has already been acquired (the leader card han been played by the client)
     * @param costo array of cost
     * @param type type of resource (int version of the leader resourceType)
     */
    public void removeFromSecondLeader(int[] costo, int type){
        while(costo[type]>0 && secondLeader.getQuantity()> 0){
            costo[type]--;
            secondLeader.setQuantity(secondLeader.getQuantity()-1);
        }
    }

    /**
     * returns boolean vector containing true if the corrisponding depotLevel is empty, false otherwise
     * @return boolean vector containing the emptyStatus of the storage
     *
     */
    public boolean[] emptyStatus(){
        boolean[] emptyDepots=new boolean[3];
        for(int i=0; i<3; i++){
            emptyDepots[i]= getLevel(i + 1).getResourceType() == null;
        }
        return emptyDepots;
    }


    /**
     * method called when a handleNotStoredResources method from clientHandler is "working". It sets the level selected by the client to the resource type that was being handled
     * and it stores all the resources available until either the level is full or the resources are 0 <br>
     * this method is only called during the IncreaseResources method
     * @param level level selected by the client
     * @param type type of resource that was being handled
     * @param resource int type of that resource
     * @param resources array of resources generated in the IncreaseResources method
     */
    public void setNewResourceToDepot(int level, TypeOfResource type, int resource, int[] resources){
        DepotLevel temp=getLevel(level);
        temp.setResourceType(type);
        resourceAdder(temp, resources, resource);
        Gson gson=new Gson();
        System.out.println(gson.toJson(storageStatus()));
    }

}

