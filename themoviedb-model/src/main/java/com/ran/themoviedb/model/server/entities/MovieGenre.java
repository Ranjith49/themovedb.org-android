package com.ran.themoviedb.model.server.entities;

import java.io.Serializable;

/**
 * Created by ranjith.suda on 12/29/2015.
 * <p/>
 * Internal Bean Model for the AllGenreListAPI
 */
public class MovieGenre implements Serializable {

  int id;
  String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
