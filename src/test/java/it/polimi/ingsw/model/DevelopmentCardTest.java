package it.polimi.ingsw.model;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DevelopmentCardTest {


    @Test
    public void jsonTest() {
        Gson gson = new Gson();
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new FileReader("src/main/resources/Prova.json"));
        } catch (FileNotFoundException e) {
            System.out.println("non trovato");
        }
        DevelopmentCard prova = gson.fromJson(buffer, DevelopmentCard.class);
        System.out.println(prova.getId());
        System.out.println(prova.getPv());
        System.out.println(prova.getColor());
        System.out.println(prova.getCost());
        System.out.println(prova.getLevel());
        System.out.println(prova.getProductionCost());
        System.out.println(prova.getProduct());
    }


}