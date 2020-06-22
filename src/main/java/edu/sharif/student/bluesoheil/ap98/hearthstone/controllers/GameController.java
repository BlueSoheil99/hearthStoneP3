package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Deck;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.Hero;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives.InfoPassive;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Player;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class GameController {
    private static GameController instance;
    private InfoPassive passive;
    private PlayerController playerController;
    private Player player, opponent;
    private Deck playerDeck, opponentDeck;
    private Hero playerHero, opponentHero;
    private ArrayList<Card> playerHand;
    {
        opponentDeck = DeckController.getDefaultDeck(HeroTypes.ROGUE);
    }

    public GameController(){
        playerController = PlayerController.getInstance();
        player = playerController.getCurrentPlayer();
        setPlayerProperties();// todo uncomment it
        //opponent = ...
        //setOpponentProperties();
    }


    ///////////
    //statics//
    ///////////
    public static GameController getInstance(){
        if (instance == null) setNewGame();
        return instance;
    }

    public static void setNewGame(){
        instance = new GameController();
    }

    public static void endGame(){
        instance.playerDeck.getHeroType().resetHero();
        instance = null;}


    /////////////////
    ///non-statics///
    ////////////////

    public void setPassive(String objName) {
        passive = InfoPassive.valueOf(objName.toUpperCase());
    }

    private void setPlayerProperties() {
        playerDeck = DeckController.getInstance().getCurrentDeck();

        Collections.shuffle(playerDeck.getCards());
        Stack<Card> cardStack = new Stack<>();
        for (int i = 0; i < playerDeck.getNumberOfCards(); i++) {
            cardStack.add(playerDeck.getCards().get(i));
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
    //todo use Stack.class for deck


}
