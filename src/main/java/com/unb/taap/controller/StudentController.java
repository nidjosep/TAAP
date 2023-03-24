package com.unb.taap.controller;

import com.unb.taap.core.command.RequestReviewCommand;
import com.unb.taap.core.singleton.TAAPManager;
import com.unb.taap.model.Student;
import com.unb.taap.model.TokenValidation;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StudentController {

  @RequestMapping(value = {"/request-review"}, method = RequestMethod.POST)
  public String requestReview(HttpSession session) {
    String id = String.valueOf(session.getAttribute("id"));
    String token = String.valueOf(session.getAttribute("token"));
    TokenValidation tokenValidation = TAAPManager.getInstance().validateToken(token);
    if (tokenValidation.isValid()) {
      TAAPManager.getInstance().getInvoker()
          .execute(new RequestReviewCommand(tokenValidation.getLabID(), id));
    }

    session.setAttribute("loggedInUser", "STUDENT_SUBMITTED");
    session.setAttribute("queueCount",
        TAAPManager.getInstance().getLabSession(tokenValidation.getLabID()).getStudentsQueueSize());
    return "index";
  }

  @RequestMapping(value = {"/revert-review-request"}, method = RequestMethod.POST)
  public String revert(HttpSession session) {
    String id = String.valueOf(session.getAttribute("id"));
    String token = String.valueOf(session.getAttribute("token"));
    TokenValidation tokenValidation = TAAPManager.getInstance().validateToken(token);

    if (tokenValidation.isValid()) {
      String commandID = String.format("%s-%s", tokenValidation.getLabID(), id);
      TAAPManager.getInstance().getInvoker().undo(commandID);
    }
    session.setAttribute("loggedInUser", "STUDENT");
    session.removeAttribute("queueCount");
    return "index";
  }

  @PostMapping("/update-queue-session")
  public ResponseEntity<String> updateSession(HttpSession session, @RequestBody int queueCount) {
    session.setAttribute("queueCount", queueCount);
    return ResponseEntity.status(HttpStatus.OK).body("Success");
  }

}
