package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.laggytrylma.common.rules.RuleInterface;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Custom RuleInterface.class JSON deserializer.
 */
public class RulesJSONDeserializer extends StdDeserializer<RuleInterface> {
    /**
     * Empty constructor (Jackson requires this).
     */
    public RulesJSONDeserializer() { this(null); }
    /**
     * Class constructor, calls StdSerializer constructor.
     * @param t RuleInterface
     */
    public RulesJSONDeserializer(Class<RulesJSONDeserializer> t) {
        super(t);
    }

    /**
     * Deserialize RuleInterface JSON String into RuleInterface object using Jackson custom deserializer.
     * @param jsonParser JsonParser
     * @param deserializationContext DeserializationContext
     * @return deserialized RuleInterface object
     * @throws IOException something bad happened.
     */
    @Override
    public RuleInterface deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode root = jsonParser.readValueAsTree();
        String name = root.get("name").asText();
        Pattern pattern = Pattern.compile("^com.laggytrylma.common.rules.[a-zA-Z]*$");
        Matcher matcher = pattern.matcher(name);
        if(!matcher.find()) return null;
        try {
            Class<?> ruleClass = Class.forName(name);
            return (RuleInterface) ruleClass.getDeclaredConstructor().newInstance();
        } catch(ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            return null;
        }
    }
}
