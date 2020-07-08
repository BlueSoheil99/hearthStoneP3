package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
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
    private static final PlayLogicConfig properties = PlayLogicConfig.getInstance();
    private static final int maximumCardsInHand = properties.getMaximumCardsInHand();
    private static final int maximumCardsOnBoard = 7;

    private InfoPassive passive;
    private Deck deck;
    private Hero hero;
    private Stack<Card> playerCards;
    private Card playerWeapon;
    private ArrayList<Card> playerHand, summonedMinions;
    private ArrayList<Integer> cardsPlayerChangedForHisFirstHand;
    private int initialMana, turnsPlayerPlayed, numberOfCardsCanBeDrawn, playerMana;
    private boolean isWarriorsEnabled, isNurseEnabled, isOffCardsEnabled;

    GamePlayer(Deck playerDeck) {
        //todo increment numbers of deck being played
        deck = DeckController.getInstance().copyDeck(playerDeck);
        hero = deck.getHeroType().getHero();
        cardsPlayerChangedForHisFirstHand = new ArrayList<>();

        setupCards();
        summonedMinions = new ArrayList<>();
        setInitialMana(1);
        setNumberOfCardsCanBeDrawn(1);
        turnsPlayerPlayed = 0;
    }

    ////////////////////////////////////////
    // generals getter and setters mayBe!///

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

    //////////////////////////////
    /////////passive stuff////////

    void setPassive(String objName) {
        passive = InfoPassive.valueOf(objName.toUpperCase());
        passive.getPassive().run(this);//todo think more about it
    }

    int getInitialMana() {
        return initialMana;
    }

    public void setInitialMana(int i) {
        initialMana = i;
        playerMana = initialMana;
    }//for manaJump passive

    public void setNumberOfCardsCanBeDrawn(int numberOfCardsCanBeDrawn) {
        this.numberOfCardsCanBeDrawn = numberOfCardsCanBeDrawn;
    } //for twice draw passive

    public void setWarriorsEnabled(boolean enabling) {
        isWarriorsEnabled = enabling;
    } //todo enable it

    public void setNurseEnable(boolean enabling) {
        isNurseEnabled = enabling;
    } //todo enable it

    public void setOffCardsEnable(boolean enabled) {
        if (enabled){
            for (Card card : playerCards) card.setManaCost(card.getManaCost() - 1);
            for (Card card : playerHand) card.setManaCost(card.getManaCost() - 1);
            //todo because of this passive, some weapons can't  be displayed well...WTF ??
        }
    }

    //////////////////////////////
    ///////turn changing//////////

    void newTurn() {
        increaseMana();
    }

    void endTurn() {
        turnsPlayerPlayed++;
    }

    private void increaseMana() {
        playerMana = initialMana + turnsPlayerPlayed;
        if (playerMana > properties.getMaximumMana()) playerMana = properties.getMaximumMana();
    }

    ////////////////////////////////
    //////card collection stuff/////

    private void setupCards() {
        Collections.shuffle(deck.getCards());
        playerCards = new Stack<>();
        for (int i = 0; i < deck.getNumberOfCards(); i++) {
            playerCards.add(deck.getCards().get(i));
        }
        playerHand = getHand();
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
        if (playerHand.size() > maximumCardsInHand) {
            playerHand.remove(playerHand.size() - 1);
            //todo you can show a message for hand overFlow
            checkHandLimit();
        }
    }

    void drawHandAgain(String cardName) throws PlayException {
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
    }

    //////////////////////////////
    ////////cards stuff///////////

    Card getCard(String playerSelectedCard) {
        Card selectedCard = null;
        for (Card card : playerHand) {
            if (card.getName().toUpperCase().equals(playerSelectedCard)) {
                selectedCard = card;
                break;
            }
        }
        return selectedCard;
    }

    void purchaseCard(Card card) throws PlayException {
//todo    void purchaseCard(Card card , int index) throws PlayException {
        if (playerMana >= card.getManaCost()) {
            playerMana -= card.getManaCost();

            if (card.getType()== Card.CardType.WEAPON) playerWeapon=card;
            if (card.getType()== Card.CardType.MINION||card.getType()== Card.CardType.BEAST) {
//                if (index = summonedMinions.size()){
                summonedMinions.add(card);
            }
        } else {
            throw new PlayException("You Don't Have Enough Mana");
        }
    }

}
