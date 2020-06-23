package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Deck;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.Hero;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives.InfoPassive;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Player;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.PlayLogicConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class GameController {
    private static GameController instance;
    private PlayLogicConfig properties;
    private InfoPassive passive;
    private PlayerController playerController;
    private DeckController deckController;
    private Player player, opponent;
    private Deck playerDeck, opponentDeck;
    private Hero playerHero, opponentHero;
    private ArrayList<Card> playerHand;
    private Stack<Card> playerCards;
    private boolean playerIsWinner = false;

    {
        opponentDeck = DeckController.getInstance().copyDeck(DeckController.getDefaultDeck(HeroTypes.ROGUE));
    }

    public GameController() {
        properties = PlayLogicConfig.getInstance();
        playerController = PlayerController.getInstance();
        deckController = DeckController.getInstance();
        player = playerController.getCurrentPlayer();
        setPlayerProperties();// todo uncomment it
        //opponent = ...
        //setOpponentProperties();
    }


    ///////////
    //statics//
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


    /////////////////
    ///non-statics///
    /////////////////

    public void setPassive(String objName) {
        passive = InfoPassive.valueOf(objName.toUpperCase());
    }

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

    public int getPlayerHP() {
        return playerDeck.getHeroType().getHero().getHp();
    }

    public int getInitialPlayerMana() {
        return 1;
    }

    //////////
    //////////

    public HeroTypes getOpponentHero() {
        return opponentDeck.getHeroType();
    }

    public int getOpponentHP() {
        return opponentDeck.getHeroType().getHero().getHp();
    }

    public int getInitialOpponentMana() {
        return 1;
    }


    public ArrayList<Card> getPlayerHand() {
        if (playerHand == null) {
            playerHand = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                playerHand.add(playerCards.pop());
            }
        }else {
            playerHand.add(playerCards.pop());
        }
        return playerHand;
    }

    public void endTurn() {

    }


}
