package com.ran.themoviedb.view_pres_med;

/**
 * Created by ranjith.suda on 12/30/2015.
 *
 * MVP for Propagating the AllMovieGenreListAPI response status to UI
 */
public interface AllMovieGenreView {
  void isMovieGenreResponseRetrieval(boolean success);
}
