package com.ran.themoviedb.listeners;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public interface ReviewClickListener {

  /**
   * Call back from Click event to the Review Click
   *
   * @param id  -- id of review
   * @param url -- url of the review
   */
  void onReviewClick(String id, String url);
}
