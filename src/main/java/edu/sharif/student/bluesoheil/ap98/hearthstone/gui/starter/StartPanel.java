package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.starter;

import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.StartPanelConfig;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class StartPanel extends JPanel {
    private JLabel logo;
    private StartPanelConfig config;
    private String logoPath, backGroundPath;
    private int width, height;
    private int labelWidth , fieldWidth;
    private int X1Inset,X2Inset,YInset;
    protected int x1, x2, y0;

    public StartPanel(){
        loadConfig();
//        loadNormally();
        setSize(new Dimension( width , height));
        setBackground(Color.white);

        ImageIcon imageIcon = new ImageIcon(logoPath);
        logo = new JLabel("",imageIcon,JLabel.CENTER);
        logo.setBounds(0,0,getWidth(),getHeight()/2);
        add(logo);
        createFields();
        setLayout(null);
        init();
    }

    private void loadConfig(){
        config = StartPanelConfig.getInstance();
        logoPath = config.getLogoURL();
        backGroundPath = config.getBackGroundURL();
        width = config.getWidth();
        height = config.getHeight();
        labelWidth = config.getLabelWidth();
        fieldWidth = config.getFieldWidth();
        X1Inset=width/2-(labelWidth+fieldWidth)/2;
        X2Inset=X1Inset+labelWidth;
        YInset= 4*height/11;
    }
    private void loadNormally(){
        logoPath = "src/res/edu/sharif/student/bluesoheil/ap98/hearthstone/Images/Hearthstone_2016_logo.png";
        backGroundPath = "src/res/edu/sharif/student/bluesoheil/ap98/hearthstone/Images/7ho000lptrr01.jpg";
        width = 640;
        height = 480;
        labelWidth = 80;
        fieldWidth = 150;
        X1Inset=width/2-(labelWidth+fieldWidth)/2;
        X2Inset=X1Inset+labelWidth;
        YInset= 4*height/11;
    }

    public int getX1Inset() {
        return X1Inset;
    }
    public int getX2Inset() {
        return X2Inset;
    }
    public int getYInset() {
        return YInset;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = ImageLoader.loadImage(backGroundPath);
        g.drawImage(img,0,0,width,height,null);
    }

    protected abstract void createFields();

    /**
     * for starter panels I didn't use a layout manager and i added components manually !
     */
    protected void init(){
        x1 = getX1Inset();
        x2 = getX2Inset();
        y0 = getYInset();
    }

}
