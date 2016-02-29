package com.ran.themoviedb.model.server.response;

import com.ran.themoviedb.model.server.entities.TvShowSeasons;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class TvShowDetailResponse implements Serializable {

  String backdrop_path;
  String first_air_date;
  String homepage;
  ArrayList<String> episode_run_time;
  int id;
  boolean in_production;
  String last_air_date;
  String name;
  int number_of_episodes;
  int number_of_seasons;
  String original_name;
  String overview;
  double popularity;
  String poster_path;
  ArrayList<TvShowSeasons> seasons;
  String status;
  String type;
  float vote_average;
  int vote_count;

  public ArrayList<String> getEpisode_running_time() {
    return episode_run_time;
  }

  public ArrayList<TvShowSeasons> getSeasons() {
    return seasons;
  }

  public String getBackdrop_path() {
    return backdrop_path;
  }

  public String getFirst_air_date() {
    return first_air_date;
  }

  public String getHomepage() {
    return homepage;
  }

  public int getId() {
    return id;
  }

  public boolean isIn_production() {
    return in_production;
  }

  public String getLast_air_date() {
    return last_air_date;
  }

  public String getName() {
    return name;
  }

  public int getNumber_of_episodes() {
    return number_of_episodes;
  }

  public int getNumber_of_seasons() {
    return number_of_seasons;
  }

  public String getOriginal_name() {
    return original_name;
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

  public String getStatus() {
    return status;
  }

  public String getType() {
    return type;
  }

  public float getVote_average() {
    return vote_average;
  }

  public int getVote_count() {
    return vote_count;
  }


}
