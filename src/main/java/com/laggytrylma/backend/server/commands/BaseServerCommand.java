package com.laggytrylma.backend.server.commands;

/**
 * Interface for GoF's Command pattern.
 * BaseGameServer will issue command to clients using this pattern.
 */
public interface BaseServerCommand {
  int execute();
}
