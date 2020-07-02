package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Minion;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Weapon;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class WeaponActualCard extends ActualCard {
    //have a look at protected items in superClass
    //todo load a static hashMap of minions and their picture.and then release bufferedImage from constructor
    private Weapon card;
    private JLabel attackLabel, durabilityLabel;
    private CardLabel attack, durability;

    public WeaponActualCard(Card card, BufferedImage image) {
        super(card, image);
    }

    @Override
    protected void loadIcons() {
        cardIcons = new HashMap<>();
        String dir = ICON_DIR + "/weapon/";
        cardIcons.put("attack", new ImageIcon(ImageLoader.loadImage(dir + "attack.png")));
        cardIcons.put("durability", new ImageIcon(ImageLoader.loadImage(dir + "durability.png")));
    }

    @Override
    protected void setCard(Card card) {
        this.card = (Weapon) card;
    }

    @Override
    protected void createLabels() {
        cardName = new CardLabel(card.getName(), new Font("Helvetica", Font.PLAIN, 15));
        description = new CardLabel("", new Font("Helvetica", Font.ITALIC, 15));
        attackLabel = new JLabel("", cardIcons.get("attack"), JLabel.CENTER);
        durabilityLabel = new JLabel("", cardIcons.get("durability"), JLabel.CENTER);
        attack = new CardLabel("");
        durability = new CardLabel("");
    }

    @Override
    protected void setStates() {
        //todo check if there is need for description
        attack.setText(String.valueOf(card.getAttack()));
        durability.setText(String.valueOf(card.getDurability()));
    }

    @Override
    protected void setupBackground() {
        setBackground(colorDictionary.get(Card.CardType.WEAPON));
        setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
    }

    @Override
    protected void setupGraphics() {
        super.setupGraphics();
        cardName.setBounds(5, 2 * FIELD_HEIGHT, CARD_WIDTH - 10, FIELD_HEIGHT);
        add(cardName);
        ///////
        attackLabel.setBounds(0, 3 * FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT);
        attack.setBounds(10, 3 * FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT);
        add(attack);
        add(attackLabel);
        ///////
        durabilityLabel.setBounds(3 * FIELD_WIDTH, 3 * FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT);
        durability.setBounds(3 * FIELD_WIDTH + 10, 3 * FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT);
        add(durability);
        add(durabilityLabel);
        ////////
    }

    void setAttack(int attack) {
        this.attack.setText(String.valueOf(attack));
    }

    void setDurability(int durability) {
        this.durability.setText(String.valueOf(durability));
    }

}
