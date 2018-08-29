package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.fragments.MovieReviewsFragment;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.TvShowSimilarDetailsResponse;
import com.ran.themoviedb.model.server.service.TvShowSimilarServiceImpl;
import com.ran.themoviedb.view_pres_med.TvShowSimilarView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class TvShowSimilarDataPresenter extends BasePresenter {

    private final int pageIndex;
    private final TvShowSimilarServiceImpl service;
    private TvShowSimilarView tvShowSimilarView;

    public TvShowSimilarDataPresenter(Context context, TvShowSimilarView tvShowSimilarView,
                                      int pageIndex, int movieId) {
        super();
        this.pageIndex = pageIndex;
        this.tvShowSimilarView = tvShowSimilarView;
        service = new TvShowSimilarServiceImpl(movieId,
                TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData(), pageIndex);
    }

    @Override
    public void start() {
        tvShowSimilarView.showProgressBar(true, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSimilarTvShowsResponse, this::onSimilarTVShowsError));
    }

    @Override
    public void stop() {
        cancelReq();
        tvShowSimilarView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    }

    private void onSimilarTvShowsResponse(TvShowSimilarDetailsResponse response) {
        tvShowSimilarView.similarTvShowsResponse(response,
                pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
        tvShowSimilarView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    }

    private void onSimilarTVShowsError(Throwable throwable) {
        if (throwable instanceof UserAPIErrorException) {
            tvShowSimilarView.similarTvShowsError(((UserAPIErrorException) throwable).getApiErrorType(),
                    pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
        } else {
            tvShowSimilarView.similarTvShowsError(UserAPIErrorType.UNEXPECTED_ERROR,
                    pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
        }

        tvShowSimilarView.showProgressBar(false, pageIndex == MovieReviewsFragment.FIRST_PAGE_INDEX);
    }
}
