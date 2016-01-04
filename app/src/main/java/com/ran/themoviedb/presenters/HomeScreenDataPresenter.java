package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.MovieStoreResults;
import com.ran.themoviedb.model.server.entities.MovieStoreType;
import com.ran.themoviedb.model.server.entities.PeopleStoreResults;
import com.ran.themoviedb.model.server.entities.PeopleStoreType;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;
import com.ran.themoviedb.model.server.entities.TvShowStoreResults;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieStoreResponse;
import com.ran.themoviedb.model.server.response.PeopleStoreResponse;
import com.ran.themoviedb.model.server.response.TVShowStoreResponse;
import com.ran.themoviedb.model.server.service.MovieStoreServiceImpl;
import com.ran.themoviedb.model.server.service.PeopleStoreServiceImpl;
import com.ran.themoviedb.model.server.service.TVShowStoreServiceImpl;
import com.ran.themoviedb.view_pres_med.HomeScreenView;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/1/2016.
 *
 * Home Screen Presenter ,for handling Movies and TV of type {@link com.ran.themoviedb
 * .customviews.HomeBannerView} and People of type {@link com.ran.themoviedb.customviews.HomePosterView}
 */
public class HomeScreenDataPresenter extends BasePresenter
    implements MovieStoreServiceImpl.Handler, TVShowStoreServiceImpl.Handler,
    PeopleStoreServiceImpl.Handler {

  private Context context;
  private HomeScreenView homeScreenView;
  private MovieStoreServiceImpl movieService;
  private TVShowStoreServiceImpl tvService;
  private PeopleStoreServiceImpl peopleService;
  private int uniqueId;

  private MovieStoreType movieStoreType;
  private TVShowStoreType tvShowStoreType;
  private PeopleStoreType peopleStoreType;
  private final int PAGE_INDEX = 1;

  private ArrayList<String> movieBannerUrls;
  private ArrayList<String> tvBannerUrls;
  private ArrayList<String> peoplePosterUrls;
  private boolean movieAPISuccess;
  private boolean tvAPISuccess;
  private boolean peopleAPISuccess;

  public HomeScreenDataPresenter(Context context, HomeScreenView homeScreenView, int uniqueId,
                                 MovieStoreType movieStoreType, TVShowStoreType tvShowStoreType,
                                 PeopleStoreType peopleStoreType) {
    this.context = context;
    this.homeScreenView = homeScreenView;
    this.uniqueId = uniqueId;
    this.tvShowStoreType = tvShowStoreType;
    this.movieStoreType = movieStoreType;
    this.peopleStoreType = peopleStoreType;

    movieService = new MovieStoreServiceImpl(this, movieStoreType, PAGE_INDEX,
        AppSharedPreferences.getInstance(context).getAppLanguageData());
    peopleService = new PeopleStoreServiceImpl(this, peopleStoreType, PAGE_INDEX,
        AppSharedPreferences.getInstance(context).getAppLanguageData());
    tvService = new TVShowStoreServiceImpl(this, tvShowStoreType, PAGE_INDEX,
        AppSharedPreferences.getInstance(context).getAppLanguageData());
  }

  @Override
  public void start() {
    movieBannerUrls = new ArrayList<>();
    tvBannerUrls = new ArrayList<>();
    peoplePosterUrls = new ArrayList<>();

    movieAPISuccess = peopleAPISuccess = tvAPISuccess = false;

    movieService.request(uniqueId);
    peopleService.request(uniqueId);
    tvService.request(uniqueId);
    homeScreenView.showProgressBar(true);
  }

  @Override
  public void stop() {
    cancelPendingServiceRequests();
    homeScreenView.showProgressBar(false);
  }

  /**
   * Utility to Cancel Any Pending Requests on
   * a) Stop Presenter
   * b) Any Service Impl retrieves Error ..
   */
  private void cancelPendingServiceRequests() {
    homeScreenView.showProgressBar(false);

    movieService.cancelRequest(uniqueId);
    peopleService.cancelRequest(uniqueId);
    tvService.cancelRequest(uniqueId);

    movieAPISuccess = tvAPISuccess = peopleAPISuccess = false;
  }

  /**
   * Utility to make sure the all Three Service APIs returned Data , send to UI
   */
  private synchronized void postHomeScreenData() {
    if (peopleAPISuccess && movieAPISuccess && tvAPISuccess) {
      homeScreenView.showProgressBar(false);
      homeScreenView.homeScreenData(movieBannerUrls, tvBannerUrls, peoplePosterUrls);
    }
  }

  // -- Call backs from all Three [Movie ,TV and People ] Service Impl ..//
  @Override
  public void onPeopleStoreResponseRetrieved(PeopleStoreResponse response, int uniqueId) {
    for (PeopleStoreResults peopleStoreResults : response.getResults()) {
      peoplePosterUrls.add(peopleStoreResults.getProfile_path());
    }
    peopleAPISuccess = true;
    postHomeScreenData();
  }

  @Override
  public void onMovieStoreResponse(MovieStoreResponse response, int uniqueId) {
    for (MovieStoreResults movieStoreResults : response.getResults()) {
      movieBannerUrls.add(movieStoreResults.getBackdrop_path());
    }
    movieAPISuccess = true;
    postHomeScreenData();
  }

  @Override
  public void onTvStoreResponseRetrieved(TVShowStoreResponse response, int uniqueId) {
    for (TvShowStoreResults tvShowStoreResults : response.getResults()) {
      tvBannerUrls.add(tvShowStoreResults.getBackdrop_path());
    }
    tvAPISuccess = true;
    postHomeScreenData();
  }

  @Override
  public void onTvStoreAPIError(UserAPIErrorType errorType, int uniqueId) {
    homeScreenView.homeScreenError(errorType);
    cancelPendingServiceRequests();
  }

  @Override
  public void onPeopleStoreAPIError(UserAPIErrorType errorType, int uniqueId) {
    homeScreenView.homeScreenError(errorType);
    cancelPendingServiceRequests();
  }

  @Override
  public void onMovieStoreAPIError(UserAPIErrorType errorType, int uniqueId) {
    homeScreenView.homeScreenError(errorType);
    cancelPendingServiceRequests();
  }
}
