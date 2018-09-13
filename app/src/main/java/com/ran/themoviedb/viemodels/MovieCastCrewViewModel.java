package com.ran.themoviedb.viemodels;

import android.arch.lifecycle.MutableLiveData;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.CastCrewDetailResponse;
import com.ran.themoviedb.model.server.service.MovieCastCrewServiceImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * View Model for MovieCastCrew Fragment
 *
 * @author ranjithsuda
 */

public class MovieCastCrewViewModel extends BaseViewModel<Integer> {

    private MutableLiveData<Boolean> progressBar;
    private MutableLiveData<CastCrewDetailResponse> castCrewResponse;
    private MutableLiveData<UserAPIErrorType> castCrewError;

    public MovieCastCrewViewModel() {
        super();
        initialiseViewModel();
    }

    @Override
    public void initialiseViewModel() {
        this.progressBar = new MutableLiveData<>();
        this.castCrewResponse = new MutableLiveData<>();
        this.castCrewError = new MutableLiveData<>();
    }

    @Override
    public void startExecution(Integer movieId) {
        progressBar.setValue(true);
        MovieCastCrewServiceImpl service = new MovieCastCrewServiceImpl(movieId,
                TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData());
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onCastCrewResponse, this::onCastCrewError));

    }

    private void onCastCrewResponse(CastCrewDetailResponse response) {
        castCrewResponse.setValue(response);
        progressBar.setValue(false);
    }

    private void onCastCrewError(Throwable errorType) {
        progressBar.setValue(false);
        if (errorType instanceof UserAPIErrorException) {
            castCrewError.setValue(((UserAPIErrorException) errorType).getApiErrorType());
        } else {
            castCrewError.setValue(UserAPIErrorType.UNEXPECTED_ERROR);
        }
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return progressBar;
    }

    public MutableLiveData<CastCrewDetailResponse> getCastCrewResponse() {
        return castCrewResponse;
    }

    public MutableLiveData<UserAPIErrorType> getCastCrewError() {
        return castCrewError;
    }
}
