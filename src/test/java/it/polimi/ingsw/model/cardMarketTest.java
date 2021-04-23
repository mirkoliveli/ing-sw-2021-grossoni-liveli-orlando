package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class cardMarketTest {

    @Test
    public void constructortest(){
        cardMarket prova=new cardMarket();
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getId());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getPv());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getColor());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getCost());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getLevel());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getProductionCost());
        System.out.println(prova.getMatrixDevelopment()[0][0][0].getProduct());
    }



}