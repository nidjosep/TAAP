package com.unb.taap.core.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unb.taap.core.iterator.Iterator;
import com.unb.taap.core.iterator.StudentCollection;
import com.unb.taap.model.Student;

import java.util.ArrayList;
import java.util.List;

public class JSONGradeExporter implements GradeExporter {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String export(StudentCollection studentCollection) {
    Iterator<Student> iterator = studentCollection.createIterator();
    List<Student> studentList = new ArrayList<>();
    while (iterator.hasNext()) {
      studentList.add(iterator.next());
    }
    try {
      return objectMapper.writeValueAsString(studentList);
    } catch (JsonProcessingException e) {
      System.out.println(e.getMessage());
      return "";
    }
  }
}
