package com.ran.themoviedb.model.server.response;

import com.ran.themoviedb.model.server.entities.TvShowSearchResults;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class TvShowSearchResponse implements Serializable {

  int page;
  ArrayList<TvShowSearchResults> results;
  int total_results;
  int total_pages;

  public int getPage() {
    return page;
  }

  public ArrayList<TvShowSearchResults> getResults() {
    return results;
  }

  public int getTotal_results() {
    return total_results;
  }

  public int getTotal_pages() {
    return total_pages;
  }
}
