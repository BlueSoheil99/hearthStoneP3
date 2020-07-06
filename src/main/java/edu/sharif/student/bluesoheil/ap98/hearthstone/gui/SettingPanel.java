package edu.sharif.student.bluesoheil.ap98.hearthstone.gui;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayerControllerException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.NavigationPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.GuiConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.ImageLoader;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class SettingPanel extends GamePanel {
    private GuiConstants properties;
    private Font font, font2;
    private JLabel settingLabel;
    private CardPanel cardPanel;
    private JButton deletePlayer, selectBtn;
    private String selectedBackCoverFileName;


    @Override
    protected void loadConfig() {
        properties = GuiConstants.getInstance();
    }

    @Override
    protected void createFields() {
        setBackground(new Color(168, 118, 94));
        setBorder(BorderFactory.createMatteBorder(20, 5, 40, 5, new Color(0x562C1C)));
        font = new Font("arial", Font.BOLD, 45);
        font2 = new Font("arial", Font.ITALIC, 30);
        settingLabel = new JLabel("You can delete user or change cards backCover here ");
        settingLabel.setFont(font);
        setupDeletePlayerBtn();
        setupCardPanelStuff();

    }

    @Override
    protected void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(20, 10, 20, 10);
        add(settingLabel, c);
        add(cardPanel, c);
        add(selectBtn, c);
        add(deletePlayer, c);
        add(NavigationPanel.getInstance(), c);

    }

    private void setupDeletePlayerBtn() {
        deletePlayer = getButton("Delete Player");
        deletePlayer.addActionListener(e -> {
            //todo visit https://stackoverflow.com/questions/8881213/joptionpane-to-get-password
            Logger.log(LogTypes.CLICK_BUTTON, "button: DELETE PLAYER  selected.");
            String ans = JOptionPane.showInputDialog(null, "Enter your password to Delete your profile.",
                    "Confirm Delete", JOptionPane.WARNING_MESSAGE);
            if (ans != null) {
                try {
                    Administer.getInstance().deletePlayer(ans);
                } catch (PlayerControllerException ex) {
                    ex.printStackTrace();
                    Logger.logError(LogTypes.PLAYER, ex);
                }
            }
        });
    }

    private void setupCardPanelStuff() {
        cardPanel = new CardPanel();
        cardPanel.setCards(getBackCovers());
        cardPanel.setClickListener(objName -> selectedBackCoverFileName = objName);
        selectBtn = getButton("select");
        selectBtn.addActionListener(e -> {
            if (selectedBackCoverFileName != null) {
                properties.setDefaultBackCoverPath(selectedBackCoverFileName);
                CardShape.updateBackCover();
                cardPanel.unselectCard();
                JOptionPane.showMessageDialog(null, "you changed backCover successfully");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Select a backCover first", "Selection Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JButton getButton(String name) {
        JButton btn = new JButton(name);
        btn.setFont(font2);
        btn.setBackground(new Color(0xC0876B));
        return btn;
    }

    private ArrayList<CardShape> getBackCovers() {
        ArrayList<CardShape> backCovers = new ArrayList<>();
        String[] files = new File(properties.getBackCoversPath()).list();
        CardShape cardShape;
        for (String fileName : files) {
            cardShape = new CardShape(fileName, ImageLoader.loadImage(properties.backCoversPath + "/" + fileName));
            backCovers.add(cardShape);
        }
        return backCovers;
    }
}
