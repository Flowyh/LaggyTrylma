package com.laggytrylma.common.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Collections.shuffle;

/**
 * Static class which holds predefined colors of Trylma pieces.
 */
public class ColorAssigner {
  /**
   * Predefined list of Colors.
   */
  private static final ArrayList<Color> colors = new ArrayList<>(Arrays.asList(
          new Color(249, 65, 68),
          new Color(248, 150, 30),
          new Color(249, 199, 79),
          new Color(144, 190, 109),
          new Color(67, 170, 139),
          new Color(87, 117, 144)
  ));

  /**
   * Shuffle the list of colors.
   */
  public static void permutate() {
    shuffle(colors);
  }

  /**
   * Get color by id. If id was out of bounds, returns null.
   * @param i index
   * @return Color
   */
  public static Color getColor(int i) {
    if(i < 0 || i > colors.size()) return null;
    return colors.get(i);
  }
}
