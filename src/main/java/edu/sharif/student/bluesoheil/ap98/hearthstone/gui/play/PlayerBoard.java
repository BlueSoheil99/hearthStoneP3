package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.CardClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.PlayLogicConfig;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class PlayerBoard extends CardPanel {
    static final int CARDS_LIMIT = PlayLogicConfig.getInstance().getBoardLimit();
    private static int boardWidth = 1800;
    private static int boardHeight = 200;

    private ArrayList<MinionActualCard> cards;
    private MinionActualCard selectedCard;
    //    private MinionActualCard emptyCard = getEmptyCard();
    private Border lastBorder;
    private boolean hasPreview = false;
    private CardClickListener cardClickListener;
    private int latestAddedCardIndex;

    PlayerBoard() {
        super();
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        cards = new ArrayList<>();
    }

//    ArrayList<MinionActualCard> getSummonedMinions() {
//        return cards;
//    }//getCards from parent does the trick

    void setCardClickListener(CardClickListener cardListener) {
        cardClickListener = cardListener;
    }

    void disableCardClickListener() {
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

    boolean isFull() {
        return cards.size() >= CARDS_LIMIT;
    }

    void enablePreviewMode() {
        hasPreview = true;
        cards.get(0).setBackground(Color.ORANGE); // preview mode enables only after a card has just putted in 0 index
        //disableTheListenerForThatCard
    }

    void disablePreviewMode() {
        if (hasPreview) {
            if (cards.size() > 0) cards.remove(latestAddedCardIndex);
            setCards(cards);
            hasPreview = false;
        }
    }

    void previewCard(MinionActualCard card, int index) {

        card.setBackground(Color.ORANGE);
        hasPreview = true;

        //disableTheListenerForThatCard
    }

    public void addCard(MinionActualCard card, int index) {
        if (index <= cards.size() ) {
            if (hasPreview) {
                cards.remove(latestAddedCardIndex);
                card.setBackground(Color.ORANGE);
                //disableTheListenerForThatCard
//                card
            }
            latestAddedCardIndex = index;
            if (index == cards.size()) cards.add(card);
            else cards.add(index, card);
            setCards(cards);
//            if (hasPreview)cards.get(latestAddedCardIndex).removeActionListener(this);
        }
    }

    public void addCard(MinionActualCard card) {
        addCard(card, 0);
    }

    void startTurn(CardClickListener currentPlayerCardListener) {
        setCardClickListener(currentPlayerCardListener);
        //todo if there's no more to do, just use setCardClickListener method
    }

    void endTurn() {
        disableCardClickListener();
        disablePreviewMode();

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

