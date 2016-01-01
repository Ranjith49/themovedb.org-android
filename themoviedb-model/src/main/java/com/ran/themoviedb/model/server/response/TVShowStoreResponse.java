package com.ran.themoviedb.model.server.response;

import com.ran.themoviedb.model.server.entities.TvShowStoreResults;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public class TVShowStoreResponse implements Serializable {

  int page;
  ArrayList<TvShowStoreResults> results;
  int total_results;
  int total_pages;

  public int getPage() {
    return page;
  }

  public ArrayList<TvShowStoreResults> getResults() {
    return results;
  }

  public int getTotal_results() {
    return total_results;
  }

  public int getTotal_pages() {
    return total_pages;
  }
}
