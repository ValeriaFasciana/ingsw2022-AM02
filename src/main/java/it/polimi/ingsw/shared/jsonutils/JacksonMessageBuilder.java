package it.polimi.ingsw.shared.jsonutils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import it.polimi.ingsw.network.messages.CharacterRequest;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseColourRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseIslandRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.MoveStudentFromCardRequest;
import it.polimi.ingsw.server.model.cards.characters.CharacterEffect;

import java.io.IOException;

/**
 * Used to parse from message to string and vice versa
 */

public class JacksonMessageBuilder {
    private final ObjectMapper objectMapper;
    private final ObjectReader objectReader;

    public JacksonMessageBuilder() {
        objectMapper = new ObjectMapper();
        objectMapper.registerSubtypes(new NamedType(CharacterRequest.class,"CharacterRequest"));
        objectMapper.registerSubtypes(new NamedType(MoveStudentFromCardRequest.class,"MoveStudentFromCardRequest"));
        objectMapper.registerSubtypes(new NamedType(ChooseColourRequest.class,"ChooseColourRequest"));
        objectMapper.registerSubtypes(new NamedType(ChooseIslandRequest.class,"ChooseIslandRequest"));
        objectReader = objectMapper.readerFor(Message.class);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }

    public String fromMessageToString(Message message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            System.out.println("Cannot serialize message: "+e.getMessage());
            return ""; //this shouldn't happen
        }
    }

    public Message fromStringToMessage(String jsonString) throws IOException {
        try {
            return objectReader.readValue(jsonString);
        } catch (JsonProcessingException e) {
            System.out.println("Cannot deserialize message"+e.getMessage());
            return null;
        }
    }

    }


