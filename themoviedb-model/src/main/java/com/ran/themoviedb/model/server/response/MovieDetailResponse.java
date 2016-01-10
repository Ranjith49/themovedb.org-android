package com.ran.themoviedb.model.server.response;

import com.ran.themoviedb.model.server.entities.MovieGenre;
import com.ran.themoviedb.model.server.entities.ProductionCompany;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/6/2016.
 */
public class MovieDetailResponse implements Serializable {

  boolean adult;
  String backdrop_path;
  long budget;
  ArrayList<MovieGenre> genres;
  ArrayList<ProductionCompany> production_companies;
  String homepage;
  int id;
  String imdb_id;
  String original_language;
  String original_title;
  String overview;
  double popularity;
  String poster_path;
  String release_date;
  int runtime;
  String status;
  String tagline;
  String title;
  boolean video;
  float vote_average;
  int vote_count;

  public ArrayList<ProductionCompany> getProduction_companies() {
    return production_companies;
  }

  public String getOriginal_title() {
    return original_title;
  }

  public boolean isAdult() {
    return adult;
  }

  public String getBackdrop_path() {
    return backdrop_path;
  }

  public long getBudget() {
    return budget;
  }

  public ArrayList<MovieGenre> getGenres() {
    return genres;
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

  public String getOriginal_language() {
    return original_language;
  }

  public String getOverview() {
    return overview;
  }

  public double getPopularity() {
    return popularity;
  }

  public String getPoster_path() {
    return poster_path;
  }

  public String getRelease_date() {
    return release_date;
  }

  public int getRuntime() {
    return runtime;
  }

  public String getStatus() {
    return status;
  }

  public String getTagline() {
    return tagline;
  }

  public String getTitle() {
    return title;
  }

  public boolean isVideo() {
    return video;
  }

  public float getVote_average() {
    return vote_average;
  }

  public int getVote_count() {
    return vote_count;
  }


}
