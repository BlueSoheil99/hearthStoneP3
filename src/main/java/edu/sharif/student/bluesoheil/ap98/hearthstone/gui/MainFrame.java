package edu.sharif.student.bluesoheil.ap98.hearthstone.gui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("HearthStone");
        setResizable(false);
        setVisible(true);
//       make it full screen

    }

    public void initFrame(JPanel panel) {
        setContentPane(panel);
        setPreferredSize(panel.getSize());
//        getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        setMinimumSize(panel.getSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        revalidate();
        setLocationRelativeTo(null);
    }

}
