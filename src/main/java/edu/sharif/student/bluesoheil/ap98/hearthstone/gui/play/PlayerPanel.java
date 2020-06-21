package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.SidePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends SidePanel {

    private JButton endTurn , playBtn;
    private JButton  rightBtn , leftBtn;
    private HeroPanel heroPanel;
    private CardPanel cardPanel;
    private String selectedCard;
    private boolean isOpponent;

    public PlayerPanel(HeroTypes hero, int hp, int startingMana){
        super();
        heroPanel = new HeroPanel(hero , hp , startingMana);
        create();
        init();
        setPreferredSize(new Dimension(PlayConfig.getInstance().getPlayerPanelWidth()
                , PlayConfig.getInstance().getPlayerPanelHeight()));
    }

    private void create(){
        cardPanel = new CardPanel();
        endTurn = new JButton("End Turn");
        playBtn = new JButton("Play");
        rightBtn = new JButton("-->");
        leftBtn = new JButton("<--");
    }

    private void init(){
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(5,5,5,5);

        if (! isOpponent){
            gb.gridx=1;
            gb.gridy=0;
            add(endTurn,gb);
            gb.gridx=0;
            gb.gridy=1;
            add(leftBtn,gb);
            gb.gridx=1;
            add(playBtn,gb);
            gb.gridx=2;
            add(rightBtn,gb);
        }
        /////
        gb.gridy=0;
        gb.gridx = 3;
        gb.gridheight=2;
        add(heroPanel,gb);
        /////
        gb.gridy=0;
        gb.gridx = 4;
        add(cardPanel,gb);
    }

    void updateHero(){

    }

    void setOpponent(boolean isOpponent){
        this.isOpponent = isOpponent;
        init();
        //todo make it correct
    }
}
