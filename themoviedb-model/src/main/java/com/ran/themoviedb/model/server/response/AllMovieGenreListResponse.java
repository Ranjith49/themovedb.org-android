package com.ran.themoviedb.model.server.response;

import com.ran.themoviedb.model.server.entities.MovieGenre;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 12/29/2015.
 * <p/>
 * Response for Success HTTP Call of {@see com.ran.themoviedb.model.server.api.AllGenreListAPI}
 */
public class AllMovieGenreListResponse implements Serializable {
  ArrayList<MovieGenre> genres;

  public ArrayList<MovieGenre> getGenres() {
    return genres;
  }
}
