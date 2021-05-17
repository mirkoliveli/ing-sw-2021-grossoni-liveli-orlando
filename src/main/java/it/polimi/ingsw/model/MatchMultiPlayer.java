package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.PlayerUpdate;
import it.polimi.ingsw.model.exceptions.CardNotFoundException;
import it.polimi.ingsw.model.exceptions.GameIsEnding;

import java.util.ArrayList;

public class MatchMultiPlayer {
    private ArrayList<Player> players;
    private MarketBoard market;
    private CardMarket cardMarket;
    private LeaderDeck leaderDeck;

    public MatchMultiPlayer() {
        players = new ArrayList<>(4);
        market = new MarketBoard();
        cardMarket = new CardMarket();
        leaderDeck = new LeaderDeck();
    }

    public void AddPlayer(Player player) {
        players.add(player);
    }

    public void AddPlayer(String name){
        Player player=new Player(name, getPlayers().size()+1);
        players.add(player);
    }

    public void MoveInFaithTrack(int move, int id) throws GameIsEnding {
        int zone = 0;
        zone = players.get(id - 1).getBoard().getFaithTrack().MultiPlayerMovement(move); //esegue movimento, modifica score
        if (zone != 0) {
            this.VaticanReport(zone);
            if (zone == 3) {
                throw new GameIsEnding();
            }
        }
    }


    /**
     * method manages the vatican report action. it's all handled automatically.
     *
     * @param zone zone that is being checked
     */
    public void VaticanReport(int zone) {
        for (Player player : players) {
            player.getBoard().getFaithTrack().activatePopeSpace(zone);
        }
    }


    /**
     * method that checks if a player has enough resources to buy a card from the market and if the slots can store this card.
     * The method expects a card that can be bought, and only handles the case where the card is not present in the market,
     * not the case where the card is an illegal buy
     *
     * @param idCard   card checked
     * @param idPlayer id of the player that is going to buy the card
     * @return boolean true if the card can be bought, false otherwise
     * @throws CardNotFoundException if the card does not exist in the market
     */

    public boolean CanIBuyThisCard(int idCard, int idPlayer) throws CardNotFoundException {
        try {
            if (!this.availableSlotForCard(idCard, idPlayer)) return false;
        } catch (CardNotFoundException e) {
            throw e;
        }
        int[] resources = players.get(idPlayer - 1).getBoard().getTotalResources();
        int[] cost = cardMarket.getCost(idCard);
        if (cost != null) {
            for (int i = 0; i < 4; i++) {
                if (resources[i] < cost[i]) return false;
            }
            return true;
        } else {
            throw new CardNotFoundException();
        }
    }


    /**
     * checks if a slot of this player can store the card selected
     *
     * @param idCard   id of the card selected
     * @param idPlayer id of the player taking the action
     * @return true if the card can be stored, false otherwise
     * @throws CardNotFoundException the card selected does not exist in the market.
     */
    public boolean availableSlotForCard(int idCard, int idPlayer) throws CardNotFoundException {
        int cardLevel = -1;
        try {
            cardLevel = cardMarket.getCardById(idCard).getLevel();
        } catch (CardNotFoundException e) {
            throw e;
        }
        for (int i = 0; i < 3; i++) {
            if (players.get(idPlayer - 1).getBoard().getSlot(i + 1).levelOfTop() + 1 == cardLevel) return true;
        }
        return false;
    }


    /**
     * this method creates an object that is an update to the status of a player, with all the info useful to recreate the
     * player in a game or to represent the player in another player's view
     *
     * @param id id of the player
     * @return PlayerUpdate object
     */
    public PlayerUpdate UpdatePlayerStatus(int id) {
        int realid = id - 1;
        PlayerUpdate playerStats = new PlayerUpdate(players.get(realid).getName(), id);
        //setting faithTrack
        playerStats.setFaithTrackProgress(players.get(realid).getBoard().getFaithTrack().getFaithMarker());
        playerStats.setPopesFavorCards(players.get(realid).getBoard().getFaithTrack().popeCardsStatus());
        //setting leaders
        if (players.get(realid).getLeaderCard1() != null) {
            playerStats.setFirstLeader(players.get(realid).getLeaderCard1().getId());
            playerStats.setFirstLeaderIsPlayed(players.get(realid).getLeaderCard1().checkIfPlayed());
        }
        if (players.get(realid).getLeaderCard2() != null) {
            playerStats.setSecondLeader(players.get(realid).getLeaderCard2().getId());
            playerStats.setSecondLeaderIsPlayed(players.get(realid).getLeaderCard2().checkIfPlayed());
        }
        //setting StrongBox
        playerStats.setStrongBox(players.get(realid).getBoard().getStrongbox().CreateCopy());
        //set storage
        playerStats.setStorage(players.get(realid).getBoard().getStorage().storageStatus());
        //set developmentSlots
        playerStats.setDevelopMentSlots(players.get(realid).getBoard().DevelopMentSlotsStatus());

        //set pv
        playerStats.setPv(players.get(realid).getVictoryPoints());

        return playerStats;
    }

//----------------------------------------------------------------------------------------------------------------------

    //getters e setters

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public MarketBoard getMarket() {
        return market;
    }

    public void setMarket(MarketBoard market) {
        this.market = market;
    }

    public CardMarket getCardMarket() {
        return cardMarket;
    }

    public void setCardMarket(CardMarket cardMarket) {
        this.cardMarket = cardMarket;
    }

    public LeaderDeck getLeaderDeck() {
        return leaderDeck;
    }

    public void setLeaderDeck(LeaderDeck leaderDeck) {
        this.leaderDeck = leaderDeck;
    }


}
