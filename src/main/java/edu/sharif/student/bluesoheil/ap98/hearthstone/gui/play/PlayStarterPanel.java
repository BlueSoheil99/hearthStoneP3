package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.NavigationPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.ClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PlayStarterPanel extends GamePanel {
    private PlayConfig properties;
    private CardPanel passivePanel , handPanel;
    private JLabel selectLabel;
    private JButton selectBtn , changeBtn;
    private String selectedPassive , selectedCard;
    private ClickListener clickListener;

    public PlayStarterPanel() {
        super();
        setBackground(new Color(0xA8765E));
        setBorder(BorderFactory.createMatteBorder(30,30,30,30 ,new Color(0x562C1C)));
    }

    @Override
    protected void loadConfig() {
        properties = PlayConfig.getInstance();
    }

    @Override
    protected void createFields() {
        Font font = new Font("serif" , Font.BOLD , 40);
        selectLabel = new JLabel("Select a passive");
        selectBtn = new JButton("Select");
        changeBtn = new JButton("Change");
        selectLabel.setFont(font);
        makeBtnLookBetter(selectBtn ,"Serif",30);
        makeBtnLookBetter(changeBtn ,"Serif",30);
        SetSelectBtnActionListener();
        SetChangeBtnActionListener();
        passivePanel = new CardPanel();
        passivePanel.setPassives(PlayHandler.getInstance().get3Passives());
        passivePanel.setClickListener(objName -> selectedPassive = objName);
        handPanel = new CardPanel();
        handPanel.setCards(PlayHandler.getInstance().getHand());
        handPanel.setClickListener(objName -> selectedCard = objName);
    }

    private void makeBtnLookBetter(JButton button , String fontName , int fontSize) {
        Font font = new Font(fontName , Font.BOLD , fontSize);
        button.setFont(font);
        button.setContentAreaFilled(false);
        button.setBackground(new Color(192, 135, 107));
    }

    private void SetSelectBtnActionListener() {
        selectBtn.addActionListener(e -> {
            if(selectedPassive ==null){
                JOptionPane.showMessageDialog(null ,"You Haven't Chosen a Passive Yet");
            }else{
                clickListener.select(selectedPassive);
            }
        });
    }

    private void SetChangeBtnActionListener() {
        changeBtn.addActionListener(e -> {
            if(selectedCard ==null){
                JOptionPane.showMessageDialog(null ,"You Haven't Chosen a Card Yet");
            }else{
                try {
                    PlayHandler.getInstance().replaceCard(selectedCard);
                    handPanel.setCards(PlayHandler.getInstance().getHand());
                } catch (PlayException ex) {
                    JOptionPane.showMessageDialog(null ,ex.getMessage());
                }
            }
        });
    }


    @Override
    protected void init() {
        passivePanel.setMinimumSize(new Dimension(properties.getPassivePanelWidth(), properties.getPassivePanelHeight()));
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets=new Insets(10,0,0,10);
        gc.gridx=0;
        gc.gridy=GridBagConstraints.RELATIVE;
        add(selectLabel , gc);
        add(passivePanel, gc);
        add(selectBtn , gc);
        add(handPanel , gc);
        add(changeBtn , gc);
        add(NavigationPanel.getInstance() , gc);
    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
