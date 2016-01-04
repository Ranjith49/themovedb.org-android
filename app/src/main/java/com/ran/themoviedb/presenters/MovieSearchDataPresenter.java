package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.fragments.MovieStoreFragment;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieSearchResponse;
import com.ran.themoviedb.model.server.service.MovieSearchServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieSearchView;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * Movie Store Presenter between View and the Service Impl
 */
public class MovieSearchDataPresenter extends BasePresenter
    implements MovieSearchServiceImpl.Handler {

  private final Context context;
  private final MovieSearchView movieSearchView;
  private final String query;
  private final int pageIndex;
  private final int uniqueId;
  private final MovieSearchServiceImpl movieService;

  public MovieSearchDataPresenter(Context context, int pageIndex, String query,
                                  MovieSearchView movieSearchView, int uniqueId) {
    this.context = context;
    this.movieSearchView = movieSearchView;
    this.pageIndex = pageIndex;
    this.uniqueId = uniqueId;
    this.query = query;
    movieService = new MovieSearchServiceImpl(this, pageIndex, query);
  }

  @Override
  public void start() {
    movieService.request(uniqueId);
    movieSearchView.showProgressBar(true, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void stop() {
    movieService.cancelRequest(uniqueId);
    movieSearchView.showProgressBar(false, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onMovieSearchResponse(MovieSearchResponse response, int uniqueId) {
    movieSearchView.showProgressBar(false, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
    movieSearchView.movieScreenData(response, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
  }

  @Override
  public void onMovieSearchAPIError(UserAPIErrorType errorType, int uniqueId) {
    movieSearchView.showProgressBar(false, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
    movieSearchView.movieScreenError(errorType, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
  }
}
