package it.polimi.ingsw.model;


public class Resource {
    private int id;
    private TypeOfResource typeResource;

    public Resource() {
        id = 0;
        typeResource = TypeOfResource.faith;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeOfResource getTypeResource() {
        return typeResource;
    }

    public void setTypeResource(TypeOfResource typeResource) {
        this.typeResource = typeResource;
    }

}
