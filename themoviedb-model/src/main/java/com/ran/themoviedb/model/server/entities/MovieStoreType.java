package com.ran.themoviedb.model.server.entities;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public enum MovieStoreType {

  MOVIE_POPULAR("movie_popular"),
  MOVIE_TOP_RATED("movie_top_rated"),
  MOVIE_UPCOMING("movie_upcoming"),
  MOVIE_NOW_PLAYING("movie_now_playing");

  String name;

  MovieStoreType(String name) {
    this.name = name;
  }

}
