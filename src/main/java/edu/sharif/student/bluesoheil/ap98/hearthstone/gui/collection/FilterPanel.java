package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.collection;

import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.DeckHandlerListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.FilterListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.NavigationPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.SidePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.GuiConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilterPanel extends SidePanel {
    private FilterListener filterListener;
    private DeckHandlerListener deckHandler;

    private JButton filterBtn;
    private JLabel titleLabel, manaLabel, nameLabel, classLabel, cardsLabel;
    private TextField searchFiled;
    private JComboBox heroBox, manaBox;
    private JCheckBox ownedBox, notOwnedBox;

    private JButton newDeckBtn, cancelBtn, deleteDeckBtn, removeCardBtn, addCardBtn, currentDeckBtn, renameBtn, changeHeroBtn;
    private JLabel  optionLabel;
    private JComboBox<HeroTypes> heroes;

    public FilterPanel(int width, int height) {
        super(width, height);
        createComponents();
        addComponents();
    }

    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
    }

    public void setDeckHandlerListener(DeckHandlerListener deckHandler) {
        this.deckHandler = deckHandler;
    }

    private void createComponents() {
        //todo create two separate panel named filter and handler both extend Jpanel
        createFilter();
        createDeckHandler();
    }

    private void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.insets = new Insets(5, 5, 5, 5);
        ///1st row //
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.weighty = 1;
        add(titleLabel, gc);
        /// next row///
        gc.gridy++;
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(nameLabel, gc);
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(searchFiled, gc);
        ///next row///
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(classLabel, gc);
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(heroBox, gc);
        ///next row////
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(manaLabel, gc);
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(manaBox, gc);
        ///next row///
        gc.gridy++;
        gc.gridx = 0;
        gc.weighty = 0.5;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(cardsLabel, gc);
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(ownedBox, gc);
        ///next row///
        gc.gridy++;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(notOwnedBox, gc);
        ///next row///
        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weighty = 2;
        gc.anchor = GridBagConstraints.NORTH;
        add(filterBtn, gc);
        ///next row///
        initDeckHandler(gc);
        ///next row///
        gc.gridy++;
        gc.gridwidth = 2;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.SOUTH;
        add(NavigationPanel.getInstance(), gc);
    }

    private void createFilter() {
        createFilterLabels();
        createComboBoxes();
        createCheckBoxes();
        createFilterButton();
    }

    private void createFilterLabels() {
        titleLabel = new JLabel(" Filters ");
        titleLabel.setFont(new Font("Serif", Font.ITALIC, 30));
        searchFiled = new TextField(15);
        nameLabel = new JLabel("Name: ");
        manaLabel = new JLabel("Mana: ");
        classLabel = new JLabel("Class: ");
        cardsLabel = new JLabel("Cards: ");
    }

    private void createComboBoxes() {
        heroBox = new JComboBox();
        DefaultComboBoxModel heroModel = new DefaultComboBoxModel();
        heroModel.addElement("All");
        heroModel.addElement("Neutral");
        heroModel.addElement("Mage");
        heroModel.addElement("Rogue");
        heroModel.addElement("Warlock");
        heroModel.addElement("Hunter");
        heroModel.addElement("Priest");
        heroBox.setModel(heroModel);
        heroBox.setSelectedIndex(0);

        manaBox = new JComboBox();
        DefaultComboBoxModel manaModel = new DefaultComboBoxModel();
        manaModel.addElement("Any");
        for (int i = 0; i < 11; i++) manaModel.addElement(Integer.toString(i));
        manaBox.setModel(manaModel);
        manaBox.setSelectedIndex(0);
    }

    private void createCheckBoxes() {
        ownedBox = new JCheckBox("Owned ");
        notOwnedBox = new JCheckBox("Not Owned ");
        ownedBox.setSelected(true);
        notOwnedBox.setSelected(true);
        ownedBox.setOpaque(false);
        notOwnedBox.setOpaque(false);
    }

    private void createFilterButton() {
        filterBtn = new JButton("");
        filterBtn.setIcon(new ImageIcon(GuiConstants.getInstance().getFilterIconPath()));
        filterBtn.setContentAreaFilled(false);
        filterBtn.setBorderPainted(false);

        filterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log(LogTypes.CLICK_BUTTON, "button: FILTER selected.");

                String name = searchFiled.getText();
                boolean owned = ownedBox.isSelected();
                boolean notOwned = notOwnedBox.isSelected();
                String hero = (String) heroBox.getSelectedItem();
                int manaCost;
                if (((String) manaBox.getSelectedItem()).equals("Any")) {
                    manaCost = -1;
                } else manaCost = Integer.parseInt((String) manaBox.getSelectedItem());

                FilterEvent filterEvent = new FilterEvent(this, name, owned, notOwned, manaCost, hero);
                if (filterListener != null) filterListener.filter(filterEvent);
            }
        });
    }

    ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////
    private void createDeckHandler() {
        createHandlerLabels();
        setBackgrounds();
        defineButtons();
    }

    public void setDeckHandlerEditable(boolean editable) {
        renameBtn.setEnabled(editable);
        deleteDeckBtn.setEnabled(editable);
        currentDeckBtn.setEnabled(editable);
        changeHeroBtn.setEnabled(editable);
        heroes.setEnabled(editable);
        cancelBtn.setEnabled(editable);
        //if we don't use false for two lines below , when we select a deck, they both get enabled without selecting any cards
        removeCardBtn.setEnabled(false);
        addCardBtn.setEnabled(false);
    }
    public void isSelectedCardFromDeck(boolean isCardAvailableInDeck){
        removeCardBtn.setEnabled(isCardAvailableInDeck);
        addCardBtn.setEnabled(!isCardAvailableInDeck);
    }

    private void initDeckHandler(GridBagConstraints g) {
        g.gridy++;
        g.gridx = 0;
        g.weighty = 1;
        g.gridwidth = 2;
        add(optionLabel, g);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(4, 5, 4, 5);
        g.gridy++;
        g.gridwidth = 1;
        add(renameBtn, g);
        g.gridx = 1;
        add(deleteDeckBtn, g);
        g.gridy++;
        g.gridx = 0;
        add(addCardBtn, g);
        g.gridx = 1;
        add(removeCardBtn, g);
        g.gridy++;
        g.gridx = 0;
        add(changeHeroBtn, g);
        g.gridx = 1;
        add(heroes, g);
        g.gridy++;
        g.gridx = 0;
        g.gridwidth = 2;
        add(currentDeckBtn, g);
        g.gridy++;
        add(cancelBtn, g);
        g.gridy++;
        g.gridx = 0;
        add(newDeckBtn, g);
    }

    private void createHandlerLabels() {
        optionLabel = new JLabel("Deck Options ");
        renameBtn = new JButton("Rename Deck");
        cancelBtn = new JButton("Cancel");
        removeCardBtn = new JButton("Remove Card");
        addCardBtn = new JButton("Add card");
        deleteDeckBtn = new JButton("Delete Deck");
        currentDeckBtn = new JButton("set Current Deck");
        newDeckBtn = new JButton("New Deck");
        changeHeroBtn = new JButton("Change Hero");
        heroes = new JComboBox<HeroTypes>();
        DefaultComboBoxModel heroTypes = new DefaultComboBoxModel();
        heroTypes.addElement(HeroTypes.MAGE);
        heroTypes.addElement(HeroTypes.WARLOCK);
        heroTypes.addElement(HeroTypes.ROGUE);
        heroTypes.addElement(HeroTypes.HUNTER);
        heroTypes.addElement(HeroTypes.PRIEST);
        heroes.setModel(heroTypes);
    }

    private void setBackgrounds() {
        optionLabel.setFont(new Font("Serif", Font.ITALIC, 30));
        Color color = new Color(192, 135, 107);
        Font font = new Font("Arial", Font.ITALIC, 20);
        renameBtn.setBackground(color);
        cancelBtn.setBackground(color);
        deleteDeckBtn.setBackground(color);
        removeCardBtn.setBackground(color);
        addCardBtn.setBackground(color);
        changeHeroBtn.setBackground(color);
        currentDeckBtn.setBackground(color);
        newDeckBtn.setBackground(color);
        newDeckBtn.setFont(font);
        newDeckBtn.setForeground(Color.white);
    }

    private void defineButtons() {

        newDeckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log(LogTypes.CLICK_BUTTON, "button : NEW DECK selected.");
                if (deckHandler != null){
                    String name = JOptionPane.showInputDialog("Enter an unselected name for your new deck : ");
                    if (name != null) {
                        String heroName = JOptionPane.showInputDialog("Enter a hero for your new deck : ");
                        if (heroName != null){
                            deckHandler.createNewDeck(name , heroName);
                        }else Logger.log(LogTypes.CLICK_BUTTON , "deck creating canceled");
                    }else  Logger.log(LogTypes.CLICK_BUTTON , "deck creating canceled");
                }
            }
        });
        /////////////////////////////////////////
        /////////////////////////////////////////
        renameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log(LogTypes.CLICK_BUTTON, "button: RENAME selected.");
                if (deckHandler != null){
                    String newName = JOptionPane.showInputDialog("Enter newName: ");
                    if (newName != null) {
                        Logger.log(LogTypes.CLICK_BUTTON , "rename confirmed");
                        deckHandler.renameDeck(newName);
                    }else {
                        Logger.log(LogTypes.CLICK_BUTTON, "rename declined");
                    }
                }
            }
        });
        ///////////////////////////////////////
        ///////////////////////////////////////
        deleteDeckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log(LogTypes.CLICK_BUTTON, "button : DELETE DECK selected.");
                if (deckHandler != null){
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete this deck ?",
                            "Confirm Delete Deck ", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Logger.log(LogTypes.CLICK_BUTTON , "delete confirmed");
                        deckHandler.deleteDeck();
                    }else{
                        Logger.log(LogTypes.CLICK_BUTTON , "delete declined");
                    }
                }
            }
        });
        ///////////////////////////////////////
        ///////////////////////////////////////
        addCardBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log(LogTypes.CLICK_BUTTON, "button : ADD CARD selected.");
                if (deckHandler != null) deckHandler.addCard();
            }
        });
        ///////////////////////////////////////
        ///////////////////////////////////////
        removeCardBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log(LogTypes.CLICK_BUTTON, "button : REMOVE CARD selected.");
                if (deckHandler != null) deckHandler.removeCard();
            }
        });
        ///////////////////////////////////////
        ///////////////////////////////////////
        currentDeckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log(LogTypes.CLICK_BUTTON, "button: SET CURRENT DECK selected.");
                if (deckHandler != null) deckHandler.setCurrentDeck();
            }
        });
        ///////////////////////////////////////
        ///////////////////////////////////////
        changeHeroBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log(LogTypes.CLICK_BUTTON, "button: CHANGE HERO selected.");
                if (deckHandler != null) deckHandler.changeHero((HeroTypes) heroes.getSelectedItem());
            }
        });
        ///////////////////////////////////////
        ///////////////////////////////////////
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logger.log(LogTypes.CLICK_BUTTON, "button : CANCEL selected.");
                if (deckHandler != null) deckHandler.cancel();
                setDeckHandlerEditable(false);
            }
        });

    }


}


