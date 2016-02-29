package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleDetailResponse;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public interface PeopleDetailView {

  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show);

  /**
   * People Detail Response from Server
   *
   * @param response -- PeopleDetailResponse
   */
  void onPeopleDetailResponse(PeopleDetailResponse response);

  /**
   * Error type of People Detail Presenter
   *
   * @param errorType -- Error Type
   */
  void onPeopleDetailError(UserAPIErrorType errorType);
}
