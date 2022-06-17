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

}
