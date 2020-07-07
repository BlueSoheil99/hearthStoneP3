package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.CardClickListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerBoard extends CardPanel {
    private static int boardWidth = 1800;
    private static int boardHeight = 200;

//    private CardPanel playerPanel;
    private ArrayList<MinionActualCard> cards;
    private MinionActualCard selectedCard;
    private CardClickListener cardClickListener;


    PlayerBoard() {
        super();
        setPreferredSize(new Dimension(boardWidth , boardHeight));
        cards = new ArrayList<>();
    }

//    ArrayList<MinionActualCard> getSummonedMinions() {
//        return cards;
//    }//getCards from parent does the trick

    private void setCardClickListener(CardClickListener cardListener) {
        cardClickListener = cardListener;
    }
    private void disableCardClickListener(){
        cardClickListener = null;
        unselectCard();
    }

    public void addCard(MinionActualCard card, int index) {
        cards.add(index, card);
        setCards(cards);
    }
    public void addCard(MinionActualCard card) {
       addCard(card, 0);
    }

    void startTurn(CardClickListener currentPlayerCardListener) {
        setCardClickListener(currentPlayerCardListener);
        //todo if there's no more to do, just use setCardClickListener method
    }

    void endTurn(){
        disableCardClickListener();
        //todo if there's no more to do, just use disableCardClickListener method
    }
}

