package edu.sharif.student.bluesoheil.ap98.hearthstone.connectors;

import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.CardController;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardFilter {

    //by changing player, static fields are still the same.
    private static CardFilter instance;
    private static HashMap<String, Card.HeroClass> heroes;
    private static HashMap<String, Card> totalGameCards = CardController.getInstance().getGameTotalCards();
    private static HashMap<String, BufferedImage> cardsAndImages = CardController.getInstance().getCardsAndImagesMap();

    private ArrayList<Card> playerTotalCards = CardController.getInstance().getPlayerTotalCards();
    private String regex;
    private boolean owned, notOwned;
    private int manaCost;
    private Card.HeroClass hero;
    private ArrayList<Card> filteredCards;

    {
        heroes = new HashMap<>();
        heroes.put("Neutral", Card.HeroClass.NEUTRAL);
        heroes.put("Mage", Card.HeroClass.MAGE);
        heroes.put("Warlock", Card.HeroClass.WARLOCK);
        heroes.put("Rogue", Card.HeroClass.ROGUE);
        heroes.put("Hunter", Card.HeroClass.HUNTER);
        heroes.put("Priest", Card.HeroClass.PRIEST);
    }


    private CardFilter() {

    }

    public static CardFilter getInstance() {
        if (instance == null) instance = new CardFilter();
        return instance;
    }

    public boolean playerCardsContain(String cardName) {
        boolean ans = false;
        for (Card card : playerTotalCards) {
            if (card.getName().toUpperCase().equals(cardName)) {
                ans = true;
                break;
            }
        }
        return ans;
    }

    public ArrayList<CardShape> filter(String regex, boolean owned, boolean notOwned, int manaCost, String hero) {
        this.regex = regex.toUpperCase();
        this.owned = owned;
        this.notOwned = notOwned;
        this.manaCost = manaCost;
        if (hero.equals("All")) {
            this.hero = null;
        } else this.hero = heroes.get(hero);

        filteredCards = new ArrayList<>();
        for (Map.Entry<String, Card> cardEntry : totalGameCards.entrySet()) {
            filteredCards.add(cardEntry.getValue());
        }

        ArrayList<CardShape> finalFilteredCards = new ArrayList<>();
        filterCardsWithRegex();
        filterCardsWithMana();
        filterCardsWithHero();

        if (owned) {
            ArrayList<Card> ownedCards = getOwnedFilterCards();
            for (Card card : ownedCards) finalFilteredCards.add(getShapeCard(card, true));
        }
        if (notOwned) {
            ArrayList<Card> notOwnedCards = getNotOwnedFilterCards();
            for (Card card : notOwnedCards) finalFilteredCards.add(getShapeCard(card, false));
        }
        return finalFilteredCards;
    }

    private void filterCardsWithRegex() {
        if (!regex.equals("")) {
            for (Map.Entry<String, Card> cardEntry : totalGameCards.entrySet()) {
                if (!cardEntry.getKey().contains(regex)) filteredCards.remove(cardEntry.getValue());
            }
        }
    }

    private void filterCardsWithMana() {
        if (manaCost != -1) {
            // java offers to use removeIf to avoid ConcurrentModificationException
            filteredCards.removeIf(card -> card.getManaCost() != manaCost);
        }
    }

    private void filterCardsWithHero() {
        if (hero != null) {
            filteredCards.removeIf(card -> card.getHeroClass() != hero);
        }
    }

    private ArrayList<Card> getOwnedFilterCards() {
        ArrayList<Card> ownedCards = new ArrayList<>();
        for (Card card : filteredCards) {
            if (playerTotalCards.contains(card)) {
                ownedCards.add(card);
            }
        }
        return ownedCards;
    }

    private ArrayList<Card> getNotOwnedFilterCards() {
        ArrayList<Card> notOwnedCards = new ArrayList<>();
        for (Card card : filteredCards) {
            if (!playerTotalCards.contains(card)) {
                notOwnedCards.add(card);
            }
        }
        return notOwnedCards;
    }

    private CardShape getShapeCard(Card card, boolean owned) {
        return new CardShape(card.getName().toUpperCase(), cardsAndImages.get(card.getName().toUpperCase()), owned);
    }

}
