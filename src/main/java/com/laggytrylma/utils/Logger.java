package com.laggytrylma.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
  /**
   * Logging depth.
   * 0 - NONE
   * 1 - ERROR
   * 2 - INFO
   * 3 - DEBUG
   */
  private static int depth = 2;
  public static final String ERROR_TAG = "[[31mERROR[0m] ";
  public static final String INFO_TAG = "[[32mINFO[0m] ";
  public static final String DEBUG_TAG = "[[34mDEBUG[0m] ";
  public static final String TEST_TAG = "[[33mTEST[0m] ";

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
  public static void setDepth(int d) { depth = d; }
  public static String logTime() {
    LocalDateTime current = LocalDateTime.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    return "[DATE: " + current.format(dateFormat) + " TIME: " + current.format(timeFormat) + "] ";
  }
  public static void error(String msg) {
    if(depth < 1) return;
    System.out.println(ERROR_TAG + msg);
  }
  public static void info(String msg) {
    if(depth < 2) return;
    System.out.println(INFO_TAG + msg);
  }
  public static void debug(String msg) {
    if (depth < 3) return;
    System.out.println(DEBUG_TAG + msg);
  }
  public static void test(String msg) {
    if (depth < 4) return;
    System.out.println(TEST_TAG + msg);
  }


}
