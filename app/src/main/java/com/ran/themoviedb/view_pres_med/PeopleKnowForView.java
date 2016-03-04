package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleKnownForResponse;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public interface PeopleKnowForView {

  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show);

  /**
   * People Known Response from Server
   *
   * @param response -- PeopleKnownForResponse
   */
  void onPeopleKnownForResponse(PeopleKnownForResponse response);

  /**
   * Error type of People Detail Presenter
   *
   * @param errorType -- Error Type
   */
  void onPeopleKnownForError(UserAPIErrorType errorType);
}
