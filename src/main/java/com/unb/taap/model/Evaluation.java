package com.unb.taap.model;

public class Evaluation {

  private String grade;
  private String gradingScale;
  private String feedback;

  private String evaluatedBy;

  public Evaluation() {
  }

  public Evaluation(String grade, String gradingScale, String feedback, String evaluatedBy) {
    this.grade = grade;
    this.gradingScale = gradingScale;
    this.feedback = feedback;
    this.evaluatedBy = evaluatedBy;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getGradingScale() {
    return gradingScale;
  }

  public void setGradingScale(String gradingScale) {
    this.gradingScale = gradingScale;
  }

  public String getFeedback() {
    return feedback;
  }

  public void setFeedback(String feedback) {
    this.feedback = feedback;
  }

  public String getEvaluatedBy() {
    return evaluatedBy;
  }

  public void setEvaluatedBy(String evaluatedBy) {
    this.evaluatedBy = evaluatedBy;
  }
}
