package com.ran.themoviedb.model.server.entities;

import java.io.Serializable;

/**
 * Created by ranjith.suda on 1/6/2016.
 */
public class VideoDetails implements Serializable {

  String id;
  String iso_639_1;
  String key;
  String name;
  String site;
  int size;
  String type;

  public String getId() {
    return id;
  }

  public String getIso_639_1() {
    return iso_639_1;
  }

  public String getKey() {
    return key;
  }

  public String getName() {
    return name;
  }

  public String getSite() {
    return site;
  }

  public int getSize() {
    return size;
  }

  public String getType() {
    return type;
  }
}
