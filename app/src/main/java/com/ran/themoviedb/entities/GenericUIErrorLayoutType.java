package com.ran.themoviedb.entities;

/**
 * Created by ranjith.suda on 12/30/2015.
 *
 * Generic Error UI Layout Type Possible Values
 */
public enum GenericUIErrorLayoutType {

  CENTER(100),
  BOTTOM(101),
  TOP(102);

  int index;

  GenericUIErrorLayoutType(int index) {
    this.index = index;
  }
}
