package com.unb.taap.model;

import com.unb.taap.core.observer.EventListener;
import com.unb.taap.core.observer.EventType;
import com.unb.taap.core.singleton.TAAPManager;
import com.unb.taap.core.state.BusyState;
import com.unb.taap.core.state.State;
import java.io.IOException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class TeachingAssistant implements EventListener {

  private final String id;
  private final String name;

  private final String token;
  private State state;

  private int evaluations;

  public TeachingAssistant(String name, String id, String token) {
    this.name = name;
    this.id = id;
    this.token = token;
    state = new BusyState(this);
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  @Override
  public void update(EventType eventType) {
    pushSessionStatusToClient(getToken(), id);
    state.handle();
  }

  public String getToken() {
    return token;
  }

  public void changeState(State state) {
    this.state = state;
  }

  public int getEvaluations() {
    return evaluations;
  }

  public void incEvaluation() {
    ++evaluations;
  }

  public synchronized void pushSessionStatusToClient(String token, String id) {
    SseEmitter sseEmitter = TAAPManager.getInstance().getEmitter("pending-evaluations", token, id);
    if (sseEmitter != null) {
      TokenValidation tokenValidation = TAAPManager.getInstance().validateToken(token);
      if (UserType.TA.equals(tokenValidation.getUserType())) {
        LabSession labSession = TAAPManager.getInstance().getLabSession(tokenValidation.getLabID());
        try {
          sseEmitter.send(String.valueOf(labSession.getStudentsQueueSize()));
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }
}
