package edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives;

import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.GameController;

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
    public void run() {
        GameController.getInstance().setInitialMana(2);
    }
}
