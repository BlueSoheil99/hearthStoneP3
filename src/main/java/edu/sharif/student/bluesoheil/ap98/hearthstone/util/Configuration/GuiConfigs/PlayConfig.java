package edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs;

import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.ConfigLoader;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.Configs;

public class PlayConfig extends GuiConfig {
    private static PlayConfig instance;
    private Configs properties;
    private int pauseWidth, pauseHeight, eventHeight,passivePanelWidth,passivePanelHeight;
    private int playerPanelHeight , playerPanelWidth;
    private int cardWidth , cardHeight;
    private String heroIconsPath , cardIconsPath;

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
        playerPanelHeight = properties.readInt("playerPanelHeight");
        playerPanelWidth  = properties.readInt("playerPanelWidth");
        heroIconsPath = properties.getProperty("heroIconsPath");
        cardIconsPath = properties.getProperty("cardIconsPath");
        cardHeight=properties.readInt("cardHeight");
        cardWidth=properties.readInt("cardWidth");
    }

    public String getHeroIconsPath() {
        return heroIconsPath;
    }

    public String getCardIconsPath() {
        return cardIconsPath;
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

    public int getPlayerPanelHeight() {
        return playerPanelHeight;
    }

    public int getPlayerPanelWidth() {
        return playerPanelWidth;
    }

    public int getCardWidth() {
        return cardWidth;
    }

    public int getCardHeight() {
        return cardHeight;
    }
}
