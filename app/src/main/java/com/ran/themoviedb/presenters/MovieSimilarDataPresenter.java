package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.fragments.MovieReviewsFragment;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieSimilarDetailsResponse;
import com.ran.themoviedb.model.server.service.MovieSimilarServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieSimilarView;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class MovieSimilarDataPresenter extends BasePresenter
    implements MovieSimilarServiceImpl.Handler {

  private MovieSimilarView movieSimilarView;
  private final int pageIndex;
  private final int uniqueId;
  private final MovieSimilarServiceImpl service;

  public MovieSimilarDataPresenter(Context context, MovieSimilarView movieSimilarView,
                                   int pageIndex, int uniqueId, int movieId) {
    this.pageIndex = pageIndex;
    this.uniqueId = uniqueId;
    this.movieSimilarView = movieSimilarView;
    service = new MovieSimilarServiceImpl(this, movieId,
        AppSharedPreferences.getInstance(context).getAppLanguageData(), pageIndex);
  }

  @Override
  public void start() {
    service.request(uniqueId);
    movieSimilarView.showProgressBar(true, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void stop() {
    service.cancelRequest(uniqueId);
    movieSimilarView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onSimilarMoviesResponse(MovieSimilarDetailsResponse response, int uniqueId) {
    movieSimilarView.similarMovieData(response, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    movieSimilarView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onSimilarMovieError(UserAPIErrorType errorType, int uniqueId) {
    movieSimilarView.similarMovieError(errorType,
        pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    movieSimilarView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
  }
}
