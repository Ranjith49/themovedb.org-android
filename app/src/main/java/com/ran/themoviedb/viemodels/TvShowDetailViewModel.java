package com.ran.themoviedb.viemodels;

import android.arch.lifecycle.MutableLiveData;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;
import com.ran.themoviedb.model.server.service.TvShowDetailServiceImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjithsuda on 13/09/18.
 */

public class TvShowDetailViewModel extends BaseViewModel<Integer> {

    private MutableLiveData<Boolean> progressBar;
    private MutableLiveData<UserAPIErrorType> showDetailError;
    private MutableLiveData<TvShowDetailResponse> showDetailResponse;

    public TvShowDetailViewModel() {
        super();
        initialiseViewModel();
    }

    @Override
    public void initialiseViewModel() {
        progressBar = new MutableLiveData<>();
        showDetailError = new MutableLiveData<>();
        showDetailResponse = new MutableLiveData<>();
    }

    @Override
    public void startExecution(Integer tvShowId) {
        progressBar.setValue(true);
        String tvShowLang = TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData();
        TvShowDetailServiceImpl service = new TvShowDetailServiceImpl(tvShowId, tvShowLang);

        disposable.add(service.requestData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTvShowDetailResponse, this::onTvShowError));
    }

    private void onTvShowDetailResponse(TvShowDetailResponse response) {
        progressBar.setValue(false);
        showDetailResponse.setValue(response);
    }

    private void onTvShowError(Throwable error) {
        progressBar.setValue(false);
        if (error instanceof UserAPIErrorException) {
            showDetailError.setValue(((UserAPIErrorException) error).getApiErrorType());
        } else {
            showDetailError.setValue(UserAPIErrorType.UNEXPECTED_ERROR);
        }
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return progressBar;
    }

    public MutableLiveData<UserAPIErrorType> getShowDetailError() {
        return showDetailError;
    }

    public MutableLiveData<TvShowDetailResponse> getShowDetailResponse() {
        return showDetailResponse;
    }
}
