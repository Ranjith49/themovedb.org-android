package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.fragments.MovieStoreFragment;
import com.ran.themoviedb.model.server.entities.MovieStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieStoreResponse;
import com.ran.themoviedb.model.server.service.MovieStoreServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieStoreView;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * Movie Store Presenter between View and the Service Impl
 */
public class MovieStoreDataPresenter extends BasePresenter
    implements MovieStoreServiceImpl.Handler {

  private final Context context;
  private final MovieStoreType movieStoreType;
  private final MovieStoreView movieStoreView;
  private final int pageIndex;
  private final int uniqueId;
  private final MovieStoreServiceImpl movieService;

  public MovieStoreDataPresenter(Context context, MovieStoreType movieStoreType, int pageIndex,
                                 MovieStoreView movieStoreView, int uniqueId) {
    this.context = context;
    this.movieStoreType = movieStoreType;
    this.movieStoreView = movieStoreView;
    this.pageIndex = pageIndex;
    this.uniqueId = uniqueId;
    movieService = new MovieStoreServiceImpl(this, movieStoreType, pageIndex,
        AppSharedPreferences.getInstance(context).getAppLanguageData());
  }

  @Override
  public void start() {
    movieService.request(uniqueId);
    movieStoreView.showProgressBar(true, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void stop() {
    movieService.cancelRequest(uniqueId);
    movieStoreView.showProgressBar(false, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onMovieStoreResponse(MovieStoreResponse response, int uniqueId) {
    movieStoreView.showProgressBar(false, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
    movieStoreView.movieScreenData(response, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onMovieStoreAPIError(UserAPIErrorType errorType, int uniqueId) {
    movieStoreView.showProgressBar(false, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
    movieStoreView.movieScreenError(errorType, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
  }
}
