package edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs;

import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.ConfigLoader;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.Configs;

public class CollectionConfig extends GuiConfig {

    private static CollectionConfig instance;
    private Configs properties;
    private int filterWidth, filterHeight, deckWidth, deckHeight;
    private int numberOfCardsInRow;

    private CollectionConfig() {
        super();
    }

    public static CollectionConfig getInstance() {
        if (instance == null) instance = new CollectionConfig();
        return instance;
    }

    @Override
    protected void setProperties() {
        properties = ConfigLoader.getInstance().getCollectionProperties();
    }

    @Override
    protected void initialize() {
        filterHeight = properties.readInt("filterHeight");
        filterWidth = properties.readInt("filterWidth");
        deckHeight = properties.readInt("deckHeight");
        deckWidth = properties.readInt("deckWidth");
        numberOfCardsInRow = properties.readInt("numberOfCardsInRow");
    }

    public int getFilterWidth() {
        return filterWidth;
    }

    public int getFilterHeight() {
        return filterHeight;
    }

    public int getDeckWidth() {
        return deckWidth;
    }

    public int getDeckHeight() {
        return deckHeight;
    }

    public int getNumberOfCardsInRow() {
        return numberOfCardsInRow;
    }


}


