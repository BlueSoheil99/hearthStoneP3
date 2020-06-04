package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EventBox extends JTextArea {

    private PlayHandler playHandler;

    public EventBox(){
        super();
        playHandler=PlayHandler.getInstance();
        setPreferredSize(new Dimension(getWidth(), PlayConfig.getInstance().getEventHeight()));
        setBorder(BorderFactory.createMatteBorder(5,5,5,5,new Color(16, 90, 115)));
        setBackground(new Color(168, 118, 94));
        setEditable(false);
        update();
    }
    public void update(){
        ArrayList<String> events = playHandler.getEvents();
        String boxText = "EVENTS:  ";
        for (String event: events){
            boxText += event+" | ";
        }
        setText(boxText);
        setFont(new Font("Arial" , Font.BOLD , 20));
    }
}
