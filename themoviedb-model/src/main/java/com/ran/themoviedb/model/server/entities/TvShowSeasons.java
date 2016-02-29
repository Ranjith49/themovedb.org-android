package com.ran.themoviedb.model.server.entities;

import java.io.Serializable;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class TvShowSeasons implements Serializable {

  String air_date;
  int episode_count;
  int id;
  String poster_path;
  int season_number;

  public String getAir_date() {
    return air_date;
  }

  public int getEpisode_count() {
    return episode_count;
  }

  public int getId() {
    return id;
  }

  public String getPoster_path() {
    return poster_path;
  }

  public int getSeason_number() {
    return season_number;
  }
}
