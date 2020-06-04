package edu.sharif.student.bluesoheil.ap98.hearthstone.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.sharif.student.bluesoheil.ap98.hearthstone.exceptions.PlayerControllerException;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Deck;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.cards.Card;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration.LogicConfigs.LogicConstants;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.LogTypes;
import edu.sharif.student.bluesoheil.ap98.hearthstone.util.log.Logger;
import edu.sharif.student.bluesoheil.ap98.hearthstone.models.Player;

import java.io.*;
import java.util.ArrayList;

public class PlayerController {

    private static PlayerController playerController;
    private static String profilesPath = LogicConstants.getInstance().getProfilesPath();

    private Player currentPlayer;

    private PlayerController() {
    }

    /*
     **** getters and setters
     */
    public static PlayerController getInstance() {
        if (playerController != null) return playerController;
        playerController = new PlayerController();
        return playerController;
    }

    public static void reset() {
        playerController = null;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getPlayerCoins() {
        return currentPlayer.getCoins();
    }

    public void setPlayerCoins(int i) {
        currentPlayer.setCoins(i);
    }

    public void setPlayerCards(ArrayList<Card> playerTotalCards) {
        currentPlayer.setPlayerTotalCards(playerTotalCards);
    }

    public ArrayList<Deck> getPlayerDecks() {
        return currentPlayer.getPlayerDecks();
    }


    /*
     **** Methods
     */
    public void signUp(String username, String password) throws PlayerControllerException {
        if (new File(profilesPath + "/" + username + ".json").exists()) {
            throw new PlayerControllerException("USERNAME IS INVALID");
        } else {
            Player player = new Player(username, password);
            saveData(player);
            Logger.createLogFile(player);
        }
    }

    public void login(String username, String password) throws PlayerControllerException {
        if (allPlayersContain(username, password)) {
            setCurrentPlayer(getPlayer(username));
            CardController.getInstance().loadPlayerCards();
            DeckController.getInstance().loadPlayerDecks();

        } else {
            throw new PlayerControllerException("username or password is incorrect");
        }
    }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public void deletePlayer(String password) throws PlayerControllerException {
        if (password.equals(currentPlayer.getPassword())) {
            File file = new File(currentPlayer.getProfilePath());
            if (!file.delete()) {
                throw new PlayerControllerException(" Deleting failed ");
            } else {     //when deleting is done successfully
                Logger.log(LogTypes.PLAYER, "successfully deleted.");
                Logger.closeLogfile(); //according to finalizeLogfile method we should first close logFile normally and then finalize log
                Logger.finalizeLogfile(currentPlayer);
            }
        } else {
            throw new PlayerControllerException(" Wrong password ");
        }
    }

    public void logOut() {
        saveData(currentPlayer);
        Logger.log(LogTypes.PLAYER, currentPlayer.getUserName() + " logged out");
        Logger.closeLogfile();
    }

    private boolean allPlayersContain(String username, String password) {
        boolean ans = false;

        try {
            JsonParser jsonParser = new JsonParser();

            FileReader reader = new FileReader(LogicConstants.getInstance().getProfilesPath() + "/" + username + ".json");
            // if this file doesn't exist, we handle the exception with no message and we print an error in signUP panel instead
            // , because this method is designed to give us true or false and exception is not expected from allPlayersContain
            JsonObject json = (JsonObject) jsonParser.parse(reader);
            reader.close();

            String playerRealPassword = json.get("password").toString();   //it gives us a string with additional quotation marks
            playerRealPassword = playerRealPassword.substring(1, playerRealPassword.length() - 1);  //we remove extra quotation marks
            if (playerRealPassword.equals(password)) {
                ans = true;
            }
            // if passwords don't match, we handle the exception with no message and we print an error in CLIRunner instead
            // , because this method is designed to give us true or false and "exception" is not expected from allPlayersContain

        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ans;
    }

    private Player getPlayer(String username) {
        Player player = null;
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        try {
            FileReader reader = new FileReader(profilesPath + "/" + username + ".json");
            player = gson.fromJson(jsonParser.parse(reader), Player.class); //here we make a json element and then turn it into a Player object
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return player;
    }

    private void saveData(Player player) {
        try {
            FileWriter writer = new FileWriter(player.getProfilePath());
            PrintWriter printer = new PrintWriter(writer);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(player);
            printer.println(json);
            printer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
