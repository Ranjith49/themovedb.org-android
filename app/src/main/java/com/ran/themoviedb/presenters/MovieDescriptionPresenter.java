package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieDetailResponse;
import com.ran.themoviedb.model.server.service.MovieDescriptionServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieDescriptionView;

/**
 * Created by ranjith.suda on 1/9/2016.
 */
public class MovieDescriptionPresenter extends BasePresenter implements
    MovieDescriptionServiceImpl.Handler {

  private MovieDescriptionView movieDescriptionView;
  private int movieId;
  private String movieLang;
  private MovieDescriptionServiceImpl service;
  private int uniqueId;

  public MovieDescriptionPresenter(Context context, MovieDescriptionView movieDescriptionView, int
      movieId, int uniqueId) {
    this.movieDescriptionView = movieDescriptionView;
    this.movieId = movieId;
    this.movieLang = AppSharedPreferences.getInstance(context).getAppLanguageData();
    this.uniqueId = uniqueId;

    service = new MovieDescriptionServiceImpl(this, movieId, movieLang);
  }

  @Override
  public void start() {
    movieDescriptionView.showProgressBar(true);
    service.request(uniqueId);
  }

  @Override
  public void stop() {
    service.cancelRequest(uniqueId);
    movieDescriptionView.showProgressBar(false);
  }

  @Override
  public void onMovieDetailResponse(MovieDetailResponse response, int uniqueId) {
    movieDescriptionView.showProgressBar(false);
    movieDescriptionView.movieResponse(response);
  }

  @Override
  public void onMovieError(UserAPIErrorType errorType, int uniqueId) {
    movieDescriptionView.showProgressBar(false);
    movieDescriptionView.movieError(errorType);
  }
}
