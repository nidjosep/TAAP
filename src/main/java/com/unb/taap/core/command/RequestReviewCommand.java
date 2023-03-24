package com.unb.taap.core.command;

import com.unb.taap.core.singleton.TAAPManager;
import com.unb.taap.model.LabSession;
import com.unb.taap.model.Student;

public class RequestReviewCommand implements Command {

  private final String labID;
  private final String studentID;

  public RequestReviewCommand(String labID, String studentID) {
    this.labID = labID;
    this.studentID = studentID;
  }

  @Override
  public void execute() {
    LabSession labSession = TAAPManager.getInstance().getLabSession(labID);
    Student student = labSession.getStudent(studentID);
    if (student != null) {
      student.setQueuePosition(labSession.submitReviewRequest(student));
    }
  }

  @Override
  public void undo() {
    LabSession labSession = TAAPManager.getInstance().getLabSession(labID);
    Student student = labSession.getStudent(studentID);
    if (student != null) {
      labSession.revertReviewRequest(student);
      student.setQueuePosition(1);
    }
  }

  @Override
  public String getCommandId() {
    return String.format("%s-%s", labID, studentID);
  }
}