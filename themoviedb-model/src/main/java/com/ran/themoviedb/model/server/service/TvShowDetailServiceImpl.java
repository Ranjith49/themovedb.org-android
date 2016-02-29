package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.TvShowDetailsAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;

import retrofit.Call;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class TvShowDetailServiceImpl extends BaseRetrofitService<TvShowDetailResponse> {

  private Handler handler;
  private int tvShowId;
  private String language;

  public TvShowDetailServiceImpl(Handler handler, int tvShowId, String language) {
    this.handler = handler;
    this.tvShowId = tvShowId;
    this.language = language;
  }

  @Override
  protected void handleApiResponse(TvShowDetailResponse response, int uniqueId) {
    handler.onTvShowDetailResponse(response, uniqueId);
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onTvShowError(errorType, uniqueId);
  }

  @Override
  protected Call<TvShowDetailResponse> getRetrofitCall() {
    TvShowDetailsAPI service =
        RetrofitAdapters.getAppRestAdapter().create(TvShowDetailsAPI.class);
    return service.getTvShowBasicDetails(tvShowId, TheMovieDbConstants.APP_API_KEY, language);
  }

  /**
   * Handler CallBack to presenter with Response /Error ..
   */
  public interface Handler {
    void onTvShowDetailResponse(TvShowDetailResponse response, int uniqueId);

    void onTvShowError(UserAPIErrorType errorType, int uniqueId);
  }
}
