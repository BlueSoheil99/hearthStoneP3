package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.CardControllerException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.*;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.LogicConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.CardConfig;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;


public class CardController {

    private static CardController instance;
    private static String cardsPath = LogicConstants.getInstance().getCardsPath();
    private static String cardsImagesPath = LogicConstants.getInstance().getCardsImagesPath();

    private PlayerController playerController;
    private CardConfig properties;

    private HashMap<String, Card> gameTotalCards;
    private HashMap<String, BufferedImage> cardsAndImagesMap;
    private ArrayList<Card> playerTotalCards;

    private CardController() {
        playerController = PlayerController.getInstance();
        properties = CardConfig.getInstance();
        initGameTotalCards();
    }

    /*
    ******* Getters and Setters
     */
    public static CardController getInstance() {
        if (instance == null) {
            instance = new CardController();
        }
        return instance;
    }
    public static void reset(){
        instance = null;
    }

    public HashMap<String, Card> getGameTotalCards() {
        return gameTotalCards;
    }

    public HashMap<String, BufferedImage> getCardsAndImagesMap() {
        return cardsAndImagesMap;
    }

    public ArrayList<Card> getPlayerTotalCards() {
        return playerTotalCards;
    }


    /*
    ****** Methods
     */
    public void initGameTotalCards() {
        Card card;
        gameTotalCards = new HashMap<String, Card>();
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        List spells = Arrays.asList(properties.getSpellCards());
        List minions =Arrays.asList( properties.getMinionCards());
        List beasts =Arrays.asList( properties.getBeastCards());
        List weapons = Arrays.asList(properties.getWeaponCards());
        List questAndRewards = Arrays.asList(properties.getQuestAndRewardCards());
        String[] allCardNames = new File(cardsPath).list();
        try {
            FileReader reader;
            for (String cardName : allCardNames) {
                cardName = cardName.substring(0, cardName.length() - 5); //because of '.json' thing we have '-5'
                reader = new FileReader(cardsPath +"/"+ cardName + ".json");

                if (spells.contains(cardName)){
                    card = gson.fromJson(jsonParser.parse(reader), Spell.class); //here we make a jsonElement and then turn it into a Card object

                } else if (weapons.contains(cardName)) {
                    card = gson.fromJson(jsonParser.parse(reader), Weapon.class);

                }else if (minions.contains(cardName)){
                    card = gson.fromJson(jsonParser.parse(reader), Minion.class); //here we make a jsonElement and then turn it into a Card object

                }else if (beasts.contains(cardName)){
                    card = gson.fromJson(jsonParser.parse(reader), Beast.class); //here we make a jsonElement and then turn it into a Card object

                }else if (questAndRewards.contains(cardName)){
                    card = gson.fromJson(jsonParser.parse(reader), QuestAndReward.class); //here we make a jsonElement and then turn it into a Card object

                }else  card = gson.fromJson(jsonParser.parse(reader), Card.class); //here we make a jsonElement and then turn it into a Card object

                gameTotalCards.put(card.getName().toUpperCase(), card);
                reader.close();
            }

            loadCardsAndImagesMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCardsAndImagesMap() {
        cardsAndImagesMap = new HashMap<>();
        String path;
        for (String cardName : gameTotalCards.keySet()) {
            path = cardsImagesPath +"/"+ cardName.toLowerCase()+".png";
            BufferedImage img = ImageLoader.loadImage(path);
            cardsAndImagesMap.put(cardName, img);
        }
    }

    public  ArrayList<Card> getDefaultCards(){ //we can't make it static because we need to have gameTotalCards initialized
        String[] defaultPlayerTotalCards = CardConfig.getInstance().getDefaultCards();
        ArrayList<Card> defaultCards = new ArrayList<>();

        for (String cardName : defaultPlayerTotalCards ) {
            cardName = cardName.toUpperCase();
            if (gameTotalCards.containsKey(cardName)){
                defaultCards.add(gameTotalCards.get(cardName));
            }
        }
        return defaultCards;
    }

    public void  loadPlayerCards(){
        //this method is called when player cards are just converted from json
        ArrayList<Card> cards = playerController.getCurrentPlayer().getPlayerTotalCards();
        playerTotalCards = cards;
        //we should revalidate our cards.
        // current cards are different from the cards in the gameTotalCards. meaning they're not the same object
        revalidateCards(playerTotalCards);
    }

    void revalidateCards(  ArrayList<Card> givenCards ){
        HashSet<String> cardSet = new HashSet<>();
        for (Card card : givenCards){
            if (cardSet.contains(card.getName())){
                givenCards.set(givenCards.indexOf(card) , copyCard(card.getName().toUpperCase()));
            }else{
                givenCards.set(givenCards.indexOf(card) , gameTotalCards.get(card.getName().toUpperCase()));
                cardSet.add(card.getName());
            }

        }
    }

    private Card copyCard(String cardName) {
        Gson gson = new Gson();
        Card card = gameTotalCards.get(cardName);
        String jsonString = gson.toJson(card);
        return gson.fromJson(jsonString , card.getClass());
    }

    public Card getCard(String cardName){
        return gameTotalCards.get(cardName.toUpperCase());
    }


    public void buyCard(String cardName) throws CardControllerException {
        cardName = cardName.toUpperCase();
        if (gameTotalCards.containsKey(cardName)) {
            Card card = gameTotalCards.get(cardName);
            int playerCoins = playerController.getPlayerCoins();
            if (playerCoins >= card.getCost()) {
                if (Collections.frequency(playerTotalCards, card) < 2) {
                    playerTotalCards.add(card);

                    playerController.setPlayerCoins(playerCoins - card.getCost());
                    playerController.setPlayerCards(playerTotalCards);//check if it's useful or not

                } else throw new CardControllerException("you can't have more than 2 cards of " + cardName);
            } else throw new CardControllerException("not enough money to buy : " + cardName);
        } else throw new CardControllerException("card( " + cardName + " ) doesn't exist");

    }

    public void sellCard(String cardName) throws CardControllerException {
        cardName=cardName.toUpperCase();
        if( gameTotalCards.containsKey(cardName) ) {
            Card card = gameTotalCards.get(cardName);

            if (playerTotalCards.contains(card)){
                ArrayList<String> decksWithThisCard = DeckController.getInstance().getDeckNamesWithCertainCard(card);
                if (decksWithThisCard == null){
                    int playerCoins = playerController.getPlayerCoins();
                    playerTotalCards.remove(card);

                    playerController.setPlayerCoins( playerCoins + card.getCost() );
                    playerController.setPlayerCards(playerTotalCards);//check if it's useful or not
                }else throw new CardControllerException("unsalable card: "+cardName+
                        " is in these decks:\t"+decksWithThisCard.toString());
            }else throw new CardControllerException("players hasn't "+cardName+" card");
        }else throw new CardControllerException("card( "+cardName + " ) doesn't exist");
    }

    public int getCardCost(String selectedCard) {
        int cost = 0;
        Card card = gameTotalCards.get(selectedCard.toUpperCase());
        if (card != null) cost = card.getCost();
        return cost;
    }
}
