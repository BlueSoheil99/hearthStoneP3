package edu.sharif.student.bluesoheil.ap98.hearthstone.gui;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.CardControllerException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.GuiException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.NavigationPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.SidePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.GuiConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import java.awt.*;


public class ShopPanel extends GamePanel {
    private GuiConstants properties;
    private SidePanel controlPanel;
    private CardPanel cardPanel;
    private String selectedCard;

    private JLabel coins, selectLabel, cardLabel, costLabel;
    private JButton sellBtn;
    private JButton buyBtn;

    public ShopPanel() {
        super();
    }

    @Override
    protected void loadConfig() {
        properties = GuiConstants.getInstance();
    }

    @Override
    protected void createFields() {
        cardPanel = new CardPanel();
        cardPanel.setCards(Administer.getInstance().getAllShapesOfCards());
        cardPanel.setClickListener(selectedCardName -> {
            selectedCard = selectedCardName;
            revalidateController();
        });
        createControlPanel();
    }

    @Override
    protected void init() {
        setLayout(new BorderLayout());
        add(new JScrollPane(cardPanel), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.WEST);

    }

    private void createControlPanel() {
        controlPanel = new SidePanel(getWidth() / 5, getHeight());
        createControllerComponents();
        addControllerComponents();
    }

    private void createControllerComponents() {
        int playerCoins = Administer.getInstance().getPlayerCoins();
        coins = createShopLabel(Integer.toString(playerCoins), controlPanel.getFont1());
        coins.setIcon(new ImageIcon(properties.getCoinsIconPath()));

        selectLabel = createShopLabel("selected card: ", controlPanel.getFont1());
        cardLabel = createShopLabel(selectedCard, controlPanel.getFont2());
        costLabel = createShopLabel("card cost: " + getSelectedCardCost(), controlPanel.getFont1());

        sellBtn = createShopButton("Sell", controlPanel.getFont1());
        buyBtn = createShopButton("Buy", controlPanel.getFont1());
        sellBtn.setContentAreaFilled(false);
        buyBtn.setContentAreaFilled(false);

        setControllerActionListeners();
    }

    private JButton createShopButton(String name, Font font) {
        JButton b = new JButton(name);
        b.setFont(font);
        return b;
    }

    private JLabel createShopLabel(String name, Font font) {
        JLabel L = new JLabel(name);
        L.setFont(font);
        return L;
    }

    private void addControllerComponents() {
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);
        gc.gridy = 0;
        gc.weighty = 2;
        controlPanel.add(coins, gc);
        /////////
        gc.gridy++;
        gc.weighty = 1;
        controlPanel.add(selectLabel, gc);
        //////////
        gc.gridy++;
        controlPanel.add(cardLabel, gc);
        //////////
        gc.gridy++;
        controlPanel.add(costLabel, gc);
        //////////
        gc.weighty = 2;
        gc.gridy++;
        controlPanel.add(sellBtn, gc);
        ////////
        gc.gridy++;
        gc.weighty = 2;
        controlPanel.add(buyBtn, gc);
        ///////
        gc.gridy++;
        gc.weighty = 0.5;
        gc.anchor = GridBagConstraints.PAGE_END;
        //in the end add exit and back button
        controlPanel.add(NavigationPanel.getInstance(), gc);
    }

    private int getSelectedCardCost() {
        if (selectedCard == null) return 0;
        return Administer.getInstance().getCardCost(selectedCard);
    }

    private void revalidateController() {
        cardLabel.setText(selectedCard);
        costLabel.setText("card cost: " + getSelectedCardCost());
        int playerCoins = Administer.getInstance().getPlayerCoins();
        coins.setText(Integer.toString(playerCoins));
    }

    private void setControllerActionListeners() {

        /////////////////////////////////////////////////////
        //////////////////   SELL BUTTON  ///////////////////
        /////////////////////////////////////////////////////

        sellBtn.addActionListener(e -> {
            Logger.log(LogTypes.CLICK_BUTTON, "button: SELL selected .");

            if (selectedCard != null) {
                //todo add image to confirm msg
                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to sell " + selectedCard + " card?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        Administer.getInstance().sellCard(selectedCard);
                        JOptionPane.showMessageDialog(null, "You sold the card successfully");
                        Logger.log(LogTypes.SHOP, "card: " + selectedCard + " sold");
                        selectedCard = null;
                        revalidateController();

                    } catch (CardControllerException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        Logger.logError(LogTypes.SHOP, ex);
                    }

                } else Logger.log(LogTypes.CLICK_BUTTON, " selling canceled");

            } else {
                JOptionPane.showMessageDialog(null, "No card is selected");
                Logger.logError(LogTypes.SHOP, new GuiException("No card is selected"));
            }
        });

        /////////////////////////////////////////////////////
        /////////////////  BUY BUTTON  //////////////////////
        /////////////////////////////////////////////////////

        buyBtn.addActionListener(e -> {
            Logger.log(LogTypes.CLICK_BUTTON, "button: BUY selected .");

            if (selectedCard != null) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to buy " + selectedCard + " card?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        Administer.getInstance().buyCard(selectedCard);
                        JOptionPane.showMessageDialog(null, "You bought the card successfully");
                        Logger.log(LogTypes.SHOP, "card: " + selectedCard + " purchased");
                        selectedCard = null;
                        revalidateController();

                    } catch (CardControllerException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        Logger.logError(LogTypes.SHOP, ex);

                    }
                } else Logger.log(LogTypes.CLICK_BUTTON, " Buying canceled");

            } else {
                JOptionPane.showMessageDialog(null, "No card is selected");
                Logger.logError(LogTypes.SHOP, new GuiException("No card is selected"));
            }
        });

    }


}

