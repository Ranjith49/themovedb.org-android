package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.TVShowStoreAPI;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TVShowStoreResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;

import retrofit.Call;

/**
 * Created by ranjith.suda on 1/1/2016.
 * <p/>
 * Retrofit Service Impl for the TvShowStore {@link TVShowStoreAPI}
 */
public class TVShowStoreServiceImpl extends BaseRetrofitService<TVShowStoreResponse> {

  private Handler handler;
  private TVShowStoreType tvShowStoreType;
  private int pageIndex;

  public TVShowStoreServiceImpl(Handler handler, TVShowStoreType storeType, int page) {
    this.handler = handler;
    this.tvShowStoreType = storeType;
    this.pageIndex = page;
  }

  @Override
  protected void handleApiResponse(TVShowStoreResponse response, int uniqueId) {
    handler.onTvStoreResponseRetrieved(response, uniqueId);
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onTvStoreAPIError(errorType, uniqueId);
  }

  @Override
  protected Call<TVShowStoreResponse> getRetrofitCall() {

    TVShowStoreAPI service =
        RetrofitAdapters.getAppRestAdapter().create(TVShowStoreAPI.class);

    switch (tvShowStoreType) {
      case TV_AIR:
        return service.getOnAirTVShows(TheMovieDbConstants.APP_API_KEY, pageIndex);
      case TV_POPULAR:
        return service.getPopularTVShows(TheMovieDbConstants.APP_API_KEY, pageIndex);
      case TV_TOP_RATED:
        return service.getTopRatedTVShows(TheMovieDbConstants.APP_API_KEY, pageIndex);
      default:
        return service.getPopularTVShows(TheMovieDbConstants.APP_API_KEY, pageIndex);
    }
  }

  /**
   * Handler call Back for the Presenters
   */
  public interface Handler {
    void onTvStoreResponseRetrieved(TVShowStoreResponse response, int uniqueId);

    void onTvStoreAPIError(UserAPIErrorType errorType, int uniqueId);
  }
}
