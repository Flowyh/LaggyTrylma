package com.laggytrylma.common.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Collections.shuffle;

public class ColorAssigner {
  private static final ArrayList<Color> colors = new ArrayList<>(Arrays.asList(
          new Color(249, 65, 68),
          new Color(248, 150, 30),
          new Color(249, 199, 79),
          new Color(144, 190, 109),
          new Color(67, 170, 139),
          new Color(87, 117, 144)
  ));
  
  public static void permutate() {
    shuffle(colors);
  }
  
  public static Color getColor(int i) {
    if(i < 0 || i > colors.size()) return null;
    return colors.get(i);
  }
}
