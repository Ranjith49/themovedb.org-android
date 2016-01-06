package com.ran.themoviedb.model.server.entities;

    import java.io.Serializable;

/**
 * Created by ranjith.suda on 1/6/2016.
 */
public class CastDetails implements Serializable {

  int cast_id;
  String character;
  String credit_id;
  int id;
  String name;
  int order;
  String profile_path;

  public int getCast_id() {
    return cast_id;
  }

  public String getCharacter() {
    return character;
  }

  public String getCredit_id() {
    return credit_id;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getOrder() {
    return order;
  }

  public String getProfile_path() {
    return profile_path;
  }
}
