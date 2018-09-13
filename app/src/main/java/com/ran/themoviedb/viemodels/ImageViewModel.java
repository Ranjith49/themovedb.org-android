package com.ran.themoviedb.viemodels;

import android.arch.lifecycle.MutableLiveData;
import android.util.Pair;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.ImageDetails;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.ImageDetailResponse;
import com.ran.themoviedb.model.server.service.ImageServiceImpl;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Image View Model for Image Fragment ..
 *
 * @author ranjithsuda
 */

public class ImageViewModel extends BaseViewModel<Pair<Integer, DisplayStoreType>> {

    private MutableLiveData<Boolean> progressBar;
    private ImageServiceImpl service;
    private MutableLiveData<UserAPIErrorType> apiError;
    private MutableLiveData<Pair<ArrayList<ImageDetails>, ArrayList<ImageDetails>>> apiSuccess;

    public ImageViewModel() {
        super();
        initialiseViewModel();
    }

    @Override
    public void initialiseViewModel() {
        progressBar = new MutableLiveData<>();
        apiError = new MutableLiveData<>();
        apiSuccess = new MutableLiveData<>();
    }

    @Override
    public void startExecution(Pair<Integer, DisplayStoreType> inputPair) {
        progressBar.setValue(true);
        service = new ImageServiceImpl(inputPair.first, inputPair.second,
                TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData());
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onImageResponse, this::onImageError));
    }

    private void onImageResponse(ImageDetailResponse response) {
        progressBar.setValue(false);
        apiSuccess.setValue(new Pair<>(response.getBackdrops(), response.getPosters()));
    }

    private void onImageError(Throwable error) {
        progressBar.setValue(false);
        if (error instanceof UserAPIErrorException) {
            apiError.setValue(((UserAPIErrorException) error).getApiErrorType());
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

    public MutableLiveData<Pair<ArrayList<ImageDetails>, ArrayList<ImageDetails>>> getApiSuccess() {
        return apiSuccess;
    }
}
