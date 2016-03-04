package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleDetailResponse;
import com.ran.themoviedb.model.server.response.PeopleKnownForResponse;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;
import com.ran.themoviedb.model.server.service.PeopleDetailServiceImpl;
import com.ran.themoviedb.model.server.service.PeopleKnownForServiceImpl;
import com.ran.themoviedb.model.server.service.TvShowDetailServiceImpl;
import com.ran.themoviedb.view_pres_med.PeopleDetailView;
import com.ran.themoviedb.view_pres_med.PeopleKnowForView;
import com.ran.themoviedb.view_pres_med.TvShowDetailView;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class PeopleKnowForPresenter extends BasePresenter
    implements PeopleKnownForServiceImpl.Handler {

  private PeopleKnowForView peopleKnowForView;
  private String peopleLang;
  private PeopleKnownForServiceImpl service;
  private int uniqueId;

  public PeopleKnowForPresenter(Context context, PeopleKnowForView peopleKnowForView, int peopleId,
                                int uniqueId) {
    this.peopleKnowForView = peopleKnowForView;
    this.peopleLang = AppSharedPreferences.getInstance(context).getAppLanguageData();
    this.uniqueId = uniqueId;
    service = new PeopleKnownForServiceImpl(this, peopleId, peopleLang);
  }

  @Override
  public void start() {
    peopleKnowForView.showProgressBar(true);
    service.request(uniqueId);
  }

  @Override
  public void stop() {
    service.cancelRequest(uniqueId);
    peopleKnowForView.showProgressBar(false);
  }

  @Override
  public void onPeopleKnownForResponse(PeopleKnownForResponse response, int uniqueId) {
    peopleKnowForView.showProgressBar(false);
    peopleKnowForView.onPeopleKnownForResponse(response);
  }

  @Override
  public void onPeopleKnownForError(UserAPIErrorType errorType, int uniqueId) {
    peopleKnowForView.showProgressBar(false);
    peopleKnowForView.onPeopleKnownForError(errorType);
  }
}
