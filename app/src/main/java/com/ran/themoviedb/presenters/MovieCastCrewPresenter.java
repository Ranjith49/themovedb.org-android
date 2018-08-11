package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.CastCrewDetailResponse;
import com.ran.themoviedb.model.server.service.MovieCastCrewServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieCastCrewView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class MovieCastCrewPresenter extends BasePresenter {

    private MovieCastCrewView movieCastCrewView;
    private final MovieCastCrewServiceImpl service;

    public MovieCastCrewPresenter(Context context, MovieCastCrewView movieCastCrewView, int movieId) {
        super();
        this.movieCastCrewView = movieCastCrewView;
        service = new MovieCastCrewServiceImpl(movieId,
                AppSharedPreferences.getInstance(context).getAppLanguageData());
    }

    @Override
    public void start() {
        movieCastCrewView.showProgressBar(true);
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onCastCrewResponse, this::onCastCrewError));
    }

    @Override
    public void stop() {
        cancelReq();
        movieCastCrewView.showProgressBar(false);
    }

    private void onCastCrewResponse(CastCrewDetailResponse response) {
        movieCastCrewView.movieCastCrewData(response);
        movieCastCrewView.showProgressBar(false);
    }


    private void onCastCrewError(Throwable errorType) {
        movieCastCrewView.showProgressBar(false);
        if (errorType instanceof UserAPIErrorException) {
            movieCastCrewView.movieCastCrewAPIError(((UserAPIErrorException) errorType).getApiErrorType());
        } else {
            movieCastCrewView.movieCastCrewAPIError(UserAPIErrorType.UNEXPECTED_ERROR);
        }
    }
}
