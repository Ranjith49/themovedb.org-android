package com.ran.themoviedb.model.server.response;

import com.ran.themoviedb.model.server.entities.CastDetails;
import com.ran.themoviedb.model.server.entities.CrewDetails;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/6/2016.
 */
public class CastCrewDetailResponse implements Serializable {

  int id;
  ArrayList<CastDetails> cast;
  ArrayList<CrewDetails> crew;

  public ArrayList<CrewDetails> getCrew() {
    return crew;
  }

  public int getId() {
    return id;
  }

  public ArrayList<CastDetails> getCast() {
    return cast;
  }
}
