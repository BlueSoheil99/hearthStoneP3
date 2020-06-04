package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems;

import javax.swing.*;
import java.awt.*;

public class NavigationPanel extends JPanel {
    private static NavigationPanel instance;
    private NavigationPanel(){
        setPreferredSize(new Dimension(350,150)); //suits both exitButton and backButton
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = GridBagConstraints.RELATIVE;
        add(ExitButton.getInstance(),gc);
        add(BackButton.getInstance(),gc);
    }
    public static NavigationPanel getInstance(){
        if (instance ==null) instance = new NavigationPanel();
        return instance;
    }

}
