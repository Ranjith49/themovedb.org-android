package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.AllGenreListAPI;
import com.ran.themoviedb.model.server.response.AllMovieGenreListResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;

import retrofit.Call;

/**
 * Created by ranjith.suda on 12/29/2015.
 * <p/>
 * Retrofit Service Implementation for {@see AllGenreListAPI}
 */
public class AllMoviesGenreServiceImpl extends BaseRetrofitService<AllMovieGenreListResponse> {

  private Handler handler;

  public AllMoviesGenreServiceImpl(Handler handler) {
    this.handler = handler;
  }

  @Override
  protected void handleApiResponse(AllMovieGenreListResponse response, int uniqueId) {
    handler.onAllMovieGenreListRetrieved(response, uniqueId);
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onAllMovieGenreAPIError(errorType, uniqueId);
  }

  @Override
  protected Call<AllMovieGenreListResponse> getRetrofitCall() {
    AllGenreListAPI service =
        RetrofitAdapters.getAppRestAdapter().create(AllGenreListAPI.class);
    return service.getMovieGenreList(TheMovieDbConstants.APP_API_KEY);
  }

  /**
   * Handler CallBack to presenter with Response /Error ..
   */
  public interface Handler {
    void onAllMovieGenreListRetrieved(AllMovieGenreListResponse response, int uniqueId);

    void onAllMovieGenreAPIError(UserAPIErrorType errorType, int uniqueId);
  }
}
