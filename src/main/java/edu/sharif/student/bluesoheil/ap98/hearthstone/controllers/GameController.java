package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Deck;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.Hero;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives.InfoPassive;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Player;

public class GameController {
    private InfoPassive passive;
    private PlayerController playerController;
    private Player player1, player2;
    private Deck deck1, deck2;
    private Hero hero1 , hero2;
    public GameController(){
        playerController = PlayerController.getInstance();
        player1 = playerController.getCurrentPlayer();
        setPlayerProperties();
    }

    public void setPassive(String objName) {
        passive = InfoPassive.valueOf(objName.toUpperCase());
    }

    private void setPlayerProperties() {
        deck1 = DeckController.getInstance().getCurrentDeck();
        hero1 = deck1.getHero().getHero();
    }

}
