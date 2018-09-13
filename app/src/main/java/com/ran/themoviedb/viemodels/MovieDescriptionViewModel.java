package com.ran.themoviedb.viemodels;

import android.arch.lifecycle.MutableLiveData;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.MovieDetailResponse;
import com.ran.themoviedb.model.server.service.MovieDescriptionServiceImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjithsuda on 13/09/18.
 */

public class MovieDescriptionViewModel extends BaseViewModel<Integer> {

    private MutableLiveData<Boolean> progressBar;
    private MutableLiveData<MovieDetailResponse> detailResponse;
    private MutableLiveData<UserAPIErrorType> detailError;

    public MovieDescriptionViewModel() {
        super();
        initialiseViewModel();
    }

    @Override
    public void initialiseViewModel() {
        this.progressBar = new MutableLiveData<>();
        this.detailResponse = new MutableLiveData<>();
        this.detailError = new MutableLiveData<>();
    }

    @Override
    public void startExecution(Integer movieId) {
        progressBar.setValue(true);
        String movieLang = TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData();

        MovieDescriptionServiceImpl service = new MovieDescriptionServiceImpl(movieId, movieLang);
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMovieDetailResponse, this::onMovieError));
    }

    private void onMovieDetailResponse(MovieDetailResponse response) {
        progressBar.setValue(false);
        detailResponse.setValue(response);
    }

    private void onMovieError(Throwable error) {
        progressBar.setValue(false);
        if (error instanceof UserAPIErrorException) {
            detailError.setValue(((UserAPIErrorException) error).getApiErrorType());
        } else {
            detailError.setValue(UserAPIErrorType.UNEXPECTED_ERROR);
        }
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return progressBar;
    }

    public MutableLiveData<MovieDetailResponse> getDetailResponse() {
        return detailResponse;
    }

    public MutableLiveData<UserAPIErrorType> getDetailError() {
        return detailError;
    }
}
