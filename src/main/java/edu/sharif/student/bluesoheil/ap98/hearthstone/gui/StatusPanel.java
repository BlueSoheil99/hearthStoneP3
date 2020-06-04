package edu.sharif.student.bluesoheil.ap98.hearthstone.gui;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.Administer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.ClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.DeckPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.NavigationPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.SidePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import java.awt.*;


public class StatusPanel extends GamePanel {

    private DeckPanel deckPanel;
    private SidePanel statesPanel;
    private String selectedDeck;
    private String[] deckStates;
    private JLabel deckName, winRatio, gamesPlayed, manaAverage, hero, mostUsedCard;
    private JLabel selectedDeckName, selectedWinRatio, selectedGamesPlayed, selectedManaAverage, selectedHero, selectedMostUsedCard;


    public StatusPanel() {
        super();
    }

    @Override
    protected void loadConfig() {

    }

    @Override
    protected void createFields() {
        deckPanel = new DeckPanel(this.getWidth() / 5, this.getHeight());
        deckPanel.setDecks(Administer.getInstance().getPlayerDecks(10));
        deckPanel.setClickListener(new ClickListener() {
            @Override
            public void select(String objName) {
                selectedDeck = objName;
                revalidateDeckStates();
                Logger.log(LogTypes.STATUS, selectedDeck +" states showed .");
            }
        });
        createStatesPanel();
    }

    @Override
    protected void init() {
        setLayout(new BorderLayout());
        add(statesPanel, BorderLayout.CENTER);
        add(new JScrollPane(deckPanel) , BorderLayout.EAST);

    }

    private void createStatesPanel() {
        statesPanel = new SidePanel();
        createStatesComponents();
        addStatesComponents();
    }

    private void createStatesComponents() {
        Font font1 = statesPanel.getFont1();
        Font font2 = statesPanel.getFont2();
        //todo create statusLabel class with constructor StatusLabel(String msg , Font font)
        deckName = new JLabel("Deck Name: ");
        winRatio = new JLabel("Win Ratio: ");
        gamesPlayed = new JLabel("Games Played: ");
        manaAverage = new JLabel("Average of Manas: ");
        hero = new JLabel("Deck Hero: ");
        mostUsedCard = new JLabel("Most Used Card: ");
        deckName.setFont(font1);
        winRatio.setFont(font1);
        gamesPlayed.setFont(font1);
        manaAverage.setFont(font1);
        hero.setFont(font1);
        mostUsedCard.setFont(font1);

        selectedDeckName = new JLabel("");
        selectedWinRatio = new JLabel("");
        selectedGamesPlayed = new JLabel("");
        selectedManaAverage = new JLabel("");
        selectedHero = new JLabel("");
        selectedMostUsedCard = new JLabel("");
        selectedDeckName.setFont(font2);
        selectedWinRatio.setFont(font2);
        selectedGamesPlayed.setFont(font2);
        selectedManaAverage.setFont(font2);
        selectedHero.setFont(font2);
        selectedMostUsedCard.setFont(font2);
    }

    private void addStatesComponents() {
        statesPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(10,10,10,10);

        gc.weighty = 1;
        gc.weightx = 1;
        gc.gridx=0;
        gc.gridy = GridBagConstraints.RELATIVE;
        statesPanel.add(deckName, gc);
        statesPanel.add(hero, gc);
        statesPanel.add(winRatio, gc);
        statesPanel.add(gamesPlayed, gc);
        statesPanel.add(manaAverage, gc);
        statesPanel.add(mostUsedCard, gc);
        gc.weighty = 4;
        gc.anchor = GridBagConstraints.SOUTHWEST;
        statesPanel.add(NavigationPanel.getInstance() , gc);
        ///////////// showing deck states ///////////////////
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx=1;
        statesPanel.add(selectedDeckName, gc);
        statesPanel.add(selectedHero, gc);
        statesPanel.add(selectedWinRatio, gc);
        statesPanel.add(selectedGamesPlayed, gc);
        statesPanel.add(selectedManaAverage, gc);
        statesPanel.add(selectedMostUsedCard, gc);
    }

    private void revalidateDeckStates() {
        if (selectedDeck != null){
            deckStates = Administer.getInstance().getDeckState(selectedDeck);
            selectedDeckName.setText(deckStates[0]);
            selectedHero.setText(deckStates[1]);
            selectedWinRatio.setText(deckStates[2]);
            selectedGamesPlayed.setText(deckStates[3]);
            selectedManaAverage.setText(deckStates[4]);
            selectedMostUsedCard.setText(deckStates[5]);
        }
    }
}
