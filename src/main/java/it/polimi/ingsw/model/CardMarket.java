package it.polimi.ingsw.model;

//manca cli che mostra solo carte acquistabili + possibili controlli su cosa è acquistabile e cosa no


import com.google.gson.Gson;
import it.polimi.ingsw.model.exceptions.CardNotFoundException;
import it.polimi.ingsw.model.exceptions.EndSoloGame;
import it.polimi.ingsw.model.exceptions.IllegalCardException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * class that defines the cardMarket section
 */
public class CardMarket {
    private final DevelopmentCard[][][] matrixDevelopment;

    /**
     * constructor for the class,
     * it uses gson and 12 files for the creation of the matrix of cards.
     * Explanation on how the matrix is populated is explained in the "populate" method.
     *
     * @author riccardo grossoni
     */
    public CardMarket() {
        this.matrixDevelopment = new DevelopmentCard[3][4][4];
        this.populate(0, 0, "src/main/resources/green_lvl1.json");
        this.populate(0, 1, "src/main/resources/purple_lvl1.json");
        this.populate(0, 2, "src/main/resources/blue_lvl1.json");
        this.populate(0, 3, "src/main/resources/yellow_lvl1.json");
        this.populate(1, 0, "src/main/resources/green_lvl2.json");
        this.populate(1, 1, "src/main/resources/purple_lvl2.json");
        this.populate(1, 2, "src/main/resources/blue_lvl2.json");
        this.populate(1, 3, "src/main/resources/yellow_lvl2.json");
        this.populate(2, 0, "src/main/resources/green_lvl3.json");
        this.populate(2, 1, "src/main/resources/purple_lvl3.json");
        this.populate(2, 2, "src/main/resources/blue_lvl3.json");
        this.populate(2, 3, "src/main/resources/yellow_lvl3.json");
    }

    public DevelopmentCard[][][] getMatrixDevelopment() {
        return matrixDevelopment;
    }

