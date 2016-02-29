package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;
import com.ran.themoviedb.model.server.service.TvShowDetailServiceImpl;
import com.ran.themoviedb.view_pres_med.TvShowDetailView;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class TvShowDetailPresenter extends BasePresenter
    implements TvShowDetailServiceImpl.Handler {

  private TvShowDetailView tvShowDetailView;
  private String tvShowLang;
  private TvShowDetailServiceImpl service;
  private int uniqueId;

  public TvShowDetailPresenter(Context context, TvShowDetailView tvShowDetailView, int tvShowId,
                               int uniqueId) {
    this.tvShowDetailView = tvShowDetailView;
    this.tvShowLang = AppSharedPreferences.getInstance(context).getAppLanguageData();
    this.uniqueId = uniqueId;
    service = new TvShowDetailServiceImpl(this, tvShowId, tvShowLang);
  }

  @Override
  public void start() {
    tvShowDetailView.showProgressBar(true);
    service.request(uniqueId);
  }

  @Override
  public void stop() {
    service.cancelRequest(uniqueId);
    tvShowDetailView.showProgressBar(false);
  }

  @Override
  public void onTvShowDetailResponse(TvShowDetailResponse response, int uniqueId) {
    tvShowDetailView.showProgressBar(false);
    tvShowDetailView.tvShowResponse(response);
  }

  @Override
  public void onTvShowError(UserAPIErrorType errorType, int uniqueId) {
    tvShowDetailView.showProgressBar(false);
    tvShowDetailView.tvShowError(errorType);
  }
}
