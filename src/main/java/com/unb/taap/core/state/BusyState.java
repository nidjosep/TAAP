package com.unb.taap.core.state;

import com.unb.taap.model.TeachingAssistant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusyState extends State {
  private static final Logger logger = LoggerFactory.getLogger(BusyState.class);

  public BusyState(TeachingAssistant teachingAssistant) {
    super(teachingAssistant);
  }

  @Override
  public void handle() {
   logger.info(teachingAssistant.getName() + " is in idle mode.");
  }
}