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

    public Card.CardType getCardType(String playerSelectedCard) { //tofMali shode in !
        Card.CardType type;
        try {
            type = gameController.getCardFromHand(playerSelectedCard).getType();
        } catch (Exception ignored) {
            type = gameController.getSummonedCard(playerSelectedCard).getType();
        }
        return type;
    }

    public void replaceCard(String cardName) throws PlayException {
        GameController.getInstance().drawHandAgain(cardName);
    }

    //***********************//
    //todo check if getHand is really necessary or not, for next phase

    /**
     * it returns hand of a specific player
     */
    public ArrayList<CardShape> getHand(boolean isMe) {
        return administer.getCardShapes(GameController.getInstance().getPlayerHand(isMe));
    }

    /**
     * it returns hand of the player with turn(currentTurn)
     */
    public ArrayList<CardShape> getCurrentTurnHand() {
        return administer.getCardShapes(GameController.getInstance().getCurrentTurnHand());
    }

    /**
     * it returns hand of the player without turn(currentPlayer)
     */
    public ArrayList<CardShape> getCurrentOpponentHand() {
        return administer.getCardShapes(GameController.getInstance().getCurrentOpponentHand());
    }

    //***********************//
    //todo check if getHeroStates is really necessary or not, for next phase

    /**
     * it returns states of  a specific player
     */
    public HashMap<String, Integer> getHeroStates(boolean isMe) {
        return gameController.getHeroStates(isMe);
    }

    /**
     * it returns states of the player with turn
     */
    public HashMap<String, Integer> getCurrentTurnHeroStates() {
        return gameController.getCurrentTurnHeroStates();
    }

    /**
     * it returns states of the player without turn
     */
    public HashMap<String, Integer> getCurrentOpponentHeroStates() {
        return gameController.getCurrentOpponentHeroStates();
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
        Minion minion = (Minion) gameController.getCardFromHand(playerSelectedCardInHand);
        return new MinionActualCard(minion, null);
    }

    public MinionActualCard summonAndGetMinion(String playerSelectedCardInHand, int index) throws PlayException {
        Minion minion = (Minion) playHandMinionOrBeast(playerSelectedCardInHand, index);
        Logger.log(LogTypes.PLAY, gameController.getPlayingSide() + " summoned " + playerSelectedCardInHand);
        return new MinionActualCard(minion, null);
    }

    public WeaponActualCard summonAndGetWeapon(String playerSelectedCardInHand) throws PlayException {
        Weapon weapon = (Weapon) playHandCard(playerSelectedCardInHand);
        Logger.log(LogTypes.PLAY, gameController.getPlayingSide() + " set weapon to " + playerSelectedCardInHand);
        return new WeaponActualCard(weapon, null);
    }

    public void playSpell(String playerSelectedCardInHand) throws PlayException {
        Spell spell = (Spell) playHandCard(playerSelectedCardInHand);
        Logger.log(LogTypes.PLAY, gameController.getPlayingSide() + " played the " + playerSelectedCardInHand + " spell");
    }

    public void playQuestAndReward(String playerSelectedCardInHand) throws PlayException {
        QuestAndReward qAndR = (QuestAndReward) playHandCard(playerSelectedCardInHand);
        Logger.log(LogTypes.PLAY, gameController.getPlayingSide() + " played the " + playerSelectedCardInHand + " Q&R");
    }

    private Card playHandCard(String playerSelectedCardInHand) throws PlayException {
        Card card = gameController.getCardFromHand(playerSelectedCardInHand);
        gameController.purchaseCard(card);
        return card;
    }

    private Card playHandMinionOrBeast(String cardToSummon, int index) throws PlayException {
        Card card = gameController.getCardFromHand(cardToSummon);
        gameController.purchaseMinionOrBeast(card, index);
        return card;
    }

    ////////////
    ////////////

    public void playCard(ActualCard playerSelectedCard) {

    }


    public void attackToHero(ActualCard attackingCard) throws PlayException {

        switch (gameController.getSummonedCard(attackingCard.getCardName()).getType()) {
            case BEAST:
            case MINION:
                gameController.attackToHero(((MinionActualCard) attackingCard).getCard());
                break;
            case WEAPON:
                gameController.attackToHero(((WeaponActualCard) attackingCard).getCard());
                break;
        }
    }
}
