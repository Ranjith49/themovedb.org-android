package com.ran.themoviedb.model.server.response;

import com.ran.themoviedb.model.server.entities.ImageDetails;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/6/2016.
 */
public class ImageDetailResponse implements Serializable {

  int id;
  ArrayList<ImageDetails> backdrops = new ArrayList<>();
  ArrayList<ImageDetails> posters = new ArrayList<>();

  public int getId() {
    return id;
  }

  public ArrayList<ImageDetails> getBackdrops() {
    return backdrops;
  }

  public ArrayList<ImageDetails> getPosters() {
    return posters;
  }
}
