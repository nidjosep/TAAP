package com.unb.taap.core.state;

import com.unb.taap.model.TeachingAssistant;

public class BusyState extends State {

  public BusyState(TeachingAssistant teachingAssistant) {
    super(teachingAssistant);
  }

  @Override
  public void handle() {
    System.out.println(teachingAssistant.getName() + " is in idle mode.");
  }
}