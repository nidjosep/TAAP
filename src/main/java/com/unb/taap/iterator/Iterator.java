package com.unb.taap.iterator;

import com.unb.taap.model.Student;

public interface Iterator<T> {

  boolean hasNext();

  Student next();

  void remove();
}
