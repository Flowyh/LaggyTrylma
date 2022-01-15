package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.awt.*;
import java.io.IOException;

/**
 * Custom Color.class JSON serializer.
 */
public class ColorJSONSerializer extends JsonSerializer<Color> {
  /**
   * Serialize Color object into JSON String using Jackson custom serializer.
   * @param value Color
   * @param gen JsonGenerator
   * @param serializers SerializerProvider
   * @throws IOException something bad happened
   */
  @Override
  public void serialize(Color value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeStartObject();
    gen.writeFieldName("argb");
    gen.writeString(Integer.toHexString(value.getRGB()));
    gen.writeEndObject();
  }
}