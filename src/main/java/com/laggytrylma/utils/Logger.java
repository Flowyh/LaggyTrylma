package com.laggytrylma.utils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger class for pretty console logging.
 * Logging depths:
 * 0 - NONE
 * 1 - ERROR
 * 2 - INFO
 * 3 - DEBUG
 */
public class Logger {
  /**
   * Append current time info before the message.
   */
  private static boolean doLogTime = true;
  /**
   * Current log depth.
   */
  private static int depth = 2;
  /**
   * Current UI component root.
   */
  private static Component messageBoxRoot = null;
  /**
   * Pretty colors for error tag.
   */
  public static final String ERROR_TAG = "[[31mERROR[0m] ";
  /**
   * Pretty colors for info tag.
   */
  public static final String INFO_TAG = "[[32mINFO[0m] ";
  /**
   * Pretty colors for debug tag.
   */
  public static final String DEBUG_TAG = "[[34mDEBUG[0m] ";
  /**
   * Pretty colors for test tag.
   */
  public static final String TEST_TAG = "[[33mTEST[0m] ";

  /**
   * Get logger depth as string.
   * @return String name of the depth
   */
  public static String getLoggerDepth() {
    return switch (depth) {
      case 0 -> "NONE";
      case 1 -> "ERROR";
      case 2 -> "INFO";
      case 3 -> "DEBUG";
      case 4 -> "TEST";
      default -> "UNDEFINED";
    };
  }

  /**
   * Set new Logger's depth.
   * @param d int new depth
   */
  public static void setDepth(int d) { depth = d; }

  /**
   * Set whether time should be logged.
   * @param d True if time should be logged, False if not.
   */
  public static void setDoLogTime(boolean d) { doLogTime = d; }

  /**
   * Set Logger's UI's component root (to show option dialogs).
   * @param root UI's component root
   */
  public static void setMessageBoxRoot(Component root){
    messageBoxRoot = root;
  }

  /**
   * Log current time.
   * @return String of current time.
   */
  public static String logTime() {
    LocalDateTime current = LocalDateTime.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    if(doLogTime) return "[DATE: " + current.format(dateFormat) + " TIME: " + current.format(timeFormat) + "] ";
    else return "";
  }

  /**
   * Display error OptionPane if UI's component root is present.
   * Log error message to the console. (Skipped if depth less than 1).
   * @param msg Message to be written to the OptionPane
   */
  public static void error(String msg) {
    if(messageBoxRoot != null){
      JOptionPane.showMessageDialog(messageBoxRoot, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    if(depth < 1) return;
    System.out.println(logTime() + ERROR_TAG + msg);
  }

  /**
   * Log info message to the console.
   * Skipped if depth less than 2.
   * @param msg String to be logged.
   */
  public static void info(String msg) {
    if(depth < 2) return;
    System.out.println(logTime() + INFO_TAG + msg);
  }

  /**
   * Log debug message to the console.
   * Skipped if depth less than3.
   * @param msg String to be logged.
   */
  public static void debug(String msg) {
    if (depth < 3) return;
    System.out.println(logTime() + DEBUG_TAG + msg);
  }

  /**
   * Log test message to the console.
   * Skipped if depth less than 4.
   * @param msg String to be logged.
   */
  public static void test(String msg) {
    if (depth < 4) return;
    System.out.println(logTime() + TEST_TAG + msg);
  }
}
