package com.ran.themoviedb.model.server.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class PeopleStoreKnownFor implements Serializable {

  private String poster_path;
  private boolean adult;
  private String overview;
  private String release_date;
  private String original_title;
  ArrayList<Integer> genre_ids;
  private int id;
  private String media_type;
  private String original_language;
  private String title;
  private String backdrop_path;
  private double popularity;
  private float vote_count;
  private boolean video;
  private float vote_average;

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

  public String getOriginal_title() {
    return original_title;
  }

  public ArrayList<Integer> getGenre_ids() {
    return genre_ids;
  }

  public int getId() {
    return id;
  }

  public String getMedia_type() {
    return media_type;
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

  public float getVote_count() {
    return vote_count;
  }

  public boolean isVideo() {
    return video;
  }

  public float getVote_average() {
    return vote_average;
  }
}
