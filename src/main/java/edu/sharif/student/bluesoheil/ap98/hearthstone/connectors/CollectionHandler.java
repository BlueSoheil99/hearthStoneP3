package edu.sharif.student.bluesoheil.ap98.hearthstone.connectors;

import com.google.gson.Gson;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.DeckControllerException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * this class is created to have no method definitions related to logic in Collection panel
 */
public class CollectionHandler {
    private static CollectionHandler instance = new CollectionHandler();
    private CardFilter cardFilter;
    private Administer administer;

    private CollectionHandler() {
        cardFilter = CardFilter.getInstance();
        administer = Administer.getInstance();
    }

    public static CollectionHandler getInstance() {
        return instance;
    }

    public ArrayList<CardShape> filterCards(String regex, boolean owned, boolean notOwned, int manaCost, String hero) {
        return cardFilter.filter(regex, owned, notOwned, manaCost, hero);
    }

    public ArrayList<CardShape> getDeckCards(String deckName) {
        return administer.getDeckCards(deckName);
    }

    public LinkedHashMap<String, String> getDecks() {
        return administer.getPlayerDecks();
    }

    public void createNewDeck(String newDeckName, String heroName) throws DeckControllerException {
        administer.createDeck(newDeckName, heroName);
    }

    public void renameDeck(String selectedDeck, String newName) throws DeckControllerException {
        administer.renameDeck(selectedDeck, newName);
    }

    public void deleteDeck(String selectedDeck) {
        administer.deleteDeck(selectedDeck);
    }

    public void addCardToDeck(String selectedDeck, String selectedCard) throws DeckControllerException {
        administer.addCardToDeck(selectedDeck, selectedCard);
    }

    public void removeCardFromDeck(String selectedDeck, String selectedCard) throws DeckControllerException {
        administer.removeCardFromDeck(selectedDeck, selectedCard);
    }

    public void setCurrentDeck(String selectedDeck) throws DeckControllerException {
        administer.setCurrentDeck(selectedDeck);
    }

    public String getCurrentDeck() {
        return administer.getCurrentDeck();
    }

    public void changeDeckHero(String selectedDeck, HeroTypes heroName) throws DeckControllerException {
        administer.changeDeckHero(selectedDeck, heroName);
    }

    public boolean playerHas(String selectedCard) {
        return cardFilter.playerCardsContain(selectedCard);
    }
}
