package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.*;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
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
    private int indexToSummonAMinion;

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

    private void setupPauseMenu() {
        pauseMenu = PauseMenu.getInstance();
        pauseMenu.setVisible(true);
    }

    private void setupListeners() {
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
        handClickListener = this::selectAHandCard;
        currentPlayerCardListener = this::selectBoardCard;
        currentOpponentCardListener = this::selectOpponentBoardCard;
        heroActionListener = new HeroActionListener() {
            //todo complete it
            @Override
            public void playHeroPower() {
                unselectHand();
                System.out.println("played hero power");
            }

            @Override
            public void playWeapon(WeaponActualCard playedWeapon) {
                unselectHand();
                System.out.println(playedWeapon.getCardName() + " played");
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
                if (selectedCardInHand != null) checkForMinionPreview(true);
            }

            @Override
            public void goLeft() {
                if (selectedCardInHand != null) checkForMinionPreview(false);
            }
        };
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
        getOppositeSide(currentTurn).board.setCardClickListener(currentOpponentCardListener);
    }

    private void startFrom(Players player) {
        currentTurn = getOppositeSide(player); // this line is just for avoiding exception
        setTurn(player);
        timer.start();
    }

    private Players getOppositeSide(Players player) {
        //or you can make a property named someThing like currentOpponent and initialize it in setTurn method
        if (player == Players.OPPONENT) return Players.ME;
        else return Players.OPPONENT;
    }

    private void unselectHand() {
        currentTurn.panel.unselectCard();
        currentTurn.board.disablePreviewMode();
    }

    //*************************************************//
    //*******methods for playing a card in hand********//
    private void selectAHandCard(String cardName) {
        currentPlayerSelectedCard = null;
        currentTurn.board.unselectCard();

        indexToSummonAMinion = 0;
        if (selectedCardInHand != null && selectedCardInHand.equals(cardName)) {
            selectedCardInHand = null;
            unselectHand();
        } else {
            selectedCardInHand = cardName;
            currentTurn.board.disablePreviewMode();
            checkForMinionPreview();
        }
    }

    private void checkForMinionPreview() {
        Card.CardType type = playHandler.getCardType(selectedCardInHand);
        if (type == Card.CardType.MINION || type == Card.CardType.BEAST) {
            if (currentTurn.board.isFull()) {
                selectedCardInHand = null;
                unselectHand();
                JOptionPane.showMessageDialog(null,
                        "Your Board is full.\nCan't summon more minions or beasts", "Full Board", JOptionPane.ERROR_MESSAGE);
            } else {
                MinionActualCard cardToPreview = playHandler.getMinion(selectedCardInHand);
                currentTurn.board.previewCard(cardToPreview, indexToSummonAMinion);
            }
        }
    }

    private void checkForMinionPreview(boolean falseForLeftAndTrueForRight) {
        Card.CardType type = playHandler.getCardType(selectedCardInHand);
        if (type == Card.CardType.MINION || type == Card.CardType.BEAST) {
            MinionActualCard cardToPreview = playHandler.getMinion(selectedCardInHand);
            if (falseForLeftAndTrueForRight) {
                if (indexToSummonAMinion < PlayerBoard.CARDS_LIMIT - 1) {
                    indexToSummonAMinion++;
                    if (currentTurn.board.getCards().size() <= indexToSummonAMinion)
                        indexToSummonAMinion = currentTurn.board.getCards().size() - 1;
                }
            } else {
                if (indexToSummonAMinion > 0) indexToSummonAMinion--;
            }
            currentTurn.board.previewCard(cardToPreview, indexToSummonAMinion);
        }
    }


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
            unselectHand();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error in card selection", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void summonMinion(String cardName) throws PlayException {
        //todo is there any need to have a different method for beasts?
        MinionActualCard cardToSummon;
        cardToSummon = playHandler.summonAndGetMinion(cardName , indexToSummonAMinion);
        currentTurn.board.disablePreviewMode();
        currentTurn.board.addCard(cardToSummon, indexToSummonAMinion);
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

    //****************************************************//
    //******* methods for playing a card in board ********//

    private void selectBoardCard(ActualCard selectedCard) {
        selectedCardInHand = null;
        unselectHand();

        if (currentPlayerSelectedCard != null && currentPlayerSelectedCard == selectedCard) {
            currentPlayerSelectedCard = null;
            currentTurn.board.unselectCard();
            System.out.println(selectedCard.getCardName() + " unselected from board");
            //                    dismissUpdateSelections();

        } else {
            currentPlayerSelectedCard = selectedCard;
            //                    updateSelections();

            System.out.println(selectedCard.getCardName() + " selected from board");
        }
    }

    private void selectOpponentBoardCard(ActualCard selectedCard) {
        currentOpponentSelectedCard = selectedCard;
        System.out.println(selectedCard.getCardName() + " selected from other board");

//                    attack(currentPlayerSelectedCard , currentOpponentSelectedCard);
    }

}
