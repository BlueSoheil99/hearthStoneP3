package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.PlayLogicConfig;

import java.util.ArrayList;
import java.util.HashMap;

public class GameController {
    private static GameController instance;

    private PlayLogicConfig properties;
    private DeckController deckController;
    private boolean playerIsWinner = false;
    private Players currentPlayer;

    public enum Players {
        ME,
        OPPONENT;
        private GamePlayer gamePlayer;
    }

    private GameController() {
        properties = PlayLogicConfig.getInstance();
        deckController = DeckController.getInstance();
        setPlayer();
        currentPlayer = Players.ME;
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
    }

    //***********************//
    private GamePlayer getPlayer(boolean isME) {
        if (isME) return Players.ME.gamePlayer;
        else return Players.OPPONENT.gamePlayer;
    }

    //***********************//
    public void setPassiveForUser(String passiveName) {
        Players.ME.gamePlayer.setPassive(passiveName);
    }

    //***********************//
    public HeroTypes getPlayerHero() {
        return currentPlayer.gamePlayer.getHeroType();
    }

    public HeroTypes getPlayerHero(boolean isMe) {
        return getPlayer(isMe).getHeroType();
    }

    //***********************//
    public HashMap<String, Integer> getHeroStates() {
        return currentPlayer.gamePlayer.getHeroStates();
    }

    public HashMap<String, Integer> getHeroStates(boolean isMe) {
        return getPlayer(isMe).getHeroStates();
    }

    //***********************//
    public ArrayList<Card> getPlayerHand(boolean isMe) {
        return getPlayer(isMe).getHand();
    }

    public ArrayList<Card> getPlayerHand() {
        return currentPlayer.gamePlayer.getHand();
    }

    //***********************//

    public void drawHandAgain(String cardName) throws PlayException {
        currentPlayer.gamePlayer.drawHandAgain(cardName);
    }

    //////////////
    //////////////

    public Card getCard(String playerSelectedCard) {
        return currentPlayer.gamePlayer.getCard(playerSelectedCard);
    }

    public void purchaseCard(Card card) throws PlayException {
        currentPlayer.gamePlayer.purchaseCard(card);
    }

    public void removeCard(Card card) {
        currentPlayer.gamePlayer.removeCard(card);
    }

    /////////////
    /////////////

    public void changeTurns() {
        if (currentPlayer == Players.ME) {
            setCurrentPlayer(Players.OPPONENT);
        } else {
            setCurrentPlayer(Players.ME);
        }
        updateHand();
    }
    private void updateHand() {
        currentPlayer.gamePlayer.updateHand();
    }

    private void setCurrentPlayer(Players opponent) {
        currentPlayer.gamePlayer.endTurn();
        currentPlayer = opponent;
        currentPlayer.gamePlayer.newTurn();
    }

    public String getPlayingSide() {
        if (currentPlayer.equals(Players.ME)) return "you";
        else return "opponent";
    }

}
