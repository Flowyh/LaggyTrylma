package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.laggytrylma.common.FarMovement;
import com.laggytrylma.common.Game;
import com.laggytrylma.common.MovementRulesInterface;
import com.laggytrylma.common.NearMovement;
import com.laggytrylma.utils.communication.commandwrappers.BaseCommandWrapper;

import java.io.IOException;
import java.util.Iterator;

public class RulesJSONDeserializer extends StdDeserializer<MovementRulesInterface> {

    public RulesJSONDeserializer() { this(null); }
    public RulesJSONDeserializer(Class<RulesJSONDeserializer> t) {
        super(t);
    }

    @Override
    public MovementRulesInterface deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode root = jsonParser.readValueAsTree();
        String name = root.get("name").asText();

        // TODO: automate this
        if(name.equals(NearMovement.class.getName()))
            return new NearMovement();
        if(name.equals(FarMovement.class.getName()))
            return new FarMovement();

        return null;
    }
}
