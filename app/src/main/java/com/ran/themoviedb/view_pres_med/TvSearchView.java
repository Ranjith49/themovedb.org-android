package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieSearchResponse;
import com.ran.themoviedb.model.server.response.MovieStoreResponse;
import com.ran.themoviedb.model.server.response.TvShowSearchResponse;

/**
 * Created by ranjith.suda on 1/3/2016.
 */
public interface TvSearchView {

  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show, boolean isFirstPage);


  /**
   * Response from the Retrofit -- API Reponse
   *
   * @param tvShowSearchResponse
   */
  void tvScreenData(TvShowSearchResponse tvShowSearchResponse, boolean isFirstPage);


  /**
   * API Error caused when retrieving the Movie Store Data
   *
   * @param errorType -- Error Type
   */
  void tvScreenError(UserAPIErrorType errorType, boolean isFirstPage);
}
