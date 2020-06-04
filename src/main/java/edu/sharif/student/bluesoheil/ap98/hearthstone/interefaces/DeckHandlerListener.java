package edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces;

import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;

import java.util.EventListener;

public interface DeckHandlerListener extends EventListener {

    void renameDeck(String newName);

    void deleteDeck();

    void removeCard();

    void addCard();

    void setCurrentDeck();

    void changeHero(HeroTypes heroName);

    void createNewDeck(String newDeckName , String heroName);

    void cancel(); //todo maybe it's redundant
}
