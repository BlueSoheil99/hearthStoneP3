package edu.sharif.student.bluesoheil.ap98.hearthstone.models;

import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.DeckControllerException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Deck {
    //TODO use configuration here.....think about how to manage the same cards we have
    private static final int MAXIMUM_NUMBER_OF_CARDS = 15;
    private static final int MINIMUM_NUMBER_OF_CARDS = 5;

    private String name;
    private int gamesPlayed;
    private int wins;
    private HeroTypes hero;
    private ArrayList<Card> cards;
    private HashMap<String,Integer> cardsUsage;


    public Deck(String name,HeroTypes hero, ArrayList<Card> cards) {
            this.name = name;
            this.hero = hero;
            gamesPlayed = 0;
            wins = 0;
            this.cards = cards;
            cardsUsage = new HashMap<>();
            for (Card card : cards) cardsUsage.put(card.getName(), 0);
    }

    ////getters and setters
    public static int getMinimumCardsInDeck (){
        return MINIMUM_NUMBER_OF_CARDS;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroTypes getHero() {
        return hero;
    }

    public void setHero(HeroTypes hero) {
        this.hero = hero;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    public String getMostUsedCard() {
        String cardName = "";
        int max =0;
        for (Map.Entry<String, Integer> entry : cardsUsage.entrySet() ){
            if (entry.getValue() > max){
                max = entry.getValue();
                cardName = entry.getKey();
            }
        }
        //todo Complete this sorting .. take a look at status panel description( phase 2 doc)
            return cardName;
    }

    public float getManaAverage() {
        int sumOfMana = 0;
        for (Card card :cards){
            sumOfMana += card.getManaCost();
        }
        return (float)sumOfMana/cards.size() ;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getWins() { return wins; }

    public void setWins(int wins) { this.wins = wins; }

    public float getWinRatio() {
        if (gamesPlayed == 0) return 0;
        return Math.round((float)wins/gamesPlayed *100) / (float)100 ;
    }

    ///other methods
    public void addCard(Card card) throws DeckControllerException {
        //what is the maximum of same cards we can have?
        if (cards.size() < MAXIMUM_NUMBER_OF_CARDS) {
            if ( card.getHeroClass().equals(Card.HeroClass.NEUTRAL) || card.getHeroClass().toString().equals(hero.toString())  ) { //this line is a little messy!
                cards.add(card);
                cardsUsage.put(card.getName(), 0);
            }else throw new DeckControllerException("this card is not valid for this hero");
        }else throw new DeckControllerException("You can't have more than "+MAXIMUM_NUMBER_OF_CARDS+" cards in a deck");
    }

    public void removeCard(Card card) throws DeckControllerException{
        if (cards.size() > MINIMUM_NUMBER_OF_CARDS) {
            cards.remove(card);
            cardsUsage.remove(card.getName());
        }else throw new DeckControllerException("You can't have less than "+MINIMUM_NUMBER_OF_CARDS+" cards in a deck");
    }

    public void incrementCardUse(Card card){
        int usage = cardsUsage.get(card.getName());
        cardsUsage.replace(card.getName() , usage+1);
    }

    public void decrementCardUse(Card card){
        int usage = cardsUsage.get(card.getName());
        cardsUsage.replace(card.getName() , usage-1 );
    }


}
