package com.laggytrylma.utils.communication.serializers;

/**
 * Abstract serializer of java Objects to given format.
 */
public abstract class AbstractObjectSerializer {
  /**
   * Set up the custom serializer.
   */
  protected abstract void setup();

  /**
   * IMPORTANT: OVERRIDE THIS
   * Serialize given object.
   * @param o Object to be serialized.
   * @return null
   */
  protected static String serialize(Object o) { return null; }
  /**
   * IMPORTANT: OVERRIDE THIS
   * Deserialize given String to given Class.
   * @param o String to deserialize
   * @param cl Class type to be created from given String
   * @return null
   */
  protected static Object deserialize(String o, Class<?> cl) { return null; }
}
