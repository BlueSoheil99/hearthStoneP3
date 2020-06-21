package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Deck;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.Hero;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.Warlock;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives.InfoPassive;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Player;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class GameController {
    private static GameController instance;
    private static HashMap<String , BufferedImage> heroImages;
    private InfoPassive passive;
    private PlayerController playerController;
    private Player player, opponent;
    private Deck playerDeck, deck2;
    private Hero playerHero, hero2;
    static {
       loadHeroAvatars();
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

    public static void endGame(){ instance = null;}

    private static void loadHeroAvatars(){

    }

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
        playerHero = playerDeck.getHero().getHero();
    }
    private void setOpponentProperties() {
//        deck1 = DeckController.getInstance().getCurrentDeck();
//        hero1 = deck1.getHero().getHero();
    }

    public HeroTypes getPlayerHero() {
        return playerDeck.getHero();
    }

    public int getInitialPlayerHP() {
        System.out.println(playerDeck.getHero().getName());
        if (playerDeck.getHero().getName().equals("Warlock"))
            return 35;
        return 30;
    }

    public int getInitialPlayerMana() {
        return 1;
    }
    //todo use Stack.class for deck


}
