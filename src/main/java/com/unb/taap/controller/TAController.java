package com.unb.taap.controller;

import com.unb.taap.core.singleton.TAAPManager;
import com.unb.taap.model.Evaluation;
import com.unb.taap.model.LabSession;
import com.unb.taap.model.Student;
import com.unb.taap.model.TeachingAssistant;
import com.unb.taap.model.TokenValidation;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TAController {

  @RequestMapping(value = {"/tap"}, method = RequestMethod.POST)
  public String tap(HttpSession session) {
    TokenValidation tokenValidation = TAAPManager.getInstance().validateToken(
        String.valueOf(session.getAttribute("token")));
    if (tokenValidation.isValid()) {
      Student student = TAAPManager.getInstance().getLabSession(tokenValidation.getLabID())
          .getNextStudentReviewRequest();
      if (student != null) {
        session.setAttribute("loggedInUser", "TA_EVALUATE");
        session.setAttribute("student", student);
        return "index";
      }
      session.setAttribute("loggedInUser", "TA_WAIT");
      TAAPManager.getInstance()
          .enableTAForNextEvaluation(String.valueOf(session.getAttribute("token")),
              String.valueOf(session.getAttribute("id")));
    }
    return "index";
  }

  @PostMapping("/update-student-session")
  public ResponseEntity<String> updateSession(HttpSession session, @RequestBody Student student) {
    session.setAttribute("loggedInUser", "TA_EVALUATE");
    session.setAttribute("student", student);
    return ResponseEntity.status(HttpStatus.OK).body("Success");
  }

  @RequestMapping(value = {"/grade"}, method = RequestMethod.POST)
  public String grade(@RequestParam("id") String id, @RequestParam("grade") String grade,
      @RequestParam("feedback") String feedback, HttpSession session) {
    String token = String.valueOf(session.getAttribute("token"));
    TokenValidation tokenValidation = TAAPManager.getInstance().validateToken(token);

    if (tokenValidation.isValid()) {
      LabSession labSession = TAAPManager.getInstance().getLabSession(tokenValidation.getLabID());
      TeachingAssistant teachingAssistant = labSession.getTeachingAssistant(
          String.valueOf(session.getAttribute("id")));
      ;
      Student student = labSession.getStudent(id);
      if (student != null) {
        teachingAssistant.incEvaluation();
        student.setEvaluation(new Evaluation(grade, "100", feedback,
            session.getAttribute("name").toString()));
        labSession.markReviewAsComplete(student);
      }
      session.setAttribute("pendingEvaluations", String.valueOf(labSession.getStudentsQueueSize()));
      session.setAttribute("evaluationsDone", String.valueOf(teachingAssistant.getEvaluations()));
    }

    session.setAttribute("loggedInUser", "TA");
    return "index";
  }

  @RequestMapping("/end-lab")
  public String end(HttpSession session) {
    String token = String.valueOf(session.getAttribute("token"));
    TokenValidation tokenValidation = TAAPManager.getInstance().validateToken(token);
    if (tokenValidation.isValid()) {
      TAAPManager.getInstance().endLab(tokenValidation.getLabID());
    }
    session.invalidate();
    return "redirect:/";
  }
}
