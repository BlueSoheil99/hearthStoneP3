package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.ConfigLoader;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.Configs;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends GamePanel {

    private PlayConfig properties;
    private PlayHandler playHandler;
    private PauseMenu pauseMenu;
    private CardPanel playerCards,opponentCards;
    private PlayerPanel playerPanel, opponentPanel;
    private BoardPanel board;



    @Override
    protected void loadConfig() {
        properties = PlayConfig.getInstance();
    }

    @Override
    protected void createFields() {
        playHandler = PlayHandler.getInstance();
        playerPanel = playHandler.getPlayerPanel();
//        opponentPanel = playHandler.getOpponentPanel();
        opponentPanel = new PlayerPanel(HeroTypes.PRIEST, 30 , 1);
        playHandler = PlayHandler.getInstance();
        pauseMenu = PauseMenu.getInstance();
        board = new BoardPanel();
    }

    @Override
    protected void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(opponentPanel);
        add(board );
        add(playerPanel );
        add(new JScrollPane(new EventBox()));

    }
}
