package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.common.rules.RuleInterface;

import java.io.IOException;

/**
 * Custom RuleInterface.class JSON serializer.
 */
public class RulesJSONSerializer extends StdSerializer<RuleInterface> {
    /**
     * Empty constructor (Jackson requires this).
     */
    protected RulesJSONSerializer() { this(null); }
    /**
     * Class constructor, calls StdSerializer constructor.
     * @param t RuleInterface
     */
    protected RulesJSONSerializer(Class<RuleInterface> t) {
        super(t);
    }

    /**
     * Serialize RuleInterface object into JSON String using Jackson custom serializer.
     * @param rule RuleInterface
     * @param jsonGenerator JsonGenerator
     * @param serializerProvider SerializerProvider
     * @throws IOException something bad happened.
     */
    @Override
    public void serialize(RuleInterface rule, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", rule.getClass().getName());
        jsonGenerator.writeEndObject();
    }
}

