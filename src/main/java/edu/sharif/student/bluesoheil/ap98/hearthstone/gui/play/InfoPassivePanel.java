package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.GamePanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.NavigationPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.ClickListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InfoPassivePanel extends GamePanel {
    private PlayConfig properties;
    private CardPanel panel;
    private JLabel selectLabel;
    private JButton selectBtn;
    private String selectedPassive;
    private ClickListener clickListener;

    public InfoPassivePanel() {
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
        selectLabel.setFont(font);
        selectBtn = new JButton("Select");
        selectBtn.setFont(font);
        selectBtn.setContentAreaFilled(false);
        selectBtn.setBackground(new Color(192, 135, 107));
        SetSelectBtnActionListener();
        panel = new CardPanel();
        panel.setPassives(PlayHandler.getInstance().get3Passives());
        panel.setClickListener(new ClickListener() {
            @Override
            public void select(String objName) {
                selectedPassive = objName;
            }
        });
    }

    private void SetSelectBtnActionListener() {
        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedPassive ==null){
                    JOptionPane.showMessageDialog(null ,"You Haven't Chosen a Passive Yet");
                }else{
                    clickListener.select(selectedPassive);
                }
            }
        });
    }


    @Override
    protected void init() {
        panel.setMinimumSize(new Dimension(properties.getPassivePanelWidth(), properties.getPassivePanelHeight()));

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets=new Insets(20,0,0,20);
        gc.gridx=0;
        gc.gridy=0;
        add(selectLabel , gc);
        gc.gridy++;
        add(panel , gc);
        gc.gridy++;
        add(selectBtn , gc);
        gc.gridy++;
        add(NavigationPanel.getInstance() , gc);

    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
