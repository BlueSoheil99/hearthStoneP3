package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.SidePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.CardClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.ClickListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BoardPanel extends SidePanel  {

    private CardPanel playerPanel , opponentPanel;
    private CardShape selectedCard , selectedOpponentCard;


    BoardPanel(){
        playerPanel = new CardPanel();
        opponentPanel = new CardPanel();
        setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        add(new JScrollPane(opponentPanel) );
        add(new JScrollPane(playerPanel) );
//        add((opponentPanel) );
//        add((playerPanel) );
    }

    void setPlayerCardClickListeners(CardClickListener cardClickListener) {
        playerPanel.setCardClickListener(cardClickListener);
    }
    void setOpponentCardClickListeners(CardClickListener cardClickListener) {
        opponentPanel.setCardClickListener(cardClickListener);
    }

    ArrayList<CardShape> getPlayerCards(){
        return getCards(playerPanel);
    }

    ArrayList<CardShape> getOpponentCards(){
        return getCards(opponentPanel);
    }

    private ArrayList<CardShape> getCards(CardPanel cardPanel){
        return cardPanel.getCards();
    }

    void addCardForPlayer(CardShape cardShape){
        playerPanel.addCard(cardShape);
    }

    void addCardForOpponent(CardShape cardShape){
        opponentPanel.addCard(cardShape);
    }


}
