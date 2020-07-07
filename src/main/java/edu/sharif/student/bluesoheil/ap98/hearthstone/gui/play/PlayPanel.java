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
    private EventBox eventBox;
    private PlayTimer timer;
    //todo delete panel and board properties later. they're not necessary
    private PlayerPanel playerPanel, opponentPanel;
    private PlayerBoard playerBoard, opponentBoard;

    private TimerListener timerListener;
    private PlayActionListener playActionListener;
    private HeroActionListener heroActionListener;
    private CardClickListener currentPlayerCardListener, currentOpponentCardListener;
    private ActualCard currentPlayerSelectedCard, currentOpponentSelectedCard;
    private ClickListener handClickListener;
    private String selectedCardInHand;

    private Players currentTurn;

    private enum Players {
        ME,
        OPPONENT;
        private PlayerPanel panel;
        private PlayerBoard board;
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
        setupPlayerPanel();
        setupOpponentPanel();
        setupPauseMenu();
        timer = PlayTimer.setNewTimer();
        timer.setTimeListener(timerListener);
        startFrom(Players.ME);
    }

    @Override
    protected void init() {
        opponentPanel.setPreferredSize(new Dimension(getWidth(), getHeight() / 7 * 2));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(pauseMenu);
        add(opponentPanel);
        add(opponentBoard);
        add(playerBoard);
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
//                    currentPlayerSelectedCard = selectedCard;
//                    updateSelections();
//            }
//        };
//        currentOpponentCardListener = new CardClickListener() {
//            @Override
//            public void selectCard(ActualCard selectedCard) {
//                    currentOpponentSelectedCard = selectedCard;
//                    attack(currentPlayerSelectedCard , currentOpponentSelectedCard);
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
                currentTurn.panel.updateTimer(timer.getRemainingTime());
            }

            @Override
            public void ring() {
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
                } else if (currentPlayerSelectedCard != null) {
                    playHandler.playCard(currentPlayerSelectedCard);
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

    private void setupPauseMenu() {
        pauseMenu = PauseMenu.getInstance();
        pauseMenu.setVisible(true);
    }

    private void setupPlayerPanel() {
        playerPanel = playHandler.getPlayerPanel(true);
        playerBoard = new PlayerBoard();
        Players.ME.panel = playerPanel;
        Players.ME.board = playerBoard;
        playerPanel.updateHand(playHandler.getHand(true), playHandler.getHeroStates(true));
    }

    private void setupOpponentPanel() {
        opponentPanel = playHandler.getPlayerPanel(false);
        opponentBoard = new PlayerBoard();
        Players.OPPONENT.panel = opponentPanel;
        Players.OPPONENT.board = opponentBoard;
        opponentPanel.updateHand(playHandler.getHand(false), playHandler.getHeroStates(false));
    }

    //**********************************//
    //****methods for turn settings*****//

    private void changeTurn() {
        playHandler.changeTurns();
        timer.reset();
        if (currentTurn == Players.ME) {
            setTurn(Players.OPPONENT);
        } else {
            setTurn(Players.ME);
        }
    }

    private void setTurn(Players player) {
        currentTurn.board.endTurn();
        currentTurn.panel.endTurn();
        currentTurn = player;
        currentTurn.panel.startTurn(handClickListener, playActionListener, heroActionListener);
        currentTurn.panel.updateHand(playHandler.getHand(), playHandler.getHeroStates());
        currentTurn.board.startTurn(currentPlayerCardListener);
    }

    private void startFrom(Players player) {
        if (player == Players.ME) currentTurn = Players.OPPONENT; // this line is just for avoiding exception
        if (player == Players.OPPONENT) currentTurn = Players.ME; // this line is just for avoiding exception
        setTurn(player);
        timer.start();
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
            currentTurn.panel.updateHand(playHandler.getHand(), playHandler.getHeroStates());
        } catch (PlayException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error in card selection", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void summonMinion(String cardName) throws PlayException {
        //todo have a minionCard and show possible arranges of cards in board before calling summonAndGetCard method in playHandler\
        //todo is there any need to have a different method for beasts?
        MinionActualCard cardToSummon;
        cardToSummon = playHandler.summonAndGetMinion(cardName);
        currentTurn.board.addCard(cardToSummon);
    }

    private void summonWeapon(String cardName) throws PlayException {
        WeaponActualCard weaponToSummon;
        weaponToSummon = playHandler.summonAndGetWeapon(cardName);
        currentTurn.panel.setWeaponCard(weaponToSummon);
    }

    private void playSpell(String spellName) throws PlayException {
        playHandler.playSpell(spellName);
    }

    private void playQuestAndReward(String QRName) throws PlayException {
        playHandler.playQuestAndReward(QRName);
    }

}
