package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Deck;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.Hero;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives.InfoPassive;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Player;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class GameController {
    private static GameController instance;
    private static HashMap<String , BufferedImage> heroImages;
    private InfoPassive passive;
    private PlayerController playerController;
    private Player player, opponent;
    private Deck deck1, deck2;
    private Hero hero1 , hero2;
    static {
       loadHeroAvatars();
    }

    public GameController(){
        playerController = PlayerController.getInstance();
        player = playerController.getCurrentPlayer();
        setPlayerProperties();
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
        deck1 = DeckController.getInstance().getCurrentDeck();
        Collections.shuffle(deck1.getCards());
        Stack<Card> cardStack = new Stack<>();
        for (int i = 0; i < deck1.getNumberOfCards(); i++) {
            cardStack.add(deck1.getCards().get(i));
        }
        hero1 = deck1.getHero().getHero();
    }
    private void setOpponentProperties() {
//        deck1 = DeckController.getInstance().getCurrentDeck();
//        hero1 = deck1.getHero().getHero();
    }
    //todo use Stack.class for deck


}
