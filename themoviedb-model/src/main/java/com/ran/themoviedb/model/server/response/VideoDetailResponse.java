package com.ran.themoviedb.model.server.response;

import com.ran.themoviedb.model.server.entities.VideoDetails;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/6/2016.
 */
public class VideoDetailResponse implements Serializable {

  int id;
  ArrayList<VideoDetails> results;

  public int getId() {
    return id;
  }

  public ArrayList<VideoDetails> getResults() {
    return results;
  }
}
