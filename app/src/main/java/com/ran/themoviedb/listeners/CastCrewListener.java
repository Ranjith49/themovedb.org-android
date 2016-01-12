package com.ran.themoviedb.listeners;

/**
 * Created by ranjith.suda on 1/12/2016.
 */
public interface CastCrewListener {

  /**
   * Call back to be informed when any Person is clicked
   *
   * @param id   -- id of the Person
   * @param name -- Name of the person
   */
  void onPersonDetail(int id, String name);
}
