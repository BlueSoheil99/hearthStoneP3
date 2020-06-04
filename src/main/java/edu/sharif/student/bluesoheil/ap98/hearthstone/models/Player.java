package edu.sharif.student.bluesoheil.ap98.hearthstone.models;

import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.CardController;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.LogicConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.controllers.DeckController;

import java.io.*;
import java.util.ArrayList;

public class Player {


    private String   userName, password;
    private int userId;
    private int coins =50;
    private String profilePath, logPath;
    private ArrayList<Card> playerTotalCards;
    private ArrayList<Deck> playerDecks;

    public Player(String newUser , String newPass ) {
        userId = new File(LogicConstants.getInstance().getLogsPath()).list().length + 1 ;
        this.userName = newUser ;
        this.password = newPass ;  // todo visit http://tutorials.jenkov.com/java-cryptography/index.html
        playerTotalCards = CardController.getInstance().getDefaultCards();
        playerDecks = DeckController.getDefaultDecks();
        profilePath = LogicConstants.getInstance().getProfilesPath()+"/"+ this.getUserName()+".json";
        logPath     = LogicConstants.getInstance().getLogsPath()+"/"+ this.getUserName()+"-"+this.getUserId()+".log";

    }

    private int getUserId(){
        return userId;
    }

    public String getUsername(){
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getLogPath(){
        return logPath;
    }

    public String getProfilePath(){
        return profilePath;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public ArrayList<Card> getPlayerTotalCards() {
        return playerTotalCards;
    }

    public ArrayList<Deck> getPlayerDecks(){ return playerDecks;    }

    public void setPlayerTotalCards(ArrayList<Card> listOfCards) {
        playerTotalCards = listOfCards;
    }

    public void setPlayerDecks(ArrayList<Deck> listOfDecks) {
        playerDecks = listOfDecks;
    }
}

