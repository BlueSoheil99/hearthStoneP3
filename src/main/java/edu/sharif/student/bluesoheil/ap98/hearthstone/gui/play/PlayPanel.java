package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.CardClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.PlayActionListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayPanel extends GamePanel {

    private PlayConfig properties;
    private PlayHandler playHandler;
    private PauseMenu pauseMenu;
    private JButton pauseBtn;
    private PlayerPanel playerPanel, opponentPanel;
    private EventBox eventBox;
    private BoardPanel board;
    private ActualCard playerSelectedCard, opponentSelectedCard;
    private String playerSelectedCardInHand, opponentSelectedCardInHand;
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
        playHandler = PlayHandler.getInstance();
        setupPlayerPanel();
        opponentPanel = playHandler.getOpponentPanel();
        opponentPanel.endTurn();
        playHandler = PlayHandler.getInstance();
        setupPausePanel();
        setupBoardPanel();
    }

    @Override
    protected void init() {
        opponentPanel.setPreferredSize(new Dimension(getWidth(), getHeight() / 7 * 2));
        playerPanel.setPreferredSize(new Dimension(getWidth(), getHeight() / 7 * 2));
        board.setPreferredSize(new Dimension(getWidth(), getHeight() / 7 * 3));
        eventBox = new EventBox();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(opponentPanel);
        add(board);
        add(playerPanel);
        add(new JScrollPane(eventBox));// todo see if there's a way to delete up scroll
    }

    private void setupBoardPanel() {
        board = new BoardPanel();
        board.setPlayerCardClickListeners(new CardClickListener() {
            @Override
            public void selectCard(ActualCard selectedCard) {
                playerSelectedCard = selectedCard;
            }
            //todo complete it
        });
        board.setOpponentCardClickListeners(new CardClickListener() {
            @Override
            public void selectCard(ActualCard selectedCard) {
                opponentSelectedCard = selectedCard;
            }
            //todo complete it
        });
    }

    private void setupPausePanel() {
        //todo you can also use JMenu
        pauseMenu = PauseMenu.getInstance();
        setFocusable(true);
//        pauseBtn = new JButton();
//        pauseBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("pausssssse");
//            }
//        });
//        pauseBtn.setMnemonic(KeyEvent.VK_ESCAPE);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("pause");
            }
        });
    }

    private void setupPlayerPanel() {
        playerPanel = playHandler.getPlayerPanel();
        playerPanel.updateHand(playHandler.getHand(), playHandler.getHeroStates());
//        playerPanel.setSelectMode(false);
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
                    JOptionPane.showMessageDialog(null, "select a card you DumbAss :/");
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

    private void changeTurn() {
//        todo if (currentTurn.equals(Players.ME)) {
//            currentTurn=Players.OPPONENT;
//            playHandler.changeTurns();
//        }
        playerPanel.endTurn();
        opponentPanel.startTurn();
    }

    private void doHandCardAction() {
        switch (playHandler.getCardType(playerSelectedCardInHand)) {
            case BEAST:
//                summonBeast(playerSelectedCardInHand);
//                break;
            case WEAPON:
                summonWeapon(playerSelectedCardInHand);
                break;
            case MINION:
                summonMinion(playerSelectedCardInHand);
                break;
            case QUESTANDREWARD:
                playQuestAndReward(playerSelectedCardInHand);
                break;
            case SPELL:
                playSpell(playerSelectedCardInHand);
                break;
        }
        playerPanel.updateHand(playHandler.getHand(), playHandler.getHeroStates());
    }

    private void summonMinion(String cardName) {
        //todo have a minionCard and show possible arranges of cards in board before calling summonAndGetCard method in playHandler\
        //todo is there any need to have a different method for beasts?
        MinionActualCard cardToSummon = null;
        try {
            cardToSummon = playHandler.summonAndGetMinion(cardName);
            board.addCardForPlayer(cardToSummon);
        } catch (PlayException e) {
            JOptionPane.showMessageDialog(null , e.getMessage(),"Error in card selection" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void summonWeapon(String cardName) {
        WeaponActualCard weaponToSummon = null;
        try {
            weaponToSummon = playHandler.summonAndGetWeapon(cardName);
            playerPanel.setWeaponCard(weaponToSummon);
        } catch (PlayException e) {
            JOptionPane.showMessageDialog(null , e.getMessage(),"Error in card selection" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void playSpell(String spellName) {
        try {
            playHandler.playSpell(spellName);
        } catch (PlayException e) {
            JOptionPane.showMessageDialog(null , e.getMessage(),"Error in card selection" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private void playQuestAndReward(String QRName) {
        try {
            playHandler.playQuestAndReward(QRName);
        } catch (PlayException e) {
            JOptionPane.showMessageDialog(null , e.getMessage(),"Error in card selection" , JOptionPane.ERROR_MESSAGE);
        }
    }

}
