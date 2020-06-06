package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.buttons;

import javax.swing.*;

public abstract class IconButton extends JButton {
    protected IconButton(String iconPath) {
        super("", new ImageIcon(iconPath));
        setBorderPainted(false);
        setContentAreaFilled(false);
        addListener();
    }

    protected abstract void addListener();
}
