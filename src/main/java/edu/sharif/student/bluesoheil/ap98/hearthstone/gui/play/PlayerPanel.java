package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.SidePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.CardClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.ClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.PlayActionListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerPanel extends SidePanel {
    private static int cardPanelWidth = 1000;
    private static int cardPanelHeight = 280;
    //todo show quest&rewards progress
    private JButton endTurnBtn, playBtn;
    private JButton rightBtn, leftBtn;
    private HeroPanel heroPanel;
    private CardPanel cardPanel;
    private String selectedCard;
    private boolean isOpponent;
    private ClickListener clickListener;
    private PlayActionListener playActionListener;

    public PlayerPanel(HeroTypes hero, int hp, int startingMana) {
        super();
        setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(0x562C1C)));
        heroPanel = new HeroPanel(hero, hp, startingMana);
        create();
        init();
        setPreferredSize(new Dimension(PlayConfig.getInstance().getPlayerPanelWidth()
                , PlayConfig.getInstance().getPlayerPanelHeight()));
    }

    private void create() {
        cardPanel = new CardPanel();
        endTurnBtn = new JButton("End Turn");
        endTurnBtn.setBackground(Color.GREEN);
        playBtn = new JButton("Play");
        rightBtn = new JButton("~>");
        leftBtn = new JButton("<~");
        setActionListeners();
    }

    private void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(0, 5, 0, 5);

        gb.gridx = 1;
        gb.gridy = 0;
        add(endTurnBtn, gb);
        gb.gridx = 0;
        gb.gridy = 1;
        add(leftBtn, gb);
        gb.gridx = 1;
        add(playBtn, gb);
        gb.gridx = 2;
        add(rightBtn, gb);
        /////
        gb.gridy = 0;
        gb.gridx = 3;
        gb.gridheight = 2;
        add(heroPanel, gb);
        /////
        gb.gridy = 0;
        gb.gridx = 4;
        gb.gridwidth = 3;
        JScrollPane jScrollPane = new JScrollPane(cardPanel);
        jScrollPane.setPreferredSize(new Dimension(cardPanelWidth, cardPanelHeight));
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        add(jScrollPane, gb);
    }

    void updateHand(ArrayList<CardShape> latestHand, HashMap<String, Integer> latestHeroStates) {
        cardPanel.setCards(latestHand, latestHand.size());
        heroPanel.updateStates(latestHeroStates.get("HP"), latestHeroStates.get("MANA"), latestHeroStates.get("CARDS"));
    }

    void endTurn() {
        endTurnBtn.setEnabled(false);
        playBtn.setEnabled(false);
        rightBtn.setEnabled(false);
        leftBtn.setEnabled(false);
        //todo card ha barAx shan va card haye tu board disabled shan
    }

    void startTurn() {
        endTurnBtn.setEnabled(true);
        playBtn.setEnabled(true);
        rightBtn.setEnabled(true);
        leftBtn.setEnabled(true);
    }

    void setClickListenerForCards(ClickListener clickListener) {
        cardPanel.setClickListener(clickListener);
    }

    void setClickListenerForActions(PlayActionListener playActionListener) {
        this.playActionListener = playActionListener;
    }

    void setWeaponCard(WeaponActualCard weaponCard) {
        heroPanel.setWeaponBtn(weaponCard);
    }

    private void setActionListeners() {
        endTurnBtn.addActionListener(e -> {
            if (playActionListener != null) playActionListener.endTurn();
        });

        playBtn.addActionListener(e -> {
            if (playActionListener != null) playActionListener.play();
        });

        rightBtn.addActionListener(e -> {
            if (playActionListener != null) playActionListener.goRight();
        });

        leftBtn.addActionListener(e -> {
            if (playActionListener != null) playActionListener.goLeft();
        });
    }

}
