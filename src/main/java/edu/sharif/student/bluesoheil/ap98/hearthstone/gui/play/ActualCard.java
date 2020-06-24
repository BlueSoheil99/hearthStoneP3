package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class ActualCard extends CardShape {
    protected static final int CARD_WIDTH = PlayConfig.getInstance().getCardWidth();
    protected static final int CARD_HEIGHT = PlayConfig.getInstance().getCardHeight();
    protected static final String ICON_DIR = PlayConfig.getInstance().getCardIconsPath();
    protected static final int FIELD_HEIGHT = CARD_HEIGHT / 4;
    protected static final int FIELD_WIDTH = CARD_WIDTH / 4;
    protected static final HashMap<Card.CardType, Color> colorDictionary = getColorDictionary();

    protected CardLabel cardName;
    protected CardLabel description;
    protected HashMap<String , ImageIcon> cardIcons;



    private static HashMap<Card.CardType, Color> getColorDictionary() {
        HashMap<Card.CardType, Color> dict = new HashMap<>();
        dict.put(Card.CardType.MINION, new Color(3, 80, 11));
        dict.put(Card.CardType.BEAST, new Color(77, 47, 0));
        dict.put(Card.CardType.WEAPON, new Color(77, 10, 69));
        return dict;
    }

    public ActualCard(Card card, BufferedImage image) {
        super(card.getName(), image);
        setIcon(null);
        loadIcons();
        setCard(card);
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        createLabels();
        setStates();
        setContentAreaFilled(true);
        setupBackground();
        setupGraphics();
    }

    protected abstract void loadIcons();
    protected abstract void setCard(Card card);
    protected abstract void createLabels();
    protected abstract void setStates();
    protected abstract void setupBackground();
    protected void setupGraphics(){
        setLayout(null);
        if (icon!= null){
            JLabel iconLabel = new JLabel("" , icon , JLabel.CENTER);
            iconLabel.setBounds(5,5,CARD_WIDTH-10,2*CARD_HEIGHT-10);
            add(iconLabel);
        }
    }


    protected class CardLabel extends JLabel{
        CardLabel(String msg , Font font , Color color){
            super(msg);
            setFont(font);
            setForeground(color);
        }
        CardLabel(String msg , Font font){
            this(msg , font , Color.WHITE);
        }
        CardLabel(String msg){
            this(msg , new Font("Helvetica" , Font.BOLD , 20));
        }
    }

}
