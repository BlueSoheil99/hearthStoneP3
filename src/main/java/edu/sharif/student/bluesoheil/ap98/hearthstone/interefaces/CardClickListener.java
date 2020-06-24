package edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;

import java.util.EventListener;

public interface CardClickListener extends EventListener {
    void selectCard( CardShape selectedCard);
}
