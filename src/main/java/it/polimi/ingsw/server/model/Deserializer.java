package it.polimi.ingsw.server.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Deserializer {

    public GameSettings getSettings(Integer numberOfPlayers) throws IOException {
        URL io = Deserializer.class.getResource("/config/gameSettingsConfig.json");
        InputStream inputStream = io.openStream();
        String file = new String(inputStream.readAllBytes());
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson( file, JsonObject.class);
        JsonObject fileSettings = jsonObj.get(numberOfPlayers.toString()).getAsJsonObject();
        int numberOfIslands = getSettingName(fileSettings,"numberOfIslands");
        int numberOfClouds =  getSettingName(fileSettings,"numberOfClouds");
        int numberOfTowersForPlayer =  getSettingName(fileSettings,"numberOfTowersForPlayer");
        int studentsInEntrance =  getSettingName(fileSettings,"studentsInEntrance");
        int studentsInClouds =  getSettingName(fileSettings,"studentsInClouds");
        int numberOfStudentsToMove =  getSettingName(fileSettings,"numberOfStudentsToMove");
        GameSettings settings = new     GameSettings(numberOfIslands,numberOfClouds,numberOfTowersForPlayer,studentsInEntrance,studentsInClouds,numberOfStudentsToMove);
        return settings;
    }

    private static int getSettingName(JsonObject fileSettings, String name){
        return fileSettings.get(name) == null ? 0 : fileSettings.get(name).getAsInt();
    }
}
