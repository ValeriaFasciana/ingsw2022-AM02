package it.polimi.ingsw.server.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import it.polimi.ingsw.network.messages.CharacterRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseColourRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseIslandRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.MoveStudentFromCardRequest;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.server.model.cards.characters.CharacterCard;
import it.polimi.ingsw.server.model.cards.characters.CharacterEffect;
import it.polimi.ingsw.server.model.game.GameSettings;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map;

public class Deserializer{

    public GameSettings getSettings(Integer numberOfPlayers) {
        ObjectMapper objectMapper = new ObjectMapper();

        try{
        List<GameSettings> settings = objectMapper.readerFor(new TypeReference<List<GameSettings>>() {}).readValue(this.getClass().getResourceAsStream("/config/gameSettingsConfig.json"));
            Map<Integer,GameSettings> settingsMap =  settings.stream()
                    .collect(Collectors.toMap(GameSettings::getNumberOfPlayers, Function.identity()));
            return settingsMap.get(numberOfPlayers);
        }catch (IOException exception){
            System.out.print("Error in reading gameSettings\n "+ exception.getMessage());
        }
        return null;
    }

    public Map<Integer, AssistantCard> getAssistantDeck(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<AssistantCard> assistantList = objectMapper.readerFor(new TypeReference<List<AssistantCard>>() {
            }).readValue(this.getClass().getResourceAsStream("/config/assistantCardConfig.json"));
            Map<Integer, AssistantCard> assistantDeck = assistantList.stream()
                    .collect(Collectors.toMap(AssistantCard::getId, Function.identity()));
            return assistantDeck;
        }catch (IOException exception){
            System.out.print("Error in reading assistantDeck\n "+ exception.getMessage());
        }
        return Collections.emptyMap();
    }

    public Map<Integer, CharacterCard> getCharacters() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerSubtypes(new NamedType(CharacterRequest.class,"CharacterRequest"));
        objectMapper.registerSubtypes(new NamedType(CharacterEffect.class,"CharacterEffect"));
        objectMapper.registerSubtypes(new NamedType(MoveStudentFromCardRequest.class,"MoveStudentFromCardRequest"));
        objectMapper.registerSubtypes(new NamedType(ChooseColourRequest.class,"ChooseColourRequest"));
        objectMapper.registerSubtypes(new NamedType(ChooseIslandRequest.class,"ChooseIslandRequest"));

        List<CharacterCard> characterCardList = objectMapper.readerFor(new TypeReference<List<CharacterCard>>() {
        }).readValue(this.getClass().getResourceAsStream("/config/characterCardsConfig.json"));


        Map<Integer,CharacterCard> characterDeck =  characterCardList.stream()
                .collect(Collectors.toMap(CharacterCard::getId, Function.identity()));
        return characterDeck;
    }

}
