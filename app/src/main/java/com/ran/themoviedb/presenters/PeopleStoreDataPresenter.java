package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.fragments.PeopleStoreFragment;
import com.ran.themoviedb.model.server.entities.PeopleStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.PeopleStoreResponse;
import com.ran.themoviedb.model.server.service.PeopleStoreServiceImpl;
import com.ran.themoviedb.view_pres_med.PeopleStoreView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/4/2016.
 * <p>
 * People Store Presenter between View and the Service Impl
 */
public class PeopleStoreDataPresenter extends BasePresenter {

    private final PeopleStoreType peopleStoreType;
    private final PeopleStoreView peopleStoreView;
    private final int pageIndex;
    private final PeopleStoreServiceImpl serviceImpl;

    public PeopleStoreDataPresenter(Context context, PeopleStoreType peopleStoreType, int pageIndex,
                                    PeopleStoreView peopleStoreView) {
        super();
        this.peopleStoreType = peopleStoreType;
        this.peopleStoreView = peopleStoreView;
        this.pageIndex = pageIndex;
        serviceImpl = new PeopleStoreServiceImpl(peopleStoreType, pageIndex,
                AppSharedPreferences.getInstance(context).getAppLanguageData());
    }

    @Override
    public void start() {
        peopleStoreView.showProgressBar(true, pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
        disposable.add(serviceImpl.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onPeopleStoreResponseRetrieved, this::onPeopleStoreAPIError));
    }

    @Override
    public void stop() {
        cancelReq();
        peopleStoreView.showProgressBar(false, pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
    }

    private void onPeopleStoreResponseRetrieved(PeopleStoreResponse response) {
        peopleStoreView.showProgressBar(false, pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
        peopleStoreView.peopleScreenData(response, pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
    }

    private void onPeopleStoreAPIError(Throwable throwable) {
        peopleStoreView.showProgressBar(false, pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
        if (throwable instanceof UserAPIErrorException) {
            peopleStoreView.peopleScreenAPIError(((UserAPIErrorException) throwable).getApiErrorType(), pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
        } else {
            peopleStoreView.peopleScreenAPIError(UserAPIErrorType.UNEXPECTED_ERROR, pageIndex == PeopleStoreFragment.FIRST_PAGE_INDEX);
        }
    }
}
