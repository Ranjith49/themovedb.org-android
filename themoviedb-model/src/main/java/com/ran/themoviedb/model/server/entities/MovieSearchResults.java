package com.ran.themoviedb.model.server.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class MovieSearchResults implements Serializable {

  String poster_path;
  boolean adult;
  String overview;
  String release_date;
  ArrayList<Integer> genre_ids;
  int id;
  String original_title;
  String original_language;
  String title;
  String backdrop_path;
  double popularity;
  int vote_count;
  boolean video;
  float vote_average;

  public boolean isAdult() {
    return adult;
  }

  public String getPoster_path() {
    return poster_path;
  }

  public String getOverview() {
    return overview;
  }

  public String getRelease_date() {
    return release_date;
  }

  public ArrayList<Integer> getGenre_ids() {
    return genre_ids;
  }

  public int getId() {
    return id;
  }

  public String getOriginal_title() {
    return original_title;
  }

  public String getOriginal_language() {
    return original_language;
  }

  public String getTitle() {
    return title;
  }

  public String getBackdrop_path() {
    return backdrop_path;
  }

  public double getPopularity() {
    return popularity;
  }

  public int getVote_count() {
    return vote_count;
  }

  public boolean isVideo() {
    return video;
  }

  public float getVote_average() {
    return vote_average;
  }
}
