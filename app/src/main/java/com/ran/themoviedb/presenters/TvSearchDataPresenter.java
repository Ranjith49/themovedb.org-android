package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.fragments.SearchTvFragment;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TvShowSearchResponse;
import com.ran.themoviedb.model.server.service.TvShowSearchServiceImpl;
import com.ran.themoviedb.view_pres_med.TvSearchView;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p>
 * Movie Store Presenter between View and the Service Impl
 */
public class TvSearchDataPresenter extends BasePresenter
    implements TvShowSearchServiceImpl.Handler {

  private final Context context;
  private final TvSearchView tvSearchView;
  private final String query;
  private final int pageIndex;
  private final int uniqueId;
  private final TvShowSearchServiceImpl serviceImpl;

  public TvSearchDataPresenter(Context context, int pageIndex, String query,
                               TvSearchView tvSearchView, int uniqueId) {
    this.context = context;
    this.tvSearchView = tvSearchView;
    this.pageIndex = pageIndex;
    this.uniqueId = uniqueId;
    this.query = query;
    serviceImpl = new TvShowSearchServiceImpl(this, pageIndex, query);
  }

  @Override
  public void start() {
    tvSearchView.showProgressBar(true, pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
    serviceImpl.request(uniqueId);
  }

  @Override
  public void stop() {
    serviceImpl.cancelRequest(uniqueId);
    tvSearchView.showProgressBar(false, pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onTvSearchResponse(TvShowSearchResponse response, int uniqueId) {
    tvSearchView.showProgressBar(false, pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
    tvSearchView.tvScreenData(response, pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onTvSearchAPIError(UserAPIErrorType errorType, int uniqueId) {
    tvSearchView.showProgressBar(false, pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
    tvSearchView.tvScreenError(errorType, pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
  }
}
