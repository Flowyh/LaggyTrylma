package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import java.awt.*;
import java.io.IOException;

/**
 * Custom Color.class JSON deserializer.
 */
public class ColorJSONDeserializer extends JsonDeserializer<Color> {
  /**
   * Deserialize Color JSON String using Jackson custom deserializer.
   * @param p JsonParser
   * @param ctx DeserializationContext
   * @return Color object
   * @throws IOException something bad happened
   */
  @Override
  public Color deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
    TreeNode root = p.getCodec().readTree(p);
    TextNode rgba = (TextNode) root.get("argb");
    return new Color(Integer.parseUnsignedInt(rgba.textValue(), 16), true);
  }
}