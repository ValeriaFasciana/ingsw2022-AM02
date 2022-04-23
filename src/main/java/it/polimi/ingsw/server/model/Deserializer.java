package it.polimi.ingsw.server.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.action.ActionType;
import it.polimi.ingsw.server.model.action.MovementAction;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

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
        GameSettings settings = new GameSettings(numberOfIslands,numberOfClouds,numberOfTowersForPlayer,studentsInEntrance,studentsInClouds,numberOfStudentsToMove);
        return settings;
    }

    private static int getSettingName(JsonObject fileSettings, String name){
        return fileSettings.get(name) == null ? 0 : fileSettings.get(name).getAsInt();
    }

//    public EnumMap<Phase,Action> getDefaultActions() throws IOException {
//        URL io = Deserializer.class.getResource("/config/defaultActionsConfig.json");
//        InputStream inputStream = io.openStream();
//        String file = new String(inputStream.readAllBytes());
//        Gson gson = new Gson();
//        JsonObject jsonObj = gson.fromJson( file, JsonObject.class);
//        EnumMap<Phase,Action> defaultActionsMap = new EnumMap<Phase,Action>(Phase.class);
//        for(Phase phase : Phase.values()){
//            JsonArray jsonActionsArray = jsonObj.get(phase.toString()).getAsJsonArray();
//            for (int i = 0; i <jsonActionsArray.size(); i++) {
//                JsonObject obj= (JsonObject) jsonActionsArray.get(i);
//                ActionType actionType = ActionType.valueOf(String.valueOf(obj.get("type")));
//                Action newAction;
//                switch (actionType){
//                    case MOVE -> newAction = new MovementAction();
//                }
//                String videoUrl=obj.get("VideoUrl");
//                String title=obj.get("title");
//                String description=obj.get("description");
////                System.out.println("videoId="+videoId   +"videoUrl="+videoUrl+"title=title"+"description="+description);
//            }
//
//        }
//    }
}
