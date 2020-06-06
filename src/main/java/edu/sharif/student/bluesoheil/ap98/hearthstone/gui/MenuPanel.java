package edu.sharif.student.bluesoheil.ap98.hearthstone.gui;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.buttons.LogOutButton;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.MenuConfig;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends GamePanel {

    private String bgPath, fontName;
    private int fontSize;
    private JLabel welcomeMsg;
    private JButton playBtn, shopBtn, statusBtn, collectionBtn, settingBtn;
    private JButton exitBtn;

    public MenuPanel() {
        super();
    }

    @Override
    protected void loadConfig() {
        MenuConfig properties = MenuConfig.getInstance();
        bgPath = properties.getBgPath();
        fontName = properties.getFontName();
        fontSize = properties.getFontSize();
    }

    @Override
    protected void createFields() {
        welcomeMsg = new JLabel("Hello " + Administer.getInstance().getCurrentPlayer().getUserName() + " :) ");
        welcomeMsg.setFont(new Font("Arial", Font.ITALIC, 60));
        welcomeMsg.setForeground(Color.white);
        createButtons();
        addListenersToButtons();
    }

    @Override
    protected void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 15, 5, getWidth() * 2 / 3);
        c.gridy = GridBagConstraints.RELATIVE;
        c.anchor = GridBagConstraints.WEST;
        c.weighty = 6;
        add(welcomeMsg, c);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weighty = 1;
        add(playBtn, c);
        add(shopBtn, c);
        add(statusBtn, c);
        add(collectionBtn, c);
        add(settingBtn, c);
        add(exitBtn, c);
        c.weighty = 4;
        add(LogOutButton.getInstance(), c);
    }

    private void createButtons() {
        playBtn = new MenuButton("Play");
        shopBtn = new MenuButton("Shop");
        statusBtn = new MenuButton("Status");
        collectionBtn = new MenuButton("Collection");
        settingBtn = new MenuButton("Setting");
        exitBtn = new MenuButton("EXIT");
        exitBtn.setForeground(Color.RED);
    }

    private void addListenersToButtons() {
        playBtn.addActionListener(e -> Administer.getInstance().runPlay());
        shopBtn.addActionListener(e -> Administer.getInstance().runShop());
        statusBtn.addActionListener(e -> Administer.getInstance().runStatus());
        collectionBtn.addActionListener(e -> Administer.getInstance().runCollection());
        settingBtn.addActionListener(e -> Administer.getInstance().runSetting());
        exitBtn.addActionListener(e -> Administer.getInstance().runExit());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage backGround = ImageLoader.loadImage(bgPath);
        g.drawImage(backGround, 0, 0, getWidth(), getHeight(), null);
    }


    private class MenuButton extends JButton {
        MenuButton(String name) {
            super(name);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFont(new Font(fontName, Font.BOLD, fontSize));
            setForeground(Color.WHITE);
        }
    }

}
