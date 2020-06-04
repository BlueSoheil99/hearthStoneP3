package edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs;

public abstract class GuiConfig {

//    private static GuiConfig guiConfig;

    public GuiConfig() {
        setProperties();
        initialize();
    }

    protected abstract void setProperties();

    protected abstract void  initialize();

}