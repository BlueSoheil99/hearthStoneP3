package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.connectors.PlayHandler;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EventBox extends JTextArea {

    private PlayHandler playHandler;

    EventBox(){
        super();
        playHandler=PlayHandler.getInstance();
//        setPreferredSize(new Dimension(getWidth(), PlayConfig.getInstance().getEventHeight()));
        setBorder(BorderFactory.createMatteBorder(3,6,3,6,new Color(199, 210, 9)));
        setBackground(new Color(168, 118, 94));
        setEditable(false);
        update();
    }
    void update(){
        ArrayList<String> events = playHandler.getEvents();
        String boxText = "EVENTS ~~~>>  ";
        for (String event: events){
            boxText += event+" ~> ";
        }
        setText(boxText);
        setFont(new Font("Arial" , Font.ITALIC , 15));
    }
}
