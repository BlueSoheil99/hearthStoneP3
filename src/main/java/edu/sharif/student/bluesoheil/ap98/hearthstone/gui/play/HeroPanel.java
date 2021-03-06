package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play;

import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.HeroActionListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.GuiConfigs.PlayConfig;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.ImageLoader;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class HeroPanel extends JPanel {
    private static HashMap<HeroTypes, BufferedImage> heroImages = new HashMap<>();
    private HeroActionListener heroActionListener;
    private boolean isOpponent;
    private JButton heroPower, weaponBtn;
    private JButton selectedCard;
    private Border lastBorder;

    private JLabel hpLabel = new JLabel("HP : ");
    private JLabel manaLabel = new JLabel("Mana : ");
    private JLabel remainingCardsLabel = new JLabel("Remaining Cards : ");
    private JLabel hp = new JLabel("");
    private JLabel mana = new JLabel("");
    private JLabel remainingCards = new JLabel("");
    private JLabel heroIcon;

    static {
        setupHeroImages();
    }

    HeroPanel(HeroTypes hero, int hp, int startingMana) {
        heroIcon = new JLabel();
        heroIcon.setIcon(new ImageIcon(heroImages.get(hero)));
        createHeroPower();
        makeWeaponBtnDefault();
        setActionListeners();
        this.hp.setText(Integer.toString(hp));
        mana.setText(Integer.toString(startingMana));
        init();
        setBorder(BorderFactory.createTitledBorder(hero.getName()));
        setOpaque(false);
    }

    private static void setupHeroImages() {
        for (HeroTypes hero : HeroTypes.values()) {
            heroImages.put(hero, ImageLoader.loadImage(PlayConfig.getInstance().getHeroIconsPath() +
                    "/" + hero.getName() + ".jpg"));
        }
    }

    private void makeWeaponBtnDefault() {
        weaponBtn = new JButton("Weapon");
        weaponBtn.setBackground(Color.BLACK);
        weaponBtn.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(77, 10, 69)));
        weaponBtn.setForeground(Color.white);
        weaponBtn.setEnabled(false);
    }

    private void createHeroPower() {
        heroPower = new JButton("hero power");
        //todo hey make this shit up:(
    }

    private void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(5, 5, 5, 5);
        gb.fill = GridBagConstraints.BOTH;
        gb.gridx = 0;
        gb.gridy = GridBagConstraints.RELATIVE;
        gb.gridheight = 6;
        add(heroPower, gb);
        ////////
        gb.gridy = 0;
        gb.gridx = 1;
        gb.gridheight = 6;
        add(heroIcon, gb);
        ////////
        gb.gridheight = 1;
        gb.gridx = 2;
        gb.gridy = GridBagConstraints.RELATIVE;
        add(manaLabel, gb);
        add(mana, gb);
        add(hpLabel, gb);
        add(hp, gb);
        add(remainingCardsLabel, gb);
        add(remainingCards, gb);
        ////////
        gb.gridx = 3;
        gb.gridheight = 6;
        add(weaponBtn, gb);
    }

    void updateStates(int remainingHp, int remainingMana, int remainingCardsInDeck) {
        hp.setText(Integer.toString(remainingHp));
        mana.setText(Integer.toString(remainingMana));
        remainingCards.setText(Integer.toString(remainingCardsInDeck));
        repaint();
    }

    void setWeaponBtn(WeaponActualCard weaponBtn) {
        if (weaponBtn == null) {
            makeWeaponBtnDefault();
        } else {
            this.weaponBtn = weaponBtn;
            setActionListeners();
            Dimension size = weaponBtn.getSize();
            this.weaponBtn.setMinimumSize(size);
            this.weaponBtn.setEnabled(true);
        }
        removeAll();
        init();
    }

    public void setHeroActionListener(HeroActionListener heroActionListener, boolean isOpponent) {
        this.heroActionListener = heroActionListener;
        this.isOpponent = isOpponent;
    }

    public void disableHeroActionListener() {
        heroActionListener = null;
    }

    private void setActionListeners() {
        heroPower.addActionListener(e -> {
            if (heroActionListener != null) {
                if (!isOpponent) {
                    heroActionListener.playHeroPower();
                }
            }
        });
        weaponBtn.addActionListener(e -> {
            if (heroActionListener != null) {
                if (!isOpponent) {
//                    unselectCards();
//                    selectCard((JButton) e.getSource());
                    heroActionListener.playWeapon((WeaponActualCard) weaponBtn);
                }
            }
        });
        heroIcon.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (heroActionListener != null) {
                            if (isOpponent) heroActionListener.selectHero();
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (heroActionListener != null)
                            if (isOpponent) heroIcon.setBorder(BorderFactory.createLoweredSoftBevelBorder());
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (heroActionListener != null)
                            if (isOpponent) heroIcon.setBorder(null);
                    }
                }
        );
    }

    public void unselectCards() {
        if (selectedCard != null) {
            if (lastBorder != null) {
                selectedCard.setBorder(lastBorder);
                selectedCard = null;
            }
        }
    }
    private void selectCard(JButton selectedCard){
        this.selectedCard = selectedCard;
        lastBorder = selectedCard.getBorder();
        selectedCard.setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, new Color(16, 90, 115)));
    }

    private class heroLabel {
        //todo use this class to have more attractive labels(hp , mana , remaining cards) in hero panel

    }

}