    /**
     * populate is the method used by the constructor to populate a specific deck of the 12 that make the market.
     * <br><br>
     * this method uses a simple gson deserialization to populate the specific deck, with the position of this deck given
     * as a parameter via the level and color param.
     * <br><br>
     * the path is given as a string.
     *
     * @param lvl    is needed to find the right "row"
     * @param color  is needed to find the right "column"
     * @param Source is the path of the JSON file that contains the 4 cards needed
     * @author Riccardo Grossoni
     */
    public void populate(int lvl, int color, String Source) {
        Gson gson = new Gson();
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new FileReader(Source));
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        }
        this.matrixDevelopment[lvl][color] = gson.fromJson(buffer, DevelopmentCard[].class);
        try {
            buffer.close();
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    /**
     * method used to shuffle all the decks, it uses the same shuffling algorithm as the MarketBoard class
     *
     * @author Riccardo Grossoni
     */
    public void shuffle() {
        int index;
        DevelopmentCard temp = new DevelopmentCard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int swap = 3; swap > 0; swap--) {
                    index = (int) Math.floor(Math.random() * (swap + 1));
                    temp = new DevelopmentCard(this.matrixDevelopment[i][j][swap]);
                    this.matrixDevelopment[i][j][swap].setAll(this.matrixDevelopment[i][j][index]);
                    this.matrixDevelopment[i][j][index].setAll(temp);
                }
            }
        }
    }


    /**
     * method that given a card id it removes the card from the market (usually after being bought)
     *
     * @param id is the id of the card being removed
     * @author Riccardo Grossoni
     */

    public void Remove(int id) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int card = 0; card < 4; card++) {
                    if ((this.matrixDevelopment[i][j][card] != null) && (this.matrixDevelopment[i][j][card].getId() == id)) {
                        this.matrixDevelopment[i][j][card] = null;
                    }
                }
            }
        }
    }

    /**
     * overload of the method Remove, in this method it has to be passed as a parameter the card being removed itself
     *
     * @param removeThis is the card being removed
     * @author Riccardo Grossoni
     */
    public void Remove(DevelopmentCard removeThis) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int card = 0; card < 4; card++) {
                    if ((this.matrixDevelopment[i][j][card] != null) && (this.matrixDevelopment[i][j][card].getId() == removeThis.getId())) {
                        this.matrixDevelopment[i][j][card] = null;
                    }
                }
            }
        }
    }


    /**
     * method used while testing, mainly for the shuffling test
     *
     * @author Riccardo Grossoni
     */
    public void PrintId() {
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                counter++;
                System.out.println("mazzo: " + (counter));
                for (int stamp = 0; stamp < 4; stamp++) {
                    if (this.matrixDevelopment[i][j][stamp] != null)
                        System.out.println(this.matrixDevelopment[i][j][stamp].getId());
                }
            }
        }
    }

    /**
     * method that returns a card present in the Market, if the card it's not present throws an exception
     *
     * @param id Id of the card searched
     * @return card Found
     * @throws CardNotFoundException card not found, throws Exception
     * @author Riccardo Grossoni
     */


    public DevelopmentCard getCardById(int id) throws CardNotFoundException {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int card = 0; card < 4; card++) {
                    if ((this.matrixDevelopment[i][j][card] != null) && (this.matrixDevelopment[i][j][card].getId() == id)) {
                        return this.matrixDevelopment[i][j][card];
                    }
                }
            }
        }
        throw new CardNotFoundException();
    }


    /**
     * method that returns A copy of the cost of the card (identified by the Id). If the card is not present the method
     * returns null.
     *
     * @param id id of the card searched
     * @return cost of the card searched
     * @author Riccardo Grossoni
     */
    public int[] getCost(int id) {
        int[] copyCost = new int[4];
        DevelopmentCard temp;
        try {
            temp = this.getCardById(id);
        } catch (CardNotFoundException e) {
            return null;            //volendo si può cambiare facendo lanciare a sua volta una eccezione
        }
        copyCost= temp.getCost().clone();
        return copyCost;
    }

    /**
     * method the composes the body of the buy a card action. This method returns the bought card, and removes it from the market.
     * it throws a "CardNotFoundException" if the id of the card is not existent in the market, and throws an "IllegalCardException" if
     * the card cannot be bought.
     *
     * @param id id of the card that is going to be bought
     * @return The bought card
     * @throws CardNotFoundException used when trying to buy a card not present in the market
     * @throws IllegalCardException  used when trying to buy a card not on top of its respective deck
     */
    public DevelopmentCard BuyCard(int id) throws CardNotFoundException, IllegalCardException {
        int temp = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int card = 0; card < 4; card++) {
                    if ((this.matrixDevelopment[i][j][card] != null) && (this.matrixDevelopment[i][j][card].getId() == id)) {    //check if the card can be bought
                        temp = card - 1;
                        while (temp >= 0) {
                            if (this.matrixDevelopment[i][j][temp] != null) {
                                throw new IllegalCardException();
                            }
                            temp--;
                        }
                        DevelopmentCard Copy = new DevelopmentCard(this.matrixDevelopment[i][j][card]); //non può essere null, è dentro un if che controlla proprio quello:/
                        this.Remove(this.matrixDevelopment[i][j][card]);
                        return Copy;
                    }
                }
            }
        }
        throw new CardNotFoundException();
    }


