package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.common.rules.RuleInterface;

import java.io.IOException;

public class RulesJSONSerializer extends StdSerializer<RuleInterface> {
    protected RulesJSONSerializer() { this(null); }
    protected RulesJSONSerializer(Class<RuleInterface> t) {
        super(t);
    }

    @Override
    public void serialize(RuleInterface rule, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", rule.getClass().getName());
        jsonGenerator.writeEndObject();
    }
}

