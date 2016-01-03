package com.ran.themoviedb.model.server.entities;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public enum TVShowStoreType {

  TV_POPULAR("tv_popular"),
  TV_TOP_RATED("tv_top_rated"),
  TV_AIR("tv_air");

  String name;

  TVShowStoreType(String name) {
    this.name = name;
  }

  /**
   * Method to get the Store Type based on Name passed
   *
   * @param name -- Name of the store
   * @return -- StoreType
   */
  public static TVShowStoreType getStoreType(String name) {
    for (TVShowStoreType storeType : values()) {
      if (storeType.name.equalsIgnoreCase(name)) {
        return storeType;
      }
    }
    return TV_POPULAR;
  }

  /**
   * Method to get the Store Name based on StoreType Passed
   *
   * @param storeType --StoreType
   * @return -- Name
   */
  public static String getStoreName(TVShowStoreType storeType) {
    for (TVShowStoreType displayStoreType : values()) {
      if (storeType == displayStoreType) {
        return displayStoreType.name;
      }
    }
    return TV_POPULAR.name;
  }
}
