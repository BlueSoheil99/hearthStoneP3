package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayPanel extends GamePanel {

    private PlayHandler playHandler;
    private PauseMenu pauseMenu;
    private CardPanel playerCards,opponentCards;
    private HeroPanel playerHeroPanel , opponentHeroPanel;



    @Override
    protected void loadConfig() {

    }

    @Override
    protected void createFields() {
        playHandler = PlayHandler.getInstance();
        pauseMenu = PauseMenu.getInstance();
    }

    @Override
    protected void init() {


        setLayout(new BorderLayout());
        add(new JScrollPane(new EventBox()) , BorderLayout.SOUTH);

    }
}
