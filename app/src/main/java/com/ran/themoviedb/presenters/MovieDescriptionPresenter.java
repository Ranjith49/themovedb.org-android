package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.MovieDetailResponse;
import com.ran.themoviedb.model.server.service.MovieDescriptionServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieDescriptionView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/9/2016.
 */
public class MovieDescriptionPresenter extends BasePresenter {

    private MovieDescriptionView movieDescriptionView;
    private String movieLang;
    private MovieDescriptionServiceImpl service;

    public MovieDescriptionPresenter(Context context, MovieDescriptionView movieDescriptionView, int movieId) {
        super();
        this.movieDescriptionView = movieDescriptionView;
        this.movieLang = TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData();

        service = new MovieDescriptionServiceImpl(movieId, movieLang);
    }

    @Override
    public void start() {
        movieDescriptionView.showProgressBar(true);
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMovieDetailResponse, this::onMovieError));
    }

    @Override
    public void stop() {
        cancelReq();
        movieDescriptionView.showProgressBar(false);
    }


    private void onMovieDetailResponse(MovieDetailResponse response) {
        movieDescriptionView.showProgressBar(false);
        movieDescriptionView.movieResponse(response);
    }

    private void onMovieError(Throwable error) {
        movieDescriptionView.showProgressBar(false);
        if (error instanceof UserAPIErrorException) {
            movieDescriptionView.movieError(((UserAPIErrorException) error).getApiErrorType());
        } else {
            movieDescriptionView.movieError(UserAPIErrorType.UNEXPECTED_ERROR);
        }

    }
}
