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

  /**
   * Method to get the Store Type based on Name passed
   *
   * @param name -- Name of the store
   * @return -- StoreType
   */
  public static MovieStoreType getStoreType(String name) {
    for (MovieStoreType storeType : values()) {
      if (storeType.name.equalsIgnoreCase(name)) {
        return storeType;
      }
    }
    return MOVIE_POPULAR;
  }

  /**
   * Method to get the Store Name based on StoreType Passed
   *
   * @param storeType --StoreType
   * @return -- Name
   */
  public static String getStoreName(MovieStoreType storeType) {
    for (MovieStoreType displayStoreType : values()) {
      if (storeType == displayStoreType) {
        return displayStoreType.name;
      }
    }
    return MOVIE_POPULAR.name;
  }

}
