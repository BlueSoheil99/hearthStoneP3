package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play.ActualCard;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.CardClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.ClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.GuiConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CardPanel extends SidePanel implements ActionListener {

    private ArrayList<CardShape> cards;
    private CardShape selectedCard;
    private Border lastBorder;
    private ClickListener clickListener;
    private CardClickListener cardClickListener;
    private boolean isPassive;

    public CardPanel() {
        super();
        setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(0x562C1C)));
        cards = new ArrayList<>();
    }

    public ArrayList<CardShape> getCards() {
        return cards;
    }

    public void setCards(ArrayList<? extends CardShape> cardShapes, int cardsInRow) {
        cards = new ArrayList<>();
        for (CardShape cardShape : cardShapes) cards.add((CardShape) cardShape);
        paintCardsInPanel(cardsInRow);
        for (CardShape cardShape : cards) cardShape.addActionListener(this);
    }

    public void setCards(ArrayList<? extends CardShape> cardShapes) {
        setCards(cardShapes, GuiConstants.getInstance().getNumberOfCardsInRow());
    }

    public void addCard(CardShape card, int index) {
        cards.add(index, card);
        setCards(cards);
    }

    public void addCard(CardShape card) {
        if (cards.size() == 0) addCard(card, 0);
        else addCard(card, cards.size() - 1);
    }

    public void setPassives(ArrayList<CardShape> passives) {
        setCards(passives);
        isPassive = true;
    }

    private void paintCardsInPanel(int numberOfCardsInRow) {
        //remember! we don't want a horizontal scroll!
        setEmpty();
//        int numberOfCardsInRow = getWidth() / CardShape.getCardWidth() - 1;
        // getWidth gives 0 for the first setting cards. that was a problem specially in shop so when we set cards in
        // cardPanel we also set number of cards in a row. default is the number in guiConstants.properties
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.gridx = 0;
        gc.insets = new Insets(1, 1, 1, 1);
        int x = 0;
        for (CardShape cardShape : cards) {
            if (x > numberOfCardsInRow - 1) {
                x = 0;
                gc.gridy++;
                gc.gridx = 0;
            }
            add(cardShape, gc);
            x++;
            gc.gridx = x;
        }
    }

    public void setEmpty() {
        removeAll();
        revalidate();
        repaint();
    }

    public void setCardsBackward(boolean enable) {
        for (CardShape card : cards) card.showBackOfCard(enable);
    }

    public void unselectCard() {
        if (lastBorder != null) selectedCard.setBorder(lastBorder);
    }

    protected void selectCard(CardShape selectedCard) {
        lastBorder = selectedCard.getBorder();
        selectedCard.setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, new Color(16, 90, 115)));
    }

    public void setClickListener(ClickListener listener) {
        this.clickListener = listener;
    }

    public void disableClickListener() {
        clickListener = null;
        unselectCard();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (clickListener != null || cardClickListener != null) {
        if (clickListener != null) {
            unselectCard();
            selectedCard = (CardShape) e.getSource();
            selectCard(selectedCard);
            if (clickListener != null) clickListener.select(selectedCard.getCardName());
//            if (cardClickListener != null) cardClickListener.selectCard((ActualCard) selectedCard);
            if (!isPassive) {
                Logger.log(LogTypes.CLICK_BUTTON, "card: " + selectedCard.getCardName() + "  selected.");
            } else {
                Logger.log(LogTypes.CLICK_BUTTON, "passive: " + selectedCard.getCardName() + "  selected.");
            }
        }

    }

}
