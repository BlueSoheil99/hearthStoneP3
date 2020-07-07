package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.CardClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Minion;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class PlayerBoard extends CardPanel {
    private static int boardWidth = 1800;
    private static int boardHeight = 200;

    private ArrayList<MinionActualCard> cards;
    private MinionActualCard selectedCard;
//    private MinionActualCard emptyCard = getEmptyCard();
    private Border lastBorder;
    private CardClickListener cardClickListener;

    PlayerBoard() {
        super();
        setPreferredSize(new Dimension(boardWidth , boardHeight));
        cards = new ArrayList<>();
    }

//    ArrayList<MinionActualCard> getSummonedMinions() {
//        return cards;
//    }//getCards from parent does the trick

    void setCardClickListener(CardClickListener cardListener) {
        cardClickListener = cardListener;
    }
    void disableCardClickListener(){
        cardClickListener = null;
        unselectCard();
    }


    protected void selectCard(MinionActualCard selectedCard) {
        this.selectedCard = selectedCard;
        lastBorder = selectedCard.getBorder();
        selectedCard.setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, new Color(16, 90, 115)));
    }

    @Override
    public void unselectCard() {
        if (selectedCard != null) {
            if (lastBorder != null) {
                selectedCard.setBorder(lastBorder);
                selectedCard = null;
            }
        }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (cardClickListener != null) {
            unselectCard();
            selectCard((MinionActualCard) e.getSource());

            Logger.log(LogTypes.CLICK_BUTTON, "passive: " + selectedCard.getCardName() + "  selected.");

            if (cardClickListener != null) cardClickListener.selectCard(selectedCard);
        }
    }
}

