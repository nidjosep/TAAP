package com.unb.taap.controller;

import com.unb.taap.core.singleton.TAAPManager;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class SseEmitterController {

  @RequestMapping(value = {"/queue-poll"}, method = RequestMethod.GET)
  public SseEmitter queuePoll(HttpSession session) {
    if (session.getAttribute("id") != null) {
      return TAAPManager.getInstance().createEmitter("queue-poll",
          String.valueOf(session.getAttribute("token")),
          String.valueOf(session.getAttribute("id")));
    }
    return null;
  }

  @RequestMapping(value = {"/queue-stat"}, method = RequestMethod.GET)
  public SseEmitter queueStat(HttpSession session) {
    if (session.getAttribute("id") != null) {
      return TAAPManager.getInstance().createEmitter("queue-stat",
          String.valueOf(session.getAttribute("token")),
          String.valueOf(session.getAttribute("id")));
    }
    return null;
  }

  @RequestMapping(value = {"/pending-evaluations"}, method = RequestMethod.GET)
  public SseEmitter pendingCount(HttpSession session) {
    if (session.getAttribute("id") != null) {
      return TAAPManager.getInstance()
          .createEmitter("pending-evaluations", String.valueOf(session.getAttribute("token")),
              String.valueOf(session.getAttribute("id")));
    }
    return null;
  }
}
