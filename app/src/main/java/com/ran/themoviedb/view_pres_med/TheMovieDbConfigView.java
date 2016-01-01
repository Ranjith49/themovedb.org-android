package com.ran.themoviedb.view_pres_med;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p/>
 * MVP for Propagating the TheMovieDbConfigAPI response status to UI
 */
public interface TheMovieDbConfigView {

  void isConfigRetrievalSuccess(boolean success);
}
