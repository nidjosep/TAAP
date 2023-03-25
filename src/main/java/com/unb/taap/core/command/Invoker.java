package com.unb.taap.core.command;

import java.util.HashMap;
import java.util.Map;

public class Invoker {

  private final Map<String, Command> commands;

  public Invoker() {
    commands = new HashMap<>();
  }

  public void execute(Command command) {
    command.execute();
    commands.put(command.getCommandId(), command);
  }

  public void undo(String commandID) {
    if (commands.containsKey(commandID)) {
      Command command = commands.get(commandID);
      command.undo();
    }
  }
}
