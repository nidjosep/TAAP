package com.unb.taap.core.factory;

import com.unb.taap.core.iterator.Iterator;
import com.unb.taap.core.iterator.StudentCollection;
import com.unb.taap.model.Student;

public class CSVGradeExporter implements GradeExporter {

  @Override
  public String export(StudentCollection studentCollection) {
    StringBuilder sb = new StringBuilder();
    sb.append("ID,Name,Grade,GradeScale,Feedback,Evaluated By\n");
    Iterator<Student> iterator = studentCollection.createIterator();

    while (iterator.hasNext()) {
      Student student = iterator.next();
      sb.append(student.getId())
          .append(",")
          .append(student.getName())
          .append(",")
          .append(student.getEvaluation() != null ? student.getEvaluation().getGrade() : "")
          .append(",")
          .append(student.getEvaluation() != null ? student.getEvaluation().getGradingScale() : "")
          .append(",")
          .append(student.getEvaluation() != null ? student.getEvaluation().getFeedback() : "")
          .append(",")
          .append(student.getEvaluation() != null ? student.getEvaluation().getEvaluatedBy() : "")
          .append("\n");
    }
    return sb.toString();
  }
}
