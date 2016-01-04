package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.fragments.TvStoreFragment;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TVShowStoreResponse;
import com.ran.themoviedb.model.server.service.TVShowStoreServiceImpl;
import com.ran.themoviedb.view_pres_med.TvStoreView;

/**
 * Created by ranjith.suda on 1/4/2016.
 * <p/>
 * TV Store Presenter between View and the Service Impl
 */
public class TvStoreDataPresenter extends BasePresenter implements TVShowStoreServiceImpl.Handler {

  private final Context context;
  private final TVShowStoreType tvStoreType;
  private final TvStoreView tvStoreView;
  private final int pageIndex;
  private final int uniqueId;
  private final TVShowStoreServiceImpl tvService;

  public TvStoreDataPresenter(Context context, TVShowStoreType tvStoreType, int pageIndex,
                              TvStoreView tvStoreView, int uniqueId) {
    this.context = context;
    this.tvStoreType = tvStoreType;
    this.tvStoreView = tvStoreView;
    this.pageIndex = pageIndex;
    this.uniqueId = uniqueId;
    tvService = new TVShowStoreServiceImpl(this, tvStoreType, pageIndex,
        AppSharedPreferences.getInstance(context).getAppLanguageData());
  }

  @Override
  public void start() {
    tvService.request(uniqueId);
    tvStoreView.showProgressBar(true, pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void stop() {
    tvService.cancelRequest(uniqueId);
    tvStoreView.showProgressBar(false, pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onTvStoreResponseRetrieved(TVShowStoreResponse response, int uniqueId) {
    tvStoreView.showProgressBar(false, pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
    tvStoreView.tvScreenData(response, pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onTvStoreAPIError(UserAPIErrorType errorType, int uniqueId) {
    tvStoreView.showProgressBar(false, pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
    tvStoreView.tvScreenError(errorType, pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
  }
}
