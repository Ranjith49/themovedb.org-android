package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.SearchAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TvShowSearchResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;

import retrofit2.Call;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class TvShowSearchServiceImpl extends BaseRetrofitService<TvShowSearchResponse> {

  private Handler handler;
  private int pageIndex;
  private String query;

  public TvShowSearchServiceImpl(Handler handler, int page, String query) {
    this.handler = handler;
    this.pageIndex = page;
    this.query = query;
  }

  @Override
  protected void handleApiResponse(TvShowSearchResponse response, int uniqueId) {
    if (response == null || response.getResults() == null || response.getResults().size() <= 0) {
      handler.onTvSearchAPIError(UserAPIErrorType.NOCONTENT_ERROR, uniqueId);
    } else {
      handler.onTvSearchResponse(response, uniqueId);
    }
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onTvSearchAPIError(errorType, uniqueId);
  }

  @Override
  protected Call<TvShowSearchResponse> getRetrofitCall() {
    SearchAPI searchAPI = RetrofitAdapters.getAppRestAdapter().create(SearchAPI.class);
    return searchAPI.getTvSearchResults(TheMovieDbConstants.APP_API_KEY, pageIndex, query);
  }

  /**
   * Handler callbacks for the Presenter ..
   */
  public interface Handler {

    void onTvSearchResponse(TvShowSearchResponse response, int uniqueId);

    void onTvSearchAPIError(UserAPIErrorType errorType, int uniqueId);
  }
}
