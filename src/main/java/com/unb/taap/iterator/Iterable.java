package com.unb.taap.iterator;

public interface Iterable<T> {

  Iterator<T> createIterator();
}
