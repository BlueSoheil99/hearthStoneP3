package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends GamePanel {

    private PlayHandler playHandler;
    private PauseMenu pauseMenu;
    private CardPanel playerCards,opponentCards;
    private PlayerPanel playerHeroPanel , opponentHeroPanel;
    private BoardPanel board;



    @Override
    protected void loadConfig() {

    }

    @Override
    protected void createFields() {
        playHandler = PlayHandler.getInstance();
        pauseMenu = PauseMenu.getInstance();
        board = new BoardPanel();
    }

    @Override
    protected void init() {


        setLayout(new BorderLayout());
        add(new JScrollPane(new EventBox()) , BorderLayout.SOUTH);
        add(board , BorderLayout.NORTH);

    }
}
