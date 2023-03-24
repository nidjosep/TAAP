package com.unb.taap.core.state;

import com.unb.taap.model.TeachingAssistant;

public abstract class State {

  protected TeachingAssistant teachingAssistant;

  public State(TeachingAssistant teachingAssistant) {
    this.teachingAssistant = teachingAssistant;
  }

  public abstract void handle();
}
