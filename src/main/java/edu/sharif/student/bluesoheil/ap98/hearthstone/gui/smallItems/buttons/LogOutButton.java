package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.buttons;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.GuiConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;


public class LogOutButton extends IconButton {
    private static LogOutButton instance;

    private LogOutButton(){
        super(GuiConstants.getInstance().getLogOutPath());
    }

    public static LogOutButton getInstance(){
        if (instance == null) instance = new LogOutButton();
        return  instance;
    }

    @Override
    protected void addListener() {
        addActionListener(e -> {
            Logger.log(LogTypes.CLICK_BUTTON , "button: LOGOUT  selected.");
            int ans = JOptionPane.showConfirmDialog(null, "Are You Sure You Want to LogOut?",
                    "Confirm LogOut", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {
                Administer.getInstance().runLogout();
            }
        });
    }
}
