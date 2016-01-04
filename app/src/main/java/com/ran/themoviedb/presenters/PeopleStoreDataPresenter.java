package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.fragments.PeopleStoreFragment;
import com.ran.themoviedb.model.server.entities.PeopleStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleStoreResponse;
import com.ran.themoviedb.model.server.service.PeopleStoreServiceImpl;
import com.ran.themoviedb.view_pres_med.PeopleStoreView;

/**
 * Created by ranjith.suda on 1/4/2016.
 *
 *  People Store Presenter between View and the Service Impl
 */
public class PeopleStoreDataPresenter extends BasePresenter
    implements PeopleStoreServiceImpl.Handler {

  private final Context context;
  private final PeopleStoreType peopleStoreType;
  private final PeopleStoreView peopleStoreView;
  private final int pageIndex;
  private final int uniqueId;
  private final PeopleStoreServiceImpl serviceImpl;

  public PeopleStoreDataPresenter(Context context, PeopleStoreType peopleStoreType, int pageIndex,
                                  PeopleStoreView peopleStoreView, int uniqueId) {
    this.context = context;
    this.peopleStoreType = peopleStoreType;
    this.peopleStoreView = peopleStoreView;
    this.pageIndex = pageIndex;
    this.uniqueId = uniqueId;
    serviceImpl = new PeopleStoreServiceImpl(this, peopleStoreType, pageIndex,
        AppSharedPreferences.getInstance(context).getAppLanguageData());
  }

  @Override
  public void start() {
    serviceImpl.request(uniqueId);
    peopleStoreView.showProgressBar(true, pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void stop() {
    serviceImpl.cancelRequest(uniqueId);
    peopleStoreView.showProgressBar(false, pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onPeopleStoreResponseRetrieved(PeopleStoreResponse response, int uniqueId) {
    peopleStoreView.showProgressBar(false, pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
    peopleStoreView.peopleScreenData(response, pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onPeopleStoreAPIError(UserAPIErrorType errorType, int uniqueId) {
    peopleStoreView.showProgressBar(false, pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
    peopleStoreView.peopleScreenAPIError(errorType,
        pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
  }
}
