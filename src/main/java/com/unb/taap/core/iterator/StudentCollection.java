package com.unb.taap.core.iterator;

import com.unb.taap.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentCollection implements Iterable<Student> {

  private final List<Student> students;

  public StudentCollection() {
    this.students = new ArrayList<>();
  }

  public void addStudent(Student student) {
    students.add(student);
  }

  public void removeStudent(Student student) {
    students.remove(student);
  }

  @Override
  public Iterator<Student> createIterator() {
    return new StudentIterator(students);
  }
}
