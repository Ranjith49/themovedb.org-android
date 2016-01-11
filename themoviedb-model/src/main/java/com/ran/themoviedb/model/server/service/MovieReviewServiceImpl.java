package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.MovieDetailsAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.ReviewsDetailResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;

import retrofit.Call;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class MovieReviewServiceImpl extends BaseRetrofitService<ReviewsDetailResponse> {

  private Handler handler;
  private int movieId;
  private String movieLang;
  private int pageIndex;

  public MovieReviewServiceImpl(Handler handler, int id, String lang,int pageIndex) {
    this.movieId = id;
    this.movieLang = lang;
    this.handler = handler;
    this.pageIndex = pageIndex;
  }

  @Override
  protected void handleApiResponse(ReviewsDetailResponse response, int uniqueId) {
    if (response == null || response.getResults() == null || response.getResults().size() <= 0) {
      handler.onMovieError(UserAPIErrorType.NOCONTENT_ERROR, uniqueId);
    } else {
      handler.onMovieReviewResponse(response, uniqueId);
    }
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onMovieError(errorType, uniqueId);
  }

  @Override
  protected Call<ReviewsDetailResponse> getRetrofitCall() {
    MovieDetailsAPI service = RetrofitAdapters.getAppRestAdapter().create(MovieDetailsAPI.class);
    return service.getReviewDetails(movieId, pageIndex, TheMovieDbConstants.APP_API_KEY, movieLang);
  }

  /**
   * Handler CallBack to presenter with Response /Error ..
   */
  public interface Handler {
    void onMovieReviewResponse(ReviewsDetailResponse response, int uniqueId);

    void onMovieError(UserAPIErrorType errorType, int uniqueId);
  }
}
