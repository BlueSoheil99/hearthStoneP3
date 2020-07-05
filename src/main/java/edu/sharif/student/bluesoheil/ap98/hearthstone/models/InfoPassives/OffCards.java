package edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives;

import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.GamePlayer;

public class OffCards extends Passive {
    private static OffCards instance;

    OffCards() {
    }

    public static OffCards getInstance() {
        if (instance == null) instance = new OffCards();
        return instance;
    }

    @Override
    public void run(GamePlayer player) {
        player.setOffCardsEnable(true);
    }
}
