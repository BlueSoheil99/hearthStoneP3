package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.CardClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.PlayActionListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

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
        add(new JScrollPane(eventBox));// todo see if there's a way to delete up scrol
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
        playerPanel.setClickListenerForCards(objName -> {
            playerSelectedCardInHand = objName;
            System.out.println(objName + " selected");
        });
        playerPanel.setClickListenerForActions(new PlayActionListener() {
            @Override
            public void endTurn() {
                playerPanel.endTurn();
                opponentPanel.startTurn();
            }

            @Override
            public void play() {
                if (playerSelectedCardInHand != null) {
                    Card.CardType type = playHandler.getCardType(playerSelectedCardInHand);
                    switch (type) {
                        //todo for now we display beast and minion like each other . create an actual card class for beast later
                        case WEAPON:
                            summonWeapon(playerSelectedCardInHand);
                            break;
                        case BEAST:
                            summonBeast(playerSelectedCardInHand);
                            break;
                        case MINION:
                            summonMinion(playerSelectedCardInHand);
                            break;
                        case QUESTANDREWARD:
                            playHandler.playQuestAndReward(playerSelectedCardInHand, PlayPanel.this);
                            break;
                        case SPELL:
                            playHandler.playSpell(playerSelectedCardInHand, PlayPanel.this);
                            playerPanel.setWeaponCard(null);//this line is for test , must be deleted!
                            break;
                    }
                    playerPanel.updateHand(playHandler.getHand(), playHandler.getHeroStates());
                } else if (playerSelectedCard != null) {
                    playHandler.playCard(playerSelectedCard);
                } else {
                    JOptionPane.showMessageDialog(null, "You suck:/");
                }
                eventBox.update();
            }

            @Override
            public void summonCard() {

            }

            @Override
            public void goRight() {

            }

            @Override
            public void goLeft() {

            }

        });
    }

    void summonMinion(String cardName) {
        MinionActualCard cardToSummon = playHandler.summonAndGetMinion(cardName);
        board.addCardForPlayer(cardToSummon);
        System.out.println("added");
    }

    void summonWeapon(String cardName) {
        WeaponActualCard weaponToSummon = playHandler.summonAndGetWeapon(cardName);
        playerPanel.setWeaponCard(weaponToSummon);
        System.out.println(cardName + " added");
    }
    void summonBeast(String cardName) {
        MinionActualCard cardToSummon = playHandler.summonAndGetMinion(cardName);
        board.addCardForPlayer(cardToSummon);
        System.out.println(cardName + " added");
    }

}
