package edu.sharif.student.bluesoheil.ap98.hearthstone.connectors;

import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.GameController;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class PlayHandler {
    private static PlayHandler instance;
    private Administer administer;
    private GameController gameController;

    public PlayHandler() {
        administer = Administer.getInstance();
    }

    public static PlayHandler getInstance() {
        if (instance == null) instance = new PlayHandler();
        return instance;
    }

    public ArrayList<CardShape> get3Passives() {
        ArrayList<CardShape> passives = administer.getPassives();
        ArrayList<CardShape> threeOnes = new ArrayList<>();
        HashSet<Integer> threeIndex = new HashSet(); // I created this set to avoid generating same indexes
        Random rand = new Random();
        int x;
        boolean isEnough = false;
        while (!isEnough) {
            x = rand.nextInt(passives.size());
            threeIndex.add(x);
            if (threeIndex.size() == 3) isEnough = true;
        }
        for (int i : threeIndex) {
            threeOnes.add(passives.get(i));
        }
        return threeOnes;
    }

    public void setGameController(GameController gameController) {
        this.gameController = new GameController();
    }

    public ArrayList<String> getEvents() {
        return Logger.getEventLogs();
    }

}
