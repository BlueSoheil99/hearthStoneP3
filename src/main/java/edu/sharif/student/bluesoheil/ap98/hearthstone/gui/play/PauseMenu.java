package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;


import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.buttons.BackButton;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.buttons.ExitButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class PauseMenu extends JMenuBar  implements ActionListener {
    private static PauseMenu instance;

    private JMenu menu;
    private JMenuItem forfeit, exit;

    private PauseMenu() {
        super();
        makeComponents();
        addComponents();
        setBackground(new Color(192, 135, 107));
    }

    public static PauseMenu getInstance() {
        if (instance == null) instance = new PauseMenu();
        return instance;
    }

    private void makeComponents() {
        Font font = new Font("helvetica" , Font.BOLD , 50);
        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_M);

        forfeit = new JMenuItem("Forfeit Game", BackButton.getInstance().getIcon() );
        forfeit.addActionListener(this);
        forfeit.setMnemonic(KeyEvent.VK_F);
        forfeit.setFont(font);
        forfeit.setBackground(new Color(238, 167, 132));
        forfeit.setBorder(BorderFactory.createMatteBorder(5,5,2,5,new Color(16, 90, 115)));

        exit = new JMenuItem("Exit to Desktop" , ExitButton.getInstance().getIcon());
        exit.addActionListener(this);
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setFont(font);
        exit.setBackground(new Color(238, 167, 132));
        exit.setBorder(BorderFactory.createMatteBorder(3,5,5,5,new Color(16, 90, 115)));

    }

    private void addComponents() {
        menu.add(forfeit);
        menu.add(exit);
        add(menu );
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (forfeit == e.getSource()) {
            PlayHandler.getInstance().forfeitMatch();
        }else if (e.getSource() == exit){
            PlayHandler.getInstance().exitGame();
        }
    }

}
