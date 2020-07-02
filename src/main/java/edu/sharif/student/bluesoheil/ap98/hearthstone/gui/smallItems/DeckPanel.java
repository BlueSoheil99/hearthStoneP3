package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems;

import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.ClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DeckPanel extends SidePanel implements ActionListener {

    private ClickListener listener;
    private ArrayList<DeckShape> decks;
    private Border lastBorder;
    private DeckShape selectedDeck;
    private int width, height;
    private String currentDeck;

    public DeckPanel(int width, int height) {
        super(width, height);
        this.width = width;
        this.height = height;
    }

    public void setDecks(LinkedHashMap<String, String> decksToShow, String currentDeckName) {
        this.decks = new ArrayList<>();
        this.currentDeck = currentDeckName;
        for (Map.Entry<String, String> entry : decksToShow.entrySet()) {
            decks.add(new DeckShape(entry.getKey(), entry.getValue()));
        }
        paintDecksInPanel();
        for (DeckShape deckShape : decks) deckShape.addActionListener(this);
    }

    private void paintDecksInPanel() {
        setEmpty();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (DeckShape deckShape : decks) {
            if (deckShape.getDeckName().equals(currentDeck)) setDeckToCurrent(deckShape);
            add(deckShape);
        }
        setPreferredSize(new Dimension(width, decks.size() * 130));
        repaint();
    }

    private void setDeckToCurrent(DeckShape currentDeck) {
        currentDeck.setBorder(
                BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(199, 210, 9)));
    }

    private void setEmpty() {
        removeAll();
        revalidate();
        repaint();
    }

    public void unselectDeck() {
        if (lastBorder != null) selectedDeck.setBorder(lastBorder);
    }

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listener != null) {
            unselectDeck();
            selectedDeck = (DeckShape) e.getSource();
            lastBorder = selectedDeck.getBorder();
            selectedDeck.setBorder(
                    BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(16, 90, 115)));
            listener.select(selectedDeck.getDeckName());
            Logger.log(LogTypes.CLICK_BUTTON, "deck:  " + selectedDeck.getDeckName() + "  selected .");
        }
    }

}
