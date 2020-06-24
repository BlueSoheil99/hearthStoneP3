package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.CardClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.ClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.PlayActionListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import java.awt.event.*;

public class PlayPanel extends GamePanel {

    private PlayConfig properties;
    private PlayHandler playHandler;
    private PauseMenu pauseMenu;
    private JButton pauseBtn;
    private PlayerPanel playerPanel, opponentPanel;
    private BoardPanel board;
    private CardShape playerSelectedCard, opponentSelectedCard;
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
        board.setPlayerCardClickListeners(selectedCard -> playerSelectedCard = selectedCard);
        board.setOpponentCardClickListeners(selectedCard -> opponentSelectedCard = selectedCard);
    }

    @Override
    protected void init() {
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
                    board.addCardForPlayer(playHandler.getCard(playerSelectedCard));
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

}
