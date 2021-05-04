package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class FaithTrackTest {

    //questi primi quattro metodi vengono controllati indirettamente nei due sotto
    //se verranno modificati e causano una situazione non prevedibile nei casi di movement e activatePopeZone
    //andranno modificati di conseguenza.

    @Test
    public void increasePosition() {
    }

    @Test
    public void checkPopeSpace() {
    }

    @Test
    public void checkZone() {
    }

    @Test
    public void updateScore() {
    }

    //in movement vengono chiamati vari metodi, ad esempio updateScore e IncreasePostion
    //per come è stato pensato al momento (8/04) movement deve essere l'unico metodo che chiama questi metodi
    //movement deve effettuare interamente l'azione di spostare il marcatore, aggiornare il punteggio vittoria (se necessario)
    // e attivare il rapporto in vaticano (ancora da scrivere)
    //in questo test per ora abbiamo due casi
    //il primo esegue due movimenti, partendo da zero, e controlla se la posizione e il punteggio alla fine di ognuno dei
    //movimenti sia corretto
    //il secondo test verifica che non si vada OutOfBounds quando raggiungiamo la fine del percorso, e controlla se viene
    //updatato correttamente il punteggio
    //come riportato sotto andrà costruito un ulteriore caso test per attivare il rapporto in vaticano correttamente

    @Test
    public void movement() {

        //assicuriamoci che i movimenti si updateano correttamente,
        FaithTrack test=new FaithTrack();
        test.Movement(4);
        assertEquals(1, test.getOnlyTrackPoints());
        assertEquals(4, test.getFaithMarker());
        test.Movement(3);
        assertEquals(2, test.getOnlyTrackPoints());
        assertEquals(7, test.getFaithMarker());


        //assicuriamoci che una volta superato lo spazio 24 il mio faithMarker non avanza oltre.
        FaithTrack testendgame = new FaithTrack(20);
        testendgame.Movement(6);
        assertEquals(24, testendgame.getFaithMarker());
        assertEquals(20, testendgame.getOnlyTrackPoints());
        System.out.println("fine test moviemento");


        //manca test per verificare che si attivi il Vatican report
        //deve essere ancora scritto dato che non ci sono ancora le interazioni tra personaggi


    }

    //testa activatePopeSpace
    //activateSpacePope è incaricato di, ricevuta la zona da testare, di scartare o girare la carta favore papale
    //activatePopeSpace utilizza al suo interno checkZone e i metodi flip e discard di PopesFavorCard
    //venogono verificati 6 casi in totale in cui viene controllato lo stato dei due boolean di PopesFavor
    //ci sono casi in cui la carta andrà scartata, quindi discarded sarà TRUE, obtained sarà FALSE
    //ci sono casi in cui la carta andrà flippata, quindi discarded sarà FALSE, obtained sarà TRUE

    @Test
    public void flip_discardPopeCardTest(){

        //vericano che le tre zone vengono attivate e venga eseguita la giusta operazione

        //primo caso, il marker è all'interno della prima zona, e viene invocato il rapporto in vaticano in questa zona
        //la prima carta "first" verrà quindi girata
        FaithTrack testPopeZone = new FaithTrack(8);
        testPopeZone.activatePopeSpace(1);
        assertTrue(testPopeZone.getFirst().isObtained());
        assertFalse(testPopeZone.getFirst().isDiscarded());

        //secondo caso, il marker è all'interno della seconda zona, e viene invocato il rapporto in vaticano in questa zona
        //la seconda carta "second" verrà quindi girata
        testPopeZone=new FaithTrack(13);
        testPopeZone.activatePopeSpace(2);
        assertTrue(testPopeZone.getSecond().isObtained());
        assertFalse(testPopeZone.getSecond().isDiscarded());

        //terzo caso, il marker è all'interno della terza zona, e viene invocato il rapporto in vaticano in questa zona
        //la terza carta "third" verrà quindi girata
        testPopeZone=new FaithTrack(22);
        testPopeZone.activatePopeSpace(3);
        assertTrue(testPopeZone.getThird().isObtained());
        assertFalse(testPopeZone.getThird().isDiscarded());

        //quarto caso, il marker si trova prima della prima zona quando viene invocato il rapporto in vaticano
        //la prima carta "first" andrà quindi scartata
        testPopeZone = new FaithTrack(3);
        testPopeZone.activatePopeSpace(1);
        assertTrue(testPopeZone.getFirst().isDiscarded());
        assertFalse(testPopeZone.getFirst().isObtained());

        //quinto caso, il marker si trova prima della seconda zona quando viene invocato il rapporto in vaticano
        //la seconda carta "second" andrà quindi scartata
        testPopeZone=new FaithTrack(11);
        testPopeZone.activatePopeSpace(2);
        assertTrue(testPopeZone.getSecond().isDiscarded());
        assertFalse(testPopeZone.getSecond().isObtained());

        //sesto caso, il marker si trova prima della terza zona quando viene invocato il rapporto in vaticano
        //la terza carta "third" andrà quindi scartata
        testPopeZone=new FaithTrack(14);
        testPopeZone.activatePopeSpace(3);
        assertTrue(testPopeZone.getThird().isDiscarded());
        assertFalse(testPopeZone.getThird().isObtained());

        //settimo caso, tecnicamente impossibile che si verifichi
        //vogliamo testare se è possibile girare una carta se il marker si trova dopo la zona
        //questo è impossibile perchè l'intento è di attivare il rapporto in vaticano nel momento in cui viene raggiunta la casella
        //questo perchè in movement l'update dello stato viene fatto iterativamente, procedendo di una casella alla volta
        //quindi la chiamata in vaticano verrà eseguita solo quando il faithmarker si trova prima o al massimo sopra
        //testiamo comunque per completezza, e per verificare se una volta cambiato il codice rimanga comunque vero il test
        testPopeZone=new FaithTrack(13);
        testPopeZone.activatePopeSpace(1);
        assertTrue(testPopeZone.getFirst().isObtained());
        assertFalse(testPopeZone.getFirst().isDiscarded());

        System.out.println("fine test flip//discard");
    }

    @Test
    public void completePath(){
        FaithTrack test=new FaithTrack();
        test.Movement(5);
        test.Movement(7);
        test.Movement(8);
        test.Movement(4);
        assertEquals(24, test.getFaithMarker());
        assertEquals(20, test.getOnlyTrackPoints());
        assertEquals(9, test.getPopepoints());
        assertTrue(test.getFirst().isObtained());
        assertTrue(test.getSecond().isObtained());
        assertTrue(test.getThird().isObtained());
        assertFalse(test.getFirst().isDiscarded());
        assertFalse(test.getSecond().isDiscarded());
        assertFalse(test.getThird().isDiscarded());
        assertEquals(29, test.TotalVictoryPointsFaithTrack());



    }

    @Test
    public void CoolPrintTest() {
        FaithTrack test=new FaithTrack();
        test.CoolPrint();
        test.Movement(8);
        test.CoolPrint();
        test.Movement(4);
        test.CoolPrint();
        test.Movement(20);
        test.CoolPrint();
    }
}