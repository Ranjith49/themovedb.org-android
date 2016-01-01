package com.ran.themoviedb.model.server.response;

import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p/>
 * Response for Success HTTP Call of {@see com.ran.themoviedb.model.server.api.TheMovieDbConfigAPI}
 */
public class TheMovieDbConfigResponse implements Serializable {

  TheMovieDbImagesConfig images;
  ArrayList<String> change_keys;

  public TheMovieDbImagesConfig getImages() {
    return images;
  }

  public void setImages(TheMovieDbImagesConfig images) {
    this.images = images;
  }

  public ArrayList<String> getChange_keys() {
    return change_keys;
  }

  public void setChange_keys(ArrayList<String> change_keys) {
    this.change_keys = change_keys;
  }


}
