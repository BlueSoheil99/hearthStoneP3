package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.PlayActionListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends GamePanel {

    private PlayConfig properties;
    private PlayHandler playHandler;
    private PauseMenu pauseMenu;
    private PlayerPanel playerPanel, opponentPanel;
    private EventBox eventBox;
    private BoardPanel board;
    private ActualCard playerSelectedCard, opponentSelectedCard;
    private String playerSelectedCardInHand, opponentSelectedCardInHand;
    private boolean myTurn = true;
//   todo private Players currentTurn;
//    private enum Players {
//        ME,
//        OPPONENT;
//        private PlayerPanel playerPanel;
//        void setPlayerPanel(PlayerPanel playerPanel) {
//            this.playerPanel = playerPanel;
//        }
//    }

    @Override
    protected void loadConfig() {
        properties = PlayConfig.getInstance();
    }

    @Override
    protected void createFields() {
        eventBox = new EventBox();
        playHandler = PlayHandler.getInstance();
        setupPlayerPanel();
        opponentPanel = playHandler.getOpponentPanel();
        opponentPanel.endTurn();
        setupOpponentPanel();
        playHandler = PlayHandler.getInstance();
        setupPausePanel();
        setupBoardPanel();
    }

    @Override
    protected void init() {
        opponentPanel.setPreferredSize(new Dimension(getWidth(), getHeight() / 7 * 2));
        board.setPreferredSize(new Dimension(getWidth(), getHeight() / 7 * 3));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(pauseMenu);
        add(opponentPanel);
        add(board);
        add(playerPanel);
        JScrollPane scrollPane = new JScrollPane(eventBox);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(getWidth(), PlayConfig.getInstance().getEventHeight()));
        add(scrollPane);
    }

    private void setupBoardPanel() {
        board = new BoardPanel();
        //todo complete it
        board.setPlayerCardClickListeners(selectedCard -> playerSelectedCard = selectedCard);
        //todo complete it
        board.setOpponentCardClickListeners(selectedCard -> opponentSelectedCard = selectedCard);
    }

    private void setupPausePanel() {
        pauseMenu = PauseMenu.getInstance();
        pauseMenu.setVisible(true);
    }

    private void setupPlayerPanel() {
        playerPanel = playHandler.getPlayerPanel();
        playerPanel.updateHand(playHandler.getHand(), playHandler.getHeroStates());
        ////setListeners////
        playerPanel.setClickListenerForCards(objName -> playerSelectedCardInHand = objName);
        playerPanel.setClickListenerForActions(new PlayActionListener() {
            @Override
            public void endTurn() {
                changeTurn();
            }

            @Override
            public void play() {
                if (playerSelectedCardInHand != null) {
                    doHandCardAction();
                    playerSelectedCardInHand = null;
                } else if (playerSelectedCard != null) {
                    playHandler.playCard(playerSelectedCard);
                } else {
                    JOptionPane.showMessageDialog(null, "select a card :/");
                }
                eventBox.update();
            }

            @Override
            public void goRight() {
            }

            @Override
            public void goLeft() {
            }
        });
    }

    private void setupOpponentPanel() {
        opponentPanel.setClickListenerForActions(new PlayActionListener() {
            @Override
            public void endTurn() {
                changeTurn();
            }

            @Override
            public void play() {

            }

            @Override
            public void goRight() {

            }

            @Override
            public void goLeft() {

            }
        });
    }

    private void changeTurn() {
//        todo if (currentTurn.equals(Players.ME)) {
//            currentTurn=Players.OPPONENT;
//            playHandler.changeTurns();
//        }
        playHandler.changeTurns();
        if (myTurn) {
            myTurn = false;
            playerPanel.endTurn();
            opponentPanel.startTurn();
        } else {
            myTurn = true;
            playerPanel.startTurn();
            playHandler.updateHand();
            playerPanel.updateHand(playHandler.getHand(), playHandler.getHeroStates());
            opponentPanel.endTurn();
        }
    }

    private void doHandCardAction() {
        try {
            switch (playHandler.getCardType(playerSelectedCardInHand)) {
                case BEAST:
//                summonBeast(playerSelectedCardInHand);
//                break;
                case MINION:
                    summonMinion(playerSelectedCardInHand);
                    break;
                case WEAPON:
                    summonWeapon(playerSelectedCardInHand);
                    break;
                case QUESTANDREWARD:
                    playQuestAndReward(playerSelectedCardInHand);
                    break;
                case SPELL:
                    playSpell(playerSelectedCardInHand);
                    break;
            }
            playerPanel.updateHand(playHandler.getHand(), playHandler.getHeroStates());
        } catch (PlayException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error in card selection", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void summonMinion(String cardName) throws PlayException {
        //todo have a minionCard and show possible arranges of cards in board before calling summonAndGetCard method in playHandler\
        //todo is there any need to have a different method for beasts?
        MinionActualCard cardToSummon;
        cardToSummon = playHandler.summonAndGetMinion(cardName);
        board.addCardForPlayer(cardToSummon);
    }

    private void summonWeapon(String cardName) throws PlayException {
        WeaponActualCard weaponToSummon;
        weaponToSummon = playHandler.summonAndGetWeapon(cardName);
        playerPanel.setWeaponCard(weaponToSummon);
    }

    private void playSpell(String spellName) throws PlayException {
        playHandler.playSpell(spellName);
    }

    private void playQuestAndReward(String QRName) throws PlayException {
        playHandler.playQuestAndReward(QRName);
    }

}
