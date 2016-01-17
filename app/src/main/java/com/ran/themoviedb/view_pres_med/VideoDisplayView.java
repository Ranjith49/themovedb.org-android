package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.VideoDetails;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/17/2016.
 */
public interface VideoDisplayView {

  /**
   * List of the Video Detailed Response
   *
   * @param videoDetails -- Arraylist of VideoDetatils
   */

  void onVideoResponse(ArrayList<VideoDetails> videoDetails);
}
