package com.unb.taap.core.state;

import com.unb.taap.core.singleton.TAAPManager;
import com.unb.taap.model.Student;
import com.unb.taap.model.TeachingAssistant;
import java.io.IOException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class FreeState extends State {

  public FreeState(TeachingAssistant teachingAssistant) {
    super(teachingAssistant);
  }

  @Override
  public void handle() {
    String userToken = String.valueOf(teachingAssistant.getToken());
    String userID = String.valueOf(teachingAssistant.getId());

    SseEmitter sseEmitter = TAAPManager.getInstance().getEmitter("queue-poll",
        userToken, userID);
    if (sseEmitter != null) {
      String labID = teachingAssistant.getToken().split("\\.")[0];
      Student student = TAAPManager.getInstance().getLabSession(labID)
          .getNextStudentReviewRequest();
      if (student != null) {
        try {
          sseEmitter.send(student);
        } catch (IOException e) {
          sseEmitter.complete();
          TAAPManager.getInstance().removeEmitter("queue-poll", userToken, userID);
        }
      }
    }
  }
}