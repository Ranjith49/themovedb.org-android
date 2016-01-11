package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieSimilarDetailsResponse;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public interface MovieSimilarView {

  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show, boolean isFirstPage);


  /**
   * Response from the Retrofit -- API Reponse
   *
   * @param response
   */
  void similarMovieData(MovieSimilarDetailsResponse response, boolean isFirstPage);


  /**
   * API Error caused when retrieving the Movie Similar Data
   *
   * @param errorType -- Error Type
   */
  void similarMovieError(UserAPIErrorType errorType, boolean isFirstPage);
}
