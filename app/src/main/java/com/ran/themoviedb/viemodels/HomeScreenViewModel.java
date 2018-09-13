package com.ran.themoviedb.viemodels;

import android.arch.lifecycle.MutableLiveData;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.server.entities.MovieStoreResults;
import com.ran.themoviedb.model.server.entities.PeopleStoreResults;
import com.ran.themoviedb.model.server.entities.TvShowStoreResults;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.service.MovieStoreServiceImpl;
import com.ran.themoviedb.model.server.service.PeopleStoreServiceImpl;
import com.ran.themoviedb.model.server.service.TVShowStoreServiceImpl;
import com.ran.themoviedb.viemodels.model.HomeScreenInputData;
import com.ran.themoviedb.viemodels.model.HomeScreenOutPutData;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * View Model for Home Screen
 * Resolves Data from Movie,TvShow and people Store API and aggregate it.
 *
 * @author ranjithsuda
 */

public class HomeScreenViewModel extends BaseViewModel<HomeScreenInputData> {

    private static final int PAGE_INDEX = 1;

    private MovieStoreServiceImpl movieService;
    private TVShowStoreServiceImpl tvService;
    private PeopleStoreServiceImpl peopleService;

    private MutableLiveData<Boolean> progressBar;
    private MutableLiveData<UserAPIErrorType> apiError;
    private MutableLiveData<HomeScreenOutPutData> apiSuccess;

    private ArrayList<String> movieBannerUrls;
    private ArrayList<String> tvBannerUrls;
    private ArrayList<String> peoplePosterUrls;

    public HomeScreenViewModel() {
        super();
        initialiseViewModel();
    }

    @Override
    public void initialiseViewModel() {
        progressBar = new MutableLiveData<>();
        apiError = new MutableLiveData<>();
        apiSuccess = new MutableLiveData<>();

        movieBannerUrls = new ArrayList<>();
        tvBannerUrls = new ArrayList<>();
        peoplePosterUrls = new ArrayList<>();
    }

    @Override
    public void startExecution(HomeScreenInputData inputData) {
        movieService = new MovieStoreServiceImpl(inputData.movieStoreType(), PAGE_INDEX,
                TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData());
        peopleService = new PeopleStoreServiceImpl(inputData.peopleStoreType(), PAGE_INDEX,
                TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData());
        tvService = new TVShowStoreServiceImpl(inputData.tvShowStoreType(), PAGE_INDEX,
                TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData());

        progressBar.setValue(true);
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

    private void postHomeScreenData(Boolean state) {
        progressBar.setValue(false);
        HomeScreenOutPutData outPutData = HomeScreenOutPutData
                .builder()
                .movieUrls(movieBannerUrls)
                .tvUrls(tvBannerUrls)
                .peopleUrls(peoplePosterUrls)
                .build();
        apiSuccess.setValue(outPutData);
    }


    private void onHomeScreenAPIError(Throwable errorType) {
        progressBar.setValue(false);
        if (errorType instanceof UserAPIErrorException) {
            apiError.setValue(((UserAPIErrorException) errorType).getApiErrorType());
        } else {
            apiError.setValue(UserAPIErrorType.UNEXPECTED_ERROR);
        }
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return progressBar;
    }

    public MutableLiveData<UserAPIErrorType> getApiError() {
        return apiError;
    }

    public MutableLiveData<HomeScreenOutPutData> getApiSuccess() {
        return apiSuccess;
    }
}
