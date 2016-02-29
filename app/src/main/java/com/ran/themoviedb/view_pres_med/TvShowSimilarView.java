package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieSimilarDetailsResponse;
import com.ran.themoviedb.model.server.response.TvShowSimilarDetailsResponse;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public interface TvShowSimilarView {

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
  void similarTvShowsResponse(TvShowSimilarDetailsResponse response, boolean isFirstPage);


  /**
   * API Error caused when retrieving the Tv show Similar Data
   *
   * @param errorType -- Error Type
   */
  void similarTvShowsError(UserAPIErrorType errorType, boolean isFirstPage);
}
