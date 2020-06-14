package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.SidePanel;

import javax.swing.*;

public class PlayerPanel extends SidePanel {
    private JLabel heroName;
    private JButton heroPower;
    private JButton endTurn , playBtn;
    private JButton  rightBtn , leftBtn;
    private HeroPanel heroPanel;
    private CardPanel cardPanel;

    PlayerPanel(String hero , int hp, int startingMana){
        super();
//        setPreferredSize();
        create();
        init();
    }

    private void create(){
        heroName = new JLabel("hero");
        endTurn = new JButton("End Turn");
        playBtn = new JButton("Play");
        rightBtn = new JButton();
        leftBtn = new JButton();
    }

    private void init(){

    }
}
