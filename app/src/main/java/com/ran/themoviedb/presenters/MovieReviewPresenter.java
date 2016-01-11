package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.fragments.MovieReviewsFragment;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.ReviewsDetailResponse;
import com.ran.themoviedb.model.server.service.MovieReviewServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieReviewView;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class MovieReviewPresenter extends BasePresenter implements MovieReviewServiceImpl.Handler {

  private MovieReviewView movieReviewView;
  private final int pageIndex;
  private final int uniqueId;
  private final MovieReviewServiceImpl service;

  public MovieReviewPresenter(Context context, MovieReviewView movieReviewView,
                              int pageIndex, int uniqueId, int movieId) {
    this.pageIndex = pageIndex;
    this.uniqueId = uniqueId;
    this.movieReviewView = movieReviewView;
    service = new MovieReviewServiceImpl(this, movieId,
        AppSharedPreferences.getInstance(context).getAppLanguageData(), pageIndex);
  }

  @Override
  public void start() {
    service.request(uniqueId);
    movieReviewView.showProgressBar(true, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void stop() {
    service.cancelRequest(uniqueId);
    movieReviewView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onMovieReviewResponse(ReviewsDetailResponse response, int uniqueId) {
    movieReviewView.movieReviewData(response, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    movieReviewView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onMovieError(UserAPIErrorType errorType, int uniqueId) {
    movieReviewView.movieReviewError(errorType, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    movieReviewView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
  }
}
