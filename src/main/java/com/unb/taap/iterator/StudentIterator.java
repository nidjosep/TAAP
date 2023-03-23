package com.unb.taap.iterator;

import com.unb.taap.model.Student;

import java.util.List;

public class StudentIterator implements Iterator<Student> {

  private final List<Student> gradedStudents;
  private int index = 0;

  public StudentIterator(List<Student> gradedStudents) {
    this.gradedStudents = gradedStudents;
  }

  @Override
  public boolean hasNext() {
    return index < gradedStudents.size();
  }

  @Override
  public Student next() {
    return gradedStudents.get(index++);
  }

  @Override
  public void remove() {
    gradedStudents.remove(index);
  }
}
