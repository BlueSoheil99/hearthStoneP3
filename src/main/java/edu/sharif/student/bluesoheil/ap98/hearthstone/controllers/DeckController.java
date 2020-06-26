package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import com.google.gson.Gson;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.DeckControllerException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Deck;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.CardConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class DeckController {
    private static final int MAXIMUM_NUMBER_OF_DECKS = 25; //i'm not sure whether it's important or not!

    private static DeckController instance;
    private ArrayList<Deck> playerDecks;
    private Deck currentDeck;

    private DeckController() {
    }

    /*
     ***** Getters and Setters
     */
    public static DeckController getInstance() {
        if (instance == null) instance = new DeckController();
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public static ArrayList<Deck> getDefaultDecks() {
        CardConfig configs = CardConfig.getInstance();
        ArrayList<Deck> defaultDecks = new ArrayList<>();

        String[] mageCards = configs.getDefaultMageDeckCards();
        String[] warlockCards = configs.getDefaultWarlockDeckCards();
        String[] rogueCards = configs.getDefaultRogueDeckCards();
        String[] priestCards = configs.getDefaultPriestDeckCards();
        String[] hunterCards = configs.getDefaultHunterDeckCards();

        ArrayList<Card> mageDeckCards = new ArrayList<>();
        ArrayList<Card> warlockDeckCards = new ArrayList<>();
        ArrayList<Card> rogueDeckCards = new ArrayList<>();
        ArrayList<Card> priestDeckCards = new ArrayList<>();
        ArrayList<Card> hunterDeckCards = new ArrayList<>();
        //todo howMany decks we need for signUp??
        for (String cardName : mageCards) mageDeckCards.add(CardController.getInstance().getCard(cardName));
        for (String cardName : warlockCards) warlockDeckCards.add(CardController.getInstance().getCard(cardName));
        for (String cardName : rogueCards) rogueDeckCards.add(CardController.getInstance().getCard(cardName));
        for (String cardName : priestCards) priestDeckCards.add(CardController.getInstance().getCard(cardName));
        for (String cardName : hunterCards) hunterDeckCards.add(CardController.getInstance().getCard(cardName));

        defaultDecks.add(new Deck("MageDefaultDeck", HeroTypes.MAGE, mageDeckCards));
        defaultDecks.add(new Deck("WarlockDefaultDeck", HeroTypes.WARLOCK, warlockDeckCards));
        defaultDecks.add(new Deck("RogueDefaultDeck", HeroTypes.ROGUE, rogueDeckCards));
        defaultDecks.add(new Deck("PriestDefaultDeck", HeroTypes.PRIEST, priestDeckCards));
        defaultDecks.add(new Deck("HunterDefaultDeck", HeroTypes.HUNTER, hunterDeckCards));

        return defaultDecks;
    }

    public static Deck getDefaultDeck(HeroTypes heroType) {
        // i use this method for opponent's deck
        for (Deck deck : getDefaultDecks()) {
            if (deck.getHeroType().equals(heroType)) {
                return deck;
            }
        }
        return null;
    }

    public ArrayList<Deck> getPlayerDecks() {
        sortDecks();
        return playerDecks;
    }

    public Deck getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(String currentDeck) throws DeckControllerException {
        if (Deck.getMinimumCardsInDeck() <= getDeck(currentDeck).getNumberOfCards()) {
            this.currentDeck = getDeck(currentDeck);
        } else
            throw new DeckControllerException("You should atLeast have " + Deck.getMinimumCardsInDeck() + "cards in currentDeck");
    }

    /*
     ***** Methods
     */
    public void loadPlayerDecks() {
        //this method is called when player decks are just converted from json
        playerDecks = PlayerController.getInstance().getPlayerDecks();
        //..revalidate decks..
        //we should revalidate our cards. current cards are different from the cards in the gameTotalCards and playerTotalCards
        for (Deck deck : playerDecks) {
            CardController.getInstance().revalidateCards(deck.getCards());
        }
    }

    public ArrayList<String> getDeckNamesWithCertainCard(Card card) {
        ArrayList<String> deckNamesIncludingThisCard = new ArrayList<>();
        for (Deck deck : playerDecks) {
            if (deckContains(deck, card)) {
                deckNamesIncludingThisCard.add(deck.getName());
            }
        }

        if (deckNamesIncludingThisCard.size() > 0) {
            return deckNamesIncludingThisCard;
        } else return null;

    }

    private boolean deckContains(Deck deck, Card card) {
        return deck.getCards().contains(card);
    }

    private void sortDecks() {
        //https://www.baeldung.com/java-comparator-comparable
        Collections.sort(playerDecks, Comparator.comparing(Deck::getManaAverage));
        Collections.reverse(playerDecks);
        Collections.sort(playerDecks, Comparator.comparing(Deck::getGamesPlayed));
        Collections.sort(playerDecks, Comparator.comparing(Deck::getWins));
        Collections.sort(playerDecks, Comparator.comparing(Deck::getWinRatio));
        Collections.reverse(playerDecks);
    }

    public String[] getDeckStates(String deckName) {
        Deck deck = getDeck(deckName);
        String[] states = new String[]{deck.getName(), deck.getHeroType().toString(), Float.toString(deck.getWinRatio())
                , Integer.toString(deck.getWins()),Integer.toString(deck.getGamesPlayed()), Float.toString(deck.getManaAverage()), deck.getMostUsedCard()};
        return states;
    }

    public Deck getDeck(String deckName) {
        Deck DK = null;
        for (Deck deck : playerDecks) {
            if (deck.getName().equals(deckName)) {
                DK = deck;
                break;
            }
        }
        return DK;
    }

    public void renameDeck(String deckName, String newDeckName) throws DeckControllerException {
        Deck deck = getDeck(deckName);
        if (isNameAvailable(newDeckName)) {
            deck.setName(newDeckName);
        } else throw new DeckControllerException("this name is not valid");
    }

    public void createDeck(String name, HeroTypes hero) throws DeckControllerException {
        //todo shall we have default cards for each hero?
        if (playerDecks.size() < MAXIMUM_NUMBER_OF_DECKS) {
            playerDecks.add(new Deck(name, hero, new ArrayList<>()));
        } else throw new DeckControllerException(" You can't have more decks! ");
    }

    public void addCard(String nameOfDeck, Card card) throws DeckControllerException {
        Deck deck = getDeck(nameOfDeck);
        if (deck != null) {
            int numberOfThisCardInPlayersCards = Collections.frequency(CardController.getInstance().getPlayerTotalCards(), card);
            int numberOfThisCardInThisDeck = Collections.frequency(deck.getCards(), card);
            if (numberOfThisCardInPlayersCards > numberOfThisCardInThisDeck) {
                deck.addCard(card);
            } else throw new DeckControllerException("You only have "+numberOfThisCardInPlayersCards+" of this card. Can't have more in a deck");
        }
    }

    public void removeCard(String nameOfDeck, Card card) throws DeckControllerException {
        for (Deck deck : playerDecks) {
            if (deck.getName().equals(nameOfDeck)) {
                deck.removeCard(card);
                break;
            }
        }
    }

    private boolean isNameAvailable(String requestedName) {
        boolean ans = true;
        if (requestedName.length() > 4) {
            for (Deck deck : playerDecks) {
                if (requestedName.toUpperCase().equals(deck.getName().toUpperCase())) {
                    ans = false;
                    break;
                }
            }
        } else ans = false;
        return ans;
    }

    public void deleteDeck(String selectedDeck) {
        playerDecks.remove(getDeck(selectedDeck));
    }

    public void changeDeckHero(String selectedDeck, HeroTypes heroName) throws DeckControllerException {
        Deck deck = getDeck(selectedDeck);
        boolean isThisOperationAvailable = true;
        for (Card card : deck.getCards()) {
            if (card.getHeroClass() != Card.HeroClass.NEUTRAL && card.getHeroClass().toString() != heroName.toString()) {
                isThisOperationAvailable = false;
                break;
            }
        }
        if (isThisOperationAvailable) {
            deck.setHero(heroName);
        } else {
            throw new DeckControllerException("You Can't change Hero According to cards in this deck");
        }
    }

    public Deck copyDeck(Deck originalDeck) {
        Gson gson = new Gson();
        String deckJson = gson.toJson(originalDeck);
        Deck copiedDeck = gson.fromJson(deckJson, Deck.class);
        ArrayList<Card> copiedDeckCards = copiedDeck.getCards();
        // copiedDeck has a list of cards that each of them is just a card! not a minion or spell or ...
        //so we should also transfer cards of this deck, to some real cards! cards with their actual class!
        for (int i = 0; i < copiedDeckCards.size(); i++) {
            copiedDeckCards.set(i,
                    CardController.getInstance().copyCard(
                            copiedDeckCards.get(i).getName().toUpperCase())
            );
        }
        return copiedDeck;
    }

    public void updateCardUsages(Deck outDatedDeck, HashMap<String, Integer> newCardUsage) {
        outDatedDeck.setCardsUsage(newCardUsage);
    }
}
