package com.ran.themoviedb.model.server.entities;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public enum PeopleStoreType {

  PEOPLE_POPULAR("people_popular");
  String name;

  PeopleStoreType(String name) {
    this.name = name;
  }


  /**
   * Method to get the Store Type based on Name passed
   *
   * @param name -- Name of the store
   * @return -- StoreType
   */
  public static PeopleStoreType getStoreType(String name) {
    for (PeopleStoreType storeType : values()) {
      if (storeType.name.equalsIgnoreCase(name)) {
        return storeType;
      }
    }
    return PEOPLE_POPULAR;
  }

  /**
   * Method to get the Store Name based on StoreType Passed
   *
   * @param storeType --StoreType
   * @return -- Name
   */
  public static String getStoreName(PeopleStoreType storeType) {
    for (PeopleStoreType displayStoreType : values()) {
      if (storeType == displayStoreType) {
        return displayStoreType.name;
      }
    }
    return PEOPLE_POPULAR.name;
  }
}
