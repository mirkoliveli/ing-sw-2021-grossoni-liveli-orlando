package it.polimi.ingsw.model;


//Classe da riguardare completamente, mancano metodi e quelli presenti sono totalmente incompleti

//totale palline
//4 bianche
//2 blu
//2 viola
//2 grigie
//2 gialle
//1 rossa

/**
 * the class cointains 2 attributes
 * board is a matrix that contains all the marbles that are available during the "Taking resources from the market" action
 *
 * @author Riccardo Grossoni
 */

public class MarketBoard {
    private final Marble[][] board;
    private final Marble slideMarble;

    // setuppa marketBoard in un modo deterministico, sarà poi "shuffle" incaricato a mescolare la griglia
    // questo aiuta per la creazione di tests

    /**
     * constructor, each ball is put in a deterministic order, you have to use the method shuffle (maybe in a future version it will be included
     * inside the constructor)
     * it's needed for deterministic testing
     *
     * @author Riccardo Grossoni
     */
    public MarketBoard() {
        this.board = new Marble[3][4];
        this.board[0][0] = new Marble(MarbleColor.purple);
        this.board[0][1] = new Marble(MarbleColor.grey);
        this.board[0][2] = new Marble(MarbleColor.yellow);
        this.board[0][3] = new Marble(MarbleColor.white);
        this.board[1][0] = new Marble(MarbleColor.blue);
        this.board[1][1] = new Marble(MarbleColor.purple);
        this.board[1][2] = new Marble(MarbleColor.white);
        this.board[1][3] = new Marble(MarbleColor.yellow);
        this.board[2][0] = new Marble(MarbleColor.grey);
        this.board[2][1] = new Marble(MarbleColor.white);
        this.board[2][2] = new Marble(MarbleColor.red);
        this.board[2][3] = new Marble(MarbleColor.blue);
        this.slideMarble = new Marble(MarbleColor.white);
    }

    //getters
    public Marble getSlideMarble() {
        return slideMarble;
    }

    public Marble[][] getBoard() {
        return board;
    }


    //metodo che mescola la griglia
    //parte mescolando ogni riga
    //poi mescola ogni colonna
    //in questo modo ogni pallina sulla griglia ha la possibilità di cadere in ogni spazio
    //infine generiamo una posizione casuale sulla griglia con i numeri 'x' ed 'y'
    //in quella posizione sostituiamo la biglia esterna
    //questo metodo è leggermente biased verso le palline bianche
    //normalmente la probabilità che la pallina esterna sia bianca è 4/13= 30%
    //nella nostra situazione la probabilità che la pallina esterna sia bianca è 3/12= 25%
    //cambiano però anche le probabilità delle altre biglie
    //per tutte le colorate non rosse da 2/13=15% diventa 2/12=16.7%
    //per la rossa da 1/13= 7.5% diventa 1/12=8.3%
    //questo perchè nel gioco reale la pallina esterna viene scelta contemporaneamente alle altre
    //mentre in questa versione viene scelta subito una pallina bianca, successivamente da sostituire con una di quella
    //mescolate sulla griglia
    //trovo l'influenza di questa scelta di costruzione poco influente sull'andamento della partita.
    //probabilità finali (per la pallina esterna, quelal della board rimane invariata rispetto al gioco fisico)
    //pallina bianca    da 30%  diventa 25%
    //pallina rossa     da 7.5% diventa 8.2%
    //pallina colorata  da 15%  diventa 16.7%

