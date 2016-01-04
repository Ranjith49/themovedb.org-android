package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleStoreResponse;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public interface PeopleStoreView {

  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show, boolean isFirstPage);


  /**
   * Response from the Retrofit -- API Reponse
   *
   * @param peopleStoreResponse
   */
  void peopleScreenData(PeopleStoreResponse peopleStoreResponse, boolean isFirstPage);


  /**
   * API Error caused when retrieving the People Store Data
   *
   * @param errorType -- Error Type
   */
  void peopleScreenAPIError(UserAPIErrorType errorType, boolean isFirstPage);
}
