package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.SearchAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleSearchResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;

import retrofit2.Call;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class PeopleSearchServiceImpl extends BaseRetrofitService<PeopleSearchResponse> {

  private Handler handler;
  private int pageIndex;
  private String query;

  public PeopleSearchServiceImpl(Handler handler, int page, String query) {
    this.handler = handler;
    this.pageIndex = page;
    this.query = query;
  }

  @Override
  protected void handleApiResponse(PeopleSearchResponse response, int uniqueId) {
    if (response == null || response.getResults() == null|| response.getResults().size() <= 0) {
      handler.onPeopleSearchAPIError(UserAPIErrorType.NOCONTENT_ERROR, uniqueId);
    } else {
      handler.onPeopleSearchResponse(response, uniqueId);
    }
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onPeopleSearchAPIError(errorType, uniqueId);
  }

  @Override
  protected Call<PeopleSearchResponse> getRetrofitCall() {
    SearchAPI searchAPI = RetrofitAdapters.getAppRestAdapter().create(SearchAPI.class);
    return searchAPI.getPeopleSearchResults(TheMovieDbConstants.APP_API_KEY, pageIndex, query);
  }

  /**
   * Handler callbacks for the Presenter ..
   */
  public interface Handler {

    void onPeopleSearchResponse(PeopleSearchResponse response, int uniqueId);

    void onPeopleSearchAPIError(UserAPIErrorType errorType, int uniqueId);
  }
}
