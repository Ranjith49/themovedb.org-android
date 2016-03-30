package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.MovieDetailsAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieSimilarDetailsResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;

import retrofit2.Call;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class MovieSimilarServiceImpl extends BaseRetrofitService<MovieSimilarDetailsResponse> {

  private Handler handler;
  private int movieId;
  private String movieLang;
  private int pageIndex;

  public MovieSimilarServiceImpl(Handler handler, int id, String lang, int pageIndex) {
    this.movieId = id;
    this.movieLang = lang;
    this.handler = handler;
    this.pageIndex = pageIndex;
  }

  @Override
  protected void handleApiResponse(MovieSimilarDetailsResponse response, int uniqueId) {
    if (response == null || response.getResults() == null || response.getResults().size() <= 0) {
      handler.onSimilarMovieError(UserAPIErrorType.NOCONTENT_ERROR, uniqueId);
    } else {
      handler.onSimilarMoviesResponse(response, uniqueId);
    }
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onSimilarMovieError(errorType, uniqueId);
  }

  @Override
  protected Call<MovieSimilarDetailsResponse> getRetrofitCall() {
    MovieDetailsAPI service = RetrofitAdapters.getAppRestAdapter().create(MovieDetailsAPI.class);
    return service.getSimilarMovies(movieId, pageIndex, TheMovieDbConstants.APP_API_KEY, movieLang);
  }

  /**
   * Handler CallBack to presenter with Response /Error ..
   */
  public interface Handler {
    void onSimilarMoviesResponse(MovieSimilarDetailsResponse response, int uniqueId);

    void onSimilarMovieError(UserAPIErrorType errorType, int uniqueId);
  }
}
