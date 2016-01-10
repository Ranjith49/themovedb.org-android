package com.ran.themoviedb.model.server.entities;

import java.io.Serializable;

/**
 * Created by ranjith.suda on 1/10/2016.
 */
public class ProductionCompany implements Serializable {

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
