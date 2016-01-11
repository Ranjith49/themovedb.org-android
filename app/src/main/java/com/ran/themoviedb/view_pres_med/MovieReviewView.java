package com.ran.themoviedb.view_pres_med;

import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.ReviewsDetailResponse;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public interface MovieReviewView {

  /**
   * Progress Bar to be shown when Presenter logic in Execution
   *
   * @param show -- true/false
   */
  void showProgressBar(boolean show, boolean isFirstPage);


  /**
   * Response from the Retrofit -- API Reponse
   *
   * @param reviewsDetailResponse
   */
  void movieReviewData(ReviewsDetailResponse reviewsDetailResponse, boolean isFirstPage);


  /**
   * API Error caused when retrieving the Movie Review Data
   *
   * @param errorType -- Error Type
   */
  void movieReviewError(UserAPIErrorType errorType, boolean isFirstPage);
}
