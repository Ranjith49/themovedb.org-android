package com.ran.themoviedb.model.server.response;

import com.ran.themoviedb.model.server.entities.PeopleKnowForData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 3/4/2016.
 */
public class PeopleKnownForResponse implements Serializable {

  ArrayList<PeopleKnowForData> cast;
  int id;

  public ArrayList<PeopleKnowForData> getCast() {
    return cast;
  }

  public int getId() {
    return id;
  }
}
