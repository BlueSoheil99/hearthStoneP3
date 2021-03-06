package edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives;

import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.GameController;
import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.GamePlayer;

public class Warriors extends Passive{
    public static Warriors instance;

    Warriors() {

    }
    public static Warriors getInstance() {
        if (instance == null) instance = new Warriors();
        return instance;
    }
    @Override
    public void run(GamePlayer player) {
        player.setWarriorsEnabled(true);
    }
}
