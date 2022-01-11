package com.laggytrylma.helpers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AbstractSystemOutCatch {
  protected final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  protected final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  protected final PrintStream originalOut = System.out;
  protected final PrintStream originalErr = System.err;

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }
}
