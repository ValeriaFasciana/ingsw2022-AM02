package it.polimi.ingsw.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;

//Used to parse from message to string and vice versa
public class JacksonMessageBuilder {
    private final ObjectMapper objectMapper;
    private final ObjectReader objectReader;

    public JacksonMessageBuilder() {
        objectMapper = new ObjectMapper();
        objectReader = objectMapper.readerFor(Message.class);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }

    public String fromMessageToString(Message message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            System.out.println("Cannot serialize message");
            return ""; //this shouldn't happen
        }
    }

    public Message fromStringToMessage(String jsonString) throws IOException {
        return objectReader.readValue(jsonString);
    }
}

