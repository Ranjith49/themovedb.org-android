package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public interface TvShowDetailView {

  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show);

  /**
   * List of Banners and Posters retrieved
   *
   * @param response -- Tv Show response
   */
  void tvShowResponse(TvShowDetailResponse response);

  /**
   * Error type of Tv show Description Presenters
   *
   * @param errorType -- Error Type
   */
  void tvShowError(UserAPIErrorType errorType);
}
