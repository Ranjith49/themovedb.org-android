package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.fragments.MovieReviewsFragment;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieSimilarDetailsResponse;
import com.ran.themoviedb.model.server.response.TvShowSimilarDetailsResponse;
import com.ran.themoviedb.model.server.service.MovieSimilarServiceImpl;
import com.ran.themoviedb.model.server.service.TvShowSimilarServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieSimilarView;
import com.ran.themoviedb.view_pres_med.TvShowSimilarView;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class TvShowSimilarDataPresenter extends BasePresenter
    implements TvShowSimilarServiceImpl.Handler {

  private TvShowSimilarView tvShowSimilarView;
  private final int pageIndex;
  private final int uniqueId;
  private final TvShowSimilarServiceImpl service;

  public TvShowSimilarDataPresenter(Context context, TvShowSimilarView tvShowSimilarView,
                                    int pageIndex, int uniqueId, int movieId) {
    this.pageIndex = pageIndex;
    this.uniqueId = uniqueId;
    this.tvShowSimilarView = tvShowSimilarView;
    service = new TvShowSimilarServiceImpl(this, movieId,
        AppSharedPreferences.getInstance(context).getAppLanguageData(), pageIndex);
  }

  @Override
  public void start() {
    tvShowSimilarView.showProgressBar(true, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    service.request(uniqueId);
  }

  @Override
  public void stop() {
    service.cancelRequest(uniqueId);
    tvShowSimilarView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onSimilarTvShowsResponse(TvShowSimilarDetailsResponse response, int uniqueId) {
    tvShowSimilarView.similarTvShowsResponse(response,
        pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    tvShowSimilarView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onSimilarTVShowsError(UserAPIErrorType errorType, int uniqueId) {
    tvShowSimilarView.similarTvShowsError(errorType,
        pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    tvShowSimilarView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
  }
}
