package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.PeopleDetailsAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleDetailResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;

import retrofit2.Call;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class PeopleDetailServiceImpl extends BaseRetrofitService<PeopleDetailResponse> {

  private Handler handler;
  private int peopleId;
  private String language;

  public PeopleDetailServiceImpl(Handler handler, int tvShowId, String language) {
    this.handler = handler;
    this.peopleId = tvShowId;
    this.language = language;
  }

  @Override
  protected void handleApiResponse(PeopleDetailResponse response, int uniqueId) {
    handler.onPeopleDetailResponse(response, uniqueId);
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onPeopleDetailError(errorType, uniqueId);
  }

  @Override
  protected Call<PeopleDetailResponse> getRetrofitCall() {
    PeopleDetailsAPI service =
        RetrofitAdapters.getAppRestAdapter().create(PeopleDetailsAPI.class);
    return service.getPeopleDetailResponse(peopleId, TheMovieDbConstants.APP_API_KEY, language);
  }

  /**
   * Handler CallBack to presenter with Response /Error ..
   */
  public interface Handler {
    void onPeopleDetailResponse(PeopleDetailResponse response, int uniqueId);

    void onPeopleDetailError(UserAPIErrorType errorType, int uniqueId);
  }
}