    /**
     * this method is needed to perform a random shuffle on the board
     * it uses the .math class to perform the generation of a random position for each iteration of the cycle
     * it implements a version of the Fisher-Yates shuffle adapted for shuffling a matrix
     * the probabilities are slightly modified compared to the basic physical game, all changes are reported here:
     * the probability of a specific ball being in a specific place is unchanged, but the probability of a specific ball
     * being outside (as the SlideMarble) is changed
     * the % of a white ball being outside at the start of the game is 25% instead of 30%
     * the % of a red ball being outside at the start of the game is 8.2% instead of 7.5%
     * the % of a resource-colored ball being outside at the start of the game is 16.7% instead of 15%
     * this is due to the fact that at the start of the random generation an already determined ball is outside the game
     * as a slideMarble, then after shuffling the board a new ball is chosen, but that influences slightly the odds of a
     * specific ball being outside.
     *
     * @author Riccardo Grossoni
     */
    public void shuffle() {
        Marble temp = null;

        //mescola le righe

        for (int i = 0; i < 3; i++) {
            for (int position = 3; position > 0; position--) {
                int switcher = (int) Math.floor(Math.random() * (position + 1));
                temp = new Marble(this.board[i][position].getColore());
                this.board[i][position].setColore(this.board[i][switcher].getColore());
                this.board[i][switcher].setColore(temp.getColore());
            }
        }

        //mescola le colonne

        for (int j = 0; j < 4; j++) {
            for (int position = 2; position > 0; position--) {
                int switcher = (int) Math.floor(Math.random() * (position + 1));
                temp = new Marble(this.board[position][j].getColore());
                this.board[position][j].setColore(this.board[switcher][j].getColore());
                this.board[switcher][j].setColore(temp.getColore());
            }
        }

        //sostituisce la pallina esterna in una posizione casuale della board

        int x = (int) Math.floor(Math.random() * (4 - 2) + 1);
        int y = (int) Math.floor(Math.random() * (5 - 2) + 1);
        temp = new Marble(this.board[x][y].getColore());
        this.board[x][y].setColore(this.slideMarble.getColore());
        slideMarble.setColore(temp.getColore());
    }


    //le due conversioni. Uno ritorna array di Risorse, grande quanto la riga o colonna
    //ritorna null per gli spazi occupati da palline bianche
    //conversion to array ritorna invece un array lungo 5
    //nell'ordine ci sono
    //numero di coins
    //numero di servants
    //numero di shields
    //numero di stones
    //numero di punti fede (1 o 0) in ogni caso
    //è possibile inserire un overload della classe per gestire anche i poteri dei leader


    /**
     * this is the "beta" of a return function which gives an array with the resources gained from the selection
     *
     * @param switcher is needed to select if it's being searched a row (true) or a column (false)
     * @param line     is needed to select the specific row/column
     * @return returns an array of the Resource type, it's length is determined inside the method, could be 3 or 4.
     * @author Riccardo Grossoni
     */

    public Resource[] ConvertionToResource(boolean switcher, int line) {

        int temp;
        if (switcher) temp = 4;
        else temp = 3;
        Resource[] risorseOttenute = new Resource[temp];


        if (switcher) {

            for (int i = 0; i < temp; i++) {
                risorseOttenute[i] = new Resource();
                switch (board[line][i].getColore()) {
                    case white:
                        risorseOttenute[i].setTypeResource(null);
                        break;
                    case red:
                        risorseOttenute[i].setTypeResource(TypeOfResource.faith);
                        break;
                    case purple:
                        risorseOttenute[i].setTypeResource(TypeOfResource.servants);
                        break;
                    case grey:
                        risorseOttenute[i].setTypeResource(TypeOfResource.stones);
                        break;
                    case blue:
                        risorseOttenute[i].setTypeResource(TypeOfResource.shields);
                        break;
                    case yellow:
                        risorseOttenute[i].setTypeResource(TypeOfResource.coins);
                        break;
                }
            }
        }

        if (!switcher) {
            for (int i = 0; i < temp; i++) {
                risorseOttenute[i] = new Resource();
                switch (board[i][line].getColore()) {
                    case white:
                        break;
                    case red:
                        risorseOttenute[i].setTypeResource(TypeOfResource.faith);
                        break;
                    case purple:
                        risorseOttenute[i].setTypeResource(TypeOfResource.servants);
                        break;
                    case grey:
                        risorseOttenute[i].setTypeResource(TypeOfResource.stones);
                        break;
                    case blue:
                        risorseOttenute[i].setTypeResource(TypeOfResource.shields);
                        break;
                    case yellow:
                        risorseOttenute[i].setTypeResource(TypeOfResource.coins);
                        break;
                }
            }

        }
        return risorseOttenute;
    }

