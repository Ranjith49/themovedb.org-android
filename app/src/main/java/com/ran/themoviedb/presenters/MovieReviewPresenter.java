package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.fragments.MovieReviewsFragment;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.ReviewsDetailResponse;
import com.ran.themoviedb.model.server.service.MovieReviewServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieReviewView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class MovieReviewPresenter extends BasePresenter {

    private MovieReviewView movieReviewView;
    private final int pageIndex;
    private final MovieReviewServiceImpl service;

    public MovieReviewPresenter(Context context, MovieReviewView movieReviewView, int pageIndex, int movieId) {
        super();
        this.pageIndex = pageIndex;
        this.movieReviewView = movieReviewView;
        service = new MovieReviewServiceImpl(movieId,
                AppSharedPreferences.getInstance(context).getAppLanguageData(), pageIndex);
    }

    @Override
    public void start() {
        movieReviewView.showProgressBar(true, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMovieReviewResponse, this::onMovieError));
    }

    @Override
    public void stop() {
        cancelReq();
        movieReviewView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    }

    private void onMovieReviewResponse(ReviewsDetailResponse response) {
        movieReviewView.movieReviewData(response, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
        movieReviewView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    }

    private void onMovieError(Throwable error) {
        if (error instanceof UserAPIErrorException) {
            movieReviewView.movieReviewError(((UserAPIErrorException) error).getApiErrorType(), pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
        } else {
            movieReviewView.movieReviewError(UserAPIErrorType.UNEXPECTED_ERROR, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
        }

        movieReviewView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    }
}
