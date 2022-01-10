package com.laggytrylma.common.models;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ColorAssignerTest {
  @Test
  public void testGetColor() {
    Color test = ColorAssigner.getColor(0);
    assertNotNull(test);
  }

  @Test
  public void testGetColorFail() {
    Color test = ColorAssigner.getColor(-1);
    assertNull(test);
    test = ColorAssigner.getColor(123123);
    assertNull(test);
  }

  @Test
  public void testPermutation() {
    ArrayList<Color> colors = new ArrayList<>();
    int i = 0;
    Color temp;
    while(true) {
      try {
        temp = ColorAssigner.getColor(i);
        colors.add(temp);
        i++;
      } catch(IndexOutOfBoundsException e) {
        break;
      }
    }
    ColorAssigner.permutate();
    boolean stillHasAll = true;
    i = 6;
    while(i > 0) {
      temp = ColorAssigner.getColor(i - 1);
      if(!colors.contains(temp)) {
        stillHasAll = false;
        break;
      }
      i--;
    }
    assertTrue(stillHasAll);
  }
}



