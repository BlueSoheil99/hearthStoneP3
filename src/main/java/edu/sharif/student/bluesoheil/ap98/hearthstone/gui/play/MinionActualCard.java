package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Minion;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MinionActualCard extends ActualCard {
    //have a look at protected items in superClass
    //todo load a static hashMap of minions and their picture.and then release bufferedImage from constructor
    private Minion card;
    private JLabel attackLabel, hpLabel;
    private CardLabel attack, hp;

    public MinionActualCard(Minion card, BufferedImage image) {
        super(card, image);
    }

    @Override
    protected void loadIcons() {
        cardIcons = new HashMap<>();
        String dir = ICON_DIR + "/minion/";
        cardIcons.put("attack", new ImageIcon(ImageLoader.loadImage(dir + "attack.png")));
        cardIcons.put("hp", new ImageIcon(ImageLoader.loadImage(dir + "hp.png")));
    }

    @Override
    protected void setCard(Card card) {
        this.card = (Minion) card;
    }

    @Override
    protected void createLabels() {
        cardName = new CardLabel(card.getName(),new Font("Helvetica", Font.PLAIN , 15));
        description = new CardLabel("",new Font("Helvetica", Font.ITALIC , 15));
        attackLabel = new JLabel("", cardIcons.get("attack"), JLabel.CENTER);
        hpLabel = new JLabel("", cardIcons.get("hp"), JLabel.CENTER);
        attack = new CardLabel("");
        hp = new CardLabel("");
    }

    @Override
    protected void setStates() {
        //todo check if there is need for description
        attack.setText(String.valueOf(card.getAttack()));
        hp.setText(String.valueOf(card.getHP()));
    }

    @Override
    protected void setupBackground() {
        setBackground(colorDictionary.get(Card.CardType.MINION));
        setBorder(BorderFactory.createLineBorder(Color.GREEN));
    }

    @Override
    protected void setupGraphics() {
        super.setupGraphics();
        cardName.setBounds(0,2*FIELD_HEIGHT , 4*FIELD_WIDTH,FIELD_HEIGHT);
        add(cardName);
        ///////
        attackLabel.setBounds(0,3*FIELD_HEIGHT , FIELD_WIDTH,FIELD_HEIGHT);
        attack.setBounds(15,3*FIELD_HEIGHT , FIELD_WIDTH,FIELD_HEIGHT);
        add(attack);
        add(attackLabel);
        ////////
        hpLabel.setBounds(3*FIELD_WIDTH,3*FIELD_HEIGHT , FIELD_WIDTH,FIELD_HEIGHT);
        hp.setBounds(3*FIELD_WIDTH+10,3*FIELD_HEIGHT , FIELD_WIDTH,FIELD_HEIGHT);
        add(hp);
        add(hpLabel);
    }

    void setHp(int hp) {
        this.hp.setText(String.valueOf(hp));
    }

    void setAttack(int attack){
        this.attack.setText(String.valueOf(attack));
    }
}
