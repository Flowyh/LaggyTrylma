package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.common.rules.MovementRulesInterface;

import java.io.IOException;

public class RulesJSONSerializer extends StdSerializer<MovementRulesInterface> {
    protected RulesJSONSerializer() { this(null); }
    protected RulesJSONSerializer(Class<MovementRulesInterface> t) {
        super(t);
    }

    @Override
    public void serialize(MovementRulesInterface rule, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", rule.getClass().getName());
        jsonGenerator.writeEndObject();
    }
}

