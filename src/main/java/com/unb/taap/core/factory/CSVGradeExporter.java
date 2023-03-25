package com.unb.taap.core.factory;

import com.unb.taap.core.iterator.Iterator;
import com.unb.taap.core.iterator.StudentCollection;
import com.unb.taap.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSVGradeExporter implements GradeExporter {

  private static final Logger logger = LoggerFactory.getLogger(CSVGradeExportFactory.class);

  @Override
  public String export(StudentCollection studentCollection) {
    StringBuilder sb = new StringBuilder();
    sb.append("ID,Name,Grade,GradeScale,Feedback,Evaluated By\n");
    Iterator<Student> iterator = studentCollection.createIterator();
    try {
      while (iterator.hasNext()) {
        Student student = iterator.next();
        sb.append(student.getId())
            .append(",")
            .append(student.getName())
            .append(",")
            .append(student.getEvaluation() != null ? student.getEvaluation().getGrade() : "")
            .append(",")
            .append(
                student.getEvaluation() != null ? student.getEvaluation().getGradingScale() : "")
            .append(",")
            .append(student.getEvaluation() != null ? student.getEvaluation().getFeedback() : "")
            .append(",")
            .append(student.getEvaluation() != null ? student.getEvaluation().getEvaluatedBy() : "")
            .append("\n");
      }
    } catch (Exception ex) {
      logger.error("CSV export Failed"+ex.getMessage());
    }
    return sb.toString();
  }
}
