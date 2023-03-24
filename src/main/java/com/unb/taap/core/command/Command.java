package com.unb.taap.core.command;

public interface Command {

  void execute();

  void undo();

  String getCommandId();
}