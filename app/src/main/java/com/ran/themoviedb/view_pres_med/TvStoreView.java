package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TVShowStoreResponse;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public interface TvStoreView {

  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show, boolean isFirstPage);


  /**
   * Response from the Retrofit -- API Reponse
   *
   * @param tvShowStoreResponse
   */
  void tvScreenData(TVShowStoreResponse tvShowStoreResponse, boolean isFirstPage);


  /**
   * API Error caused when retrieving the Movie Store Data
   *
   * @param errorType -- Error Type
   */
  void tvScreenError(UserAPIErrorType errorType, boolean isFirstPage);
}
