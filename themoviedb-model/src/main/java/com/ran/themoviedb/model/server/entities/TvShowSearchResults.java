package com.ran.themoviedb.model.server.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class TvShowSearchResults implements Serializable {

  String poster_path;
  double popularity;
  int id;
  String backdrop_path;
  float vote_average;
  String overview;
  String first_air_date;
  ArrayList<String> origin_country;
  ArrayList<Integer> genre_ids;
  String original_language;
  int vote_count;
  String name;
  String original_name;

  public double getPopularity() {
    return popularity;
  }

  public String getPoster_path() {
    return poster_path;
  }

  public int getId() {
    return id;
  }

  public String getBackdrop_path() {
    return backdrop_path;
  }

  public float getVote_average() {
    return vote_average;
  }

  public String getOverview() {
    return overview;
  }

  public String getFirst_air_date() {
    return first_air_date;
  }

  public ArrayList<String> getOrigin_country() {
    return origin_country;
  }

  public ArrayList<Integer> getGenre_ids() {
    return genre_ids;
  }

  public String getOriginal_language() {
    return original_language;
  }

  public int getVote_count() {
    return vote_count;
  }

  public String getName() {
    return name;
  }

  public String getOriginal_name() {
    return original_name;
  }
}
