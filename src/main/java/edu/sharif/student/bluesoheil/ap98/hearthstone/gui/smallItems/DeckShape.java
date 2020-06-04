package edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems;

import javax.swing.*;
import java.awt.*;

//todo change JButton to something less complicated
public class DeckShape extends JButton {
    private String deckName, hero;
    private JLabel nameLabel, heroLabel;

    public DeckShape(String deckName, String hero) {
        this.deckName = deckName;
        this.hero = hero;
        nameLabel = new JLabel(deckName);
        heroLabel = new JLabel(hero);
        Font font = new Font("serif", Font.PLAIN, 30);
        nameLabel.setFont(font);
        heroLabel.setFont(font);
        setBackground(new Color(137, 96, 77));
        setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.gridy = 0;
        g.anchor = GridBagConstraints.WEST;
        add(nameLabel, g);
        g.gridy++;
        add(heroLabel, g);
    }

    public String getDeckName() {
        return deckName;
    }

    public String getHero() {
        return hero;
    }
}
