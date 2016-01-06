package com.ran.themoviedb.model.server.entities;

import java.io.Serializable;

/**
 * Created by ranjith.suda on 1/6/2016.
 */
public class CrewDetails implements Serializable {

  String credit_id;
  String department;
  int id;
  String job;
  String name;
  String profile_path;

  public String getCredit_id() {
    return credit_id;
  }

  public String getDepartment() {
    return department;
  }

  public int getId() {
    return id;
  }

  public String getJob() {
    return job;
  }

  public String getName() {
    return name;
  }

  public String getProfile_path() {
    return profile_path;
  }
}
