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
    private JLabel deckName, winRatio, wins, gamesPlayed, manaAverage, hero, mostUsedCard;
    private JLabel selectedDeckName, selectedWinRatio, selectedWins, selectedGamesPlayed, selectedManaAverage, selectedHero, selectedMostUsedCard;


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
        deckPanel.setClickListener(objName -> {
            selectedDeck = objName;
            revalidateDeckStates();
            Logger.log(LogTypes.STATUS, selectedDeck + " states showed .");
        });
        createStatesPanel();
    }

    @Override
    protected void init() {
        setLayout(new BorderLayout());
        add(statesPanel, BorderLayout.CENTER);
        add(new JScrollPane(deckPanel), BorderLayout.EAST);

    }

    private void createStatesPanel() {
        statesPanel = new SidePanel();
        createStatesComponents();
        addStatesComponents();
    }

    private void createStatesComponents() {
        Font font1 = statesPanel.getFont1();
        Font font2 = statesPanel.getFont2();

        deckName = createStatusLabel("Deck Name: ", font1);
        winRatio = createStatusLabel("Win Ratio: ", font1);
        wins = createStatusLabel("Games Won: ", font1);
        gamesPlayed = createStatusLabel("Games Played: ", font1);
        manaAverage = createStatusLabel("Average of Manas: ", font1);
        hero = createStatusLabel("Deck hero: ", font1);
        mostUsedCard = createStatusLabel("Most Used Card: ", font1);

        selectedDeckName = createStatusLabel("", font2);
        selectedWinRatio = createStatusLabel("", font2);
        selectedWins = createStatusLabel("", font2);
        selectedGamesPlayed = createStatusLabel("", font2);
        selectedManaAverage = createStatusLabel("", font2);
        selectedHero = createStatusLabel("", font2);
        selectedMostUsedCard = createStatusLabel("", font2);

    }

    private JLabel createStatusLabel(String name, Font font) {
        JLabel L = new JLabel(name);
        L.setFont(font);
        return L;
    }

    private void addStatesComponents() {
        statesPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(10, 10, 10, 10);

        gc.weighty = 1;
        gc.weightx = 1;
        gc.gridx = 0;
        gc.gridy = GridBagConstraints.RELATIVE;
        statesPanel.add(deckName, gc);
        statesPanel.add(hero, gc);
        statesPanel.add(winRatio, gc);
        statesPanel.add(wins, gc);
        statesPanel.add(gamesPlayed, gc);
        statesPanel.add(manaAverage, gc);
        statesPanel.add(mostUsedCard, gc);
        gc.weighty = 4;
        gc.anchor = GridBagConstraints.SOUTHWEST;
        statesPanel.add(NavigationPanel.getInstance(), gc);
        ///////////// showing deck states ///////////////////
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        statesPanel.add(selectedDeckName, gc);
        statesPanel.add(selectedHero, gc);
        statesPanel.add(selectedWinRatio, gc);
        statesPanel.add(selectedWins, gc);
        statesPanel.add(selectedGamesPlayed, gc);
        statesPanel.add(selectedManaAverage, gc);
        statesPanel.add(selectedMostUsedCard, gc);
    }

    private void revalidateDeckStates() {
        if (selectedDeck != null) {
            deckStates = Administer.getInstance().getDeckState(selectedDeck);
            selectedDeckName.setText(deckStates[0]);
            selectedHero.setText(deckStates[1]);
            selectedWinRatio.setText(deckStates[2]);
            selectedWins.setText(deckStates[3]);
            selectedGamesPlayed.setText(deckStates[4]);
            selectedManaAverage.setText(deckStates[5]);
            selectedMostUsedCard.setText(deckStates[6]);
        }
    }
}
