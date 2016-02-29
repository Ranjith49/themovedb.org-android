package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleDetailResponse;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;
import com.ran.themoviedb.model.server.service.PeopleDetailServiceImpl;
import com.ran.themoviedb.model.server.service.TvShowDetailServiceImpl;
import com.ran.themoviedb.view_pres_med.PeopleDetailView;
import com.ran.themoviedb.view_pres_med.TvShowDetailView;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class PeopleDetailPresenter extends BasePresenter
    implements PeopleDetailServiceImpl.Handler {

  private PeopleDetailView peopleDetailView;
  private String peopleLang;
  private PeopleDetailServiceImpl service;
  private int uniqueId;

  public PeopleDetailPresenter(Context context, PeopleDetailView peopleDetailView, int peopleId,
                               int uniqueId) {
    this.peopleDetailView = peopleDetailView;
    this.peopleLang = AppSharedPreferences.getInstance(context).getAppLanguageData();
    this.uniqueId = uniqueId;
    service = new PeopleDetailServiceImpl(this, peopleId, peopleLang);
  }

  @Override
  public void start() {
    peopleDetailView.showProgressBar(true);
    service.request(uniqueId);
  }

  @Override
  public void stop() {
    service.cancelRequest(uniqueId);
    peopleDetailView.showProgressBar(false);
  }

  @Override
  public void onPeopleDetailResponse(PeopleDetailResponse response, int uniqueId) {
    peopleDetailView.showProgressBar(false);
    peopleDetailView.onPeopleDetailResponse(response);
  }

  @Override
  public void onPeopleDetailError(UserAPIErrorType errorType, int uniqueId) {
    peopleDetailView.showProgressBar(false);
    peopleDetailView.onPeopleDetailError(errorType);
  }
}
