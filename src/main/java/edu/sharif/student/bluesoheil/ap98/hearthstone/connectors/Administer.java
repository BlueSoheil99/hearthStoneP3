package edu.sharif.student.bluesoheil.ap98.hearthstone.connectors;

import edu.sharif.student.bluesoheil.ap98.hearthstone.Main;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.CardControllerException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.DeckControllerException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayerControllerException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.*;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.collection.CollectionPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play.OpponentSelectionPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play.PlayStarterPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.play.PlayPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.smallItems.CardShape;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.starter.LoginPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.gui.starter.SignUpPanel;
import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.*;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Deck;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Heroes.HeroTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.InfoPassives.InfoPassive;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Player;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Administer {
    private static Administer administer;
    private MainFrame mainFrame;
    private ArrayList<GamePanel> recentPanels;
    private PlayerController playerController;
    private CardController cardController;
    private DeckController deckController;

    private Administer() {
        mainFrame = new MainFrame();
        recentPanels = new ArrayList<>();
        playerController = PlayerController.getInstance();
        cardController = CardController.getInstance();
        deckController = DeckController.getInstance();
    }

    public static Administer getInstance() {
        if (administer == null) {
            administer = new Administer();
        }
        return administer;
    }


    public Player getCurrentPlayer() {
        return playerController.getCurrentPlayer();
    }

    /*
     **
     ***
     * GUI Managing
     ***
     **
     */

    public void runLogin() {
        LoginPanel loginPanel = new LoginPanel();
        mainFrame.initFrame(loginPanel);
    }

    public void runSignUp() {
        SignUpPanel signUpPanel = new SignUpPanel();
        mainFrame.initFrame(signUpPanel);
    }

    public void runMenu() {
        MenuPanel menuPanel = new MenuPanel();
        recentPanels.add(menuPanel);
        mainFrame.initFrame(menuPanel);
    }

    public void runPlay() {
        if (DeckController.getInstance().getCurrentDeck() == null) {
            JOptionPane.showMessageDialog(null, "CurrentDeck is not set.\nChoose one deck as your Current one...",
                    "current deck is not available", JOptionPane.ERROR_MESSAGE);
            runCollection();
        } else {
            Logger.log(LogTypes.NAVIGATION, "To PlayStarter");
            GameController.setNewGame();
            Logger.restartRecording();
            GameController gameController = GameController.getInstance();
            PlayStarterPanel playStarterPanel = new PlayStarterPanel();
            playStarterPanel.setClickListener(objName -> {
                gameController.setPassiveForUser(objName);
                selectOpponent(gameController);
            });
            mainFrame.initFrame(playStarterPanel);
        }
    }

    private void selectOpponent(GameController gameController) {
        OpponentSelectionPanel opponentSelectionPanel = new OpponentSelectionPanel();
        opponentSelectionPanel.setClickListener(objName -> {
            Logger.log(LogTypes.PLAY , objName+" is selected as opponent");
            gameController.setOpponent(objName);
            startGame(gameController);
        });

        mainFrame.initFrame(opponentSelectionPanel);
    }

    private void startGame(GameController gameController) {
        Logger.log(LogTypes.NAVIGATION, "To Play");
        PlayHandler.setNewHandler(gameController);
        PlayPanel playPanel = new PlayPanel();
        recentPanels.add(playPanel);
        mainFrame.initFrame(playPanel);
    }


    public void runShop() {
        ShopPanel shopPanel = new ShopPanel();
        recentPanels.add(shopPanel);
        Logger.log(LogTypes.NAVIGATION, "To Shop");
        mainFrame.initFrame(shopPanel);
    }

    public void runStatus() {
        StatusPanel statusPanel = new StatusPanel();
        recentPanels.add(statusPanel);
        Logger.log(LogTypes.NAVIGATION, "To Status");
        mainFrame.initFrame(statusPanel);
    }

    public void runCollection() {
        CollectionPanel collectionPanel = new CollectionPanel();
        recentPanels.add(collectionPanel);
        Logger.log(LogTypes.NAVIGATION, "To Collection");
        mainFrame.initFrame(collectionPanel);
    }

    public void runSetting() {
        SettingPanel settingPanel = new SettingPanel();
        recentPanels.add(settingPanel);
        Logger.log(LogTypes.NAVIGATION, "To Setting");
        mainFrame.initFrame(settingPanel);
    }

    public void runExit() {
//      i've put confirmDialog here so I didn't duplicate code in exitButton class and exitBtn in menuPanel
        int ans = JOptionPane.showConfirmDialog(null, "Are You Sure You Want to Exit?",
                "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            playerController.logOut();
            System.exit(0);
        }
    }

    public void back() {
        Logger.log(LogTypes.NAVIGATION, "Back to previous panel");
        GamePanel lastPanel = getPreviousPanel();
        if (lastPanel.getClass() == CollectionPanel.class) lastPanel = new CollectionPanel();
        // todo line above is because when we get back to collection in shop, navigation panel disappears!/ find the reason and fix that instead of lines like line above
        mainFrame.initFrame(lastPanel);
    }

    public GamePanel getPreviousPanel() {
        GamePanel previousPanel;
        if (recentPanels.size() > 1)
            recentPanels.remove(recentPanels.size() - 1); //i don't know how to explain! it's like closing the last panel...
        // ... if we don't do this we will have logical errors.....if statement is for when we get back from passivePanel to menu
        previousPanel = recentPanels.get(recentPanels.size() - 1);
        previousPanel.repaint();
        return previousPanel;
    }

    public void runLogout() {
        playerController.logOut();
        PlayerController.reset();
        CardController.reset();
        DeckController.reset();
        playerController = PlayerController.getInstance();
        deckController = DeckController.getInstance();
        cardController = CardController.getInstance();
        Main.main(null);
    }

    public void deletePlayer(String password) throws PlayerControllerException {
        playerController.deletePlayer(password);
        JOptionPane.showMessageDialog(null, "You deleted your profile successfully");
        System.exit(0);
    }

    /*
     **
     ****
     * Logic Managing
     ****
     **
     */

    /////////////////
    //////start//////
    public void signUp(String username, String password) throws PlayerControllerException {
        playerController.signUp(username, password);
        //maybe you'd better create log file here , not in the controller
        // but i'm won't do it because i can't refer to the new player in  Logger.createLogFile(player); method.
    }

    public void login(String username, String password) throws PlayerControllerException {
        playerController.login(username, password);
        Logger.initLogger(PlayerController.getInstance().getCurrentPlayer());
        Logger.log(LogTypes.PLAYER, PlayerController.getInstance().getCurrentPlayer().getUserName() + " logged in");

    }

    //////////////////////
    ////////common/////////
    public ArrayList<CardShape> getAllShapesOfCards() {
        ArrayList<CardShape> cardShapes = new ArrayList<>();
        for (Map.Entry<String, BufferedImage> entry : cardController.getCardsAndImagesMap().entrySet()) {
            cardShapes.add(new CardShape(entry.getKey(), entry.getValue(), true));
        }
        return cardShapes;
    }

    public ArrayList<CardShape> getCardShapes(ArrayList<Card> cards) {
        ArrayList<CardShape> allCards = getAllShapesOfCards();
        ArrayList<CardShape> cardShapes = new ArrayList<>();
        for (Card card : cards) {
            for (CardShape shape : allCards) {
                if (shape.getCardName().equals(card.getName().toUpperCase())) {
                    if (!cardShapes.contains(shape)) {
                        cardShapes.add(shape);
                    } else {
                        cardShapes.add(CardShape.copyCardShape(shape));
                        //by the line above line we're able to see repeated cards
                    }
                }
            }
        }
        return cardShapes;
    }

    //////////////////
    //////shop////////
    public int getPlayerCoins() {
        return playerController.getPlayerCoins();
    }

    public int getCardCost(String selectedCard) {
        return cardController.getCardCost(selectedCard);
    }

    public void buyCard(String cardName) throws CardControllerException {
        cardController.buyCard(cardName);
    }

    public void sellCard(String cardName) throws CardControllerException {
        cardController.sellCard(cardName);
    }

    //////////////////
    /////status///////
    public LinkedHashMap<String, String> getPlayerDecks(int numberOfDecks) {
        LinkedHashMap<String, String> decksInfo = new LinkedHashMap<>();
        int x = 0;
        ArrayList<Deck> playerDecks = deckController.getPlayerDecks();
        for (Deck deck : playerDecks) {
            if (x < numberOfDecks) {
                decksInfo.put(deck.getName(), deck.getHeroType().toString());
                x++;
            }
        }
        return decksInfo;
    }

    public String[] getDeckState(String deckName) {
        return deckController.getDeckStates(deckName);
    }

    /////////////////////////
    ///////collection////////
    public LinkedHashMap<String, String> getPlayerDecks() {
        // returns *ALL* decks of player
        return getPlayerDecks(deckController.getPlayerDecks().size());
    }

    public ArrayList<CardShape> getDeckCards(String deckName) {
        return getCardShapes(deckController.getDeck(deckName).getCards());
    }

    public void createDeck(String deckName, String hero) throws DeckControllerException {
        switch (hero.toUpperCase()) {
            case ("MAGE"):
                deckController.createDeck(deckName, HeroTypes.MAGE);
                break;
            case ("WARLOCK"):
                deckController.createDeck(deckName, HeroTypes.WARLOCK);
                break;
            case ("ROGUE"):
                deckController.createDeck(deckName, HeroTypes.ROGUE);
                break;
            case ("HUNTER"):
                deckController.createDeck(deckName, HeroTypes.HUNTER);
                break;
            case ("PRIEST"):
                deckController.createDeck(deckName, HeroTypes.PRIEST);
                break;
            default:
                throw new DeckControllerException("You Entered a wrong Hero");
        }
    }

    public void addCardToDeck(String selectedDeck, String selectedCard) throws DeckControllerException {
        Card card = cardController.getCard(selectedCard);
        deckController.addCard(selectedDeck, card);
    }

    public void removeCardFromDeck(String selectedDeck, String selectedCard) throws DeckControllerException {
        Card card = cardController.getCard(selectedCard);
        deckController.removeCard(selectedDeck, card);
    }

    public void renameDeck(String selectedDeck, String newName) throws DeckControllerException {
        deckController.renameDeck(selectedDeck, newName);
    }

    public void deleteDeck(String selectedDeck) {
        deckController.deleteDeck(selectedDeck);
    }

    public void setCurrentDeck(String selectedDeck) throws DeckControllerException {
        deckController.setCurrentDeck(selectedDeck);
    }

    public String getCurrentDeck() {
        if (deckController.getCurrentDeck() == null) return null;
        return deckController.getCurrentDeck().getName();
    }


    public void changeDeckHero(String selectedDeck, HeroTypes heroName) throws DeckControllerException {
        deckController.changeDeckHero(selectedDeck, heroName);
    }

    //////////////////////
    //////////play////////
    public ArrayList<CardShape> getPassives() {
        ArrayList<CardShape> passives = new ArrayList<>();
        for (InfoPassive passive : InfoPassive.values()) {
            passives.add(new CardShape(passive.getName(), passive.getDescription()));
        }
        return passives;
    }

    public LinkedHashMap<String, String> getAvailableDecks() {
        LinkedHashMap<String, String> decks = getPlayerDecks();
        LinkedHashMap<String, String> availableDecks = new LinkedHashMap<>();
        for (String deckName : decks.keySet()) {
            if (!deckName.equals(deckController.getCurrentDeck().getName())) {
                if (deckController.deckIsAvailable(deckController.getDeck(deckName))) {
                    availableDecks.put(deckName, decks.get(deckName));
                }
            }
        }
        return availableDecks;
    }
}
