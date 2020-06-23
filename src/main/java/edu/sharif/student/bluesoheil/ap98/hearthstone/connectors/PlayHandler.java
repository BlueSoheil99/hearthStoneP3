package edu.sharif.student.bluesoheil.ap98.hearthstone.connectors;

import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.GameController;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play.PlayPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play.PlayerPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class PlayHandler {
    private static PlayHandler instance;
    private Administer administer;
    private GameController gameController;
    private PlayPanel playPanel;

    /**
     * this class works as a connection between gui and logic(administer + gameController)
     */
    private PlayHandler() {
        administer = Administer.getInstance();
    }

    ///////////////////////////////
    ///////////statics/////////////

    public static PlayHandler getInstance() {
        if (instance == null || instance.gameController == null)
            instance = new PlayHandler(); //it's not stable until gets a gameController and therefore a game get started
        return instance;
    }

    public static void setNewHandler(GameController gameController) {
        instance.gameController = gameController;
//        instance.playPanel = playPanel;
    }

    public static void dismissHandler(){
        instance.gameController = null;
    }

    //////////////////////////////
    /////////non-statics//////////

    //
    ///
    ////getters and setters
    ///
    //

    public ArrayList<CardShape> get3Passives() {
        ArrayList<CardShape> passives = administer.getPassives();
        ArrayList<CardShape> threeOnes = new ArrayList<>();
        Collections.shuffle(passives);
        for (int i = 0; i < 3; i++) threeOnes.add(passives.get(i));
        return threeOnes;
    }

    public ArrayList<String> getEvents() {
        return Logger.getEventLogs();
    }

    public PlayerPanel getPlayerPanel() {
        return new PlayerPanel(gameController.getPlayerHero() ,
                gameController.getPlayerHP() , gameController.getInitialPlayerMana());
    }

    public PlayerPanel getOpponentPanel() {
        return new PlayerPanel(gameController.getOpponentHero() ,
                gameController.getOpponentHP() , gameController.getInitialOpponentMana());
    }

    public ArrayList<CardShape> getHand() {
       return administer.getCardShapes(gameController.getPlayerHand());
    }

    public void playCard(String cardName){

    }

    //
    ///
    ////other methods
    ///
    //

}
