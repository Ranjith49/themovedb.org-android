package com.ran.themoviedb.model.server.response;

import com.ran.themoviedb.model.server.entities.MovieStoreResults;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public class MovieStoreResponse implements Serializable {

  String page;
  ArrayList<MovieStoreResults> results;
  int total_results;
  int total_pages;

  public String getPage() {
    return page;
  }

  public ArrayList<MovieStoreResults> getResults() {
    return results;
  }

  public int getTotal_results() {
    return total_results;
  }

  public int getTotal_pages() {
    return total_pages;
  }
}
