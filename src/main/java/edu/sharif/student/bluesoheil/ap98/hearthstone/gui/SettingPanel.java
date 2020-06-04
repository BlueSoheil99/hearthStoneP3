package edu.sharif.student.bluesoheil.ap98.hearthstone.gui;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayerControllerException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.NavigationPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPanel extends GamePanel {
    @Override
    protected void loadConfig() {

    }

    @Override
    protected void createFields() {

    }

    @Override
    protected void init() {
        setBackground(new Color(168, 118, 94));
        setBorder(BorderFactory.createMatteBorder(20, 5, 40, 5, new Color(0x562C1C)));

        Font font = new Font("arial", Font.BOLD, 60);
        JLabel label = new JLabel("under construction !");
        JLabel label2 = new JLabel("you can just delete your player if you want :) ");
        label.setFont(font);
        label2.setFont(font);
        JButton deletePlayer = new JButton("Delete Player");
        deletePlayer.setFont(font);
        deletePlayer.setBackground(new Color(0xC0876B));
        deletePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo visit https://stackoverflow.com/questions/8881213/joptionpane-to-get-password
                Logger.log(LogTypes.CLICK_BUTTON , "button: DELETE PLAYER  selected.");
                String ans = JOptionPane.showInputDialog(null, "Enter your password to Delete your profile.",
                        "Confirm Delete", JOptionPane.WARNING_MESSAGE);
                if (ans != null ) {
                    try {
                        Administer.getInstance().deletePlayer(ans);
                    } catch (PlayerControllerException ex) {
                        ex.printStackTrace();
                        Logger.logError(LogTypes.PLAYER , ex);
                    }
                }
            }
        });
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(20,10,20,10);
        add(label, c);
        add(label2, c);
        add(deletePlayer, c);
        add(NavigationPanel.getInstance(), c);

    }

}
