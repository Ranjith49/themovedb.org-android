package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.PeopleDetailResponse;
import com.ran.themoviedb.model.server.service.PeopleDetailServiceImpl;
import com.ran.themoviedb.view_pres_med.PeopleDetailView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class PeopleDetailPresenter extends BasePresenter {

    private PeopleDetailView peopleDetailView;
    private String peopleLang;
    private PeopleDetailServiceImpl service;

    public PeopleDetailPresenter(Context context, PeopleDetailView peopleDetailView, int peopleId) {
        super();
        this.peopleDetailView = peopleDetailView;
        this.peopleLang = TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData();
        service = new PeopleDetailServiceImpl(peopleId, peopleLang);
    }

    @Override
    public void start() {
        peopleDetailView.showProgressBar(true);
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onPeopleDetailResponse, this::onPeopleDetailError));
    }

    @Override
    public void stop() {
        cancelReq();
        peopleDetailView.showProgressBar(false);
    }

    private void onPeopleDetailResponse(PeopleDetailResponse response) {
        peopleDetailView.showProgressBar(false);
        peopleDetailView.onPeopleDetailResponse(response);
    }


    private void onPeopleDetailError(Throwable error) {
        peopleDetailView.showProgressBar(false);
        if (error instanceof UserAPIErrorException) {
            peopleDetailView.onPeopleDetailError(((UserAPIErrorException) error).getApiErrorType());
        } else {
            peopleDetailView.onPeopleDetailError(UserAPIErrorType.UNEXPECTED_ERROR);
        }

    }
}
