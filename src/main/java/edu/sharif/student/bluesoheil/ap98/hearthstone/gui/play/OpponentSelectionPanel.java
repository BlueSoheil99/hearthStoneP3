package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.DeckPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.NavigationPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.ClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class OpponentSelectionPanel extends GamePanel {
    private PlayConfig properties;
    private JLabel selectLabel;
    private DeckPanel deckPanel;
    private JButton selectBtn, playBtn;
    private String selectedPassive, selectedDeck;
    private ClickListener clickListener;

    public OpponentSelectionPanel() {
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
        selectLabel = new JLabel(" Select a deck as an opponent \n passive and first hand for opponent are random ");
        selectLabel.setFont(font);

        setupDeckPanel();

        playBtn = new JButton(" PLAY ");
        makeBtnLookBetter(playBtn, "Tahoma", 40, true);
        SetPlayBtnActionListener();
    }

    private void setupDeckPanel() {
        deckPanel = new DeckPanel(properties.getDeckPanelWidth(), properties.getDeckPanelHeight());
        deckPanel.setDecks(PlayHandler.getInstance().getAvailableDecks(), null);
        deckPanel.setClickListener(objName -> selectedDeck = objName);
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

    private void SetPlayBtnActionListener() {
        playBtn.addActionListener(e -> {
            if (selectedDeck == null){
                JOptionPane.showMessageDialog(null , "Choose an opponent first ","match setup error" , JOptionPane.ERROR_MESSAGE);
            }else clickListener.select(selectedDeck);
        });
    }


    @Override
    protected void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 0, 0, 10);
        gc.gridx = 0;
        gc.gridy = GridBagConstraints.RELATIVE;

        add(selectLabel, gc);
        add(playBtn, gc);
        add(NavigationPanel.getInstance(), gc);
        gc.gridx = 1;
        gc.gridy = GridBagConstraints.RELATIVE;
        gc.gridheight = 5;
        JScrollPane scrollPane = new JScrollPane(deckPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, gc);
    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}


