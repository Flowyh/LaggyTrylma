package com.laggytrylma.utils.communication.serializers;

public abstract class AbstractObjectSerializer {
  protected abstract void setup();
  protected static String serialize(Object o) { return null; }
  protected static Object deserialize(String o, Class<?> cl) { return null; }
}
