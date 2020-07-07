package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.*;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.PlayTimer;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends GamePanel {

    private PlayConfig properties;
    private PlayHandler playHandler;
    private PauseMenu pauseMenu;
    private PlayerPanel playerPanel, opponentPanel;
    private EventBox eventBox;
    private BoardPanel board;
    private PlayTimer timer;

    private TimerListener timerListener;
    private CardClickListener currentPlayerCardListener, currentOpponentCardListener;
    private ActualCard playerSelectedCard, opponentSelectedCard;
    private ActualCard selectedCard; // it will be deleted probably
    private ClickListener handClickListener;
    private String selectedCardInHand;
    private PlayActionListener playActionListener;
    private HeroActionListener heroActionListener;

    private Players currentTurn;

    private enum Players {
        ME,
        OPPONENT;
        private PlayerPanel playerPanel;
    }

    //**********************************//
    //****methods to setup playPanel****//

    @Override
    protected void loadConfig() {
        properties = PlayConfig.getInstance();
    }

    @Override
    protected void createFields() {
        eventBox = new EventBox();
        playHandler = PlayHandler.getInstance();
        setupListeners();
        timer =PlayTimer.setNewTimer(5);
        timer.setTimeListener(timerListener);
        setupPlayerPanel();
        setupOpponentPanel();
        setupPauseMenu();
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

    private void setupListeners() {
        handClickListener = objName -> selectedCardInHand = objName;
        //todo  2 listeners for board will be made // maybe you need a new Interface  named boardListener
//        currentPlayerCardListener = new CardClickListener() {
//            @Override
//            public void selectCard(ActualCard selectedCard) {
//
//            }
//        };
//        currentOpponentCardListener = new CardClickListener() {
//            @Override
//            public void selectCard(ActualCard selectedCard) {
//
//            }
//        };
        heroActionListener = new HeroActionListener() {
            //todo complete it
            @Override
            public void playHeroPower() {
                System.out.println("played hero power");
            }

            @Override
            public void playWeapon(WeaponActualCard playedWeapon) {
                System.out.println(playedWeapon.getCardName() + " played");
            }

        };

        timerListener = new TimerListener() {
            @Override
            public void tick() {
                System.out.println(timer.getRemainingTime());
            }

            @Override
            public void ring() {
                System.out.println("riiiiiing");
                changeTurn();
            }
        };

        playActionListener = new PlayActionListener() {
            @Override
            public void endTurn() {
                changeTurn();
            }

            @Override
            public void play() {
                if (selectedCardInHand != null) {
                    doHandCardAction();
                    selectedCardInHand = null;
                } else if (selectedCard != null) {
                    playHandler.playCard(selectedCard);
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
        };
    }

    private void setupBoardPanel() {
        board = new BoardPanel();
        //todo complete it
        board.setPlayerCardClickListeners(selectedCard -> playerSelectedCard = selectedCard);
        board.setOpponentCardClickListeners(selectedCard -> opponentSelectedCard = selectedCard);
    }

    private void setupPauseMenu() {
        pauseMenu = PauseMenu.getInstance();
        pauseMenu.setVisible(true);
    }

    private void setupPlayerPanel() {
        playerPanel = playHandler.getPlayerPanel(true);
        Players.ME.playerPanel = playerPanel;
        playerPanel.updateHand(playHandler.getHand(true), playHandler.getHeroStates(true));
        setTurn(Players.ME);
        timer.start();
    }

    private void setupOpponentPanel() {
        opponentPanel = playHandler.getPlayerPanel(false);
        Players.OPPONENT.playerPanel = opponentPanel;
        opponentPanel.updateHand(playHandler.getHand(false), playHandler.getHeroStates(false));
        opponentPanel.endTurn();
    }

    //**********************************//
    //****methods for turn changing*****//

    private void changeTurn() {
        playHandler.changeTurns();
        timer.reset();
        currentTurn.playerPanel.endTurn();
        if (currentTurn == Players.ME) {
            setTurn(Players.OPPONENT);
        } else {
            setTurn(Players.ME);
        }
    }

    private void setTurn(Players player) {
        currentTurn = player;
        currentTurn.playerPanel.startTurn(handClickListener, playActionListener, heroActionListener);
        currentTurn.playerPanel.updateHand(playHandler.getHand(), playHandler.getHeroStates());
//        timer.start();
    }

    //**********************************//
    //*******methods for playing********//

    private void doHandCardAction() {
        try {
            switch (playHandler.getCardType(selectedCardInHand)) {
                case BEAST:
//                summonBeast(playerSelectedCardInHand);
//                break;
                case MINION:
                    summonMinion(selectedCardInHand);
                    break;
                case WEAPON:
                    summonWeapon(selectedCardInHand);
                    break;
                case QUESTANDREWARD:
                    playQuestAndReward(selectedCardInHand);
                    break;
                case SPELL:
                    playSpell(selectedCardInHand);
                    break;
            }
            currentTurn.playerPanel.updateHand(playHandler.getHand(), playHandler.getHeroStates());
        } catch (PlayException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error in card selection", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void summonMinion(String cardName) throws PlayException {
        //todo have a minionCard and show possible arranges of cards in board before calling summonAndGetCard method in playHandler\
        //todo is there any need to have a different method for beasts?
        MinionActualCard cardToSummon;
        cardToSummon = playHandler.summonAndGetMinion(cardName);
        if (currentTurn == Players.ME) board.addCardForPlayer(cardToSummon);
        if (currentTurn == Players.OPPONENT) board.addCardForOpponent(cardToSummon);
    }

    private void summonWeapon(String cardName) throws PlayException {
        WeaponActualCard weaponToSummon;
        weaponToSummon = playHandler.summonAndGetWeapon(cardName);
        currentTurn.playerPanel.setWeaponCard(weaponToSummon);
    }

    private void playSpell(String spellName) throws PlayException {
        playHandler.playSpell(spellName);
    }

    private void playQuestAndReward(String QRName) throws PlayException {
        playHandler.playQuestAndReward(QRName);
    }

}