    /**
     * this is used a return function which gives back an int array which is 6 units long;
     * it cointains the numbers of each resource in the selected row/column (in the standard coins->servants->shields->stones->faith order)
     * the benefits is that the length of the array is pre-determined as 6.
     * <br>
     * the sixth space is used to store the number of white marbles, as it can be used with a leader to produce resources
     *
     * @param switcher is needed to select if it's being searched a row (true) or a column (false)
     * @param line     is needed to select the specific row/column
     * @return an array of length 6
     * @author Riccardo Grossoni
     */
    public int[] ConversionToArray(boolean switcher, int line) {
        int[] risorseOttenute = new int[6];
        int temp;
        if (switcher) temp = 4;
        else temp = 3;
        if (switcher) {
            for (int i = 0; i < temp; i++) {
                switch (board[line][i].getColore()) {
                    case white:
                        risorseOttenute[5]+=1;
                        break;
                    case red:
                        risorseOttenute[4] += 1;
                        break;
                    case purple:
                        risorseOttenute[1] += 1;
                        break;
                    case grey:
                        risorseOttenute[3] += 1;
                        break;
                    case blue:
                        risorseOttenute[2] += 1;
                        break;
                    case yellow:
                        risorseOttenute[0] += 1;
                        break;
                }
            }

        }
        if (!switcher) {
            for (int i = 0; i < temp; i++) {
                switch (board[i][line].getColore()) {
                    case white:
                        risorseOttenute[5]+=1;
                        break;
                    case red:
                        risorseOttenute[4] += 1;
                        break;
                    case purple:
                        risorseOttenute[1] += 1;
                        break;
                    case grey:
                        risorseOttenute[3] += 1;
                        break;
                    case blue:
                        risorseOttenute[2] += 1;
                        break;
                    case yellow:
                        risorseOttenute[0] += 1;
                        break;
                }
            }

        }
        return risorseOttenute;
    }

    /**
     * method used to change the board after a selection is done
     * it doesn't update the view, so a new call to the method used to view the state is needed
     *
     * @param switcher is needed to select if it's being searched a row (true) or a column (false)
     * @param line     is needed to select the specific row/column
     * @author Riccardo Grossoni
     */
    public void ChangeBoard(boolean switcher, int line) {
        Marble temporaneo = null;
        line--;
        if (switcher) {
            temporaneo = new Marble(this.board[line][3].getColore());
            this.board[line][3].setColore(this.board[line][2].getColore());
            this.board[line][2].setColore(this.board[line][1].getColore());
            this.board[line][1].setColore(this.board[line][0].getColore());
            this.board[line][0].setColore(this.slideMarble.getColore());
            this.slideMarble.setColore(temporaneo.getColore());
        } else {
            temporaneo = new Marble(this.board[2][line].getColore());
            this.board[2][line].setColore(this.board[1][line].getColore());
            this.board[1][line].setColore(this.board[0][line].getColore());
            this.board[0][line].setColore(this.slideMarble.getColore());
            this.slideMarble.setColore(temporaneo.getColore());
        }
    }

    //metodo per stampare la board
    //formatta già le righe così da avere una sorta di indicazione corretta

    /**
     * method that prints the state of the board
     * it already allines the board, so it's easier to visualize, used for testing
     *
     * @author Riccardo Grossoni
     */
    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.board[i][j].getColore() == MarbleColor.blue || this.board[i][j].getColore() == MarbleColor.grey) {
                    System.out.printf(this.board[i][j].getColore() + "    ");
                } else if (this.board[i][j].getColore() == MarbleColor.red) {
                    System.out.printf(this.board[i][j].getColore() + "     ");
                } else if (this.board[i][j].getColore() == MarbleColor.white) {
                    System.out.printf(this.board[i][j].getColore() + "   ");
                } else {
                    System.out.printf(this.board[i][j].getColore() + "  ");
                }
            }
            System.out.printf("\n");

        }
        System.out.println(this.slideMarble.getColore());
    }

    /**
     * method used to print the resource gained from the action
     *
     * @param switcher is needed to select if it's being searched a row (true) or a column (false)
     * @param line     is needed to select the specific row/column
     */

    public void printGivenResources(boolean switcher, int line) {
        Resource[] temp = ConvertionToResource(switcher, line);

        for (int i = 0; i < temp.length; i++) {
            System.out.println(temp[i].getTypeResource());
        }
    }


    public MarbleColor[][] status() {
        MarbleColor[][] status = new MarbleColor[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                status[i][j] = this.board[i][j].getColore();
            }
        }
        return status;
    }


}

