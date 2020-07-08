package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.*;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.PlayTimer;
import jdk.nashorn.internal.scripts.JO;

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
    private HeroActionListener currentTurnHeroActionListener, currentOpponentHeroActionListener;
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
        setupHeroActionListeners();
        setupPlayActionListener();
    }

    private void setupHeroActionListeners() {
        currentTurnHeroActionListener = new HeroActionListener() { //this is used for attack of a hero
            @Override
            public void playHeroPower() {
//                for now i can't use this method for heroPower because heroPower can't be treated like cards
//                unselectHand();
                System.out.println("played hero power");
            }

            @Override
            public void playWeapon(WeaponActualCard playedWeapon) {
                selectAHandCard(playedWeapon.getCardName());
                currentPlayerSelectedCard = playedWeapon;
                System.out.println("weapon "+playedWeapon.getCardName() + " selected");
            }

            @Override
            public void selectHero() {
            }
        };

        currentOpponentHeroActionListener = new HeroActionListener() { // this one is used for a hero being attacked
            @Override
            public void playHeroPower() {
            }

            @Override
            public void playWeapon(WeaponActualCard playedWeapon) {
            }

            @Override
            public void selectHero() {
                attackToHero();
            }
        };
    }

    private void setupPlayActionListener() {
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
        playerPanel.updatePlayer(playHandler.getHand(true), playHandler.getHeroStates(true));
    }

    private void setupOpponentPanel() {
        opponentPanel = playHandler.getPlayerPanel(false);
        opponentBoard = new PlayerBoard();
        Players.OPPONENT.panel = opponentPanel;
        Players.OPPONENT.board = opponentBoard;
        opponentPanel.updatePlayer(playHandler.getHand(false), playHandler.getHeroStates(false));
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
        currentTurn.panel.endTurn(currentOpponentHeroActionListener);
        currentTurn = player;
        currentTurn.panel.startTurn(handClickListener, playActionListener, currentTurnHeroActionListener);
        currentTurn.panel.updatePlayer(playHandler.getCurrentTurnHand(), playHandler.getCurrentTurnHeroStates());
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
        selectedCardInHand = null;
        currentTurn.panel.unselectCard();
//        currentTurn.panel.unselectHeroOptions();
        currentTurn.board.disablePreviewMode();
    }

    private void unselectBoard() {
        currentPlayerSelectedCard = null;
        currentTurn.board.unselectCard();
    }

    //*************************************************//
    //*******methods for playing a card in hand********//
    private void selectAHandCard(String cardName) {
        // we both use this method for handCards and weapon and heroPower
        unselectBoard();
        indexToSummonAMinion = 0;
        if (selectedCardInHand != null && selectedCardInHand.equals(cardName)) {
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
            currentTurn.panel.updatePlayer(playHandler.getCurrentTurnHand(), playHandler.getCurrentTurnHeroStates());
        } catch (PlayException e) {
            unselectHand();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid selected card", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void summonMinion(String cardName) throws PlayException {
        //todo is there any need to have a different method for beasts?
        MinionActualCard cardToSummon;
        cardToSummon = playHandler.summonAndGetMinion(cardName, indexToSummonAMinion);
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
//      attack(currentPlayerSelectedCard , currentOpponentSelectedCard);
    }

    private void attackToHero() {
        if (currentPlayerSelectedCard != null) {
//todo  if (opponentHeroCanBeAttacked) { //write opponentHeroCanBeAttacked as a method
//          if (thereIsNoTaunt) //write thereIsNoTaunt as a method
            try {
                playHandler.attackToHero(currentPlayerSelectedCard);
                System.out.println("attacked to hero ");
                updatePlayers();
            } catch (PlayException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "invalid move", JOptionPane.ERROR_MESSAGE);
            }
            unselectBoard();
        }
    }

    private void attackToBoardCard() {

    }

    private void updatePlayers() {
        //for now i think it's only useful after attacking to hero
        currentTurn.panel.updatePlayer(playHandler.getCurrentTurnHand(), playHandler.getCurrentTurnHeroStates());
        getOppositeSide(currentTurn).panel.updatePlayer(playHandler.getCurrentOpponentHand(), playHandler.getCurrentOpponentHeroStates());
        System.out.println("players are updated");
    }
}
