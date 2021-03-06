package edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs;

import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.ConfigLoader;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.Configs;

public class PlayLogicConfig extends LogicConfig {
    private static PlayLogicConfig instance;
    private Configs config;
    private int maximumStartHints , maximumMana, maximumCardsInHand;
    private int timerPeriod , boardLimit;

    @Override
    protected void setProperties() {
        config =ConfigLoader.getInstance().getPlayLogicConfigs();
    }

    @Override
    protected void initialize() {
        maximumStartHints = config.readInt("maximumStartHints");
        maximumMana = config.readInt("maximumMana");
        maximumCardsInHand = config.readInt("maximumCardsInHand");
        timerPeriod = config.readInt("timerPeriod");
        boardLimit = config.readInt("boardLimit");
    }

    public static PlayLogicConfig getInstance(){
        if (instance==null)  instance = new PlayLogicConfig();
        return instance;
    }

    public int getMaximumStartHints() {
        return maximumStartHints;
    }

    public int getMaximumMana() {
        return maximumMana;
    }

    public int getMaximumCardsInHand() {
        return maximumCardsInHand;
    }

    public int getDefaultTimerPeriod() {
        return timerPeriod;
    }

    public int getBoardLimit() {
        return boardLimit;
    }
}
