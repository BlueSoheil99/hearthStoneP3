package edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs;

import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.ConfigLoader;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.Configs;

public class GuiConstants extends GuiConfig {
    private static GuiConstants instance;
    public String defaultBackCover , backCoversPath;
    private Configs properties;
    private int gameWidth, gameHeight, cardWidth, cardHeight , passiveWidth, passiveHeight;
    private int numberOfCardsInRow ;
    private String exitIconPath, backIconPath, filterIconPath, coinsIconPath , logOutPath;

    private GuiConstants() {
        super();
    }

    public static GuiConstants getInstance() {
        if (instance == null) instance = new GuiConstants();
        return instance;
    }

    @Override
    protected void setProperties() {
        properties = ConfigLoader.getInstance().getGuiConstantsProperties();
    }

    @Override
    protected void initialize() {
        gameHeight = properties.readInt("gameHeight");
        gameWidth = properties.readInt("gameWidth");
        cardHeight = properties.readInt("cardHeight");
        cardWidth = properties.readInt("cardWidth");
        passiveHeight = properties.readInt("passiveHeight");
        passiveWidth = properties.readInt("passiveWidth");
        numberOfCardsInRow = properties.readInt("numberOfCardsInRow");
        coinsIconPath = properties.getProperty("coinsURL");
        exitIconPath = properties.getProperty("exitURL");
        backIconPath = properties.getProperty("backURL");
        filterIconPath = properties.getProperty("filterURL");
        logOutPath = properties.getProperty("logOutURL");
        defaultBackCover = properties.getProperty("defaultBackCover");
        backCoversPath = properties.getProperty("backCoversPath");
    }


    public int getGameWidth() {
        return gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public int getCardWidth() {
        return cardWidth;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    public int getPassiveHeight() {
        return passiveHeight;
    }

    public int getPassiveWidth() {
        return passiveWidth;
    }

    public int getNumberOfCardsInRow() {
        return numberOfCardsInRow;
    }

    public String getExitIconPath() {
        return exitIconPath;
    }

    public String getBackIconPath() {
        return backIconPath;
    }

    public String getFilterIconPath() {
        return filterIconPath;
    }

    public String getCoinsIconPath() {
        return coinsIconPath;
    }

    public String getLogOutPath() {
        return logOutPath;
    }

    public String getDefaultBackCoverPath() {
        return defaultBackCover;
    }

    public String getBackCoversPath() {
        return backCoversPath;
    }

    public void setDefaultBackCoverPath(String selectedBackCoverNumber) {
        properties.setProperty("defaultBackCover",backCoversPath+"/"+selectedBackCoverNumber);
        defaultBackCover = properties.getProperty("defaultBackCover");
    }
}
