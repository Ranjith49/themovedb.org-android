package com.ran.themoviedb.viemodels;

import android.arch.lifecycle.MutableLiveData;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.PeopleDetailResponse;
import com.ran.themoviedb.model.server.service.PeopleDetailServiceImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjithsuda on 13/09/18.
 */

public class PeopleDetailViewModel extends BaseViewModel<Integer> {

    private MutableLiveData<Boolean> progressBar;
    private MutableLiveData<UserAPIErrorType> peopleDetailError;
    private MutableLiveData<PeopleDetailResponse> peopleDetailResponse;

    public PeopleDetailViewModel() {
        super();
        initialiseViewModel();
    }

    @Override
    public void initialiseViewModel() {
        this.progressBar = new MutableLiveData<>();
        this.peopleDetailResponse = new MutableLiveData<>();
        this.peopleDetailError = new MutableLiveData<>();
    }

    @Override
    public void startExecution(Integer peopleId) {
        progressBar.setValue(true);
        String peopleLang = TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData();
        PeopleDetailServiceImpl service = new PeopleDetailServiceImpl(peopleId, peopleLang);
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onPeopleDetailResponse, this::onPeopleDetailError));
    }

    private void onPeopleDetailResponse(PeopleDetailResponse response) {
        progressBar.setValue(false);
        peopleDetailResponse.setValue(response);
    }

    private void onPeopleDetailError(Throwable error) {
        progressBar.setValue(false);
        if (error instanceof UserAPIErrorException) {
            peopleDetailError.setValue(((UserAPIErrorException) error).getApiErrorType());
        } else {
            peopleDetailError.setValue(UserAPIErrorType.UNEXPECTED_ERROR);
        }
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return progressBar;
    }

    public MutableLiveData<UserAPIErrorType> getPeopleDetailError() {
        return peopleDetailError;
    }

    public MutableLiveData<PeopleDetailResponse> getPeopleDetailResponse() {
        return peopleDetailResponse;
    }
}
