package edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs;


import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.ConfigLoader;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.Configs;

public class MenuConfig extends GuiConfig {
    private static MenuConfig config;
    private Configs properties;
    private String backGroundURL, fontName;
    private int fontSize;
    private int width, height;

    private MenuConfig(){
        super();
    }

    public static MenuConfig getInstance(){
        if (config == null){
            config = new MenuConfig();
        }
        return config;
    }

    @Override
    public void setProperties() {
        properties = ConfigLoader.getInstance().getMenuProperties();
    }

    @Override
    public void initialize() {
//        width = properties.readInt("width");
//        height = properties.readInt("height");
        backGroundURL = properties.getProperty("backGround");
        fontName = properties.getProperty("fontName");
        fontSize = properties.readInt("fontSize");
    }

    public String getBgPath(){
        return backGroundURL;
    }
    public String getFontName(){return fontName;}
    public int getFontSize(){return fontSize;}

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
