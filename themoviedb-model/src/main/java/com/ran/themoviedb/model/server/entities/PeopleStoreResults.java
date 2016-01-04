package com.ran.themoviedb.model.server.entities;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public class PeopleStoreResults {

  String profile_path;
  boolean adult;
  int id;
  String name;
  double popularity;
  ArrayList<PeopleStoreKnownFor> known_for;

  public boolean isAdult() {
    return adult;
  }

  public String getProfile_path() {
    return profile_path;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPopularity() {
    return popularity;
  }

  public ArrayList<PeopleStoreKnownFor> getKnown_for() {
    return known_for;
  }

}
