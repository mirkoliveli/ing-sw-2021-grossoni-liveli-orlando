package it.polimi.ingsw.model;


//completamente da progettare

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * class that defines the cardMarket section
 */
public class cardMarket {
    private DevelopmentCard[][][] matrixDevelopment;

    /**
     * constructor for the class
     * it uses gson and 12 files for the creation of the matrix of cards
     * explanation on how the matrix is populated is explained in the "populate" method
     * @author riccardo grossoni
     */
    public cardMarket(){
        this.matrixDevelopment=new DevelopmentCard[3][4][4];
        this.populate(0,0,"src/main/resources/green_lvl1.json");
        this.populate(0,1,"src/main/resources/purple_lvl1.json");
        this.populate(0,2,"src/main/resources/blue_lvl1.json");
        this.populate(0,3,"src/main/resources/yellow_lvl1.json");
        this.populate(1,0,"src/main/resources/green_lvl2.json");
        this.populate(1,1,"src/main/resources/purple_lvl2.json");
        this.populate(1,2,"src/main/resources/blue_lvl2.json");
        this.populate(1,3,"src/main/resources/yellow_lvl2.json");
        this.populate(2,0,"src/main/resources/green_lvl3.json");
        this.populate(2,1,"src/main/resources/purple_lvl3.json");
        this.populate(2,2,"src/main/resources/blue_lvl3.json");
        this.populate(2,3,"src/main/resources/yellow_lvl3.json");
    }

    public DevelopmentCard[][][] getMatrixDevelopment() {
        return matrixDevelopment;
    }

    /**
     * populate is the method used by the constructor to populate a specific deck of the 12 that make the market
     * @param lvl is needed to find the right "row"
     * @param color is needed to find the right "column"
     * @param Source is the path of the JSON file that contains the 4 cards needed
     * populate uses a simple gson deserialization to populate the specific deck, with the position of this deck given
     * as a parameter via the level and color param.
     * the path is given as a string
     * @author Riccardo Grossoni
     */
    public void populate(int lvl, int color, String Source){
        Gson gson= new Gson();
        BufferedReader buffer=null;
        try{
            buffer=new BufferedReader(new FileReader(Source));
        }catch (FileNotFoundException e){
            System.out.println("File non trovato");
        }
        this.matrixDevelopment[lvl][color]= gson.fromJson(buffer, DevelopmentCard[].class);
        try{ buffer.close();
        }catch(IOException e ){ System.out.println("error");}
    }




}
