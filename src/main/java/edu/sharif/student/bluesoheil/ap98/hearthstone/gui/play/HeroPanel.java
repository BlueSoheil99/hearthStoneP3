package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.SidePanel;

import javax.swing.*;

public class HeroPanel extends SidePanel {
    private JLabel heroName;
    private JButton heroPower;
    private JButton endTurn , playBtn;
    private CardPanel cardPanel;

    HeroPanel(String hero , int hp, int startingMana){
        super();
//        setPreferredSize();
        create();
        init();
    }

    void create(){
        heroName = new JLabel("hero");
//        heroName

    }
    void init(){

    }
}
