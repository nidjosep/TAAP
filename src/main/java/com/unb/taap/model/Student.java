package com.unb.taap.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unb.taap.core.observer.EventListener;
import com.unb.taap.core.observer.EventType;
import com.unb.taap.core.singleton.TAAPManager;
import java.io.IOException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student implements EventListener {

  private String name;
  private String id;
  private String seat;
  private String token;
  private Evaluation evaluation;
  private int queuePosition = 1;

  public Student() {
  }

  public Student(String name, String id, String seat, String token) {
    this.name = name;
    this.id = id;
    this.seat = seat;
    this.token = token;
    this.evaluation = null;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public String getSeat() {
    return seat;
  }

  public String getToken() {
    return token;
  }

  public Evaluation getEvaluation() {
    return evaluation;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setSeat(String seat) {
    this.seat = seat;
  }

  public void setEvaluation(Evaluation evaluation) {
    this.evaluation = evaluation;
  }

  @Override
  public void update(EventType eventType) {
    this.queuePosition--;
    SseEmitter sseEmitter = TAAPManager.getInstance()
        .getEmitter("queue-stat", token, id);
    if (sseEmitter != null) {
      try {
        sseEmitter.send(queuePosition);
      } catch (IOException e) {
        sseEmitter.completeWithError(e);
      }
    }
  }

  public void setQueuePosition(int position) {
    this.queuePosition = position;
  }
}

