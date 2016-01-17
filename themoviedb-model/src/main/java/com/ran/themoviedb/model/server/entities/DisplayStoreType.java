package com.ran.themoviedb.model.server.entities;

/**
 * Created by ranjith.suda on 1/3/2016.
 */
public enum DisplayStoreType {

  MOVIE_STORE("movies_store"),
  TV_STORE("tv_store"),
  PEOPLE_STORE("people_store");

  String name;

  DisplayStoreType(String name) {
    this.name = name;
  }

  /**
   * Method to get the Store Type based on Name passed
   *
   * @param name -- Name of the store
   * @return -- StoreType
   */
  public static DisplayStoreType getStoreType(String name) {
    for (DisplayStoreType storeType : values()) {
      if (storeType.name.equalsIgnoreCase(name)) {
        return storeType;
      }
    }
    return MOVIE_STORE;
  }

  /**
   * Method to get the Store Name based on StoreType Passed
   *
   * @param storeType --StoreType
   * @return -- Name
   */
  public static String getStoreName(DisplayStoreType storeType) {
    for (DisplayStoreType displayStoreType : values()) {
      if (storeType == displayStoreType) {
        return displayStoreType.name;
      }
    }
    return MOVIE_STORE.name;
  }
}
