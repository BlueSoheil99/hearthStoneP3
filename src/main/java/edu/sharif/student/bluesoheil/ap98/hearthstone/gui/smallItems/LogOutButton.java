package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.GuiConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogOutButton extends JButton {
    private static LogOutButton instance;

    public LogOutButton(){
        super();
        setIcon(new ImageIcon(GuiConstants.getInstance().getLogOutPath()));
        setContentAreaFilled(false);
        setBorderPainted(false);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log(LogTypes.CLICK_BUTTON , "button: LOGOUT  selected.");
                int ans = JOptionPane.showConfirmDialog(null, "Are You Sure You Want to LogOut?",
                        "Confirm LogOut", JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.YES_OPTION) {
                    Administer.getInstance().runLogout();
                }
            }
        });
    }

//    public static LogOutButton getInstance(){
//        if (instance == null) instance = new LogOutButton();
//        return  instance;
//    }

}
