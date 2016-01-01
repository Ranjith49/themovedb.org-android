package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.MovieStoreAPI;
import com.ran.themoviedb.model.server.entities.MovieStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieStoreResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;

import retrofit.Call;

/**
 * Created by ranjith.suda on 1/1/2016.
 * <p/>
 * Retrofit Service Impl to get MovieStore Response for {@link MovieStoreAPI}
 */
public class MovieStoreServiceImpl extends BaseRetrofitService<MovieStoreResponse> {

  private Handler handler;
  private MovieStoreType movieStoreType;
  private int pageIndex;

  public MovieStoreServiceImpl(Handler handler, MovieStoreType storeType, int page) {
    this.handler = handler;
    this.movieStoreType = storeType;
    this.pageIndex = page;
  }

  @Override
  protected void handleApiResponse(MovieStoreResponse response, int uniqueId) {
    handler.onMovieStoreResponse(response, uniqueId);
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onMovieStoreAPIError(errorType, uniqueId);
  }

  @Override
  protected Call<MovieStoreResponse> getRetrofitCall() {
    MovieStoreAPI service =
        RetrofitAdapters.getAppRestAdapter().create(MovieStoreAPI.class);
    switch (movieStoreType) {
      case MOVIE_POPULAR:
        return service.getPopularMovieList(TheMovieDbConstants.APP_API_KEY, pageIndex);
      case MOVIE_NOW_PLAYING:
        return service.getNowShowingMovieList(TheMovieDbConstants.APP_API_KEY, pageIndex);
      case MOVIE_TOP_RATED:
        return service.getTopRatedMovieList(TheMovieDbConstants.APP_API_KEY, pageIndex);
      case MOVIE_UPCOMING:
        return service.getUpComingMovieList(TheMovieDbConstants.APP_API_KEY, pageIndex);
      default:
        return service.getPopularMovieList(TheMovieDbConstants.APP_API_KEY, pageIndex);
    }
  }

  /**
   * Handler callbacks for the Presenter ..
   */
  public interface Handler {

    void onMovieStoreResponse(MovieStoreResponse response, int uniqueId);

    void onMovieStoreAPIError(UserAPIErrorType errorType, int uniqueId);
  }
}
