package edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs;

import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.ConfigLoader;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.Configs;

public class CardConfig extends LogicConfig{
    private static CardConfig instance;
    private Configs config;
    private String[] defaultCards;
    private String[] defaultMageDeckCards;
    private String[] defaultWarlockDeckCards;
    private String[] defaultRogueDeckCards;
    private String[] defaultPriestDeckCards;
    private String[] defaultHunterDeckCards;
    private String[] minionCards;
    private String[] spellCards;
    private String[] weaponCards;
    private String[] beastCards;
    private String[] questAndRewardCards;


    private CardConfig(){
        super();
    }

    @Override
    protected void setProperties() {
        config = ConfigLoader.getInstance().getCardProperties();

    }

    @Override
    protected void initialize() {
        defaultCards = config.readArrays("defaultPlayerTotalCards");
        defaultMageDeckCards = config.readArrays("defaultMageDeckCards");
        defaultRogueDeckCards = config.readArrays("defaultRogueDeckCards");
        defaultWarlockDeckCards = config.readArrays("defaultWarlockDeckCards");
        defaultHunterDeckCards = config.readArrays("defaultHunterDeckCards");
        defaultPriestDeckCards = config.readArrays("defaultPriestDeckCards");
        weaponCards = config.readArrays("weaponCards");
        spellCards = config.readArrays("spellCards");
        minionCards = config.readArrays("minionCards");
        beastCards = config.readArrays("beastCards");
        questAndRewardCards = config.readArrays("questAndRewardCards");
    }

    public static CardConfig getInstance(){
        if (instance==null)  instance = new CardConfig();
        return instance;
    }

    public String[] getDefaultCards(){
        return defaultCards;
    }

    public String[] getDefaultMageDeckCards() {
        return defaultMageDeckCards;
    }

    public String[] getDefaultWarlockDeckCards() {
        return defaultWarlockDeckCards;
    }

    public String[] getDefaultRogueDeckCards() {
        return defaultRogueDeckCards;
    }

    public String[] getDefaultPriestDeckCards() {
        return defaultPriestDeckCards;
    }

    public String[] getDefaultHunterDeckCards() {
        return defaultHunterDeckCards;
    }

    public String[] getMinionCards() {
        return minionCards;
    }

    public String[] getSpellCards() {
        return spellCards;
    }

    public String[] getWeaponCards() {
        return weaponCards;
    }

    public String[] getBeastCards() {
        return beastCards;
    }

    public String[] getQuestAndRewardCards() {
        return questAndRewardCards;
    }
}
