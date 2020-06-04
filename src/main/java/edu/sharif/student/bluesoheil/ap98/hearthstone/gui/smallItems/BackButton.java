package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.GuiConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButton extends JButton {
    private static BackButton instance;

    private BackButton() {
        super("", new ImageIcon(GuiConstants.getInstance().getBackIconPath()));
        setBorderPainted(false);
        setContentAreaFilled(false);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log(LogTypes.CLICK_BUTTON, "button: BACK selected .");
                Administer.getInstance().back();
            }
        });
    }

    public static BackButton getInstance() {
        if (instance == null) instance = new BackButton();
        return instance;
    }

}
