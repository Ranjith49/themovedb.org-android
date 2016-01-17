package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.CastCrewDetailResponse;
import com.ran.themoviedb.model.server.service.MovieCastCrewServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieCastCrewView;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class MovieCastCrewPresenter extends BasePresenter
    implements MovieCastCrewServiceImpl.Handler {

  private MovieCastCrewView movieCastCrewView;
  private final int uniqueId;
  private final MovieCastCrewServiceImpl service;

  public MovieCastCrewPresenter(Context context, MovieCastCrewView movieCastCrewView, int uniqueId,
                                int movieId) {
    this.uniqueId = uniqueId;
    this.movieCastCrewView = movieCastCrewView;
    service = new MovieCastCrewServiceImpl(this, movieId,
        AppSharedPreferences.getInstance(context).getAppLanguageData());
  }

  @Override
  public void start() {
    movieCastCrewView.showProgressBar(true);
    service.request(uniqueId);
  }

  @Override
  public void stop() {
    service.cancelRequest(uniqueId);
    movieCastCrewView.showProgressBar(false);
  }

  @Override
  public void onCastCrewResponse(CastCrewDetailResponse response, int uniqueId) {
    movieCastCrewView.movieCastCrewData(response);
    movieCastCrewView.showProgressBar(false);
  }

  @Override
  public void onCastCrewError(UserAPIErrorType errorType, int uniqueId) {
    movieCastCrewView.movieCastCrewAPIError(errorType);
    movieCastCrewView.showProgressBar(false);
  }
}
