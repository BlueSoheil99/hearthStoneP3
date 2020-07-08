package edu.sharif.student.bluesoheil.ap98.hearthstone.connectors;

import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.GameController;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play.*;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.*;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.PlayTimer;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * this class works as a connection between gui and logic(administer + gameController)
 */
public class PlayHandler {

    private PlayHandler() {
        administer = Administer.getInstance();
    }

    private static PlayHandler instance;
    private Administer administer;
    private GameController gameController;

    ///////////////////////////////
    ///////////statics/////////////

    public static PlayHandler getInstance() {
        if (instance == null || instance.gameController == null)
            instance = new PlayHandler(); //it's not stable until gets a gameController and therefore a game get started
        return instance;
    }

    public static void setNewHandler(GameController gameController) {
        instance.gameController = gameController;
    }

    public static void dismissHandler() {
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

    public LinkedHashMap<String, String> getAvailableDecks() {
        return administer.getAvailableDecks();
    }

    public ArrayList<String> getEvents() {
        return Logger.getEventLogs();
    }

    public PlayerPanel getPlayerPanel(boolean isMe) {
        return new PlayerPanel(gameController.getPlayerHero(isMe),
                gameController.getHeroStates(isMe).get("HP"), gameController.getHeroStates(isMe).get("MANA"));
    }

    public Card.CardType getCardType(String playerSelectedCard) {
        return gameController.getCard(playerSelectedCard).getType();
    }

    public void replaceCard(String cardName) throws PlayException {
        GameController.getInstance().drawHandAgain(cardName);
    }

    //***********************//

    /**
     * it returns hand of a specific player
     */
    public ArrayList<CardShape> getHand(boolean isMe) {
        return administer.getCardShapes(GameController.getInstance().getPlayerHand(isMe));
    }

    /**
     * it returns hand of the player with turn(currentPlayer)
     */
    public ArrayList<CardShape> getHand() {
        return administer.getCardShapes(GameController.getInstance().getPlayerHand());
    }

    //***********************//

    /**
     * it returns states of  a specific player
     */
    public HashMap<String, Integer> getHeroStates(boolean isMe) {
        return gameController.getHeroStates(isMe);
    }

    /**
     * it returns states of the player with turn
     */
    public HashMap<String, Integer> getHeroStates() {
        return gameController.getHeroStates();
    }
    //***********************//

    //
    ///
    ////general settings
    ///
    //

    public void changeTurns() {
        gameController.changeTurns();
    }

    //todo exiting and forfeiting match must have a penalty(loosing or etc.)

    public void exitGame() {
        administer.runExit();
    }

    public void forfeitMatch() {
        Logger.log(LogTypes.PLAY, gameController.getPlayingSide() + " forfeited the fight");
        PlayTimer.getCurrentTimer().stopTimer();
        administer.back();
    }

    //
    ///
    //// card playing methods
    ///
    //

    public MinionActualCard getMinion(String playerSelectedCardInHand) {
        Minion minion = (Minion) gameController.getCard(playerSelectedCardInHand);
        return new MinionActualCard(minion, null);
    }

    public MinionActualCard summonAndGetMinion(String playerSelectedCardInHand) throws PlayException {
        Minion minion = (Minion) getCardFromController(playerSelectedCardInHand);
        Logger.log(LogTypes.PLAY, gameController.getPlayingSide() + " summoned " + playerSelectedCardInHand);
        return new MinionActualCard(minion, null);
    }

    public WeaponActualCard summonAndGetWeapon(String playerSelectedCardInHand) throws PlayException {
        Weapon weapon = (Weapon) getCardFromController(playerSelectedCardInHand);
        Logger.log(LogTypes.PLAY, gameController.getPlayingSide() + " set weapon to " + playerSelectedCardInHand);
        return new WeaponActualCard(weapon, null);
    }

    public void playSpell(String playerSelectedCardInHand) throws PlayException {
        Spell spell = (Spell) getCardFromController(playerSelectedCardInHand);
        Logger.log(LogTypes.PLAY, gameController.getPlayingSide() + " played the " + playerSelectedCardInHand + " spell");
    }

    public void playQuestAndReward(String playerSelectedCardInHand) throws PlayException {
        QuestAndReward qAndR = (QuestAndReward) getCardFromController(playerSelectedCardInHand);
        Logger.log(LogTypes.PLAY, gameController.getPlayingSide() + " played the " + playerSelectedCardInHand + " Q&R");
    }

    private Card getCardFromController(String playerSelectedCardInHand) throws PlayException {
        Card card = gameController.getCard(playerSelectedCardInHand);
        gameController.purchaseCard(card);
        return card;
    }

    ////////////
    ////////////

    public void playCard(ActualCard playerSelectedCard) {

    }


}
