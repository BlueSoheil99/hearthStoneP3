package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.CardClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.PlayActionListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
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
        board = new BoardPanel();
        board.setPlayerCardClickListeners(new CardClickListener() {
            @Override
            public void selectCard(ActualCard selectedCard) {
                playerSelectedCard = selectedCard;
            }
            @Override
            public void selectCard(CardShape selectedCard) {
            }
        });
        board.setOpponentCardClickListeners(new CardClickListener() {
            @Override
            public void selectCard(ActualCard selectedCard) {
                opponentSelectedCard = selectedCard;
            }
            @Override
            public void selectCard(CardShape selectedCard) {
            }
        });
    }

    @Override
    protected void init() {
        opponentPanel.setPreferredSize(new Dimension(getWidth() , getHeight()/7*2));
        playerPanel.setPreferredSize(new Dimension(getWidth() , getHeight()/7*2));
        board.setPreferredSize(new Dimension(getWidth() , getHeight()/7*3));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(opponentPanel);
        add(board);
        add(playerPanel);
        add(new JScrollPane(new EventBox()));
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
                if (playerSelectedCardInHand !=null){
                    Card.CardType type = playHandler.getCardType(playerSelectedCardInHand);
                    if (type.equals(Card.CardType.MINION)||type.equals(Card.CardType.BEAST)) playHandler.summonMinion(playerSelectedCardInHand , PlayPanel.this);
                    //todo for now we display beast and minion like each other . create an actual card class for beast later
                    if (type.equals(Card.CardType.WEAPON)) playHandler.summonWeapon(playerSelectedCardInHand,PlayPanel.this);
                    if (type.equals(Card.CardType.QUESTANDREWARD)) playHandler.playQuestAndReward(playerSelectedCardInHand,PlayPanel.this);
                    if (type.equals(Card.CardType.SPELL)) playHandler.playSpell(playerSelectedCardInHand,PlayPanel.this);
                }else if (playerSelectedCard !=null) {
                    playHandler.playCard(playerSelectedCard);
                }else{
                    JOptionPane.showMessageDialog(null,"You suck:/");
                }
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

    public void summonMinion(MinionActualCard card) {
        board.addCardForPlayer(card);
        System.out.println("added");
    }
}
