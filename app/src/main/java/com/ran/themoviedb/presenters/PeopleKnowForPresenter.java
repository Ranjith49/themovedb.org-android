package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.PeopleDetailResponse;
import com.ran.themoviedb.model.server.response.PeopleKnownForResponse;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;
import com.ran.themoviedb.model.server.service.PeopleDetailServiceImpl;
import com.ran.themoviedb.model.server.service.PeopleKnownForServiceImpl;
import com.ran.themoviedb.model.server.service.TvShowDetailServiceImpl;
import com.ran.themoviedb.view_pres_med.PeopleDetailView;
import com.ran.themoviedb.view_pres_med.PeopleKnowForView;
import com.ran.themoviedb.view_pres_med.TvShowDetailView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class PeopleKnowForPresenter extends BasePresenter {

    private PeopleKnowForView peopleKnowForView;
    private String peopleLang;
    private PeopleKnownForServiceImpl service;

    public PeopleKnowForPresenter(Context context, PeopleKnowForView peopleKnowForView, int peopleId) {
        super();
        this.peopleKnowForView = peopleKnowForView;
        this.peopleLang = AppSharedPreferences.getInstance(context).getAppLanguageData();
        service = new PeopleKnownForServiceImpl(peopleId, peopleLang);
    }

    @Override
    public void start() {
        peopleKnowForView.showProgressBar(true);
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onPeopleKnownForResponse, this::onPeopleKnownForError));
    }

    @Override
    public void stop() {
        cancelReq();
        peopleKnowForView.showProgressBar(false);
    }

    private void onPeopleKnownForResponse(PeopleKnownForResponse response) {
        peopleKnowForView.showProgressBar(false);
        peopleKnowForView.onPeopleKnownForResponse(response);
    }

    private void onPeopleKnownForError(Throwable errorType) {
        peopleKnowForView.showProgressBar(false);
        if (errorType instanceof UserAPIErrorException) {
            peopleKnowForView.onPeopleKnownForError(((UserAPIErrorException) errorType).getApiErrorType());
        } else {
            peopleKnowForView.onPeopleKnownForError(UserAPIErrorType.UNEXPECTED_ERROR);
        }

    }
}
