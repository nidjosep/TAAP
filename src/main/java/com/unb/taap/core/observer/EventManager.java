package com.unb.taap.core.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

  private final Map<EventType, List<EventListener>> listeners = new HashMap<>();

  public EventManager() {
    registerEvent(EventType.REQ_SUBMITTED);
    registerEvent(EventType.REQ_REVERTED);
    registerEvent(EventType.REQ_EVALUATED);
  }

  private void registerEvent(EventType reqSubmitted) {
    listeners.put(reqSubmitted, new ArrayList<>());
  }

  public void subscribe(EventType eventType, EventListener eventListener) {
    listeners.get(eventType).add(eventListener);
  }

  public void unsubscribe(EventType eventType, EventListener eventListener) {
    listeners.get(eventType).remove(eventListener);
  }

  public void notify(EventType eventType) {
    List<EventListener> subscribers = listeners.get(eventType);
    for (EventListener subscriber : subscribers) {
      subscriber.update(eventType);
    }
  }
}
