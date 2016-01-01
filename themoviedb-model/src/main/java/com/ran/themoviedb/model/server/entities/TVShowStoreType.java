package com.ran.themoviedb.model.server.entities;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public enum TVShowStoreType {

  TV_POPULAR("tv_popular"),
  TV_TOP_RATED("tv_top_rated"),
  TV_AIR("tv_air");

  String name;

  TVShowStoreType(String name) {
    this.name = name;
  }
}
