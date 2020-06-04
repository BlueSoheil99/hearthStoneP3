package edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs;

import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.ConfigLoader;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.Configs;

public class PlayConfig extends GuiConfig {
    private static PlayConfig instance;
    private Configs properties;
    private int pauseWidth, pauseHeight, eventHeight,passivePanelWidth,passivePanelHeight;

    public static PlayConfig getInstance(){
        if (instance== null) instance = new PlayConfig();
        return instance;
    }

    @Override
    protected void setProperties() {
        properties= ConfigLoader.getInstance().getPlayProperties();
    }

    @Override
    protected void initialize() {
        pauseWidth = properties.readInt("pauseWidth");
        pauseHeight = properties.readInt("pauseHeight");
        eventHeight = properties.readInt("eventHeight");
        passivePanelHeight = properties.readInt("passivePanelHeight");
        passivePanelWidth = properties.readInt("passivePanelWidth");
    }

    public int getEventHeight() {
        return eventHeight;
    }

    public int getPassivePanelWidth() {
        return passivePanelWidth;
    }

    public int getPassivePanelHeight() {
        return passivePanelHeight;
    }
}
