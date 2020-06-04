package edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs;

import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.ConfigLoader;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.Configs;

public class LogicConstants extends LogicConfig {

    private Configs config;
    private static LogicConstants instance;

    private String profilesPath;
    private String logsPath;
    private String cardsPath;
    private String cardsImagesPath;

    @Override
    protected void setProperties() {
        config = ConfigLoader.getInstance().getConstantsProperties();
    }

    @Override
    protected void initialize() {
        profilesPath = config.getProperty("profilesPath");
        logsPath = config.getProperty("logsPath");
        cardsPath = config.getProperty("cardsPath");
        cardsImagesPath = config.getProperty("cardsImagesPath");
    }

    public static LogicConstants getInstance() {
        if (instance == null) {
            instance = new LogicConstants();
        }
        return instance;
    }

    public String getProfilesPath() {
        return profilesPath;
    }

    public String getLogsPath() {
        return logsPath;
    }

    public String getCardsPath() {
        return cardsPath;
    }

    public String getCardsImagesPath() {
        return cardsImagesPath;
    }

}
