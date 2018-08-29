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
import com.ran.themoviedb.model.server.service.MovieStoreServiceImpl;
import com.ran.themoviedb.model.server.service.PeopleStoreServiceImpl;
import com.ran.themoviedb.model.server.service.TVShowStoreServiceImpl;
import com.ran.themoviedb.view_pres_med.HomeScreenView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/1/2016.
 * <p>
 * Home Screen Presenter ,for handling Movies and TV of type {@link com.ran.themoviedb
 * .customviews.HomeBannerView} and People of type {@link com.ran.themoviedb.customviews.HomePosterView}
 */
public class HomeScreenDataPresenter extends BasePresenter {

    private final int PAGE_INDEX = 1;
    private HomeScreenView homeScreenView;
    private MovieStoreServiceImpl movieService;
    private TVShowStoreServiceImpl tvService;
    private PeopleStoreServiceImpl peopleService;
    private ArrayList<String> movieBannerUrls;
    private ArrayList<String> tvBannerUrls;
    private ArrayList<String> peoplePosterUrls;


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

        disposable.add(Observable.zip(movieService.requestData(), tvService.requestData(), peopleService.requestData(),
                (movieStoreResponse, tvShowStoreResponse, peopleStoreResponse) -> {
                    for (MovieStoreResults movieStoreResults : movieStoreResponse.getResults()) {
                        movieBannerUrls.add(movieStoreResults.getBackdrop_path());
                    }

                    for (TvShowStoreResults tvShowStoreResults : tvShowStoreResponse.getResults()) {
                        tvBannerUrls.add(tvShowStoreResults.getBackdrop_path());
                    }

                    for (PeopleStoreResults peopleStoreResults : peopleStoreResponse.getResults()) {
                        peoplePosterUrls.add(peopleStoreResults.getProfile_path());
                    }

                    return true;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::postHomeScreenData, this::onHomeScreenAPIError));
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
    }

    /**
     * Utility to make sure the all Three Service APIs returned Data , send to UI
     */
    private void postHomeScreenData(Boolean state) {
        homeScreenView.showProgressBar(false);
        homeScreenView.homeScreenData(movieBannerUrls, tvBannerUrls, peoplePosterUrls);
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
