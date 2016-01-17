package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.fragments.SearchPeopleFragment;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleSearchResponse;
import com.ran.themoviedb.model.server.service.PeopleSearchServiceImpl;
import com.ran.themoviedb.view_pres_med.PeopleSearchView;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p>
 * Movie Store Presenter between View and the Service Impl
 */
public class PeopleSearchDataPresenter extends BasePresenter
    implements PeopleSearchServiceImpl.Handler {

  private final Context context;
  private final PeopleSearchView peopleSearchView;
  private final String query;
  private final int pageIndex;
  private final int uniqueId;
  private final PeopleSearchServiceImpl serviceImpl;

  public PeopleSearchDataPresenter(Context context, int pageIndex, String query,
                                   PeopleSearchView peopleSearchView, int uniqueId) {
    this.context = context;
    this.peopleSearchView = peopleSearchView;
    this.pageIndex = pageIndex;
    this.uniqueId = uniqueId;
    this.query = query;
    serviceImpl = new PeopleSearchServiceImpl(this, pageIndex, query);
  }

  @Override
  public void start() {
    peopleSearchView.showProgressBar(true, pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
    serviceImpl.request(uniqueId);
  }

  @Override
  public void stop() {
    serviceImpl.cancelRequest(uniqueId);
    peopleSearchView.showProgressBar(false, pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onPeopleSearchResponse(PeopleSearchResponse response, int uniqueId) {
    peopleSearchView.showProgressBar(false, pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
    peopleSearchView.peopleScreenData(response, pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onPeopleSearchAPIError(UserAPIErrorType errorType, int uniqueId) {
    peopleSearchView.showProgressBar(false, pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
    peopleSearchView.peopleScreenError(errorType,
        pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
  }
}
