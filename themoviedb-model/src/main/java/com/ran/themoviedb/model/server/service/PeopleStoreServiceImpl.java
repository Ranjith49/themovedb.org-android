package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.PeopleStoreAPI;
import com.ran.themoviedb.model.server.entities.PeopleStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleStoreResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;

import retrofit.Call;

/**
 * Created by ranjith.suda on 1/1/2016.
 */
public class PeopleStoreServiceImpl extends BaseRetrofitService<PeopleStoreResponse> {

  private Handler handler;
  private PeopleStoreType peopleStoreType;
  private int pageIndex;
  private String language;

  public PeopleStoreServiceImpl(Handler handler, PeopleStoreType storeType, int page, String lang) {
    this.handler = handler;
    this.peopleStoreType = storeType;
    this.pageIndex = page;
    this.language = lang;
  }

  @Override
  protected void handleApiResponse(PeopleStoreResponse response, int uniqueId) {
    handler.onPeopleStoreResponseRetrieved(response, uniqueId);
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onPeopleStoreAPIError(errorType, uniqueId);
  }

  @Override
  protected Call<PeopleStoreResponse> getRetrofitCall() {

    PeopleStoreAPI peopleStoreAPI =
        RetrofitAdapters.getAppRestAdapter().create(PeopleStoreAPI.class);

    switch (peopleStoreType) {
      case PEOPLE_POPULAR:
      default:
        return peopleStoreAPI.getPeopleStoreList(TheMovieDbConstants.APP_API_KEY, pageIndex,
            language);
    }
  }

  /**
   * Handler call Back for the Presenters
   */
  public interface Handler {
    void onPeopleStoreResponseRetrieved(PeopleStoreResponse response, int uniqueId);

    void onPeopleStoreAPIError(UserAPIErrorType errorType, int uniqueId);
  }
}
