package edu.sharif.student.bluesoheil.ap98.hearthstone.gui;

import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.GuiConstants;

import javax.swing.*;
import java.awt.*;

/**
 * we use this abstract class to create big panel that we stick them into our MainFrame
 */
public abstract class GamePanel extends JPanel {
    private final int width = GuiConstants.getInstance().getGameWidth();
    private final int height = GuiConstants.getInstance().getGameHeight();

    public GamePanel() {

        setSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setBackground(Color.white);
        //todo read about layouts
        setLayout(null);
        loadConfig();
        createFields();
        init();
    }

    /**
     * it loads config file in order to use properties
     */
    protected abstract void loadConfig();

    /**
     * it creates user interface objects separately
     */
    protected abstract void createFields();

    /**
     * it sets a layout for panel and then adds created components to the panel and sets listeners for them
     */
    protected abstract void init();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //when we recall a panel, it gets smaller! For Instance: when you switch between mainMenu and settingPanel,
        // you'll see that the size of our mainMenu panel gets smaller and smaller! that's why we should reSize our
        // panel every time we want to display it, if you didn't get me(!) make the line below as a comment and switch
        // between two already initialized panels.
        setSize(new Dimension(width, height));
    }
}
