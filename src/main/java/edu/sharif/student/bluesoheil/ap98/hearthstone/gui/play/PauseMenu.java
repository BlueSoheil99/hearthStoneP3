package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.BackButton;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.ExitButton;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.SidePanel;

import javax.swing.*;
import java.awt.*;

//todo by pressing esc button it must appear
public class PauseMenu extends SidePanel {
    private static PauseMenu instance;

    private JLabel pauseLabel;
    private JButton continueLabel, endGame, exitGame;

    private PauseMenu() {
//        super(PlayConfig.getInstance().getPauseWidth(),PlayConfig.getInstance().getPauseHeight());
        makeComponents();
        addComponents();
    }

    public static PauseMenu getInstance() {
        if (instance == null) instance = new PauseMenu();
        return instance;
    }

    private void makeComponents() {
        pauseLabel = new JLabel("PAUSE");
        continueLabel = new JButton("Continue");
        endGame = BackButton.getInstance();
        exitGame = ExitButton.getInstance();
    }

    private void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;
        g.weighty = 2;
        add(pauseLabel, g);
        g.gridy++;
        g.weighty = 1;
        add(continueLabel, g);
        g.gridy++;
        add(endGame, g);
        g.gridy++;
        add(exitGame, g);

    }


}
