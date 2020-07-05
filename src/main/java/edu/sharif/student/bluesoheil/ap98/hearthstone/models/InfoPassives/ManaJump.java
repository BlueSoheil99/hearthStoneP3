package edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives;

import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.GamePlayer;

public class ManaJump extends Passive {
//    private static ManaJump instance;

    public ManaJump() {

    }
//
//    public static ManaJump getInstance() {
//        if (instance == null) instance = new ManaJump();
//        return instance;
//    }

    @Override
    public void run(GamePlayer player) {
        player.setInitialMana(2);
    }
}
