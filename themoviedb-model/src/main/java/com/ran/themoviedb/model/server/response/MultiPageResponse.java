package com.ran.themoviedb.model.server.response;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/6/2016.
 */
public class MultiPageResponse<T> implements Serializable {

  int id;
  int page;
  ArrayList<T> results;
  int total_pages;
  int total_results;

  public int getPage() {
    return page;
  }

  public int getId() {
    return id;
  }

  public ArrayList<T> getResults() {
    return results;
  }

  public int getTotal_pages() {
    return total_pages;
  }

  public int getTotal_results() {
    return total_results;
  }
}
