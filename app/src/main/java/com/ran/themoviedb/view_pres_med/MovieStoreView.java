package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieStoreResponse;

/**
 * Created by ranjith.suda on 1/3/2016.
 */
public interface MovieStoreView {

  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show, boolean isFirstPage);


  /**
   * Response from the Retrofit -- API Reponse
   *
   * @param movieStoreResponse
   */
  void movieScreenData(MovieStoreResponse movieStoreResponse, boolean isFirstPage);


  /**
   * API Error caused when retrieving the Movie Store Data
   *
   * @param errorType -- Error Type
   */
  void movieScreenError(UserAPIErrorType errorType, boolean isFirstPage);
}
