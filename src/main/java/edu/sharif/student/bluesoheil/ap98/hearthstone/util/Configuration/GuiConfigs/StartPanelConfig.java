package edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs;

import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.ConfigLoader;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.Configs;

public class StartPanelConfig  extends GuiConfig{

    private static StartPanelConfig startPanelConfig;
    private Configs properties;
    private int width, height, labelWidth, fieldWidth;
    private String logoURL , backGroundURL;

    private StartPanelConfig(){
        super();
    }
    public static StartPanelConfig getInstance(){
        if (startPanelConfig == null){
            startPanelConfig = new StartPanelConfig();
        }
        return startPanelConfig;
    }
    @Override
    public void setProperties() {
        properties = ConfigLoader.getInstance().getStartPanelProperties();
    }

    @Override
    public void initialize() {
        width = properties.readInt("width");
        height = properties.readInt("height");
        labelWidth = properties.readInt("labelWidth");
        fieldWidth = properties.readInt("fieldWidth");
        logoURL = properties.getProperty("logoURL");
        backGroundURL = properties.getProperty("backGroundURL");
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLabelWidth() {
        return labelWidth;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public String getBackGroundURL() {
        return backGroundURL;
    }
}
