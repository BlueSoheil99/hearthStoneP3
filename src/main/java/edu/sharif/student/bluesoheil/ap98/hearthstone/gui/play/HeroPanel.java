package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class HeroPanel extends JPanel {
    private static HashMap<HeroTypes , BufferedImage> heroImages = new HashMap<>();
    {
        for(HeroTypes hero : HeroTypes.values()){
            heroImages.put(hero , ImageLoader.loadImage(PlayConfig.getInstance().getHeroIconsPath()+
                    "/"+hero.getName() + ".jpg"));
        }
//        heroImages.put(HeroTypes.PRIEST , ImageLoader.loadImage(PlayConfig.getInstance().getHeroIconsPath()+"/PRIEST.jpg"));
    }
//    private static PlayConfig properties = PlayConfig.getInstance();

    private JLabel hpLabel = new JLabel("HP : ");
    private JLabel manaLabel = new JLabel("Mana : ");
    private JLabel hp = new JLabel("");
    private JLabel mana = new JLabel("");
    private JLabel heroIcon;
    private JButton heroPower;

    HeroPanel(HeroTypes hero, int hp, int startingMana) {
        heroIcon = new JLabel();
        heroIcon.setIcon(new ImageIcon(heroImages.get(hero)));
        heroPower = new JButton("hero power");
        setHp(hp);
        setMana(startingMana);
        init();
        setBorder(BorderFactory.createTitledBorder(hero.getName()));
        setOpaque(false);
    }

    private void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(5, 5, 5, 5);
        gb.anchor = GridBagConstraints.CENTER;
        gb.fill = GridBagConstraints.BOTH;
        gb.gridx = 0;
        gb.gridy = 0;
        gb.gridheight = 4;
        add(heroPower, gb);
        ////////
        gb.gridy = 0;
        gb.gridx = 1;
        gb.gridheight = 4;
        add(heroIcon, gb);
        ////////
        gb.gridheight = 1;
        gb.gridx = 2;
        gb.gridy = GridBagConstraints.RELATIVE;
        add(manaLabel, gb);
        add(mana, gb);
        add(hpLabel, gb);
        add(hp, gb);
    }

    void setHp(int newHp) {
        hp.setText(Integer.toString(newHp));
    }

    void setMana(int newMana) {
        mana.setText(Integer.toString(newMana));
    }

    private class heroLabel {

    }

}
