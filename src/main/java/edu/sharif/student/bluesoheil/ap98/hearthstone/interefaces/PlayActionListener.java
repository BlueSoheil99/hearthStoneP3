package edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces;

import java.util.EventListener;

public interface PlayActionListener extends EventListener {
    void endTurn();
    void play();
    void summonCard();
    void goRight();
    void goLeft();
}
