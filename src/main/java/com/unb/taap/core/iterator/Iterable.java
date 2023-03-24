package com.unb.taap.core.iterator;

public interface Iterable<T> {

  Iterator<T> createIterator();
}
