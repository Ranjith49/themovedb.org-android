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
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.MovieStoreResponse;
import com.ran.themoviedb.model.server.response.PeopleStoreResponse;
import com.ran.themoviedb.model.server.response.TVShowStoreResponse;
import com.ran.themoviedb.model.server.service.MovieStoreServiceImpl;
import com.ran.themoviedb.model.server.service.PeopleStoreServiceImpl;
import com.ran.themoviedb.model.server.service.TVShowStoreServiceImpl;
import com.ran.themoviedb.view_pres_med.HomeScreenView;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/1/2016.
 * <p>
 * Home Screen Presenter ,for handling Movies and TV of type {@link com.ran.themoviedb
 * .customviews.HomeBannerView} and People of type {@link com.ran.themoviedb.customviews.HomePosterView}
 */
public class HomeScreenDataPresenter extends BasePresenter {


    private HomeScreenView homeScreenView;
    private MovieStoreServiceImpl movieService;
    private TVShowStoreServiceImpl tvService;
    private PeopleStoreServiceImpl peopleService;


    private final int PAGE_INDEX = 1;

    private ArrayList<String> movieBannerUrls;
    private ArrayList<String> tvBannerUrls;
    private ArrayList<String> peoplePosterUrls;
    private boolean movieAPISuccess;
    private boolean tvAPISuccess;
    private boolean peopleAPISuccess;

    public HomeScreenDataPresenter(Context context, HomeScreenView homeScreenView,
                                   MovieStoreType movieStoreType,
                                   TVShowStoreType tvShowStoreType,
                                   PeopleStoreType peopleStoreType) {
        super();
        this.homeScreenView = homeScreenView;

        movieService = new MovieStoreServiceImpl(movieStoreType, PAGE_INDEX,
                AppSharedPreferences.getInstance(context).getAppLanguageData());
        peopleService = new PeopleStoreServiceImpl(peopleStoreType, PAGE_INDEX,
                AppSharedPreferences.getInstance(context).getAppLanguageData());
        tvService = new TVShowStoreServiceImpl(tvShowStoreType, PAGE_INDEX,
                AppSharedPreferences.getInstance(context).getAppLanguageData());

    }

    @Override
    public void start() {
        homeScreenView.showProgressBar(true);

        movieBannerUrls = new ArrayList<>();
        tvBannerUrls = new ArrayList<>();
        peoplePosterUrls = new ArrayList<>();

        movieAPISuccess = peopleAPISuccess = tvAPISuccess = false;

        disposable.add(movieService.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMovieStoreResponse, this::onHomeScreenAPIError));
        disposable.add(peopleService.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onPeopleStoreResponseRetrieved, this::onHomeScreenAPIError));
        disposable.add(tvService.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTvStoreResponseRetrieved, this::onHomeScreenAPIError));
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

        cancelReq();
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


    private void onPeopleStoreResponseRetrieved(PeopleStoreResponse response) {
        for (PeopleStoreResults peopleStoreResults : response.getResults()) {
            peoplePosterUrls.add(peopleStoreResults.getProfile_path());
        }
        peopleAPISuccess = true;
        postHomeScreenData();
    }

    private void onMovieStoreResponse(MovieStoreResponse response) {
        for (MovieStoreResults movieStoreResults : response.getResults()) {
            movieBannerUrls.add(movieStoreResults.getBackdrop_path());
        }
        movieAPISuccess = true;
        postHomeScreenData();
    }

    private void onTvStoreResponseRetrieved(TVShowStoreResponse response) {
        for (TvShowStoreResults tvShowStoreResults : response.getResults()) {
            tvBannerUrls.add(tvShowStoreResults.getBackdrop_path());
        }
        tvAPISuccess = true;
        postHomeScreenData();
    }

    private void onHomeScreenAPIError(Throwable errorType) {
        if (errorType instanceof UserAPIErrorException) {
            homeScreenView.homeScreenError(((UserAPIErrorException) errorType).getApiErrorType());
        } else {
            homeScreenView.homeScreenError(UserAPIErrorType.UNEXPECTED_ERROR);
        }
        cancelPendingServiceRequests();
    }
}
