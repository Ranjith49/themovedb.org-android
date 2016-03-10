package com.ran.themoviedb.model.utils;

/**
 * Created by ranjith.suda on 3/10/2016.
 * TODO [ranjith, enable better logic]
 * <p/>
 * Class that is responsible for generating the Unique Id for every Service Request
 */
public class UniqueIdCreator {

  private static int uniqueIdCounter = 0;
  private static UniqueIdCreator instance;

  public static UniqueIdCreator getInstance() {
    if (instance == null) {
      synchronized (UniqueIdCreator.class) {
        if (instance == null) {
          instance = new UniqueIdCreator();
        }
      }
    }
    return instance;
  }

  /**
   * Method that generate the Unique id for each generated ..
   *
   * @return -- Id
   */
  public int generateUniqueId() {
    return uniqueIdCounter++;
  }
}
