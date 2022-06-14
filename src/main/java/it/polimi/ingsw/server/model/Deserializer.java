package it.polimi.ingsw.server.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import it.polimi.ingsw.network.messages.CharacterRequest;
import it.polimi.ingsw.network.messages.clienttoserver.events.ChooseIslandResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseColourRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseIslandRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.MoveStudentFromCardRequest;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.server.model.cards.characters.CharacterCard;
import it.polimi.ingsw.server.model.cards.characters.CharacterEffect;
import it.polimi.ingsw.server.model.game.GameSettings;
import it.polimi.ingsw.shared.jsonutils.JsonUtility;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map;

public class Deserializer extends JsonUtility{

    public GameSettings getSettings(Integer numberOfPlayers) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(Objects.requireNonNull(Deserializer.class.getResource("/config/gameSettingsConfig.json")).getFile());
        List<GameSettings> settings = new ArrayList<>();
        try{
             settings = objectMapper.readValue(file, new TypeReference<>() {});
        }catch (IOException exception){
            System.out.print("Error in reading gameSettings");
        }
        Map<Integer,GameSettings> settingsMap =  settings.stream()
                .collect(Collectors.toMap(GameSettings::getNumberOfPlayers, Function.identity()));
        return settingsMap.get(numberOfPlayers);
    }

    public Map<Integer, AssistantCard> getAssistantDeck() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(Objects.requireNonNull(Deserializer.class.getResource("/config/assistantCardConfig.json")).getFile());
        List<AssistantCard> assistantList = objectMapper.readValue(file, new TypeReference<List<AssistantCard>>() {});
        Map<Integer,AssistantCard> assistantDeck =  assistantList.stream()
                .collect(Collectors.toMap(AssistantCard::getId, Function.identity()));
        return assistantDeck;
    }

    public Map<Integer, CharacterCard> getCharacters() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerSubtypes(new NamedType(CharacterRequest.class,"CharacterRequest"));
        objectMapper.registerSubtypes(new NamedType(CharacterEffect.class,"CharacterEffect"));
        objectMapper.registerSubtypes(new NamedType(MoveStudentFromCardRequest.class,"MoveStudentFromCardRequest"));
        objectMapper.registerSubtypes(new NamedType(ChooseColourRequest.class,"ChooseColourRequest"));
        objectMapper.registerSubtypes(new NamedType(ChooseIslandRequest.class,"ChooseIslandRequest"));

        File file = new File(Objects.requireNonNull(Deserializer.class.getResource("/config/characterCardsConfig.json")).getFile());
        List<CharacterCard> characterCardList = objectMapper.readValue(file, new TypeReference<List<CharacterCard>>() {});
        Map<Integer,CharacterCard> characterDeck =  characterCardList.stream()
                .collect(Collectors.toMap(CharacterCard::getId, Function.identity()));
        return characterDeck;
    }

//    public Map<Integer, CharacterCard> getCharacters() throws IOException {
//    }

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
