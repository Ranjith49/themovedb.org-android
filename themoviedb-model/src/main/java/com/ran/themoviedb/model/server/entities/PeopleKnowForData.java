package com.ran.themoviedb.model.server.entities;

import java.io.Serializable;

/**
 * Created by ranjith.suda on 3/4/2016.
 */
public class PeopleKnowForData implements Serializable {

  boolean adult;
  String character;
  String credit_id;
  int id;
  String title;
  String original_title;
  String poster_path;
  String release_date;
  String media_type;

  //Tv Related ones
  String name;
  String original_name;
  String first_air_date;

  public String getFirst_air_date() {
    return first_air_date;
  }

  public String getOriginal_name() {
    return original_name;
  }

  public String getName() {
    return name;
  }

  public boolean isAdult() {
    return adult;
  }

  public String getCharacter() {
    return character;
  }

  public String getCredit_id() {
    return credit_id;
  }

  public int getId() {
    return id;
  }

  public String getOriginal_title() {
    return original_title;
  }

  public String getPoster_path() {
    return poster_path;
  }

  public String getRelease_date() {
    return release_date;
  }

  public String getTitle() {
    return title;
  }

  public String getMedia_type() {
    return media_type;
  }
}
