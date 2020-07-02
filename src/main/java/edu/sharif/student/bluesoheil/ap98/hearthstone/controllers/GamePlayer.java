package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Deck;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.Hero;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives.InfoPassive;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.PlayLogicConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class GamePlayer {
    private final PlayLogicConfig properties;
    private InfoPassive passive;
    private Deck deck;
    private Hero hero;
    private Stack<Card> playerCards;
    private ArrayList<Card> playerHand;
    private int initialMana;
    private int turnsPlayerPlayed=0;
    private int numberOfCardsCanBeDrawn=1;
    private int playerMana;

    GamePlayer(Deck playerDeck){
        properties = PlayLogicConfig.getInstance();
        deck = DeckController.getInstance().copyDeck(playerDeck);
        hero = deck.getHeroType().getHero();
        setupCards();
        initialMana=1;
        playerMana = initialMana;

    }

    private void setupCards() {
        Collections.shuffle(deck.getCards());
        playerCards = new Stack<>();
        for (int i = 0; i < deck.getNumberOfCards(); i++) {
            playerCards.add(deck.getCards().get(i));
        }
    }

    public void setPassive(String objName) {
        passive = InfoPassive.valueOf(objName.toUpperCase());
        passive.getPassive().run();//todo think about it
    }

    HeroTypes getHeroType() {
        return deck.getHeroType();
    }

    int getHp() {
       return deck.getHeroType().getHero().getHp();
    }


    HashMap<String, Integer> getHeroStates() {
        HashMap<String, Integer> states = new HashMap<>();
        states.put("HP", getHp());
        states.put("MANA", playerMana);
        states.put("CARDS", playerCards.size());
        return states;
    }

    int getInitialMana() {
        return initialMana;
    }
    void setInitialMana(int i) {
        initialMana = i;
        playerMana = initialMana;
    }


    ArrayList<Card> getHand() {
        if (playerHand == null) {
            playerHand = new ArrayList<>();
            for (int i = 0; i < 3; i++) playerHand.add(playerCards.pop());
        }
        return playerHand;
    }

    void updateHand() {
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
}
