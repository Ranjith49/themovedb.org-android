package com.ran.themoviedb.listeners;

import com.ran.themoviedb.model.server.entities.DisplayStoreType;

/**
 * Created by ranjith.suda on 1/3/2016.
 */
public interface StoreClickListener {

  /**
   * Call Back from Store Item Click
   *
   * @param id               -- Id of the Item
   * @param name             -- Name
   * @param displayStoreType -- Display Store
   */
  void onStoreItemClick(int id, String name, DisplayStoreType displayStoreType);

  /**
   * Call Back for the Share CLick
   *
   * @param id               -- Id of the Item
   * @param name             -- name
   * @param displayStoreType -- Display Store
   */
  void onStoreItemShare(int id, String name, DisplayStoreType displayStoreType);
}