//----------------------------------------------------------------------------------------------------------------------
    //   METODI PER SINGLE PLAYER

    /**
     * method that is called to perform the action, it's just a selection for the real method, which is
     * DeleteTwoCardsByColor. this method re-throws an Exception EndSoloGame that is thrown by the DeleteTwoCardsByColor method.
     *
     * @param ColoreToken color taken from the SoloActionToken
     * @throws EndSoloGame Exception thrown by DeleteTwoCardsByColor, it's re-thrown to the controller.
     */
    public void SoloAction(Color ColoreToken) throws EndSoloGame {
        int color = 0;
        switch (ColoreToken) {
            case green:
                color = 0;

                break;
            case purple:
                color = 1;
                break;
            case blue:
                color = 2;
                break;
            case yellow:
                color = 3;
                break;
        }
        try {
            this.DeleteTwoCardsByColor(color);
        } catch (EndSoloGame e) {
            throw e;
        }
    }

    /**
     * METHOD  strictly used in the singlePlayer action made by the "bot" Lorenzo
     * <br>
     * given a color (that the controller can take from the token) as an int, the method deletes the first 2 cards available
     * in a deck of that given color, it handles all the cases, and throws an EndSoloGame Exception if the EndGameScenario is reached
     * if not the method just deletes the 2 cards and end.
     *
     * @param color color that the SoloActionToken returned
     * @throws EndSoloGame in the scenario that an EndGameState is reached, an Exception for that is throwed
     */

    public void DeleteTwoCardsByColor(int color) throws EndSoloGame {
        int carta = 0;
        int livello = 0;
        int deleted = 0;

        boolean EsciDaWhile = false;
        while (!EsciDaWhile) {

            carta = this.NumberOfCardsInLevel(color, livello); //trova quante carte ci sono nel livello

            //nessuna carta nel mazzo
            if (carta == 0) {
                if (livello == 2) throw new EndSoloGame();
                else {
                    livello++;
                }
            }
            //almeno due carte nel mazzo, se entro qua devo uscire dal ciclo
            else if (carta >= 2) {
                if (deleted == 0) {
                    this.Remove(this.matrixDevelopment[livello][color][4 - carta]);
                    this.Remove(this.matrixDevelopment[livello][color][5 - carta]);
                    deleted = 2;
                    if (this.NoMoreCardsInLevelThree(color)) throw new EndSoloGame();
                } else if (deleted == 1) {
                    this.Remove(this.matrixDevelopment[livello][color][4 - carta]);
                    deleted++;
                }
                EsciDaWhile = true;
            }
            //solo una carta nel mazzo
            else if (carta == 1) {
                this.Remove(this.matrixDevelopment[livello][color][3]);
                deleted++;
                if (this.NoMoreCardsInLevelThree(color)) throw new EndSoloGame();
                if (deleted != 2) livello++;
                else EsciDaWhile = true;
            }
        }
    }


    /**
     * method that returns the number of cards in a specified deck. the method will give a runtime error if the level or color is invalid
     *
     * @param color color of the deck
     * @param level level of the deck (0-2)
     * @return number of cards in the deck (0-4)
     */
    public int NumberOfCardsInLevel(int color, int level) {
        int posCarta = 0;
        boolean esci = false;
        while (!esci) {
            if (posCarta < 4 && this.matrixDevelopment[level][color][posCarta] == null) {
                posCarta++;
            } else if (this.matrixDevelopment[level][color][posCarta] != null) {
                esci = true;

            }
            if (posCarta == 4) esci = true;
        }
        return (4 - posCarta);
    }


    /**
     * very small method used for SinglePlayer for checking if the EndGameState is reached
     *
     * @param color color of the level 3 deck will be checked
     * @return true if no more cards are present (EndGameState for singleP), false otherwise
     */
    public boolean NoMoreCardsInLevelThree(int color) {
        return this.matrixDevelopment[2][color][3] == null;
    }

//----------------------------------------------------------------------------------------------------------------------

    // UTILS

    /**
     * method that returns a pointer to the card on top of the deck selected. the method EXPECTS a correct level and color,
     * and also expects a not-empty deck. If the deck is empty the method throws an Exception, if the color or level is not valid
     * there will be an error at runtime
     *
     * @param color color selected, given as int
     * @param level level selected (0 to 2)
     * @return pointer to the card selected
     * @throws CardNotFoundException card not found
     */
    public DevelopmentCard findFirstCardOfDeck(int color, int level) throws CardNotFoundException {
        int carta = 0;
        while (carta < 3) {
            if (this.matrixDevelopment[level][color][carta] != null) {
                return this.matrixDevelopment[level][color][carta];
            }
            carta++;
        }
        throw new CardNotFoundException();
    }


    /**
     * method that creates a matrix containing the id of the cards visible (and that can be bought) by the players
     *
     * @return id's of the cards available;
     */
    public int[][] cardMarketStatus() {
        int[][] visibleMarket = new int[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int card = 0;
                while (matrixDevelopment[i][j][card] == null && card < 4) {
                    card++;
                }
                if (card < 4) {
                    visibleMarket[i][j] = matrixDevelopment[i][j][card].getId();
                } else {
                    visibleMarket[i][j] = 0;
                }
            }
        }
        return visibleMarket;
    }


}
