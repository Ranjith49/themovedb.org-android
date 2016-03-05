package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.preloaddb.BaseSnappyDBService;
import com.ran.themoviedb.model.server.api.TheMovieDbConfigAPI;
import com.ran.themoviedb.model.server.response.TheMovieDbConfigResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;

import retrofit.Call;

/**
 * Created by ranjith.suda on 12/30/2015.
 * Retrofit Service Implementation for {@see TheMovieDbConfigAPI}
 */
public class TheMovieDbConfigServiceImpl extends BaseSnappyDBService<TheMovieDbConfigResponse> {

  private Handler handler;

  public TheMovieDbConfigServiceImpl(Handler handler) {
    this.handler = handler;
  }

  @Override
  protected void handleApiResponse(TheMovieDbConfigResponse response, int uniqueId) {
    handler.onConfigReturned(response, uniqueId);
  }

  @Override
  protected Call<TheMovieDbConfigResponse> getRetrofitCall() {
    TheMovieDbConfigAPI service =
        RetrofitAdapters.getAppRestAdapter().create(TheMovieDbConfigAPI.class);
    return service.getAppConfig(TheMovieDbConstants.APP_API_KEY);
  }

  /**
   * Handler CallBack to presenter with Response /Error ..
   */
  public interface Handler {
     void onConfigReturned(TheMovieDbConfigResponse response, int uniqueId);
  }
}
