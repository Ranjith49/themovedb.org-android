package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.MovieDetailsAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.CastCrewDetailResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;

import retrofit.Call;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class MovieCastCrewServiceImpl extends BaseRetrofitService<CastCrewDetailResponse> {

  private Handler handler;
  private int movieId;
  private String movieLang;

  public MovieCastCrewServiceImpl(Handler handler, int id, String lang) {
    this.movieId = id;
    this.movieLang = lang;
    this.handler = handler;
  }

  @Override
  protected void handleApiResponse(CastCrewDetailResponse response, int uniqueId) {
    if (response == null) {
      handler.onCastCrewError(UserAPIErrorType.NOCONTENT_ERROR, uniqueId);
    } else {
      handler.onCastCrewResponse(response, uniqueId);
    }
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onCastCrewError(errorType, uniqueId);
  }

  @Override
  protected Call<CastCrewDetailResponse> getRetrofitCall() {
    MovieDetailsAPI service = RetrofitAdapters.getAppRestAdapter().create(MovieDetailsAPI.class);
    return service.getCastCrewDetails(movieId, TheMovieDbConstants.APP_API_KEY, movieLang);
  }

  /**
   * Handler CallBack to presenter with Response /Error ..
   */
  public interface Handler {
    void onCastCrewResponse(CastCrewDetailResponse response, int uniqueId);

    void onCastCrewError(UserAPIErrorType errorType, int uniqueId);
  }
}
