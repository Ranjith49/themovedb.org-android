package com.ran.themoviedb.model.server.entities;

import java.io.Serializable;

/**
 * Created by ranjith.suda on 1/6/2016.
 */
public class ReviewsDetail implements Serializable {

  String id;
  String author;
  String content;
  String url;

  public String getId() {
    return id;
  }

  public String getAuthor() {
    return author;
  }

  public String getContent() {
    return content;
  }

  public String getUrl() {
    return url;
  }
}
