package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Deck;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.Hero;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives.InfoPassive;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Player;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.PlayLogicConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class GameController {
    private static GameController instance;
    private static final int maximumCardsInHand = 7;
    private static final int maximumCardsOnBoard = 7;

    private PlayLogicConfig properties;
    private InfoPassive passive; //todo you can make it local
    private PlayerController playerController;
    private DeckController deckController;

    private boolean isWarriorsEnabled, isNurseEnabled, isOffCardsEnabled;
    private Player player, opponent;
    private int playerMana, playerInitialMana;
    private int turnsPlayerPlayed, numberOfCardsCanBeDrawn;
    private Deck playerDeck, opponentDeck;
    private Hero playerHero, opponentHero;
    private ArrayList<Card> playerHand, playerMinions;
    private Card playerWeapon;
    private Stack<Card> playerCards;
    private boolean playerIsWinner = false;
    private boolean playerTurn = true;
    private ArrayList<Integer> cardsPlayerChangedForHisFirstHand;

    {
        opponentDeck = DeckController.getInstance().copyDeck(DeckController.getDefaultDeck(HeroTypes.ROGUE));
    }

//    todo private enum Players {
//        ME,
//        OPPONENT;
//        private GamePlayer player;
//        Players() {
//        }
//        void setPlayer(GamePlayer player) {
//            this.player = player;
//        }
//    }


    private GameController() {
        properties = PlayLogicConfig.getInstance();
        cardsPlayerChangedForHisFirstHand = new ArrayList<>();
        setInitialMana(1);
        setNumberOfCardsCanBeDrawn(1);
        turnsPlayerPlayed = 0;
        playerController = PlayerController.getInstance();
        deckController = DeckController.getInstance();
        player = playerController.getCurrentPlayer();
        setPlayerProperties();// todo uncomment it
        //opponent = ...
        //setOpponentProperties();
    }

    ///////////
    /////////////////
    ///statics//////////
    /////////////////
    ///////////
    public static GameController getInstance() {
        if (instance == null) setNewGame();
        return instance;
    }

    public static void setNewGame() {
        instance = new GameController();
    }

    public static void endGame() {
        if (instance.playerIsWinner) instance.deckController.getCurrentDeck().incrementWins();
        instance.deckController.getCurrentDeck().setCardsUsage(instance.playerDeck.getCardsUsage());
        instance.playerDeck.getHeroType().resetHero();
        instance.opponentDeck.getHeroType().resetHero();
        PlayHandler.dismissHandler();
        instance = null;
    }

    ///////////
    /////////////////
    ///non-statics//////
    /////////////////
    ///////////
    private void setPlayerProperties() {
        Deck mainPlayerDeck = deckController.getCurrentDeck();
        mainPlayerDeck.incrementGamesPlayed();
        playerDeck = DeckController.getInstance().copyDeck(deckController.getCurrentDeck());
        Collections.shuffle(playerDeck.getCards());
        playerCards = new Stack<>();
        for (int i = 0; i < playerDeck.getNumberOfCards(); i++) {
            playerCards.add(playerDeck.getCards().get(i));
        }
        playerHero = playerDeck.getHeroType().getHero();
    }

    private void setOpponentProperties() {
//        deck1 = DeckController.getInstance().getCurrentDeck();
//        hero1 = deck1.getHero().getHero();
    }

    public HeroTypes getPlayerHero() {
        return playerDeck.getHeroType();
    }

    public HashMap<String, Integer> getHeroStates() {
        HashMap<String, Integer> states = new HashMap<>();
        states.put("HP", playerHero.getHp());
        states.put("MANA", playerMana);
        states.put("CARDS", playerCards.size());
        return states;
    }


    //////////
    //////////
    public void setOpponent(String deckName) {
        Deck deck = deckController.getDeck(deckName);
        //todo setup opponent here
    }
    public HeroTypes getOpponentHero() {
        return opponentDeck.getHeroType();
    }

    public int getOpponentHP() {
        return opponentDeck.getHeroType().getHero().getHp();
    }

    public int getInitialOpponentMana() {
        return 1;
    }

    ////////////////////
    ///////passives/////

    public void setPassive(String objName) {
        passive = InfoPassive.valueOf(objName.toUpperCase());
        passive.getPassive().run();
    }

    public void setInitialMana(int i) {
        playerInitialMana = i;
        playerMana = playerInitialMana;
    } //for manaJump passive

    public void setNumberOfCardsCanBeDrawn(int numberOfCardsCanBeDrawn) {
        this.numberOfCardsCanBeDrawn = numberOfCardsCanBeDrawn;
    } //for twice draw passive

    public void setWarriorsEnabled(boolean enabling) {
        isWarriorsEnabled = enabling;
    }

    public void setNurseEnable(boolean enabling) {
        isNurseEnabled = enabling;
    }

    public void setOffCardsEnable(boolean enabling) {
        isOffCardsEnabled = enabling;
    }

    ///////////////////
    ///////////////////
    public ArrayList<Card> getPlayerHand() {
        if (playerHand == null) {
            playerHand = new ArrayList<>();
            for (int i = 0; i < 3; i++) playerHand.add(playerCards.pop());
        }
        return playerHand;
    }

    public void updateHand() {
        if (turnsPlayerPlayed > 0) { //this works when we change our cards at the beginning or when we want to show our hand for the first time in playPanel
            for (int i = 0; i < numberOfCardsCanBeDrawn; i++) {//numberOfCardsCanBeDrawn is for twiceDraw
                if (playerCards.size() > 0) playerHand.add(playerCards.pop());
            }
            checkHandLimit();
        }
    }

    private void checkHandLimit() {
        if (playerHand.size() > properties.getMaximumCardsInHand()) {
            playerHand.remove(playerHand.size() - 1);
            checkHandLimit();
        }
    }

    public ArrayList<Card> drawHandAgain(String cardName) throws PlayException {
        Card card;
        for (Card handCard : playerHand) {
            if (handCard.getName().toUpperCase().equals(cardName)) {
                if (cardsPlayerChangedForHisFirstHand.size() < properties.getMaximumStartHints()) {
                    card = handCard;
                    int x = playerHand.indexOf(card);
                    if (!cardsPlayerChangedForHisFirstHand.contains(x)) {
                        playerHand.set(playerHand.indexOf(card), playerCards.pop());
                        playerCards.push(card);
                        //we push selected card after we changed the card so this card won't be drawn again. but it might be drawn for next attempts
                        Collections.shuffle(playerCards);
                        cardsPlayerChangedForHisFirstHand.add(x);
                        break;
                    } else throw new PlayException("you can't change this card again");
                } else
                    throw new PlayException("you can't change your hand more than " + properties.getMaximumStartHints() + " times");
            }
        }
        return getPlayerHand();
    }

    public Card getCard(Card playerSelectedCard) {
        for (Card card : playerHand) if (card.getName().toUpperCase().equals(playerSelectedCard.getName())) return card;
        return null;
    }

    public Card getCard(String playerSelectedCard) {
        Card selectedCard = null;
        for (Card card : playerHand) {
            if (card.getName().toUpperCase().equals(playerSelectedCard)) {
                selectedCard = card;
                break;
            }
        }
        return selectedCard;
    }

    public void purchaseCard(Card card) throws PlayException {
        if (playerMana >= card.getManaCost()) { //todo implement offCards passive here
            playerMana -= card.getManaCost();
        } else {
            throw new PlayException("You Don't Have Enough Mana");
        }
    }

    public void removeCard(Card card) {
        if (playerHand.contains(card)) playerHand.remove(card);
    }

    public void changeTurns() {
        if (playerTurn) {
            playerTurn = false;
            turnsPlayerPlayed++;
        } else {
            playerTurn = true;
            increaseMana();
        }
    }

    private void increaseMana() {
        playerMana = playerInitialMana + turnsPlayerPlayed;
        if (playerMana > properties.getMaximumMana()) playerMana = properties.getMaximumMana();
    }

    public String getPlayingSide() {
        return "you";
    }


}
