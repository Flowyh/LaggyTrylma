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

public class RulesJSONDeserializer extends StdDeserializer<RuleInterface> {

    public RulesJSONDeserializer() { this(null); }
    public RulesJSONDeserializer(Class<RulesJSONDeserializer> t) {
        super(t);
    }

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
