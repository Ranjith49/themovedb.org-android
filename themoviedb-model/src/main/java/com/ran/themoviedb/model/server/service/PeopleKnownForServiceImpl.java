package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.PeopleDetailsAPI;
import com.ran.themoviedb.model.server.api.TvShowDetailsAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleDetailResponse;
import com.ran.themoviedb.model.server.response.PeopleKnownForResponse;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;
import com.ran.themoviedb.model.utils.RetrofitAdapters;

import retrofit.Call;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class PeopleKnownForServiceImpl extends BaseRetrofitService<PeopleKnownForResponse> {

  private Handler handler;
  private int peopleId;
  private String language;

  public PeopleKnownForServiceImpl(Handler handler, int tvShowId, String language) {
    this.handler = handler;
    this.peopleId = tvShowId;
    this.language = language;
  }

  @Override
  protected void handleApiResponse(PeopleKnownForResponse response, int uniqueId) {
    handler.onPeopleKnownForResponse(response, uniqueId);
  }

  @Override
  protected void handleError(UserAPIErrorType errorType, int uniqueId) {
    handler.onPeopleKnownForError(errorType, uniqueId);
  }

  @Override
  protected Call<PeopleKnownForResponse> getRetrofitCall() {
    PeopleDetailsAPI service =
        RetrofitAdapters.getAppRestAdapter().create(PeopleDetailsAPI.class);
    return service.getPeopleKnownForResponse(peopleId, TheMovieDbConstants.APP_API_KEY, language);
  }

  /**
   * Handler CallBack to presenter with Response /Error ..
   */
  public interface Handler {
    void onPeopleKnownForResponse(PeopleKnownForResponse response, int uniqueId);

    void onPeopleKnownForError(UserAPIErrorType errorType, int uniqueId);
  }
}
