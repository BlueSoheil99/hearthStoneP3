package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Beast;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Minion;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Weapon;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class ActualCard extends CardShape {
    //todo have two children named minionActualCArd and weaponActualCard
    private static final int CARD_WIDTH = PlayConfig.getInstance().getCardWidth();
    private static final int CARD_HEIGHT = PlayConfig.getInstance().getCardHeight();
    private static final int FIELD_HEIGHT = CARD_HEIGHT / 9;
    private static final int FIELD_WIDTH = CARD_WIDTH / 2;
    private static final HashMap<Card.CardType, Color> colorDictionary = getColorDictionary();

    private static ArrayList<ImageIcon> hpList;
    private static ArrayList<ImageIcon> attackList;
    private static ArrayList<ImageIcon> durabilityList;
    private Minion minionCard;
    private Weapon weaponCard;
    //    private ImageIcon cardIcon;
    private JLabel hp, attack, durability;
    private JLabel hpLabel, attackLabel, durabilityLabel;

    static {
        loadIcons();
    }

    private static void loadIcons() {

    }

    private static HashMap<Card.CardType, Color> getColorDictionary() {
        HashMap<Card.CardType, Color> dict = new HashMap<>();
        dict.put(Card.CardType.MINION, new Color(3, 80, 11));
        dict.put(Card.CardType.BEAST, new Color(77, 47, 0));
        dict.put(Card.CardType.WEAPON, new Color(77, 10, 69));
        return dict;
    }

    public ActualCard(Card card, BufferedImage image) {
        super(card.getName(), image);
        setCard(card);
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        createLabels();
        setupGraphics();
    }

    private void setCard(Card card) {
        if (card.getType().equals(Card.CardType.MINION)) minionCard = (Minion) card;
        if (card.getType().equals(Card.CardType.BEAST)) minionCard = (Beast) card;
        if (card.getType().equals(Card.CardType.WEAPON)) weaponCard = (Weapon) card;
    }


    private void createLabels() {
        hpLabel = new JLabel();
        attackLabel = new JLabel();
        durability = new JLabel();
        if (minionCard != null) {
            hp = new JLabel(String.valueOf(minionCard.getHP()));
            attack = new JLabel(String.valueOf(minionCard.getAttack()));
        } else attack = new JLabel(String.valueOf(weaponCard.getAttack()));
        durability = new JLabel(String.valueOf(weaponCard.getDurability()));
        if (hp != null) hp.setForeground(Color.white);
        attack.setForeground(Color.white);
        if (durability != null) durability.setForeground(Color.white);
    }

    private void setupGraphics() {
//        setLayout(null);
//        hpLabel.setBounds(0,2/3*CARD_HEIGHT ,FIELD_WIDTH , FIELD_HEIGHT );
//        attackLabel.setBounds(0,2/3*CARD_HEIGHT ,FIELD_WIDTH , FIELD_HEIGHT );
//        repaint();
        setIcon(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0;
        gb.gridy = 0;
        gb.gridheight = 3;
        gb.gridwidth = 3;
        add(new JLabel("", icon, JLabel.CENTER), gb);
        gb.gridx = 0;
        gb.gridy = 1;
        gb.gridheight = 1;
        gb.gridwidth = 1;
        add(attackLabel, gb);
        gb.gridx = 3;
        gb.gridy = 1;
        add(attack, gb);
    }

    private void setStates() {

    }
}
