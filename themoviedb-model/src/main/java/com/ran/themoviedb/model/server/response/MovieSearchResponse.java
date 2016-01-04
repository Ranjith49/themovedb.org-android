package com.ran.themoviedb.model.server.response;

import com.ran.themoviedb.model.server.entities.MovieSearchResults;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class MovieSearchResponse {

  int page;
  ArrayList<MovieSearchResults> results;
  int total_results;
  int total_pages;

  public int getPage() {
    return page;
  }

  public ArrayList<MovieSearchResults> getResults() {
    return results;
  }

  public int getTotal_results() {
    return total_results;
  }

  public int getTotal_pages() {
    return total_pages;
  }
}
