package edu.sharif.student.bluesoheil.ap98.hearthstone.connectors;

import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.GameController;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play.*;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.interefaces.PlayActionListener;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Minion;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Weapon;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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

    public void replaceCard(String cardName) throws PlayException {
        GameController.getInstance().drawHandAgain(cardName);
    }


    public ArrayList<String> getEvents() {
        return Logger.getEventLogs();
    }

    public PlayerPanel getPlayerPanel() {
        return new PlayerPanel(gameController.getPlayerHero(),
                gameController.getHeroStates().get("HP"), gameController.getHeroStates().get("MANA"));
    }

    public PlayerPanel getOpponentPanel() {
        return new PlayerPanel(gameController.getOpponentHero(),
                gameController.getOpponentHP(), gameController.getInitialOpponentMana());
    }

    public ArrayList<CardShape> getHand() {
        return administer.getCardShapes(GameController.getInstance().getPlayerHand());
    }

    public HashMap<String, Integer> getHeroStates() {
        return gameController.getHeroStates();
    }

    public Card.CardType getCardType(String playerSelectedCard) {
        return gameController.getCard(playerSelectedCard).getType();
    }

    public MinionActualCard summonAndGetMinion(String playerSelectedCardInHand) {
        //todo check mana
        Minion minion = (Minion) gameController.getCard(playerSelectedCardInHand);
        gameController.removeCard(minion);
        Logger.log(LogTypes.PLAY , gameController.getPlayingSide()+" summoned "+playerSelectedCardInHand);
        return new MinionActualCard( minion,null);
    }

    public WeaponActualCard summonAndGetWeapon(String playerSelectedCardInHand) {
        //todo check mana
        Weapon weapon = (Weapon) gameController.getCard(playerSelectedCardInHand);
        gameController.removeCard(weapon);
        Logger.log(LogTypes.PLAY , gameController.getPlayingSide()+" set weapon to "+playerSelectedCardInHand);
        return new WeaponActualCard( weapon,null);
    }

    public void playSpell(String playerSelectedCardInHand) {
        //todo check mana
        gameController.removeCard(gameController.getCard(playerSelectedCardInHand));
        Logger.log(LogTypes.PLAY , gameController.getPlayingSide()+" played the "+playerSelectedCardInHand + " spell");
    }

    public void playQuestAndReward(String playerSelectedCardInHand) {
        System.out.println("Q&R is played: "+playerSelectedCardInHand);
        //todo check mana
    }

    public void getCard(CardShape playerSelectedCard) {
//        Card card = gameController.getHandCard(playerSelectedCard.ca);
//        return new CardShape(card);
    }

    public void playHandCard(String cardName) {
//        gameController.d
    }

    public void playCard(ActualCard playerSelectedCard) {
    }

    //
    ///
    ////other methods
    ///
    //

}
