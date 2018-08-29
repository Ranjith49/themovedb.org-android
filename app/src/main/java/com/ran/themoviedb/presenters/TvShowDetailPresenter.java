package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;
import com.ran.themoviedb.model.server.service.TvShowDetailServiceImpl;
import com.ran.themoviedb.view_pres_med.TvShowDetailView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class TvShowDetailPresenter extends BasePresenter {

    private TvShowDetailView tvShowDetailView;
    private String tvShowLang;
    private TvShowDetailServiceImpl service;


    public TvShowDetailPresenter(Context context, TvShowDetailView tvShowDetailView, int tvShowId) {
        super();
        this.tvShowDetailView = tvShowDetailView;
        this.tvShowLang = TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData();
        service = new TvShowDetailServiceImpl(tvShowId, tvShowLang);
    }

    @Override
    public void start() {
        tvShowDetailView.showProgressBar(true);
        disposable.add(service.requestData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTvShowDetailResponse, this::onTvShowError));
    }

    @Override
    public void stop() {
        cancelReq();
        tvShowDetailView.showProgressBar(false);
    }

    private void onTvShowDetailResponse(TvShowDetailResponse response) {
        tvShowDetailView.showProgressBar(false);
        tvShowDetailView.tvShowResponse(response);
    }

    private void onTvShowError(Throwable error) {
        tvShowDetailView.showProgressBar(false);
        if (error instanceof UserAPIErrorException) {
            tvShowDetailView.tvShowError(((UserAPIErrorException) error).getApiErrorType());
        } else {
            tvShowDetailView.tvShowError(UserAPIErrorType.UNEXPECTED_ERROR);
        }

    }
}
