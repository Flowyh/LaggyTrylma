package com.laggytrylma.utils.communication.commands.models;

/**
 * Application model commands interface.
 */
public interface IModelCommands {
  /**
   * Get current enum's command as a String.
   * @return String command
   */
  String command();
  /**
   * Get current enum's model as a String.
   * @return String model
   */
  String model();
}
