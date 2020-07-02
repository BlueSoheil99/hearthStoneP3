package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.NavigationPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.ClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PlayStarterPanel extends GamePanel {
    private PlayConfig properties;
    private CardPanel passivePanel, handPanel;
    private JLabel selectLabel;
    private JButton selectBtn, changeBtn, nextBtn;
    private String selectedPassive, selectedCard;
    private ClickListener clickListener;

    public PlayStarterPanel() {
        super();
        setBackground(new Color(0xA8765E));
        setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, new Color(0x562C1C)));
    }

    @Override
    protected void loadConfig() {
        properties = PlayConfig.getInstance();
    }

    @Override
    protected void createFields() {
        Font font = new Font("serif", Font.ITALIC, 30);
        selectLabel = new JLabel("Select a passive and edit first hand for your currentDeck");
        selectLabel.setFont(font);
        selectBtn = new JButton("Select");
        changeBtn = new JButton("Change");
        nextBtn = new JButton(" Next ");
        changeBtn.setEnabled(false);
        nextBtn.setEnabled(false);
        makeBtnLookBetter(selectBtn, "Serif", 30, false);
        makeBtnLookBetter(changeBtn, "Serif", 30, false);
        makeBtnLookBetter(nextBtn, "Tahoma", 40, true);
        SetSelectBtnActionListener();
        SetChangeBtnActionListener();
        SetPlayBtnActionListener();
        passivePanel = new CardPanel();
        passivePanel.setPassives(PlayHandler.getInstance().get3Passives());
        passivePanel.setClickListener(objName -> selectedPassive = objName);
        handPanel = new CardPanel();
        handPanel.setCards(PlayHandler.getInstance().getHand());
    }

    private void makeBtnLookBetter(JButton button, String fontName, int fontSize, boolean blueBorder) {
        Font font = new Font(fontName, Font.BOLD, fontSize);
        button.setFont(font);
        button.setContentAreaFilled(false);
        button.setBackground(new Color(192, 135, 107));
        if (blueBorder) {
            Border lastBorder = button.getBorder();
            button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(16, 90, 115), 5), lastBorder));
        }
    }

    private void SetSelectBtnActionListener() {
        selectBtn.addActionListener(e -> {
            if (selectedPassive == null) {
                JOptionPane.showMessageDialog(null, "You Haven't Chosen a Passive Yet");
            } else {
                changeBtn.setEnabled(true);
                nextBtn.setEnabled(true);
                selectBtn.setEnabled(false);
                handPanel.setClickListener(objName -> selectedCard = objName);
                passivePanel.disableClickListener();
            }
        });
    }

    private void SetChangeBtnActionListener() {
        changeBtn.addActionListener(e -> {
            if (selectedCard == null) {
                JOptionPane.showMessageDialog(null, "You Haven't Chosen a Card Yet");
            } else {
                try {
                    PlayHandler.getInstance().replaceCard(selectedCard);
                    handPanel.setCards(PlayHandler.getInstance().getHand());
                } catch (PlayException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    private void SetPlayBtnActionListener() {
        nextBtn.addActionListener(e -> {
            clickListener.select(selectedPassive);

        });
    }


    @Override
    protected void init() {
        passivePanel.setMinimumSize(new Dimension(properties.getPassivePanelWidth(), properties.getPassivePanelHeight()));
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 0, 0, 10);
        gc.gridx = 0;
        gc.gridy = GridBagConstraints.RELATIVE;
        add(selectLabel, gc);
        add(passivePanel, gc);
        add(selectBtn, gc);
        add(handPanel, gc);
        add(changeBtn, gc);
        add(nextBtn, gc);
        add(NavigationPanel.getInstance(), gc);
    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
