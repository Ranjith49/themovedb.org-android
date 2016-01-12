package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.CastCrewDetailResponse;

/**
 * Created by ranjith.suda on 1/12/2016.
 */
public interface MovieCastCrewView {

  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show);


  /**
   * Response from the Retrofit -- API Reponse
   *
   * @param response
   */
  void movieCastCrewData(CastCrewDetailResponse response);


  /**
   * API Error caused when retrieving the Movie Review Data
   *
   * @param errorType -- Error Type
   */
  void movieCastCrewAPIError(UserAPIErrorType errorType);
}
