package edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives;

import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.GameController;
import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.GamePlayer;

public class Nurse extends Passive {
    private static Nurse instance;

    Nurse() {
    }

    public static Nurse getInstance() {
        if (instance == null) instance = new Nurse();
        return instance;
    }
    @Override
    public void run(GamePlayer player) {
        player.setNurseEnable(true);
    }
}
