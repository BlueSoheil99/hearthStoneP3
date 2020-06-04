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
import java.util.HashMap;
import java.util.Map;

public class DeckPanel extends SidePanel implements ActionListener {

    private ClickListener listener;
    private ArrayList<DeckShape> decks;
    private Border lastBorder;
    private DeckShape selectedDeck;

    public DeckPanel(int width, int height) {
        super(width, height);
    }

    public void setDecks(HashMap<String, String> decksToShow) {
        this.decks = new ArrayList<>();

        for (Map.Entry<String, String> entry : decksToShow.entrySet())
            decks.add(new DeckShape(entry.getKey(), entry.getValue()));

        paintDecksInPanel();

        for (DeckShape deckShape : decks) deckShape.addActionListener(this);
    }

    private void paintDecksInPanel() {
        //todo this is not working properly with decks more...it's because its height is set on 1080 and so it doesn't resize right
        setEmpty();
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 0, 5, 0);
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        for (DeckShape deckShape : decks) {
            add(deckShape, gc);
            gc.gridy++;
        }
        repaint();

    }

    private void setEmpty(){
        removeAll();
        revalidate();
        repaint();
    }

    public void unselectDeck(){
        if (lastBorder!=null)selectedDeck.setBorder(lastBorder);
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
                    BorderFactory.createMatteBorder(4,4,4,4,new Color(16, 90, 115)));
            listener.select(selectedDeck.getDeckName());
            Logger.log(LogTypes.CLICK_BUTTON, "deck:  " + selectedDeck.getDeckName() + "  selected .");
        }
    }

}
