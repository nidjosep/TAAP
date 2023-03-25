package com.unb.taap.core.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unb.taap.core.iterator.Iterator;
import com.unb.taap.core.iterator.StudentCollection;
import com.unb.taap.model.Student;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONGradeExporter implements GradeExporter {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private static final Logger logger = LoggerFactory.getLogger(JSONGradeExporter.class);

  @Override
  public String export(StudentCollection studentCollection) {
    Iterator<Student> iterator = studentCollection.createIterator();
    List<Student> studentList = new ArrayList<>();
    while (iterator.hasNext()) {
      studentList.add(iterator.next());
    }
    try {
      return objectMapper.writeValueAsString(studentList);
    } catch (Exception e) {
      logger.error("JSON export Failed"+e.getMessage());
      return "";
    }
  }
}
