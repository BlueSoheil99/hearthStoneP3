package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives.InfoPassive;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Beast;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Minion;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Weapon;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.PlayLogicConfig;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameController {
    private static GameController instance;

    private PlayLogicConfig properties;
    private DeckController deckController;
    private boolean playerIsWinner = false;
    private Players currentTurn;

    public enum Players {
        ME,
        OPPONENT;

        private GamePlayer gamePlayer;

    }

    private GameController() {
        properties = PlayLogicConfig.getInstance();
        deckController = DeckController.getInstance();
        setPlayer();
        startFrom(Players.ME);
    }

    ///////////
    /////////////////
    ///statics//////////
    /////////////////
    ///////////
    public static GameController getInstance() {
        if (instance == null) setNewGame();
        return instance;
    }

    public static void setNewGame() {
        instance = new GameController();
    }

    public static void endGame() {
        //todo check this at the EEnnnnnnnnnnndddddd -- winner is the opponent by default until the opposite happens
        // -- this view helps you to code forfeiting mach better
//        if (instance.playerIsWinner) instance.deckController.getCurrentDeck().incrementWins();
//        instance.deckController.getCurrentDeck().setCardsUsage(instance.playerDeck.getCardsUsage());
//        instance.playerDeck.getHeroType().resetHero();
//        instance.opponentDeck.getHeroType().resetHero();
//        PlayHandler.dismissHandler();
//        instance = null;
    }


    ///////////
    /////////////////
    ///non-statics//////
    /////////////////
    ///////////

    private void setPlayer() {
        Players.ME.gamePlayer = new GamePlayer(deckController.getCurrentDeck());
    }

    public void setOpponent(String deckName) {
        Players.OPPONENT.gamePlayer = new GamePlayer(deckController.getDeck(deckName));
        Random random = new Random();
        int index = random.nextInt(InfoPassive.values().length);
        String randomPassive = InfoPassive.values()[index].getName();
        Players.OPPONENT.gamePlayer.setPassive(randomPassive);
        Logger.log(LogTypes.PLAY, "opponent has '" + randomPassive + "' passive");
    }


    //***********************//
    private GamePlayer getPlayer(boolean isME) {
        if (isME) return Players.ME.gamePlayer;
        else return Players.OPPONENT.gamePlayer;
    }

    //***********************//
    public void setPassiveForUser(String passiveName) {
        Players.ME.gamePlayer.setPassive(passiveName);
        Logger.log(LogTypes.PLAY, "you have '" + passiveName + "' passive");
    }

    //***********************//
    public HeroTypes getPlayerHero() {
        return currentTurn.gamePlayer.getHeroType();
    }

    public HeroTypes getPlayerHero(boolean isMe) {
        return getPlayer(isMe).getHeroType();
    }

    //***********************//

    public HashMap<String, Integer> getHeroStates(boolean isMe) {
        return getPlayer(isMe).getHeroStates();
    }

    public HashMap<String, Integer> getCurrentTurnHeroStates() {
        return currentTurn.gamePlayer.getHeroStates();
    }

    public HashMap<String, Integer> getCurrentOpponentHeroStates() {
        return getOppositeSide(currentTurn).gamePlayer.getHeroStates();
    }


    //***********************//

    public ArrayList<Card> getPlayerHand(boolean isMe) {
        return getPlayer(isMe).getHand();
    }

    public ArrayList<Card> getCurrentTurnHand() {
        return currentTurn.gamePlayer.getHand();
    }

    public ArrayList<Card> getCurrentOpponentHand() {
        return getOppositeSide(currentTurn).gamePlayer.getHand();
    }


    //***********************//
    public ArrayList<Card> getCurrentTurnBoardCards() {
        return currentTurn.gamePlayer.getSummonedCards();
    }

    public ArrayList<Card> getCurrentOpponentBoardCards() {
        return getOppositeSide(currentTurn).gamePlayer.getSummonedCards();
    }

    //***********************//
    public void drawHandAgain(String cardName) throws PlayException {
        currentTurn.gamePlayer.drawHandAgain(cardName);
    }


    //////////////
    //////////////
    public Card getCardFromHand(String playerSelectedCard) {
        return currentTurn.gamePlayer.getCardFromHand(playerSelectedCard);
    }

    public Card getSummonedCard(String playerSelectedCard) {
        return currentTurn.gamePlayer.getSummonedCard(playerSelectedCard);
    }

    public void purchaseCard(Card card) throws PlayException {
        currentTurn.gamePlayer.purchaseCard(card);
    }

    public void purchaseMinionOrBeast(Card card, int index) throws PlayException {
        currentTurn.gamePlayer.purchaseMinionOrBeast(card, index);
    }


    /////////////
    /////////////
    public void changeTurns() {
        if (currentTurn == Players.ME) {
            setCurrentTurn(Players.OPPONENT);
        } else {
            setCurrentTurn(Players.ME);
        }
        updateHand();
    }

    private void updateHand() {
        currentTurn.gamePlayer.updateHand();
    }

    private void startFrom(Players player) {
        currentTurn = player;
        currentTurn.gamePlayer.newTurn();
    }

    private void setCurrentTurn(Players opponent) {
        currentTurn.gamePlayer.endTurn();
        currentTurn = opponent;
        currentTurn.gamePlayer.newTurn();
    }

    private Players getOppositeSide(Players player) {
        if (player == Players.ME) return Players.OPPONENT;
        return Players.ME;
    }

    public String getPlayingSide() {
        if (currentTurn.equals(Players.ME)) return "you";
        else return "opponent";
    }

    ////////////////
    ////////////////

    public void attackToHero(Card attackingCard) throws PlayException {
        if (currentTurn.gamePlayer.cardIsAvailableInThisTurn(attackingCard)) {
            switch (attackingCard.getType()) {
                case WEAPON:
                    damageHero(((Weapon) attackingCard).getAttack());
                    break;
                case MINION:
                    damageHero(((Minion) attackingCard).getAttack());
                    break;
                case BEAST:
                    damageHero(((Beast) attackingCard).getAttack());
                    break;
            }

            currentTurn.gamePlayer.cardPlayedInThisTurn(attackingCard);
        } else throw new PlayException("you can't play with this card in this turn");
    }

    private void damageHero(int attack) {
        getOppositeSide(currentTurn).gamePlayer.damageHero(attack);
    }

    public void attackToBoardCard(Card defenderMinion, Card attackingCard) throws PlayException {
        if (currentTurn.gamePlayer.cardIsAvailableInThisTurn(attackingCard)) {
            switch (attackingCard.getType()) {
                case WEAPON:
                    damageMinion((Minion) defenderMinion, ((Weapon) attackingCard).getAttack());
                    break;
                case MINION:
                    damageMinion((Minion) defenderMinion, ((Minion) attackingCard).getAttack());
                    break;
                case BEAST:
                    damageMinion((Beast) defenderMinion, ((Beast) attackingCard).getAttack());
                    break;
            }

            currentTurn.gamePlayer.cardPlayedInThisTurn(attackingCard);
        } else throw new PlayException("you can't play with this card in this turn");
    }

    void damageMinion(Minion defenderMinion, int attack) {
        getOppositeSide(currentTurn).gamePlayer.damageMinion(defenderMinion, attack);
    }

}
