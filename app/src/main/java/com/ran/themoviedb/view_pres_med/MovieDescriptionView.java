package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieDetailResponse;

/**
 * Created by ranjith.suda on 1/9/2016.
 */
public interface MovieDescriptionView {
  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show);

  /**
   * List of Banners and Posters retrieved
   *
   * @param response -- Movie response
   */
  void movieResponse(MovieDetailResponse response);

  /**
   * Error type of Movie Description Presenters
   *
   * @param errorType -- Error Type
   */
  void movieError(UserAPIErrorType errorType);
}
