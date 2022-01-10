package com.laggytrylma.utils;

import com.laggytrylma.helpers.AbstractSystemOutCatch;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class TestLogger extends AbstractSystemOutCatch {

  @Test
  public void testTime() {
    String loggedTime = Logger.logTime();
    LocalDateTime current = LocalDateTime.now();
    String currentTest = "[DATE: " + current.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " TIME: " + current.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ";
    assertEquals(currentTest, loggedTime);
  }

  @Test
  public void testDepth() {
    // Test depth info
    assertEquals("INFO", Logger.getLoggerDepth());
    Logger.setDepth(0);
    assertEquals("NONE", Logger.getLoggerDepth());
    Logger.setDepth(11);
    assertEquals("UNDEFINED", Logger.getLoggerDepth());
    Logger.setDepth(2);
  }

  @Test
  public void testNone() {
    assertEquals("INFO", Logger.getLoggerDepth());
    Logger.setDepth(0);
    Logger.info("TEST");
    // Nothing should be printed
    assertEquals("", outContent.toString());
    // Reset depth to info
    Logger.setDepth(2);
  }

  @Test
  public void testError() {
    Logger.setDepth(1);
    // Check if depth is 1 (error)
    assertEquals("ERROR", Logger.getLoggerDepth());
    Logger.error("TEST");
    // Check info logging, should not be empty on depth = 1
    LocalDateTime current = LocalDateTime.now();
    String logTime = "[DATE: " + current.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " TIME: " + current.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ";
    assertEquals(logTime + "[[31mERROR[0m] TEST" + System.lineSeparator(), outContent.toString());
    // Reset outputStream
    outContent.reset();
    // Check other logging, should be empty on anything that requires depth > 1
    Logger.debug("TEST");
    assertEquals("", outContent.toString());
    // Reset depth to info
    Logger.setDepth(2);
  }

  @Test
  public void testInfo() {
    // Check if depth is 2 (info)
    assertEquals("INFO", Logger.getLoggerDepth());
    Logger.info("TEST");
    // Check info logging, should not be empty on depth = 2
    LocalDateTime current = LocalDateTime.now();
    String logTime = "[DATE: " + current.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " TIME: " + current.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ";
    assertEquals(logTime + "[[32mINFO[0m] TEST" + System.lineSeparator(), outContent.toString());
    // Reset outputStream
    outContent.reset();
    // Check other logging, should be empty on anything that requires depth > 2
    Logger.debug("TEST");
    assertEquals("", outContent.toString());
  }

  @Test
  public void testDebug() {
    Logger.setDepth(3);
    // Check if depth is now 3 (debug)
    assertEquals("DEBUG", Logger.getLoggerDepth());
    Logger.debug("TEST");
    // Check debug logging, should not be empty on depth = 3
    LocalDateTime current = LocalDateTime.now();
    String logTime = "[DATE: " + current.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " TIME: " + current.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ";
    assertEquals(logTime + "[[34mDEBUG[0m] TEST" + System.lineSeparator(), outContent.toString());
    // Reset outputStream
    outContent.reset();
    // Check other logging, should be empty on anything that requires depth > 3
    Logger.test("TEST");
    assertEquals("", outContent.toString());
    // Reset depth to info
    Logger.setDepth(2);
  }

  @Test
  public void testTest() {
    Logger.setDepth(4);
    // Check if depth is now 4 (test)
    assertEquals("TEST", Logger.getLoggerDepth());
    Logger.test("TEST");
    // Check test logging, should not be empty on depth = 4
    LocalDateTime current = LocalDateTime.now();
    String logTime = "[DATE: " + current.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " TIME: " + current.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ";
    assertEquals(logTime + "[[33mTEST[0m] TEST" + System.lineSeparator(), outContent.toString());
    // Reset depth to info
    Logger.setDepth(2);
  }

  @Test
  public void testDoLogTime() {
    Logger.setDoLogTime(false);
    assertEquals("", Logger.logTime());
  }
}
