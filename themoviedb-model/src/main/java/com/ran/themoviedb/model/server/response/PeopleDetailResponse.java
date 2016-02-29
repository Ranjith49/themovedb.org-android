package com.ran.themoviedb.model.server.response;

import java.io.Serializable;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class PeopleDetailResponse implements Serializable {

  boolean adult;
  String biography;
  String birthday;
  String homepage;
  int id;
  String imdb_id;
  String name;
  String place_of_birth;
  double popularity;
  String profile_path;

  public String getBiography() {
    return biography;
  }

  public boolean isAdult() {
    return adult;
  }

  public String getBirthday() {
    return birthday;
  }

  public String getHomepage() {
    return homepage;
  }

  public int getId() {
    return id;
  }

  public String getImdb_id() {
    return imdb_id;
  }

  public String getName() {
    return name;
  }

  public String getPlace_of_birth() {
    return place_of_birth;
  }

  public double getPopularity() {
    return popularity;
  }

  public String getProfile_path() {
    return profile_path;
  }
}
