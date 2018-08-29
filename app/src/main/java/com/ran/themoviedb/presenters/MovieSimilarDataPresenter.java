package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.fragments.MovieReviewsFragment;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.MovieSimilarDetailsResponse;
import com.ran.themoviedb.model.server.service.MovieSimilarServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieSimilarView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class MovieSimilarDataPresenter extends BasePresenter {

    private final int pageIndex;
    private final MovieSimilarServiceImpl service;
    private MovieSimilarView movieSimilarView;

    public MovieSimilarDataPresenter(Context context, MovieSimilarView movieSimilarView,
                                     int pageIndex, int movieId) {
        super();
        this.pageIndex = pageIndex;
        this.movieSimilarView = movieSimilarView;
        service = new MovieSimilarServiceImpl(movieId,
                TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData(),
                pageIndex);
    }

    @Override
    public void start() {
        movieSimilarView.showProgressBar(true, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSimilarMoviesResponse, this::onSimilarMovieError));
    }

    @Override
    public void stop() {
        cancelReq();
        movieSimilarView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    }

    private void onSimilarMoviesResponse(MovieSimilarDetailsResponse response) {
        movieSimilarView.similarMovieData(response, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
        movieSimilarView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    }

    private void onSimilarMovieError(Throwable error) {
        if (error instanceof UserAPIErrorException) {
            movieSimilarView.similarMovieError(((UserAPIErrorException) error).getApiErrorType(),
                    pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
        } else {
            movieSimilarView.similarMovieError(UserAPIErrorType.UNEXPECTED_ERROR,
                    pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
        }
        movieSimilarView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    }
}
