package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.SidePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.ClickListener;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends SidePanel  {

    private CardPanel playerPanel , opponentPanel;


    BoardPanel(){
        playerPanel = new CardPanel();
        opponentPanel = new CardPanel();
        setLayout(new BorderLayout());
        add(new JScrollPane(playerPanel) , BorderLayout.SOUTH);
        add(new JScrollPane(opponentPanel) , BorderLayout.NORTH);
        setClickListeners();
    }

    private void setClickListeners() {
        playerPanel.setClickListener(new ClickListener() {
            @Override
            public void select(String objName) {

            }
        });

        opponentPanel.setClickListener(new ClickListener() {
            @Override
            public void select(String objName) {

            }
        });
    }
}
