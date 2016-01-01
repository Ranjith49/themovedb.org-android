package com.ran.themoviedb.model.server.entities;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public enum PeopleStoreType {

  PEOPLE_POPULAR("people_popular");
  String name;

  PeopleStoreType(String name) {
    this.name = name;
  }
}
